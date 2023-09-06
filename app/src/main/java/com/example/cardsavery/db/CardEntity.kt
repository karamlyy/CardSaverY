package com.example.cardsavery.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cardsavery.utils.Constants.CARD_TABLE

@Entity(tableName = CARD_TABLE)
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val cardId :Int,
    @ColumnInfo(name = "card_title")
    val cardTitle:String,
    @ColumnInfo(name = "card_holder")
    val cardHolder : String,
    @ColumnInfo(name = "card_number")
    val cardNumber : String,
    @ColumnInfo(name = "card_date")
    val cardDate : String,
    @ColumnInfo(name = "card_cvv")
    val cardCVV : String
)

