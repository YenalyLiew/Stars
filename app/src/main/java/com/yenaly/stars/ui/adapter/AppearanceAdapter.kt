package com.yenaly.stars.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.yenaly.stars.R

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 22:31
 * @Description : Description...
 */
class AppearanceAdapter(private val which: (Int) -> Unit) :
    RecyclerView.Adapter<AppearanceAdapter.ViewHolder>() {

    private val listOfAppearance =
        listOf(R.drawable.ic_uni_1, R.drawable.ic_uni_2, R.drawable.ic_uni_3, R.drawable.ic_uni_4)

    var firstPosition = 0
    var tempPosition = -1

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val linearLayout: View = view.findViewById(R.id.uni_appearance_ll)
        val imageView: ImageView = view.findViewById(R.id.uni_appearance_iv)
        val checkBox: CheckBox = view.findViewById(R.id.check_box)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_appearance, parent, false)
        val viewHolder = ViewHolder(view)
        which.invoke(listOfAppearance[firstPosition])
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            viewHolder.checkBox.isChecked = true
            tempPosition = firstPosition
            firstPosition = position
            which.invoke(listOfAppearance[firstPosition])
            notifyItemChanged(tempPosition)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(listOfAppearance[position])
        holder.checkBox.isChecked = holder.bindingAdapterPosition == firstPosition
        holder.checkBox.isEnabled = false
    }

    override fun getItemCount(): Int {
        return listOfAppearance.size
    }
}