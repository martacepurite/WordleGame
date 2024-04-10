package com.example.gamethree

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

// funkcijas datubāzes vaicājumiem
// kods veidots pēc oficiālās dokumentācijas koda piemēriem
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    suspend fun loadAllByIds(userIds: Array<String>): List<User>

    @Query("SELECT * FROM user WHERE uid = :uid")
    suspend fun loadUserById(uid: String): User

    @Update
    suspend fun updateUsers(vararg users: User)

    @Query("UPDATE user SET points=:points WHERE uid = :uid")
    suspend fun updatePoints(uid: String,points: Int)

    @Query("SELECT points FROM user WHERE uid = :uid")
    suspend fun getPoints(uid: String): Int


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg users: User)

    @Delete
    suspend fun delete(user: User)
}
