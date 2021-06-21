package com.robin.githubrep.services

import com.robin.githubrep.models.WrapperJSON
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryService {
    //Define functions which will map to our webservices and point URLs

    @GET(value = "repositories")
    fun getRepositoryList(@Query("q") q: String): Call<WrapperJSON>
}