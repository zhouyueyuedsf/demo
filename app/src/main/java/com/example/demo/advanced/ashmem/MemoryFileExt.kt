package com.example.demo.advanced.ashmem

import android.os.MemoryFile
import android.os.ParcelFileDescriptor
import java.io.FileDescriptor
import java.io.IOException
import android.os.Build
import com.example.demo.utils.InvokeUtil
import java.nio.ByteBuffer


/**
 * create by yueyue_projects on 2019/11/23
 */


fun MemoryFile.getParcelFileDescriptor(): ParcelFileDescriptor {
    val method = MemoryFile::class.java.getDeclaredMethod("getFileDescriptor")
    val fd = method.invoke(this) as FileDescriptor
    return ParcelFileDescriptor.dup(fd)
}

object MemoryFileHelper

private val PROT_READ = 0x1
private val PROT_WRITE = 0x2
val OPEN_READONLY = PROT_READ
val OPEN_READWRITE = PROT_READ or PROT_WRITE

fun MemoryFileHelper.create(name: String, size: Int): MemoryFile? {
    try {
        return MemoryFile(name, size)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}

fun MemoryFileHelper.openMemoryFile(pfd: ParcelFileDescriptor, size: Int, mode: Int): MemoryFile?  {
    requireNotNull(pfd) { "ParcelFileDescriptor is null" }
    val fd = pfd.fileDescriptor
    return openMemoryFile(fd, size, mode)
}

fun openMemoryFile(fd: FileDescriptor, size: Int, mode: Int): MemoryFile?  {
    require(!(mode != OPEN_READONLY && mode != OPEN_READWRITE)) { "invalid mode, only support OPEN_READONLY and OPEN_READWRITE" }
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
        return openMemoryFileV26(fd, size, mode)
    }

    return openMemoryFileV27(fd, mode)
}

fun openMemoryFileV26(fd: FileDescriptor, length: Int, mode: Int): MemoryFile? {
    var memoryFile: MemoryFile? = null
    try {
        memoryFile = MemoryFile("service.remote", 1)
        memoryFile.close()
        val c = MemoryFile::class.java
        InvokeUtil.setValueOfField(memoryFile, "mFD", fd)
        InvokeUtil.setValueOfField(memoryFile, "mLength", length)
        val address = InvokeUtil.invokeStaticMethod(c, "native_mmap", fd, length, mode) as Long
        InvokeUtil.setValueOfField(memoryFile, "mAddress", address)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return memoryFile
}
private fun openMemoryFileV27(fd: FileDescriptor, mode: Int): MemoryFile {
    var memoryFile: MemoryFile? = null
    try {
        memoryFile = MemoryFile("service.remote", 1)
        memoryFile.close()
        val c = Class.forName("android.os.SharedMemory")
        val sharedMemory = InvokeUtil.newInstanceOrThrow(c, fd)
        //SharedMemory sm;

        var mapping: ByteBuffer? = null
        mapping = if (mode == OPEN_READONLY) {
            InvokeUtil.invokeMethod(sharedMemory, "mapReadOnly") as ByteBuffer
        } else {
            InvokeUtil.invokeMethod(sharedMemory, "mapReadWrite") as ByteBuffer
        }

        InvokeUtil.setValueOfField(memoryFile, "mSharedMemory", sharedMemory)
        InvokeUtil.setValueOfField(memoryFile, "mMapping", mapping)
        return memoryFile
    } catch (e: Exception) {
        throw RuntimeException("openMemoryFile failed!", e)
    }

}

