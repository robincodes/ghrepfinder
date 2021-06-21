package com.robin.githubrep.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.robin.githubrep.databinding.DialogImageBinding


class ImageDialogFragment : DialogFragment() {


    private lateinit var imageURL: String
    private lateinit var binding: DialogImageBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogImageBinding.inflate(LayoutInflater.from(context))

        //Get the URL of the product, built interpolating the product 'prodid' field
        imageURL = arguments?.getString(URL_KEY).toString()
        Log.d(TAG, "Image URL: $imageURL")

        //Downloading the main image of the product from the URL
        Glide.with(binding.root)
            .load(imageURL)
            .transform(RoundedCorners(20))
            //.error(R.drawable.no_image)
            .into(binding.ivPreviewImage)

        //Create the Dialog
        val view = binding.root
        val dialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .create()

        //Listener for the ImageButton to dismiss the dialog ('x')
        binding.ibDismissDialog.setOnClickListener {
            dialog.dismiss()
        }
        return dialog
    }

    companion object {
        val TAG = ImageDialogFragment::class.java.simpleName
        const val URL_KEY = "URL_key"
    }
}
