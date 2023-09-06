package com.example.cardsavery.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.cardsavery.databinding.ActivityUpdateCardBinding
import com.example.cardsavery.db.CardDatabase
import com.example.cardsavery.db.CardEntity
import com.example.cardsavery.utils.Constants.BUNDLE_CARD_ID
import com.example.cardsavery.utils.Constants.CARD_DATABASE
import com.google.android.material.snackbar.Snackbar

class UpdateCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateCardBinding
    private val cardDB: CardDatabase by lazy {
        Room.databaseBuilder(this, CardDatabase::class.java, CARD_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private lateinit var cardEntity: CardEntity
    private var cardId = 0
    private var defaultTitle = ""
    private var defaultHolder = ""
    private var defaultNumber = ""
    private var defaultDate = ""
    private var defaultCVV = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            cardId = it.getInt(BUNDLE_CARD_ID)
        }

        binding.apply {
            defaultTitle = cardDB.doa().getCard(cardId).cardTitle
            defaultHolder = cardDB.doa().getCard(cardId).cardHolder
            defaultNumber = cardDB.doa().getCard(cardId).cardNumber
            defaultDate = cardDB.doa().getCard(cardId).cardDate
            defaultCVV = cardDB.doa().getCard(cardId).cardCVV

            edtTitle.setText(defaultTitle)
            edtHolder.setText(defaultHolder)
            edtNumber.setText(defaultNumber)
            edtDate.setText(defaultDate)
            edtCvv.setText(defaultCVV)

            btnDelete.setOnClickListener {
                cardEntity= CardEntity(cardId,defaultTitle,defaultHolder,defaultNumber,defaultDate,defaultCVV)
                cardDB.doa().deleteCard(cardEntity)
                finish()
            }

            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val holder = edtHolder.text.toString()
                val number = edtNumber.text.toString()
                val date = edtDate.text.toString()
                val cvv = edtCvv.text.toString()

                if (title.isNotEmpty() && holder.isNotEmpty() && number.isNotEmpty() && date.isNotEmpty() && cvv.isNotEmpty()){
                    cardEntity= CardEntity(cardId,title,holder,number,date,cvv)
                    cardDB.doa().updateCard(cardEntity)
                    finish()
                }
                else{
                    Snackbar.make(it,"Fields cannot be Empty", Snackbar.LENGTH_LONG).show()
                }
            }
        }

    }
}