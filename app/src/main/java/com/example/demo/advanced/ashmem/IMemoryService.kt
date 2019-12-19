package com.example.demo.advanced.ashmem

import android.os.*
import android.os.IInterface
import android.os.IBinder
import android.R.attr.data
import android.os.ParcelFileDescriptor
import android.os.Parcel


/**
 * create by yueyue_project on 2019/11/23
 */
interface IMemoryService : IInterface {
    abstract class Stub : Binder(), IMemoryService {
        companion object {
            const val DESCRIPTOR = "com.example.demo.advanced.ashmem.IMemoryService"
            const val TRANSACTION_getFileDescriptor = IBinder.FIRST_CALL_TRANSACTION + 0
            const val TRANSACTION_setValue = IBinder.FIRST_CALL_TRANSACTION + 1
            fun asInterface(obj: IBinder?): IMemoryService? {
                if (obj == null) {
                    return null
                }

                val iin = obj.queryLocalInterface(DESCRIPTOR)
                return if (iin != null && iin is IMemoryService) {
                    iin
                } else Proxy(obj)

            }
        }

        override fun asBinder(): IBinder {
            return this
        }

        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            when (code) {
                IBinder.INTERFACE_TRANSACTION -> {
                    reply?.writeString(DESCRIPTOR)
                    return true
                }
                TRANSACTION_getFileDescriptor -> {
                    data.enforceInterface(DESCRIPTOR)

                    val result = this.getFileDescriptor()

                    reply?.writeNoException()

                    if (result != null) {
                        reply?.writeInt(1)
                        result.writeToParcel(reply, 0)
                    } else {
                        reply?.writeInt(0)
                    }

                    return true
                }
                TRANSACTION_setValue -> {
                    data.enforceInterface(DESCRIPTOR)

                    val value = data.readInt()
                    insertValueIntoShareMemory(value)

                    reply?.writeNoException()

                    return true
                }
            }

            return super.onTransact(code, data, reply, flags)
        }

        fun Stub() {
            attachInterface(this, DESCRIPTOR)
        }

        class Proxy(var mRemote: IBinder) : IMemoryService {
            fun getInterfaceDescriptor(): String {
                return DESCRIPTOR
            }

            override fun getFileDescriptor(): ParcelFileDescriptor? {
                val data = Parcel.obtain()
                val reply = Parcel.obtain()

                val result: ParcelFileDescriptor?

                result = try {
                    data.writeInterfaceToken(DESCRIPTOR)

                    mRemote.transact(TRANSACTION_getFileDescriptor, data, reply, 0)

                    reply.readException()
                    if (0 != reply.readInt()) {
                        ParcelFileDescriptor.CREATOR.createFromParcel(reply)
                    } else {
                        null
                    }
                } finally {
                    reply.recycle()
                    data.recycle()
                }

                return result
            }

            override fun insertValueIntoShareMemory(value: Int) {
                val data = Parcel.obtain()
                val reply = Parcel.obtain()

                try {
                    data.writeInterfaceToken(DESCRIPTOR)
                    data.writeInt(value)

                    mRemote.transact(TRANSACTION_setValue, data, reply, 0)

                    reply.readException()
                } finally {
                    reply.recycle()
                    data.recycle()
                }
            }

            override fun asBinder(): IBinder {
                return mRemote
            }

        }
    }

    @Throws(RemoteException::class)
    fun getFileDescriptor(): ParcelFileDescriptor?

    @Throws(RemoteException::class)
    fun insertValueIntoShareMemory(value: Int)
}