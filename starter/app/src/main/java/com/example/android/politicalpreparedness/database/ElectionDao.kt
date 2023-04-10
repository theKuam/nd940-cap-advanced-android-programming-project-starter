package com.example.android.politicalpreparedness.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    //TODO: Add insert query
    @Insert
    suspend fun insertElection(election: Election)

    @Query("select exists(select * from election_table where id = :id)")
    fun isExist(id: Int): Boolean

    @Query("select * from election_table")
    fun getAll(): List<Election>

    @Query("select * from election_table where id = :id")
    fun getElection(id: Int): List<Election>

    @Query("delete from election_table where id = :id")
    fun removeElection(id: Int)

    @Query("delete from election_table")
    fun clear()

}