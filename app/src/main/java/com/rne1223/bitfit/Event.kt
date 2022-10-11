package com.rne1223.bitfit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="event_table")
data class Event (
    @ColumnInfo(name ="foodName") val foodName: String,
    @ColumnInfo(name ="calories") val calories: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)