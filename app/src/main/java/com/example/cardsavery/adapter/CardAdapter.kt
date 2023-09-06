package com.example.cardsavery.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsavery.databinding.ItemCardBinding
import com.example.cardsavery.db.CardEntity
import com.example.cardsavery.ui.UpdateCardActivity
import com.example.cardsavery.utils.Constants.BUNDLE_CARD_ID

class CardAdapter : RecyclerView.Adapter<CardAdapter.ViewHolder>(){
    private lateinit var binding: ItemCardBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemCardBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: CardEntity) {
            //InitView
            binding.apply {
                //Set text
                tvTitle.text = item.cardTitle
                tvHolder.text= item.cardHolder
                tvCardNumber.text = item.cardNumber.toString()
                tvExpiredDate.text = item.cardDate
                tvCvv.text = item.cardCVV.toString()

                root.setOnClickListener {
                    val intent=Intent(context,UpdateCardActivity::class.java)
                    intent.putExtra(BUNDLE_CARD_ID, item.cardId)
                    context.startActivity(intent)
                }

            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<CardEntity>() {
        override fun areItemsTheSame(oldItem: CardEntity, newItem: CardEntity): Boolean {
            return oldItem.cardId == newItem.cardId
        }

        override fun areContentsTheSame(oldItem: CardEntity, newItem: CardEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}