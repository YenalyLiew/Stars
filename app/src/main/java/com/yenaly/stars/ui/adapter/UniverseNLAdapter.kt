package com.yenaly.stars.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.yenaly.stars.R
import com.yenaly.stars.databinding.ItemUniverseBinding
import com.yenaly.stars.logic.model.Universe
import com.yenaly.stars.ui.dialog.AddBottomDialog
import com.yenaly.stars.ui.dialog.NLInfoBottomDialog

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 18:10
 * @Description : Description...
 */
class UniverseNLAdapter(
    private val fragment: Fragment,
    private val uniList: List<Universe>,
    private val refresh: (() -> Unit)? = null
) : RecyclerView.Adapter<UniverseNLAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemUniverseBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemUniverseBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_universe,
            parent,
            false
        )
        val viewHolder = ViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            when (position) {
                in uniList.indices -> {
                    val dialog = NLInfoBottomDialog.getInstance(uniList[position], refresh)
                    dialog.show(fragment.requireActivity().supportFragmentManager, "NLDialog")
                }
                else -> {
                    val dialog = AddBottomDialog {
                        refresh?.invoke()
                    }
                    dialog.show(fragment.requireActivity().supportFragmentManager, "AddDialog")
                }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == uniList.size) {
            holder.binding.uniAppearance.setImageResource(R.drawable.ic_baseline_add_circle_outline_120)
            holder.binding.uniName.setText(R.string.add_universe)
            return
        }
        holder.binding.uniAppearance.setImageResource(uniList[position].appearance)
        holder.binding.uniName.text = uniList[position].name
    }

    override fun getItemCount(): Int {
        return uniList.size + 1
    }
}