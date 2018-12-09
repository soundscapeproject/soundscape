package com.example.dinhh.soundscape.presentation.screens.home


import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.logD
import com.example.dinhh.soundscape.common.show
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import com.example.dinhh.soundscape.presentation.base.RecyclerViewListener
import com.example.dinhh.soundscape.presentation.screens.mixer.MixerActivity
import kotlinx.android.synthetic.main.fragment_home_2.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), HomeAdapterViewHolderClicks {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var adapter: HomeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.viewState.observe(this, Observer {
            it?.run(this@HomeFragment::handleView)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_2, container, false)

        homeViewModel.getLocalSoundscapes()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupViews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun setupButtons() {
        btnCreateSoundScape.setOnClickListener {
            goToMixerActivity()
        }
    }

    private fun setupViews() {
        adapter = HomeAdapter(mutableListOf(), this)
        val layoutManager = GridLayoutManager(activity, 2)
        rvSoundScapes.layoutManager = layoutManager
        adapter.setOnItemClickListener(object : RecyclerViewListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
            }
        })

        rvSoundScapes.adapter = adapter
    }

    private fun handleView(viewState: HomeViewState) {
        when (viewState) {
            HomeViewState.Loading -> {
                progressBar.visible()
                logD("LOADINNG")
            }

            is HomeViewState.GetSoundScapeSuccess -> {
                progressBar.gone()
//                handleViewBasedOnSoundScapeList(viewState.list)
                adapter.replaceData(viewState.list)
                logD("REPLEACET DATA DATA TDATA")
            }

            is HomeViewState.Failure -> {
                progressBar.gone()
                logD("ERROR: ${viewState.throwable.localizedMessage}")
                Toast.makeText(activity, "Error ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleViewBasedOnSoundScapeList(soundScapeList: List<LocalSoundscape>) {

        val numberOfSoundscape = soundScapeList.size

        when(numberOfSoundscape) {

            0 -> {
                rvSoundScapes.gone()
                noSoundScapeContainer.show()
            }

            else -> {
                rvSoundScapes.show()
                noSoundScapeContainer.gone()
            }
        }
    }

    private fun goToMixerActivity() {
        val intent = Intent(activity, MixerActivity::class.java)
        startActivity(intent)
    }

    override fun uploadSound(layoutPosition: Int) {
        //DO UPLOAD
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}