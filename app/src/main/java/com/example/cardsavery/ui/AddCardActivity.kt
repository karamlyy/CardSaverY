package com.example.cardsavery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.room.Room
import com.example.cardsavery.R
import com.example.cardsavery.databinding.ActivityAddCardBinding
import com.example.cardsavery.db.CardDatabase
import com.example.cardsavery.db.CardEntity
import com.example.cardsavery.utils.Constants.CARD_DATABASE
import com.google.android.material.snackbar.Snackbar

class AddCardActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddCardBinding
    private val cardDB: CardDatabase by lazy {
        Room.databaseBuilder(this, CardDatabase::class.java, CARD_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                if (title.isNotEmpty() && holder.isNotEmpty() && number.isNotEmpty() && date.isNotEmpty() && cvv.isNotEmpty()) {
                    val formattedNumber = formatCardNumber(number)
                    val cardEntity = CardEntity(0, title, holder, formattedNumber, date, cvv, selectedCardType)
                    cardDB.doa().insertCard(cardEntity)
                    finish()
                } else {
                    Snackbar.make(it, "Fields cannot be Empty", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun formatCardNumber(inputNumber: String): String {
        val cleanedNumber = inputNumber.replace("[^\\d]".toRegex(), "")

        if (cleanedNumber.length != 16) {
            return "Invalid input"
        }

        val formattedNumber = StringBuilder()
        for (i in 0 until 16 step 4) {
            formattedNumber.append(cleanedNumber.substring(i, i + 4))
            if (i < 12) {
                formattedNumber.append(" ")
            }
        }

        return formattedNumber.toString()
    }
}
