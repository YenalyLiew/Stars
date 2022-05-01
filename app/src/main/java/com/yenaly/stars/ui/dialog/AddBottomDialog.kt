package com.yenaly.stars.ui.dialog

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yenaly.stars.R
import com.yenaly.stars.logic.UniverseRepo
import com.yenaly.stars.logic.model.Universe
import com.yenaly.stars.ui.adapter.AppearanceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 22:21
 * @Description : Description...
 */
class AddBottomDialog(private val refresh: (() -> Unit)? = null) : BottomSheetDialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var etName: EditText
    private lateinit var etExpect: TextView
    private lateinit var etRemark: EditText
    private lateinit var btnCreate: Button
    private lateinit var btnDelete: Button

    private var universe: Universe? = null

    private var whichAppearance: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        universe = arguments?.getSerializable(UNI_KEY) as Universe?
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_universe, null)
        dialog.setContentView(view)
        initView(view, dialog)
        return dialog
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView(view: View, dialog: Dialog) {
        recyclerView = view.findViewById(R.id.recycler_view)
        etExpect = view.findViewById(R.id.et_expect)
        etName = view.findViewById(R.id.et_name)
        etRemark = view.findViewById(R.id.et_remark)
        btnCreate = view.findViewById(R.id.btn_create)
        btnDelete = view.findViewById(R.id.btn_delete)

        if (universe != null) {
            etName.setText(universe!!.name)
            etName.isEnabled = false
            btnDelete.visibility = View.VISIBLE
        } else {
            btnDelete.visibility = View.INVISIBLE
        }

        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = AppearanceAdapter {
            whichAppearance = it
        }

        etExpect.setOnClickListener {
            showDatePickerDialog()
        }

        btnDelete.setOnClickListener {

            MaterialAlertDialogBuilder(requireContext())
                .setView(R.layout.dialog_delete)
                .setPositiveButton("确认") { _, _ ->
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            UniverseRepo.getInstance().deleteUniverse(universe!!)
                            withContext(Dispatchers.Main) {
                                dialog.dismiss()
                            }
                        }
                    }
                }
                .setNegativeButton("取消", null)
                .show()
        }

        btnCreate.setOnClickListener {
            if (universe == null) {
                val newUni = Universe(
                    etName.text.toString(),
                    whichAppearance,
                    etExpect.text.toString(),
                    "",
                    0,
                    System.currentTimeMillis(),
                    etRemark.text.toString(),
                    0F,
                    false
                )
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        if (UniverseRepo.getInstance().findByName(newUni.name) == null) {
                            UniverseRepo.getInstance().addUniverse(newUni)
                            withContext(Dispatchers.Main) {
                                dialog.dismiss()
                                refresh?.invoke()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(activity, "存在该星球", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                universe!!.appearance = whichAppearance
                universe!!.expectLightDate = etExpect.text.toString()
                universe!!.remark = etRemark.text.toString()
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        UniverseRepo.getInstance().updateUniverse(universe!!)
                        withContext(Dispatchers.Main) {
                            dialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                etExpect.text = "$year/$month/$dayOfMonth"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    companion object {
        private const val UNI_KEY = "UNI"

        @JvmStatic
        fun newInstance(universe: Universe): AddBottomDialog {
            val abd = AddBottomDialog()
            val bundle = Bundle()
            bundle.putSerializable(UNI_KEY, universe)
            abd.arguments = bundle
            return abd
        }
    }
}