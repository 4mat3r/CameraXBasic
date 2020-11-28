package com.android.example.cameraxbasic.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.cameraxbasic.models.MediaObject
import com.android.example.cameraxbasic.models.reposatories.MediaRepo

class MediaViewModel: ViewModel() {
    private val mediaData: MutableLiveData<MutableList<MediaObject>> = MediaRepo().getMediaData()
    fun getMedia(): MutableLiveData<MutableList<MediaObject>>{
        return mediaData
    }
}