package com.example.dinhh.soundscape.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.widget.ProgressBar
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.visible
import kotlinx.android.synthetic.main.dialog_save_soundscape.view.*

private const val ARG_TITLE = "title"

class SaveSoundscapeDialog: AppCompatDialogFragment() {

    private var title: String? = null
    private var listener: SaveDialogListener? = null

    private lateinit var progressBar: ProgressBar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val view = it.layoutInflater.inflate(R.layout.dialog_save_soundscape, null)

            progressBar = view.progressBar

            builder
                .setView(view)
                .setTitle(title)
                .setPositiveButton(getString(R.string.save),
                    DialogInterface.OnClickListener { dialog, id ->
                        listener?.onSaveDialogPositiveClick(view.soundScapleName.text.toString())
                    })
                .setNegativeButton(getString(R.string.cancel),
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
        if (context is SaveSoundscapeDialog.SaveDialogListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }

    fun showLoading() {
        progressBar.visible()
    }

    fun hideLoading() {
        progressBar.gone()
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String) = SaveSoundscapeDialog().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
            }
        }
    }

    interface SaveDialogListener {

        fun onSaveDialogPositiveClick(soundScapeName: String)

        fun onSaveDialogNegativeClick(recordDialog: SaveSoundscapeDialog)
    }

}