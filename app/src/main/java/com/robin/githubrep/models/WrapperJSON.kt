package com.robin.githubrep.models

data class WrapperJSON(
    //Single JSON Object that wraps the array of JSON repository objects.

    var total_count: Int = 0,
    var incomplete_results: Boolean? = false,
    var items: Array<Repository>,
)
