package com.example.dinhh.soundscape.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.dinhh.soundscape.R
import kotlinx.android.synthetic.main.dialog_save_record.view.*

private const val ARG_TITLE = "title"

private val categoryList = listOf(
    "Nature",
    "Human",
    "Machine",
    "Story",
    "Unknown"
)

class SaveDialog: AppCompatDialogFragment() {

    private var title: String? = null
    private var listener: SaveDialogListener? = null
    private var category = categoryList[0]

   override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val view = it.layoutInflater.inflate(R.layout.dialog_save_record, null)

            val recordName = view.recordName
            setupCategorySpinner(view)

           builder
                .setView(view)
                .setTitle(title)
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener?.onSaveDialogPositiveClick(recordName.text.toString(), category)
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener?.onSaveDialogNegativeClick(this)
                    })
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
        if (context is SaveDialog.SaveDialogListener) {
            listener = context
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

    companion object {
        @JvmStatic
        fun newInstance(title: String) = SaveDialog().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
            }
        }
    }

    interface SaveDialogListener {

        fun onSaveDialogPositiveClick(recordName: String, category: String)

        fun onSaveDialogNegativeClick(dialog: SaveDialog)
    }

}