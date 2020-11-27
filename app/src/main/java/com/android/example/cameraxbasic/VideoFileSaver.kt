package com.android.example.cameraxbasic

import android.content.ContentResolver
import android.content.ContentValues
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.GuardedBy
import androidx.camera.core.VideoCapture
import androidx.camera.core.VideoCapture.VideoCaptureError

class VideoFileSaver : VideoCapture.OnVideoSavedCallback {
    val TAG = "VideoFileSaver"
    val mLock = Any()
    @GuardedBy("mLock") var mIsSaving = false

    override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
        if (outputFileResults.savedUri != null) {
            Log.d(TAG, "Saved file: " + outputFileResults.savedUri!!.path)
        }
        synchronized(mLock) { mIsSaving = false }
    }

    override fun onError(@VideoCaptureError videoCaptureError: Int, message: String,
                         cause: Throwable?) {
        Log.e(TAG, "Error: $videoCaptureError, $message")
        if (cause != null) {
            Log.e(TAG, "Error cause: " + cause.cause)
        }
        synchronized(mLock) { mIsSaving = false }
    }

    /** Return a VideoOutputFileOption which is used to save a video.  */
    fun getNewVideoOutputFileOptions(resolver: ContentResolver?): VideoCapture.OutputFileOptions {
        val videoFileName = "video_" + System.currentTimeMillis()
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
        contentValues.put(MediaStore.Video.Media.TITLE, videoFileName)
        contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, videoFileName)
        contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis())
        return VideoCapture.OutputFileOptions.Builder(resolver!!,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues).build()
    }

    fun isSaving(): Boolean {
        synchronized(mLock) { return mIsSaving }
    }

    /** Sets saving state after video startRecording  */
    fun setSaving() {
        synchronized(mLock) { mIsSaving = true }
    }
}