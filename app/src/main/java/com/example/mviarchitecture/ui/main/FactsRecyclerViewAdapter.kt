package com.example.mviarchitecture.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mviarchitecture.R
import com.example.mviarchitecture.model.Fact
import kotlinx.android.synthetic.main.item_view.view.*

class FactsRecyclerViewAdapter(private val callback: OnItemClickListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Fact>() {

        override fun areItemsTheSame(oldItem: Fact, newItem: Fact): Boolean {
            return oldItem.createdAt == oldItem.createdAt
        }

        override fun areContentsTheSame(oldItem: Fact, newItem: Fact): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return FactViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view,
                parent,
                false
            ),
            callback
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FactViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Fact>) {
        differ.submitList(list)
    }

    class FactViewHolder(
        itemView: View,
        private val interaction: OnItemClickListener?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Fact) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(absoluteAdapterPosition, item)
            }
            textView.text = item.text
        }
    }

    interface OnItemClickListener {
        fun onItemSelected(position: Int, item: Fact)
    }
}