# Google Photo Picker

A simple demo app to pick a photo, save to internal app storage, and share

![Demo screenshot](https://github.com/jocmp/GooglePhotoPicker/blob/master/screenshots/screenshot.png)

## Technical stuff

* Uses Kotlin, Kotlin Coroutines, androidx, data binding and MVVM pattern
* View actions are routed through the VM. More details here: [Single Actions](https://android.jlelse.eu/android-arch-handling-clicks-and-single-actions-in-your-view-model-with-livedata-ab93d54bc9dc)
* File Provider used for storage. More details here: [File Provider](https://blog.stylingandroid.com/fileprovider/)
* More on Coroutine gotchas in Android, and why I made `Pools.kt` in the project: [Kotlin Coroutines on Android](https://medium.com/capital-one-developers/kotlin-coroutines-on-android-things-i-wish-i-knew-at-the-beginning-c2f0b1f16cff)
