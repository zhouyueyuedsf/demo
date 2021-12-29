package com.example.demo.media

import android.content.ContentResolver
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.opengl.GLES11Ext
import android.opengl.GLES20
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.TextureView
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_media.*

class MediaActivity : AppCompatActivity(), TextureView.SurfaceTextureListener {

    companion object {
        const val TAG = "MediaActivity"
    }

    private val mediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        initMedia()
    }

    private fun initMedia() {
        val uri = Uri.parse(ContentResolver.SCHEME_FILE + "://" + "/sdcard/test3.mkv")
        mediaPlayer.setDataSource(this, uri)
        textureView.surfaceTextureListener = this
        buttonPause.setOnClickListener {
            mediaPlayer.pause()
        }
        buttonPlay.setOnClickListener {
//            mediaPlayer.setDisplay(surfaceView.holder)
            mediaPlayer.start()
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        mediaPlayer.prepare()
//        val textureIds = intArrayOf(0)
//        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
//        GLES20.glGenTextures(1, textureIds, 0)
//        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureIds[0])
//        val newSurfaceTexture = SurfaceTexture(textureIds[0])
        mediaPlayer.setSurface(Surface(surface))
//        surface.setOnFrameAvailableListener {
////            newSurfaceTexture.updateTexImage()
//            Log.d(TAG, "onSurfaceTextureAvailable:")
//        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
        Log.d(TAG, "onSurfaceTextureUpdated: ")
    }
}