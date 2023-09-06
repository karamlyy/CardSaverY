package com.example.cardsavery.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.cardsavery.adapter.CardAdapter
import com.example.cardsavery.databinding.ActivityMainBinding
import com.example.cardsavery.db.CardDatabase
import com.example.cardsavery.utils.Constants.CARD_DATABASE

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    // creating object of our database
    private val cardDB : CardDatabase by lazy {
        Room.databaseBuilder(this,CardDatabase::class.java,CARD_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private val cardAdapter by lazy { CardAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddCard.setOnClickListener {
            startActivity(Intent(this,AddCardActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        checkItem()
    }

    private fun checkItem(){
        binding.apply {
            // if the database is not empty then show the list of data
            if(cardDB.doa().getAllCards().isNotEmpty()){
                rvCardList.visibility= View.VISIBLE
                //tvCards.visibility = View.VISIBLE
                tvEmptyText.visibility=View.GONE
                cardAdapter.differ.submitList(cardDB.doa().getAllCards())
                setupRecyclerView()
            }else{
                rvCardList.visibility=View.GONE
                tvEmptyText.visibility=View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView(){
        binding.rvCardList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = cardAdapter
        }

    }
}