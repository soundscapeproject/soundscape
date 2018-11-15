package com.example.dinhh.soundscape.presentation.screens.record


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dinhh.soundscape.R
import kotlinx.android.synthetic.main.fragment_record.*

class RecordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRecord.setOnClickListener {
            startActivity(Intent(activity, RecordActivity::class.java))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecordFragment()
    }
}
