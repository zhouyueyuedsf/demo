package com.example.demo.binder

import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel

interface IFocusScreen : IInterface {

    fun goToOne()

    abstract class Stub : Binder(), IFocusScreen {
        companion object {
            const val TAG = "IFocusScreen_Stub"
            const val DESCRIPTOR = "com.example.demo.binder.IFocusScreen"
            fun asInterface(binder: Binder): IFocusScreen {
                return Proxy(binder)
            }
        }
        init {
            attachInterface(this, DESCRIPTOR)
        }

        override fun asBinder(): IBinder {
            return this
        }

        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            return super.onTransact(code, data, reply, flags)
        }
    }

    class Proxy(val proxy: Binder) : IFocusScreen {
        override fun goToOne() {
        }


        override fun asBinder(): IBinder {
            return proxy
        }


    }
}