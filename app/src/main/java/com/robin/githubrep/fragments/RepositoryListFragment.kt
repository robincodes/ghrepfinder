package com.robin.githubrep.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.robin.githubrep.R
import com.robin.githubrep.databinding.ActivityRepositoryListBinding
import com.robin.githubrep.helpers.RepositoryAdapter
import com.robin.githubrep.models.Repository
import com.robin.githubrep.models.WrapperJSON
import com.robin.githubrep.services.RepositoryService
import com.robin.githubrep.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryListFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityRepositoryListBinding
    var productList: ArrayList<Repository> = arrayListOf()

    // Inflate the view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        binding = ActivityRepositoryListBinding.inflate(layoutInflater)

        recyclerView = binding.repositoryRecyclerView
        setHasOptionsMenu(true)

        val toolbar: Toolbar = binding.toolbarRepositoryList
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.close_app -> {
                    Log.i(TAG, "App closed from Menu")
                    activity?.finish()
                    true
                }
                else -> false
            }
        }

        val surfaceFont = ResourcesCompat.getFont(activity!!.baseContext, R.font.surface_bold)
        binding.tvAppName.typeface = surfaceFont
        binding.tvRepositoryList.typeface = surfaceFont

        //Return the View.
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        //recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = LinearLayoutManager(context)

        binding.svRepository.queryHint = "Type name and press search"

        binding.svRepository.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(qString: String): Boolean {

                if (qString.isEmpty()) {
                    productList.clear()
                    recyclerView.adapter?.notifyDataSetChanged()
                    binding.tvRepositoriesFound.text = ""
                    binding.ivRecyclerViewBackground.visibility = View.VISIBLE
                }
                return true
            }

            override fun onQueryTextSubmit(qString: String): Boolean {
                loadRepositories(qString)
                return true
            }
        })

    }


    private fun loadRepositories(criteria: String) {

        val productService: RepositoryService =
            ServiceBuilder.buildService(RepositoryService::class.java)

        val requestCall: Call<WrapperJSON> = productService.getRepositoryList(
            criteria
        )

        requestCall.enqueue(object : Callback<WrapperJSON> {

            override fun onResponse(
                call: Call<WrapperJSON>,
                response: Response<WrapperJSON>
            ) {
                when (response.code()) {
                    in 200..300 -> {
                        Log.d(TAG, "Response Successful: ${response.code()}")

                        val wrapperJSON: WrapperJSON = response.body()!!
                        productList = wrapperJSON.items.toCollection(ArrayList())
                        Log.d(TAG, "${productList.size} products have been fetched from the API")

                        //binding.tvTotalItemsValue.text = productList.size.toString()
                        if (productList.isNotEmpty()) {
                            binding.tvRepositoriesFound.text = "${productList.size} repositories found"
                            binding.ivRecyclerViewBackground.visibility = View.INVISIBLE
                            recyclerView.adapter =
                                RepositoryAdapter(
                                    productList, requireContext()
                                )
                        }else{
                            Toast.makeText(context,"No results found",Toast.LENGTH_SHORT).show()
                        }
                    }
                    400 -> Toast.makeText(
                        context,
                        "400: Bad Request",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    401 -> Toast.makeText(
                        context,
                        "401: Unauthorized",
                        Toast.LENGTH_SHORT
                    ).show()
                    403 -> Toast.makeText(context, "403: Forbidden", Toast.LENGTH_SHORT)
                        .show()
                    404 -> Toast.makeText(context, "404: Not found", Toast.LENGTH_SHORT)
                        .show()
                    408 -> Toast.makeText(
                        context,
                        "408: Request timeout",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    500 -> Toast.makeText(
                        context,
                        "500: Internal Server Error",
                        Toast.LENGTH_SHORT
                    ).show()
                    505 -> Toast.makeText(
                        context,
                        "505: Version not supported",
                        Toast.LENGTH_SHORT
                    ).show()
                    else -> Toast.makeText(
                        context,
                        "An error occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http response
            override fun onFailure(call: Call<WrapperJSON>, t: Throwable) {
                Log.d(TAG, "onFailure() called")
                when (t) {
                    is java.net.UnknownHostException -> {
                        Log.d(TAG, "Error occurred: $t")
                        Toast.makeText(
                            context,
                            "Unable to resolve host.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Log.d(TAG, "Error occurred: $t")
                        Toast.makeText(
                            context,
                            "An error occurred. Please, contact support.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }

    companion object {
        val TAG = RepositoryListFragment::class.java.simpleName
        fun newInstance() = RepositoryListFragment()
    }

}
