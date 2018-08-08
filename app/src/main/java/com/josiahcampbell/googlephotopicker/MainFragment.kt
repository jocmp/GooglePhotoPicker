package com.josiahcampbell.googlephotopicker

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.Intent.*
import android.net.Uri.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap.getFileExtensionFromUrl
import android.widget.ImageView
import androidx.core.content.FileProvider.*
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import backgroundPool
import com.josiahcampbell.googlephotopicker.BuildConfig.APPLICATION_ID
import com.josiahcampbell.googlephotopicker.databinding.MainFragmentBinding
import kotlinx.coroutines.experimental.launch
import java.io.File
import java.io.InputStream
import java.util.*

const val ACTION_PICK_REQUEST_CODE = 200

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        return DataBindingUtil.inflate<MainFragmentBinding>(inflater, R.layout.main_fragment, container, false).run {
            setLifecycleOwner(viewLifecycleOwner)
            viewModel = this@MainFragment.viewModel
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.openPhotosAction.observe(viewLifecycleOwner, Observer {
            selectPhotos()
        })

        viewModel.sharePhotoAction.observe(viewLifecycleOwner, Observer {
            startActivity(Intent.createChooser(Intent(ACTION_SEND).apply {
                type = "image/*"
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                putExtra(EXTRA_STREAM, parse(viewModel.savedPhotoUrl.value!!))
            }, "Share"))
        })

        viewModel.imageUrl.observe(viewLifecycleOwner, Observer {
            savePhoto(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.updateSelectedPhoto(data?.dataString)
    }

    fun selectPhotos() {
        startActivityForResult(Intent(ACTION_PICK).apply {
            type = "image/*"
        }, ACTION_PICK_REQUEST_CODE)
    }

    fun savePhoto(contentUrl: String?) {
        if (!contentUrl.isNullOrBlank()) {
            launch(backgroundPool) {
                val contentResolver = context!!.contentResolver
                val extension = contentResolver.getType(parse(contentUrl))?.split("/")?.last() ?: ""
                val savedFile = File(context!!.filesDir, "${UUID.randomUUID()}.${extension}")
                savedFile.writeFromInputStream(contentResolver.openInputStream(parse(contentUrl)))
                viewModel.savedPhotoUrl.postValue(getUriForFile(context!!, APPLICATION_ID, savedFile).toString())
            }
        }
    }
}

fun File.writeFromInputStream(inputStream: InputStream?) {
    inputStream?.use { input -> outputStream().use { output -> input.copyTo(outputStream()) } }
}

@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, imageUrl: String?) {
    GlideApp.with(imageView.context)
            .load(imageUrl)
            .placeholder(R.color.placeholder)
            .into(imageView)
}