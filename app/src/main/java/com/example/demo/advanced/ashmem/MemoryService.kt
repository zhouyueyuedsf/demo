package com.example.demo.advanced.ashmem

import android.os.MemoryFile
import android.util.Log
import java.io.IOException
import android.os.ParcelFileDescriptor



/**
 * create by yueyue_project on 2019/11/23
 */

class MemoryService : IMemoryService.Stub() {
    var file: MemoryFile? = null
    override fun getFileDescriptor(): ParcelFileDescriptor? {
        Log.i(LOG_TAG, "Get File Descriptor.")

        var pfd: ParcelFileDescriptor? = null
        try {
            pfd = file?.getParcelFileDescriptor()
        } catch (ex: IOException) {
            Log.i(LOG_TAG, "Failed to get file descriptor.")
            ex.printStackTrace()
        }
        return pfd
    }

    companion object {
        const val LOG_TAG = "MemoryService"
    }


    init {
        file = MemoryFile("ashmem", 4)
        insertValueIntoShareMemory(0)
    }


    override fun insertValueIntoShareMemory(value: Int) {
        if (file == null) {
            return
        }
        val buffer = ByteArray(4)
        buffer[0] = (value.ushr(24) and 0xFF).toByte()
        buffer[1] = (value.ushr(16) and 0xFF).toByte()
        buffer[2] = (value.ushr(8) and 0xFF).toByte()
        buffer[3] = (value and 0xFF).toByte()

        try {
            file?.writeBytes(buffer, 0, 0, 4)
            Log.i(LOG_TAG, "Set value $value to memory file. ")
        } catch (ex: IOException) {
            Log.i(LOG_TAG, "Failed to write bytes to memory file.")
            ex.printStackTrace()
        }

    }
}
