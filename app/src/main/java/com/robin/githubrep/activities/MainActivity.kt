package com.robin.githubrep.activities

import android.util.Log
import androidx.fragment.app.Fragment
import com.robin.githubrep.R
import com.robin.githubrep.fragments.MainFragment


class MainActivity : SingleFragmentActivity() {

    override fun createFragment() = MainFragment.newInstance()

    public override fun replaceFragment(fragment: Fragment) {
        Log.d(TAG, "replaceFragment() was called with Fragment ID: ${fragment.id}")
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
}
