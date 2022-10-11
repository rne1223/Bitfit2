package com.rne1223.bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM event_table")
    fun getAll(): Flow<List<Event>>

    @Insert
    fun insertAll(events: List<Event>)

    @Insert
    fun insertEvent(event: Event)

    @Query("DELETE FROM event_table")
    fun deleteAll()
}