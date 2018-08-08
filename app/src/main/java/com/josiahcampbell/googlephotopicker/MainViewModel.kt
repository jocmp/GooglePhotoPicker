package com.josiahcampbell.googlephotopicker

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.josiahcampbell.googlephotopicker.MainViewActions.SELECT_PHOTO
import com.josiahcampbell.googlephotopicker.MainViewActions.SHARE_PHOTO

class MainViewModel : ViewModel() {
    val openPhotosAction = Action<MainViewActions>()
    val sharePhotoAction = Action<MainViewActions>()
    val imageUrl = MutableLiveData<String>()
    val savedPhotoUrl = MutableLiveData<String>()

    fun openPhotos() {
        openPhotosAction.sendAction(SELECT_PHOTO)
    }

    fun sharePhoto() {
        if (savedPhotoUrl.value.isPresent()) {
            sharePhotoAction.sendAction(SHARE_PHOTO)
        }
    }

    fun updateSelectedPhoto(imageUrl: String?) {
        if (imageUrl.isPresent()) {
            this.imageUrl.value = imageUrl
            savedPhotoUrl.value = null
        }
    }
}

enum class MainViewActions {
    SELECT_PHOTO,
    SHARE_PHOTO
}

class Action<T> : MutableLiveData<T>() {
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (it != null) {
                observer.onChanged(it)
                value = null
            }
        })
    }

    @MainThread
    fun sendAction(action: T) {
        value = action
    }
}