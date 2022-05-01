package com.yenaly.stars.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.yenaly.stars.R
import com.yenaly.stars.databinding.ItemUniverseBinding
import com.yenaly.stars.logic.model.Universe
import com.yenaly.stars.ui.dialog.LInfoBottomDialog

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 18:09
 * @Description : Description...
 */
class UniverseLAdapter(private val fragment: Fragment, private val uniList: List<Universe>) :
    RecyclerView.Adapter<UniverseLAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemUniverseBinding) : RecyclerView.ViewHolder(binding.root)

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
            val dialog = LInfoBottomDialog.getInstance(uniList[position])
            dialog.show(fragment.requireActivity().supportFragmentManager, "LDialog")
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.uniAppearance.setImageResource(uniList[position].appearance)
        holder.binding.uniName.text = uniList[position].name
    }

    override fun getItemCount(): Int {
        return uniList.size
    }
}