package com.example.dinhh.soundscape.presentation.screens.sounds


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.Model
import kotlinx.android.synthetic.main.fragment_sound.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val ARG_CATEGORY = "category"

class SoundFragment : Fragment() {

    private val soundViewModel: SoundViewModel by viewModel()
    // TODO: Rename and change types of parameters
    private var category: String? = null
    lateinit var soundView: RecyclerView


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
        soundView = view.findViewById(R.id.soundList) as RecyclerView


        return view
    }

    private fun handleView(viewState: SoundViewState) = when (viewState) {

        SoundViewState.Loading -> {
            progressBar.visible()
        }

        is SoundViewState.Success -> {
            progressBar.gone()
            Model.sounds = viewState.listSound
            setupListView()
        }

        is SoundViewState.Failure -> {
            progressBar.gone()
            Toast.makeText(activity, "Error: ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupListView() {
        soundList.layoutManager = LinearLayoutManager(this.context!!)
        soundList.adapter = SoundAdapter(Model.sounds)
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
