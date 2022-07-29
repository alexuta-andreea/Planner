package com.example.planner

import io.realm.kotlin.types.RealmObject

class Note : RealmObject {
    var title: String? = null
    var description: String? = null
    var createdTime: Long = 0
}