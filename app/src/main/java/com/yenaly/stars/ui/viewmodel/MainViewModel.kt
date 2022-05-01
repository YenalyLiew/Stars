package com.yenaly.stars.ui.viewmodel

import android.app.Application
import com.yenaly.stars.R
import com.yenaly.stars.logic.UniverseRepo
import com.yenaly.stars.logic.model.Universe
import com.yenaly.yenaly_libs.base.YenalyViewModel

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 14:59
 * @Description : Description...
 */
class MainViewModel(application: Application) : YenalyViewModel(application) {
    val testList = listOf(
        Universe(
            name = "Earth",
            appearance = R.drawable.ic_uni_3,
            lightDate = "2021/02/02",
            expectLightDate = "2021/05/02",
            lightTime = 1236,
            addTime = 1235,
            remark = "rwerggg",
            focusTime = 1233,
            isLight = false
        ), Universe(
            name = "Earth2",
            appearance = R.drawable.ic_uni_3,
            lightDate = "2021/03/02",
            expectLightDate = "2021/04/02",
            lightTime = 122343,
            addTime = 122343,
            remark = "werwer",
            focusTime = 123,
            isLight = true
        )
    )
    val uniList = ArrayList<Universe>()
    val uniListLiveData = UniverseRepo.getInstance().getAllUniverse()
}