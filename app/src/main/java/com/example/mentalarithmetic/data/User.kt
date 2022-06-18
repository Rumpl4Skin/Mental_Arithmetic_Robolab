package com.example.mentalarithmetic.data

import com.example.mentalarithmetic.R
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val id: String="0",
                var name: String= "Гость",
                var mail:String,
                var password:String,
                val status:String="STUD",
                var raiting:Int=0,
                var groupName:String="-"
                 )
{
    constructor() : this("0", "", "","","",0,"") {}
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "mail" to mail,
            "password" to password,
            "status" to status,
            "rating" to raiting,
            "groupName" to groupName
        )
    }
}
