package com.example.dinhh.soundscape.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.View
import android.widget.*
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.entity.SoundCategory
import kotlinx.android.synthetic.main.dialog_save_record.view.*

private const val ARG_TITLE = "title"

class SaveRecordDialog : AppCompatDialogFragment() {

    private lateinit var categoryList: List<String>
    private lateinit var category: String

    private var title: String? = null
    private var listener: SaveDialogListener? = null

    private lateinit var progressBar: ProgressBar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val view = it.layoutInflater.inflate(R.layout.dialog_save_record, null)

            val negativeBtn = view?.findViewById<Button>(R.id.btnCancel)
            val positiveBtn = view?.findViewById<Button>(R.id.btnSave)

            positiveBtn?.setOnClickListener {
                listener?.onSaveDialogPositiveClick(view.recordName.text.toString(), category)
            }

            negativeBtn?.setOnClickListener {
                listener?.onSaveDialogNegativeClick(this)
            }
            setupCategorySpinner(view)
            progressBar = view.progressBar

            builder
                .setView(view)
                .setTitle(title)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SaveRecordDialog.SaveDialogListener) {
            listener = context

            categoryList = listOf(
                getString(R.string.nature),
                getString(R.string.human),
                getString(R.string.machine),
                getString(R.string.recordings)
            )

            category = categoryList[0]
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }

    private fun setupCategorySpinner(view: View) {
        val categorySpinner = view.categorySpinner
        val spinnerAdapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, categoryList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = spinnerAdapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                category = categoryList[p2]
            }
        }
    }

    fun showLoading() {
        progressBar.visible()
    }

    fun hideLoading() {
        progressBar.gone()
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String) = SaveRecordDialog().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
            }
        }
    }

    interface SaveDialogListener {

        fun onSaveDialogPositiveClick(recordName: String, category: String)

        fun onSaveDialogNegativeClick(recordDialog: SaveRecordDialog)
    }

}