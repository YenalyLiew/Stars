package com.yenaly.stars.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yenaly.stars.R
import com.yenaly.stars.logic.UniverseRepo
import com.yenaly.stars.logic.model.Universe
import com.yenaly.yenaly_libs.utils.span.SpannedTextGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 21:20
 * @Description : Description...
 */
class NLInfoBottomDialog(private val refresh: (() -> Unit)? = null) : BottomSheetDialogFragment() {

    private lateinit var uniAppearance: ImageView
    private lateinit var uniName: TextView
    private lateinit var uniLightTime: TextView
    private lateinit var uniFocusTime: TextView
    private lateinit var uniRemark: TextView
    private lateinit var modifyInfo: Button
    private lateinit var light: Button

    private lateinit var universe: Universe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_nl_universe_info, null)
        dialog.setContentView(view)
        universe = arguments?.get(UNI_KEY) as Universe
        initView(view, dialog)
        return dialog
    }

    private fun initView(view: View, dialog: Dialog) {
        uniAppearance = view.findViewById(R.id.uni_appearance)
        uniFocusTime = view.findViewById(R.id.uni_focus_time)
        uniLightTime = view.findViewById(R.id.uni_light_time)
        uniName = view.findViewById(R.id.uni_name)
        uniRemark = view.findViewById(R.id.uni_remark)
        modifyInfo = view.findViewById(R.id.modify_info)
        light = view.findViewById(R.id.light)

        uniAppearance.setImageResource(universe.appearance)

        SpannedTextGenerator.KotlinBuilder()
            .addText(getString(R.string.focus_time), isBold = true, isNewLine = false)
            .addText(" ", isNewLine = false)
            .addText(String.format("%.2f", universe.focusTime), isNewLine = false)
            .addText(getString(R.string.minute), isNewLine = false)
            .showIn(uniFocusTime)

        SpannedTextGenerator.KotlinBuilder()
            .addText(getString(R.string.expect_light_time), isBold = true, isNewLine = false)
            .addText(" ", isNewLine = false)
            .addText(universe.expectLightDate, isNewLine = false)
            .showIn(uniLightTime)

        uniName.text = universe.name

        SpannedTextGenerator.KotlinBuilder()
            .addText(getString(R.string.remark), isBold = true, isNewLine = false)
            .addText(" ", isNewLine = false)
            .addText(universe.remark, isNewLine = false)
            .showIn(uniRemark)

        modifyInfo.setOnClickListener {
            dialog.dismiss()
            val modifyDialog = AddBottomDialog.newInstance(universe)
            modifyDialog.show(requireActivity().supportFragmentManager, "AddDialog")
        }

        light.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("提示")
                .setMessage("星球即将被点亮，做好准备了吗？")
                .setPositiveButton("确认") { _, _ ->
                    lifecycleScope.launch {
                        universe.isLight = true
                        val c = Calendar.getInstance()
                        universe.lightDate =
                            "${c.get(Calendar.YEAR)}/${c.get(Calendar.MONTH)}/${c.get(Calendar.DAY_OF_MONTH)}"
                        withContext(Dispatchers.IO) {
                            UniverseRepo.getInstance().updateUniverse(universe)
                            withContext(Dispatchers.Main) {
                                refresh?.invoke()
                                dialog.dismiss()
                                Toast.makeText(activity, "恭喜你已经成功点亮一颗星星", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                .setNegativeButton("取消", null)
                .show()
        }
    }

    companion object {
        private const val UNI_KEY = "UNI"

        @JvmStatic
        fun getInstance(
            universe: Universe,
            refresh: (() -> Unit)? = null
        ): NLInfoBottomDialog {
            val infoBottomDialog = NLInfoBottomDialog(refresh)
            val bundle = Bundle()
            bundle.putSerializable(UNI_KEY, universe)
            infoBottomDialog.arguments = bundle
            return infoBottomDialog
        }
    }
}