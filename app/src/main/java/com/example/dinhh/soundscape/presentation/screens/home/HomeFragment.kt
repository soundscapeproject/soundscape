package com.example.dinhh.soundscape.presentation.screens.home


import android.app.AlertDialog
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
import kotlinx.android.synthetic.main.fragment_home.*
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
        val view = inflater.inflate(R.layout.fragment_home, container, false)

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
            goToMixerActivity(null)
        }
    }

    private fun setupViews() {
        adapter = HomeAdapter(mutableListOf(), this)
        val layoutManager = GridLayoutManager(activity, 2)
        rvSoundScapes.layoutManager = layoutManager
        adapter.setOnItemClickListener(object : RecyclerViewListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val localSoundscape = adapter.getData()[position]

                goToMixerActivity(localSoundscape)
            }
        })

        rvSoundScapes.adapter = adapter
    }

    private fun handleView(viewState: HomeViewState) {
        when (viewState) {
            HomeViewState.Loading -> {
                progressBar.visible()
            }

            is HomeViewState.Failure -> {
                progressBar.gone()
                Toast.makeText(activity, "Error ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

            is HomeViewState.UploadSuccess -> {

                val builder = AlertDialog.Builder(activity)
                builder.setTitle("Uploading")
                builder.setMessage("Do you want to upload this soundscape ?")
                builder.setPositiveButton("YES"){
                        _, _ ->
                    progressBar.gone()
                    homeViewModel.getLocalSoundscapes()
                    Toast.makeText(activity, "Uploaded", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("NO"){
                        _, _ ->
                    progressBar.gone()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

            is HomeViewState.DeleteSuccess -> {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("Deleting")
                builder.setMessage("Do you want to delete this soundscape ?")
                builder.setPositiveButton("YES"){
                        _, _ ->
                    progressBar.gone()
                    homeViewModel.getLocalSoundscapes()
                    Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("NO"){
                        _, _ ->
                    progressBar.gone()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

            is HomeViewState.GetSoundScapeSuccess -> {
                progressBar.gone()
                handleViewBasedOnSoundScapeList(viewState.list)
                adapter.replaceData(viewState.list)
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

    private fun goToMixerActivity(localSoundscape: LocalSoundscape?) {
        val intent = Intent(activity, MixerActivity::class.java)

        if (localSoundscape == null) {
            intent.putExtra(MixerActivity.KEY_IS_TO_EDIT, false)
        } else {
            intent.putExtra(MixerActivity.KEY_IS_TO_EDIT, true)
            intent.putExtra(MixerActivity.KEY_SOUNDSCAPE_ID, localSoundscape.soundId)
            intent.putExtra(MixerActivity.KEY_SOUNDSCAPE_TITLE,localSoundscape.title)
        }

        startActivity(intent)
    }

    override fun uploadSound(layoutPosition: Int) {
        val localSoundscape = adapter.getData()[layoutPosition]

        homeViewModel.uploadSoundScape(localSoundscape)
    }

    override fun deleteSound(layoutPosition: Int) {
        val localSoundscape = adapter.getData()[layoutPosition]

        homeViewModel.deleteSoundScape(localSoundscape)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}