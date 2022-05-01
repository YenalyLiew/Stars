package com.yenaly.stars.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yenaly.stars.logic.model.Universe

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 18:27
 * @Description : Description...
 */
@Dao
abstract class UniverseDao {
    @Insert
    abstract fun insert(vararg universe: Universe)

    @Delete
    abstract fun delete(vararg universe: Universe)

    @Update
    abstract fun update(vararg universe: Universe)

    @Query("SELECT * FROM universe WHERE name = (:name)")
    abstract fun findByName(name: String): Universe?

    @Query("SELECT * FROM universe ORDER BY addTime DESC")
    abstract fun loadAllUniverse(): LiveData<List<Universe>>
}