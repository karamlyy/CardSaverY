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
    private var defaultDesc = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            cardId = it.getInt(BUNDLE_CARD_ID)
        }

        binding.apply {
            defaultTitle=cardDB.doa().getCard(cardId).cardTitle
            defaultDesc=cardDB.doa().getCard(cardId).cardDesc

            edtTitle.setText(defaultTitle)
            edtDesc.setText(defaultDesc)

            btnDelete.setOnClickListener {
                cardEntity= CardEntity(cardId,defaultTitle,defaultDesc)
                cardDB.doa().deleteCard(cardEntity)
                finish()
            }

            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc=edtDesc.text.toString()
                if (title.isNotEmpty() || desc.isNotEmpty()){
                    cardEntity= CardEntity(cardId,title,desc)
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