package com.robin.githubrep.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.robin.githubrep.R
import com.robin.githubrep.activities.MainActivity
import com.robin.githubrep.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    // Inflate the view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        //Return the View.
        return binding.root
    }

    // Wait until the View is guaranteed to be not null to grab View elements.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "MainFragment view was created")

        val surfaceFont = ResourcesCompat.getFont(activity!!.baseContext, R.font.surface_bold)


        binding.tvRepositoryFinder.typeface = surfaceFont
        binding.tvRepositorySearching.typeface = surfaceFont

        binding.tvRepositorySearching.setOnClickListener {

            Log.d(TAG, "TextView Repository Search pressed")

            (activity as MainActivity?)!!.replaceFragment(RepositoryListFragment.newInstance())

        }
    }

    companion object {
        fun newInstance() = MainFragment()
        val TAG = MainFragment::class.java.simpleName
    }
}
