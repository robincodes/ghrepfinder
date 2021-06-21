package com.robin.githubrep.helpers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

import com.robin.githubrep.models.Repository
import com.robin.githubrep.R
import com.robin.githubrep.databinding.RepositoryHolderBinding
import com.robin.githubrep.fragments.RepositoryDetailFragmentDialog

class RepositoryAdapter(private val repositoryList: List<Repository>, private val context: Context) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

	private lateinit var binding: RepositoryHolderBinding

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		//It is important to set the attachToParent parameter to false
		binding = RepositoryHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		// create a ProgressDrawable object which we will show as placeholder
		val drawable = CircularProgressDrawable(context)
		drawable.setColorSchemeColors(
			R.color.black,
		)
		drawable.centerRadius = 50f
		drawable.strokeWidth = 5f
		drawable.start()


		holder.repository = repositoryList[position]
		holder.textViewRepositoryName.text = holder.repository!!.name
		holder.textviewRepositoryOwnerName.text = holder.repository!!.owner.login
		holder.textviewRepositoryStargazers.text = holder.repository!!.stargazers_count.toString()
		if(holder.repository?.language.isNullOrEmpty().not()) {
			holder.textviewRepositoryLanguage.text = holder.repository!!.language
		}else{
			holder.textviewRepositoryLanguage.visibility = View.INVISIBLE
			holder.imageViewRepositoryLanguage.visibility = View.INVISIBLE
		}

		Glide.with(holder.itemView)
			.load(repositoryList[position].owner.avatar_url)
			.transform(RoundedCorners(25))
			.placeholder(drawable)
			.into(binding.userAvatar)

		holder.itemView.setOnClickListener {

			val supportFragmentManager =
				(context as AppCompatActivity).supportFragmentManager

			val dialog = RepositoryDetailFragmentDialog()
			val bundle = Bundle()

			bundle.putSerializable(RepositoryDetailFragmentDialog.REP_KEY, holder.repository)
			dialog.arguments = bundle

			dialog.show(supportFragmentManager,TAG)
		}
	}

	override fun getItemCount(): Int {
		return repositoryList.size
	}

	override fun getItemViewType(position: Int): Int {
		return position
	}


	class ViewHolder(binding: RepositoryHolderBinding) : RecyclerView.ViewHolder(binding.root) {

		val textViewRepositoryName = binding.tvRepositoryName
		val textviewRepositoryOwnerName = binding.tvRepositoryOwnerName
		val textviewRepositoryStargazers = binding.tvRepositoryStargazers
		val textviewRepositoryLanguage = binding.tvRepositoryLanguage
		val imageViewRepositoryLanguage = binding.ivRepositoryLanguage
		var repository: Repository? = null

	}

	companion object{
		 val TAG = RepositoryAdapter::class.java.simpleName
	}
}
