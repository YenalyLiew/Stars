package com.yenaly.stars.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yenaly.stars.R
import com.yenaly.stars.logic.model.Universe
import com.yenaly.yenaly_libs.utils.span.SpannedTextGenerator

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 20:36
 * @Description : Description...
 */
class LInfoBottomDialog : BottomSheetDialogFragment() {

    private lateinit var uniAppearance: ImageView
    private lateinit var uniName: TextView
    private lateinit var uniLightDate: TextView
    private lateinit var uniTime: TextView
    private lateinit var uniRemark: TextView

    private lateinit var universe: Universe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_l_universe_info, null)
        dialog.setContentView(view)
        universe = arguments?.get(UNI_KEY) as Universe
        initView(view, dialog)
        return dialog
    }

    private fun initView(view: View, dialog: Dialog) {
        uniAppearance = view.findViewById(R.id.uni_appearance)
        uniTime = view.findViewById(R.id.uni_time)
        uniLightDate = view.findViewById(R.id.uni_light_date)
        uniName = view.findViewById(R.id.uni_name)
        uniRemark = view.findViewById(R.id.uni_remark)

        uniAppearance.setImageResource(universe.appearance)
        uniName.text = universe.name
        uniRemark.text = universe.remark

        SpannedTextGenerator.KotlinBuilder()
            .addText(getString(R.string.light_date), isBold = true, isNewLine = false)
            .addText(" ", isNewLine = false)
            .addText(universe.lightDate, isNewLine = false)
            .showIn(uniLightDate)

        val time = (System.currentTimeMillis() - universe.addTime) / 1000 / 60
        SpannedTextGenerator.KotlinBuilder()
            .addText(getString(R.string.time), isBold = true, isNewLine = false)
            .addText(" ", isNewLine = false)
            .addText(time.toString(), isNewLine = false)
            .addText(getString(R.string.minute), isNewLine = false)
            .showIn(uniTime)
        SpannedTextGenerator.KotlinBuilder()
            .addText(getString(R.string.remark), isBold = true, isNewLine = false)
            .addText(" ", isNewLine = false)
            .addText(universe.remark, isNewLine = false)
            .showIn(uniRemark)
    }

    companion object {
        private const val UNI_KEY = "UNI"

        @JvmStatic
        fun getInstance(universe: Universe): LInfoBottomDialog {
            val infoBottomDialog = LInfoBottomDialog()
            val bundle = Bundle()
            bundle.putSerializable(UNI_KEY, universe)
            infoBottomDialog.arguments = bundle
            return infoBottomDialog
        }
    }
}