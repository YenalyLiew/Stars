package com.yenaly.stars.ui.viewmodel

import android.app.Application
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
    val uniList = ArrayList<Universe>()
    val uniListLiveData = UniverseRepo.getInstance().getAllUniverse()
}