package com.example.cardsavery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.cardsavery.R
import com.example.cardsavery.databinding.ActivityAddCardBinding
import com.example.cardsavery.db.CardDatabase
import com.example.cardsavery.db.CardEntity
import com.example.cardsavery.utils.Constants.CARD_DATABASE
import com.google.android.material.snackbar.Snackbar

class AddCardActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddCardBinding

    // make an object of database to access the methods
    private val cardDB : CardDatabase by lazy {
        Room.databaseBuilder(this,CardDatabase::class.java,CARD_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private lateinit var cardEntity: CardEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc = edtDesc.text.toString()

                /* if title and description of a card are not empty, then data can be inserted and
                card can be saved in the database */
                if (title.isNotEmpty() || desc.isNotEmpty()){
                    cardEntity= CardEntity(0,title,desc)
                    cardDB.doa().insertCard(cardEntity)
                    finish()
                }
                else{
                    Snackbar.make(it,"Title and Description cannot be Empty",Snackbar.LENGTH_LONG).show()
                }
            }
        }

    }
}