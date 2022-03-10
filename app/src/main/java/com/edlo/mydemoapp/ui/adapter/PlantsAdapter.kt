package com.edlo.mydemoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edlo.mydemoapp.databinding.ItemPlantBinding
import com.edlo.mydemoapp.repository.data.PlantData
import com.edlo.mydemoapp.util.glideLoadUrl

class PlantsAdapter: EmptyViewSupportAdapter<PlantsAdapter.PlantViewHolder>() {

    var data = ArrayList<PlantData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClick: ((Int, PlantData) -> Unit)? = null

    override fun getItemCount(): Int { return data.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return  PlantViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
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

    class PlantViewHolder(private val binding: ItemPlantBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): PlantViewHolder {
                var binding = ItemPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PlantViewHolder(binding)
            }
        }

        private lateinit var item: PlantData

        fun bind(item: PlantData) {
            this.item = item
            binding.txtName.text = item.nameCh
            binding.txtBrief.text = item.brief

            glideLoadUrl(item.pic01URL, binding.imgPic)
        }

    }
}