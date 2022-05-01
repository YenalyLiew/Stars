package com.yenaly.stars.logic

import androidx.lifecycle.LiveData
import com.yenaly.stars.logic.dao.UniverseDao
import com.yenaly.stars.logic.dao.UniverseDatabase
import com.yenaly.stars.logic.model.Universe

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 18:52
 * @Description : Description...
 */
class UniverseRepo(uniDb: UniverseDatabase) {

    private var uniDb: UniverseDao = uniDb.universeDao()

    companion object {
        private var sInstance: UniverseRepo? = null

        @JvmStatic
        fun getInstance(): UniverseRepo {
            if (null == sInstance) {
                synchronized(UniverseRepo::class.java) {
                    if (null == sInstance) {
                        sInstance = UniverseRepo(UniverseDatabase.getDatabase())
                    }
                }
            }
            return sInstance!!
        }
    }

    fun getAllUniverse(): LiveData<List<Universe>> {
        return uniDb.loadAllUniverse()
    }

    fun deleteUniverse(vararg universe: Universe) {
        uniDb.delete(*universe)
    }

    fun addUniverse(vararg universe: Universe) {
        uniDb.insert(*universe)
    }

    fun findByName(name: String): Universe? {
        return uniDb.findByName(name)
    }
}