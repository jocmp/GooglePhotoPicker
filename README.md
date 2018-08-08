# Google Photo Picker

A simple app to pick a photo, save to internal app storage, and share

* Once selected, tap the image to share

## Technical stuff

* Uses Kotlin, Kotlin Coroutines, androidx, data binding and MVVM pattern
* View actions are routed through the VM. More details here: [Single Actions](https://android.jlelse.eu/android-arch-handling-clicks-and-single-actions-in-your-view-model-with-livedata-ab93d54bc9dc)
* File Provider used for storage. More details here: [File Provider](https://blog.stylingandroid.com/fileprovider/)