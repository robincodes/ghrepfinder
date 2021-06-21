package com.robin.githubrep.models

import java.io.Serializable

data class Repository (
    // JSON Repository Object Model

    var id: Int,
    var name: String,
    var owner: Owner,
    var description: String,
    var forks: Int,
    var stargazers_count:Int,
    var watchers_count: Int,
    var open_issues: Int,
    var language:String,
    var readme: String
):Serializable
