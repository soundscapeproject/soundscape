package com.example.dinhh.soundscape.presentation.screens.sounds


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.visible
import kotlinx.android.synthetic.main.fragment_sound.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val ARG_CATEGORY = "category"

class SoundFragment : Fragment() {

    private val soundViewModel: SoundViewModel by viewModel()
    // TODO: Rename and change types of parameters
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(ARG_CATEGORY)
        }

        soundViewModel.viewState.observe(this, Observer {
            it?.run(this@SoundFragment::handleView)
        })

        soundViewModel.beginSearch(category!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_sound, container, false)

        return view
    }

    private fun handleView(viewState: SoundViewState) = when (viewState) {

        SoundViewState.Loading -> {
            progressBar.visible()
            Log.d("Booyah!", "LOADING SHOW")
        }

        is SoundViewState.Success -> {
            progressBar.gone()
            Log.d("Booyah!", "LOADING HIDE")
            Log.d("Booyah!", "SUCCESS LIST SONGS: ${viewState.listSound}")
        }

        is SoundViewState.Failure -> {
            progressBar.gone()
            Log.d("Booyah!", "LOADING HIDE")
            Log.d("Booyah!", "ERROR: ${viewState.throwable.localizedMessage}")
            Toast.makeText(activity, "Error: ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(category: String) =
            SoundFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY, category)
                }
            }
    }
}
