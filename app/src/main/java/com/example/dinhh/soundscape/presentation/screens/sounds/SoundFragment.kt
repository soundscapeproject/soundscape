package com.example.dinhh.soundscape.presentation.screens.sounds


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.Model
import kotlinx.android.synthetic.main.fragment_sound.*
import kotlinx.android.synthetic.main.item_sound.view.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val ARG_CATEGORY = "category"

class SoundFragment : Fragment() {

    private val soundViewModel: SoundViewModel by viewModel()
    private var category: String? = null
    lateinit var soundView: RecyclerView
    private val handler = Handler()


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

        SoundViewState.PlayLoading -> {
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
        is SoundViewState.PlaySuccess -> {
            progressBar.gone()
            showStop(viewState.holder, viewState.length)
        }
        is SoundViewState.StopSuccess -> {
            showPlay(viewState.holder)
        }
    }

    private fun setupListView() {
        soundList.layoutManager = LinearLayoutManager(this.context!!)
        soundList.adapter = SoundAdapter(Model.sounds, soundViewModel)
    }

    private fun showStop(holder: ViewHolder, length: Long){

        holder.itemView.itemSoundPlayBtn.invisible()
        holder.itemView.itemSoundStopBtn.visible()

        handler.removeCallbacks { showPlay(holder) }
        handler.postDelayed({ showPlay(holder) },length * 1000)
    }

    private fun showPlay(holder: ViewHolder){
                holder.itemView.itemSoundPlayBtn.visible()
                holder.itemView.itemSoundStopBtn.invisible()
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
