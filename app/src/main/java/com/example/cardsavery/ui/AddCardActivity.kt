package com.example.cardsavery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isNotEmpty
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

        // Inside onCreate method
        val cardTypes = resources.getStringArray(R.array.card_types)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cardTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.typeSpinner.adapter = adapter


        binding.apply {
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val holder = edtHolder.text.toString()
                val number = edtNumber.text.toString()
                val date = edtDate.text.toString()
                val cvv = edtCvv.text.toString()
                val selectedCardType = binding.typeSpinner.selectedItem.toString()

                /* if title and description of a card are not empty, then data can be inserted and
                card can be saved in the database */
                if (title.isNotEmpty() && holder.isNotEmpty() && number.isNotEmpty() && date.isNotEmpty() && cvv.isNotEmpty() ){
                    cardEntity= CardEntity(0,title,holder, number, date, cvv, selectedCardType)
                    cardDB.doa().insertCard(cardEntity)
                    finish()
                }
                else{
                    Snackbar.make(it,"Fields cannot be Empty",Snackbar.LENGTH_LONG).show()
                }
            }
        }

    }
}