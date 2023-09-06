package com.example.cardsavery.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cardsavery.utils.Constants.CARD_TABLE

@Dao
interface CardDao {

    // onConflict is used for replacing old data and continue the transaction with the new data.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(cardEntity: CardEntity)

    @Update
    fun updateCard(cardEntity: CardEntity)

    @Delete
    fun deleteCard(cardEntity: CardEntity)

    // getting all the cards and showing them to the recyclerView in the MainActivity
    @Query("SELECT * FROM $CARD_TABLE ORDER BY cardId DESC")
    fun getAllCards() : MutableList<CardEntity>

    // selecting one card at a time
    @Query("SELECT * FROM $CARD_TABLE WHERE cardId LIKE :id")
    fun getCard(id : Int) : CardEntity

}