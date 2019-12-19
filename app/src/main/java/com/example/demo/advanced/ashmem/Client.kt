package com.example.demo.advanced.ashmem

/**
 * create by yueyue_projects on 2019/11/23
 */

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import com.example.demo.R
import java.io.IOException
import kotlin.experimental.and

class Client : Activity(), OnClickListener {

    internal var memoryService: IMemoryService? = null
    internal var memoryFile: MemoryFile? = null

    private var valueText: EditText? = null
    private var readButton: Button? = null
    private var writeButton: Button? = null
    private var clearButton: Button? = null
    private var clientConnection: ServiceConnection? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main_ashmen)

        val ms = getMemoryService()
//        if (ms == null) {
//            startService(Intent(".advanced.ashmem.Server"))
//        } else {
//            Log.i(LOG_TAG, "Memory Service has started.")
//        }

        valueText = findViewById(R.id.edit_value)
        readButton = findViewById(R.id.button_read)
        writeButton = findViewById(R.id.button_write)
        clearButton = findViewById(R.id.button_clear)

        readButton!!.setOnClickListener(this)
        writeButton!!.setOnClickListener(this)
        clearButton!!.setOnClickListener(this)
        bind()
        Log.i(LOG_TAG, "Client Activity Created.")
    }

    public override fun onResume() {
        super.onResume()

        Log.i(LOG_TAG, "Client Activity Resumed.")
    }

    public override fun onPause() {
        super.onPause()

        Log.i(LOG_TAG, "Client Activity Paused.")
    }

    override fun onClick(v: View) {
        if (v == readButton) {
            var value = 0

            val mf = getMemoryFile()
            if (mf != null) {
                try {
                    val buffer = ByteArray(4)
                    mf.readBytes(buffer, 0, 0, 4)

                    value = buffer[0].toInt() shl 24 or ((buffer[1] and 0xFF.toByte()).toInt() shl 16) or ((buffer[2] and 0xFF.toByte()).toInt() shl 8) or ((buffer[3] and 0xFF.toByte()).toInt())
                } catch (ex: IOException) {
                    Log.i(LOG_TAG, "Failed to read bytes from memory file.")
                    ex.printStackTrace()
                }

            }

            val text = value.toString()
            valueText!!.setText(text)
        } else if (v == writeButton) {
            val text = valueText!!.text.toString()
            val value = Integer.parseInt(text)


            if (memoryService != null) {
                try {
                    memoryService?.insertValueIntoShareMemory(value)
                } catch (ex: RemoteException) {
                    Log.i(LOG_TAG, "Failed to set value to memory service.")
                    ex.printStackTrace()
                }

            }
        } else if (v == clearButton) {
            val text = ""
            valueText!!.setText(text)
        }
    }


    fun getClientConnection(): ServiceConnection {
        if (clientConnection == null) {
            clientConnection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName, service: IBinder) {
                    Log.d(LOG_TAG, "onServiceConnected")
                    memoryService = IMemoryService.Stub.asInterface(service)
                }

                override fun onServiceDisconnected(name: ComponentName) {
                    Log.d(LOG_TAG, "onServiceDisconnected")
                    memoryService = null
                }
            }
        }
        return clientConnection!!
    }


    private fun getMemoryService(): IMemoryService? {
        if (memoryService != null) {
            return memoryService
        }

//        memoryService = IMemoryService.Stub.asInterface(
//                ServiceManager.getService("AnonymousSharedMemory"))

        Log.i(LOG_TAG, if (memoryService != null) "Succeed to get memeory service." else "Failed to get memory service.")

        return memoryService
    }

    private fun getMemoryFile(): MemoryFile? {
        if (memoryFile != null) {
            return memoryFile
        }

        if (memoryService != null) {
            try {
                val pfd = memoryService!!.getFileDescriptor()
                if (pfd == null) {
                    Log.i(LOG_TAG, "Failed to get memory file descriptor.")
                    return null
                }

                try {
                    val fd = pfd.fileDescriptor
                    if (fd == null) {
                        Log.i(LOG_TAG, "Failed to get memeory file descriptor.")
                        return null
                    }
                    memoryFile = MemoryFileHelper.openMemoryFile(pfd, 4, OPEN_READONLY)
                } catch (ex: IOException) {
                    Log.i(LOG_TAG, "Failed to create memory file.")
                    ex.printStackTrace()
                }

            } catch (ex: RemoteException) {
                Log.i(LOG_TAG, "Failed to get file descriptor from memory service.")
                ex.printStackTrace()
            }

        }

        return memoryFile
    }
    private fun bind() {
        val intent = Intent(this, Server::class.java)
        bindService(intent, getClientConnection(), Service.BIND_AUTO_CREATE)
    }
    private fun unbind() {
        unbindService(getClientConnection())
    }

    override fun onDestroy() {
        unbind()
        super.onDestroy()
    }
    companion object {
        private val LOG_TAG = "yueyue_project.Client"
    }
}