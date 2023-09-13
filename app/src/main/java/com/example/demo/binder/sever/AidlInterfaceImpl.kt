package com.example.demo.binder.sever

import com.example.demo.binder.IMyAidlInterface

class AidlInterfaceImpl : IMyAidlInterface.Stub() {
    override fun getCount(): Int {
        return 100
    }
}