package com.edlo.mydemoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.edlo.mydemoapp.databinding.ItemPavilionBinding
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PavilionData
import com.edlo.mydemoapp.util.glideLoadUrl

class PavilionsAdapter: EmptyViewSupportAdapter<PavilionsAdapter.PavilionIntroViewHolder>() {

    var data = ArrayList<PavilionData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClick: ((Int, PavilionData) -> Unit)? = null

    override fun getItemCount(): Int { return data.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PavilionIntroViewHolder {
        return  PavilionIntroViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PavilionIntroViewHolder, position: Int) {
        var itemData = data[position]
        holder.bind(itemData)
        onClick?.let {
            holder.itemView.setOnClickListener {
             it(position, itemData)
            }
        }
    }

//    fun removeItem(adapterPosition: Int) {
//        notifyItemRemoved(adapterPosition)
//        data.removeAt(adapterPosition)
//    }

    class PavilionIntroViewHolder(private val binding: ItemPavilionBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): PavilionIntroViewHolder {
                var binding = ItemPavilionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PavilionIntroViewHolder(binding)
            }

            var factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        }

        private lateinit var item: PavilionData

        fun bind(item: PavilionData) {
            this.item = item
            binding.txtName.text = item.name

            glideLoadUrl(item.picURL, binding.imgPic)
        }

    }
}