package com.yenaly.stars.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yenaly.stars.R
import com.yenaly.stars.logic.model.Universe
import com.yenaly.stars.ui.adapter.SelectAdapter
import com.yenaly.yenaly_libs.utils.fromJson
import com.yenaly.yenaly_libs.utils.span.SpannedTextGenerator
import com.yenaly.yenaly_libs.utils.toJson

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/05/01 001 14:34
 * @Description : Description...
 */
class SelectBottomDialog(private val changes: (Int) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var uniName: TextView
    private lateinit var uniLightTime: TextView
    private lateinit var uniFocusTime: TextView
    private lateinit var change: Button

    private lateinit var uniList: List<Universe>
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        uniList = requireArguments().getString(UNI_KEY)!!.fromJson()
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_select_universe, null)
        dialog.setContentView(view)
        initView(view, dialog)
        return dialog
    }

    private fun initView(view: View, dialog: Dialog) {
        uniFocusTime = view.findViewById(R.id.uni_focus_time)
        uniLightTime = view.findViewById(R.id.uni_expect)
        uniName = view.findViewById(R.id.uni_name)
        recyclerView = view.findViewById(R.id.recycler_view)
        change = view.findViewById(R.id.change)

        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = SelectAdapter(this, uniList) {
            position = it

            SpannedTextGenerator.KotlinBuilder()
                .addText(getString(R.string.name), isBold = true, isNewLine = false)
                .addText(" ", isNewLine = false)
                .addText(uniList[it].name, isNewLine = false)
                .showIn(uniName)
            SpannedTextGenerator.KotlinBuilder()
                .addText(getString(R.string.focus_time), isBold = true, isNewLine = false)
                .addText(" ", isNewLine = false)
                .addText(String.format("%.2f", uniList[it].focusTime), isNewLine = false)
                .addText(getString(R.string.minute), isNewLine = false)
                .showIn(uniFocusTime)
            SpannedTextGenerator.KotlinBuilder()
                .addText(getString(R.string.expect_light_time), isBold = true, isNewLine = false)
                .addText(" ", isNewLine = false)
                .addText(uniList[it].expectLightDate, isNewLine = false)
                .showIn(uniLightTime)

            change.setOnClickListener {
                changes.invoke(position)
                dialog.dismiss()
            }
        }
    }

    companion object {
        private const val UNI_KEY = "UNI"

        @JvmStatic
        fun newInstance(universes: List<Universe>, changes: (Int) -> Unit): SelectBottomDialog {
            val sbd = SelectBottomDialog(changes)
            val bundle = Bundle()
            bundle.putString(UNI_KEY, universes.toJson())
            sbd.arguments = bundle
            return sbd
        }
    }
}