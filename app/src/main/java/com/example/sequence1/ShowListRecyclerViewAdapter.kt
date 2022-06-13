package com.example.sequence1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ShowListRecyclerViewAdapter(
    private val listeToDo: ListeToDo,
    private val mContext: Context) : RecyclerView.Adapter<ShowListRecyclerViewAdapter.ItemViewHolder>()
{
    private var itemList: MutableList<ItemToDo> = listeToDo.getLesItems()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var checkBox: CheckBox
        val description: TextView
        val parentLayout: ConstraintLayout

        init {
            checkBox = itemView.findViewById(R.id.checkBox)
            description = itemView.findViewById(R.id.textitem)
            parentLayout = itemView.findViewById(R.id.showlist_parent_layout)
        }
        // permet de retenir si la case est cochée, le parent layout et le texte
        fun bind(item: ItemToDo) {
            checkBox.isChecked = item.getFait()
            description.text = item.getDescription()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false)

        val holder = ItemViewHolder(view)
        return holder

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        when(holder){
            is ItemViewHolder ->{
                holder.parentLayout.setOnClickListener {
                    itemList.get(position).setFait(!itemList.get(position).getFait())
                }
                holder.bind(itemList.get(position))
            }
        }


    }

    override fun getItemCount(): Int {
            return itemList.size
    }


}


