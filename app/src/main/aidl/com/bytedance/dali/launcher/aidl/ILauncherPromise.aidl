// ILauncherPromise.aidl
package com.bytedance.dali.launcher.aidl;

// Declare any non-default types here with import statements

interface ILauncherPromise {
    /**
    * IPC调用，开启负一屏
    */
    oneway void promiseToFocusScreen();
}