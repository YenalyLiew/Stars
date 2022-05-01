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
class AddBottomDialog(private val refresh: () -> Unit) : BottomSheetDialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var etName: EditText
    private lateinit var etExpect: TextView
    private lateinit var etRemark: EditText
    private lateinit var btnCreate: Button

    private var whichAppearance: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
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

        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = AppearanceAdapter {
            whichAppearance = it
        }

        etExpect.setOnClickListener {
            showDatePickerDialog()
        }

        btnCreate.setOnClickListener {
            val newUni = Universe(
                etName.text.toString(),
                whichAppearance,
                etExpect.text.toString(),
                "",
                0,
                System.currentTimeMillis(),
                etRemark.text.toString(),
                0,
                false
            )
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    if (UniverseRepo.getInstance().findByName(newUni.name) == null) {
                        UniverseRepo.getInstance().addUniverse(newUni)
                        withContext(Dispatchers.Main) {
                            dialog.hide()
                            refresh.invoke()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(activity, "存在该星球", Toast.LENGTH_SHORT).show()
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
}