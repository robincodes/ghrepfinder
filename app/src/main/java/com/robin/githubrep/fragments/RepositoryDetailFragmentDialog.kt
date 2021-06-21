package com.robin.githubrep.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.robin.githubrep.databinding.FragmentDialogRepositoryDetailBinding
import com.robin.githubrep.models.Repository


class RepositoryDetailFragmentDialog : DialogFragment() {

    private lateinit var binding: FragmentDialogRepositoryDetailBinding
    private lateinit var repository: Repository

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onCreateView() has been called")


        if (arguments != null) {
            repository = arguments!!.getSerializable(REP_KEY) as Repository
        }
        binding = FragmentDialogRepositoryDetailBinding.inflate(LayoutInflater.from(context))

        //Downloading the main image of the product from the URL
        Glide.with(binding.root)
            .load(repository.owner.avatar_url)
            .transform(RoundedCorners(20))
            //.error(R.drawable.no_image)
            .into(binding.roundedPicture)


        binding.roundedPicture.setOnClickListener {
            Log.d(TAG, "Image was clicked on RoundedPicture")
            val dialog = ImageDialogFragment()
            val bundle = Bundle()
            bundle.putString(ImageDialogFragment.URL_KEY,repository.owner.avatar_url)
            dialog.arguments = bundle
            dialog.show(activity!!.supportFragmentManager,TAG)


        }
        binding.repOwnerIdValue.text = repository.owner.id.toString()
        binding.repOwnerNameValue.text = repository.owner.login
        binding.repIdValue.text = repository.id.toString()
        binding.repNameValue.text = repository.name
        binding.repDescriptionValue.text = repository.description
        binding.repForksValue.text = repository.forks.toString()
        binding.repWatchersValue.text = repository.watchers_count.toString()

        //Create the Dialog
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);


        //Listener for the ImageButton to dismiss the dialog ('x')

//        binding.ibDismissDialog.setOnClickListener {
//            dialog.dismiss()
//        }

        return dialog
    }

    companion object {
        val TAG = RepositoryDetailFragmentDialog::class.java.simpleName
        const val REP_KEY = "Repository_key"
    }
}