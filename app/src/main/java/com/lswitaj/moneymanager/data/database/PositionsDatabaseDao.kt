package com.lswitaj.moneymanager.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PositionsDatabaseDao {
    //TODO(implement remove)

    @Insert
    suspend fun addPosition(position: Position)

    @Query("SELECT * from positions ORDER BY positionId ASC")
    fun getAllPositions(): LiveData<List<Position>>

    @Query("SELECT positionName from positions ORDER BY positionId ASC")
    fun getAllPositionNames(): List<String>

    @Query("SELECT * from positions ORDER BY positionId DESC LIMIT 1")
    fun getLastPosition(): Position

    @Query("UPDATE positions SET lastClosePrice = :lastClosePrice WHERE (positionName = :positionName AND lastClosePrice <> :lastClosePrice)")
    fun updatePrice(positionName: String?, lastClosePrice: Double)

    @Query("SELECT COUNT(*) AS PositionsCount FROM positions")
    fun countPositions(): Int

    @Query("DELETE FROM positions")
    suspend fun clearDatabase()
}