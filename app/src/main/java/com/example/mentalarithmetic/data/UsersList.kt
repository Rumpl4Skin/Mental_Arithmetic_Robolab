package com.example.mentalarithmetic.data

import com.example.mentalarithmetic.R
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UsersList(
                     var GroupName: String= "",
                     var mail:String="",
                     var raiting:Int=0,
                     var status:String=""
                 )
{
    constructor() : this("0", "", 0,"") {}
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "groupName" to GroupName,
            "mail" to mail,
            "status" to status,
            "rating" to raiting
        )
    }
}
