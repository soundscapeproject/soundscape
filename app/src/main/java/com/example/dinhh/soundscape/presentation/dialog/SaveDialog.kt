package com.example.dinhh.soundscape.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.logD
import kotlinx.android.synthetic.main.dialog_save_record.*
import kotlinx.android.synthetic.main.dialog_save_record.view.*

private const val ARG_TITLE = "title"

class SaveDialog: AppCompatDialogFragment() {

    private var title: String? = null
    private var listener: SaveDialogListener? = null

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val view = it.layoutInflater.inflate(R.layout.dialog_save_record, null)
            val recordName = view.recordName
           builder
                .setView(view)
                .setTitle(title)
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener?.onSaveDialogPositiveClick(recordName.text.toString())
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener?.onSaveDialogNegativeClick(this)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
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

        fun onSaveDialogPositiveClick(recordName: String)

        fun onSaveDialogNegativeClick(dialog: SaveDialog)
    }

}