package com.yenaly.stars.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 18:20
 * @Description : Description...
 */
@Entity(tableName = "Universe")
data class Universe(
    @PrimaryKey
    var name: String,
    var appearance: Int,
    var expectLightDate: String,
    var lightDate: String,
    var lightTime: Int,
    val addTime: Long,
    var remark: String,
    var focusTime: Int,
    var isLight: Boolean
): Serializable
