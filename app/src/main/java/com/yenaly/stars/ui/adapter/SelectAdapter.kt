package com.yenaly.stars.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yenaly.stars.R
import com.yenaly.stars.logic.model.Universe
import com.yenaly.stars.ui.dialog.AddBottomDialog
import com.yenaly.stars.ui.dialog.SelectBottomDialog

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/05/01 001 16:05
 * @Description : Description...
 */
class SelectAdapter(
    private val dialog: SelectBottomDialog,
    private val uniList: List<Universe>,
    private val which: (Int) -> Unit
) : RecyclerView.Adapter<SelectAdapter.ViewHolder>() {

    var firstPosition = 0
    var tempPosition = -1

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.uni_appearance_iv)
        val checkBox: CheckBox = view.findViewById(R.id.check_box)
        val uniName: TextView = view.findViewById(R.id.uni_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_appearance, parent, false)
        val viewHolder = ViewHolder(view)
        which.invoke(firstPosition)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            when (position) {
                in uniList.indices -> {
                    viewHolder.checkBox.isChecked = true
                    tempPosition = firstPosition
                    firstPosition = position
                    which.invoke(firstPosition)
                    notifyItemChanged(tempPosition)
                }
                else -> {
                    dialog.dismiss()
                    val abd = AddBottomDialog()
                    abd.show(dialog.requireActivity().supportFragmentManager, "AddDialog")
                }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == uniList.size) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_add_circle_outline_120)
            holder.uniName.setText(R.string.add_universe)
            holder.uniName.visibility = View.VISIBLE
            holder.checkBox.visibility = View.INVISIBLE
            return
        }
        holder.imageView.setImageResource(uniList[position].appearance)
        holder.uniName.visibility = View.VISIBLE
        holder.uniName.text = uniList[position].name
        holder.checkBox.isChecked = holder.bindingAdapterPosition == firstPosition
        holder.checkBox.isEnabled = false
    }

    override fun getItemCount(): Int {
        return uniList.size + 1
    }
}