package com.example.dinhh.soundscape.presentation.screens.record


import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.isVisible
import com.example.dinhh.soundscape.common.visible
import kotlinx.android.synthetic.main.fragment_record.*
import org.koin.android.viewmodel.ext.android.viewModel

class RecordFragment : Fragment() {

    private val recordViewModel: RecordViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        handleButtonClicked()

        recordViewModel.viewState.observe(this, Observer {
            it?.run(this@RecordFragment::handleView)
        })
    }

    private fun setupView() {
        btnStopRecording.invisible()
        btnPlay.invisible()
        recordingTextView.invisible()
    }

    private fun handleView(viewState: RecordViewState) = when (viewState) {
        is RecordViewState.Success -> {
            //Success State
        }

        is RecordViewState.Failure -> {
            Toast.makeText(activity, "Error: ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleButtonClicked() {
        btnStartRecording.setOnClickListener {
            btnStartRecording.invisible()
            btnStopRecording.visible()
            recordingTextView.visible()
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
            recordViewModel.startRecording()
        }

        btnStopRecording.setOnClickListener {
            btnStartRecording.visible()
            btnStopRecording.invisible()
            recordingTextView.invisible()
            chronometer.stop()
            if (!btnPlay.isVisible()){
                btnPlay.visible()
            }
            recordViewModel.stopRecording()
        }

        btnPlay.setOnClickListener{
            recordViewModel.playRecord()
        }

        btnSave.setOnClickListener {
            val intent = Intent(activity, RecordActivity::class.java)
            startActivity(intent)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = RecordFragment()
    }
}
