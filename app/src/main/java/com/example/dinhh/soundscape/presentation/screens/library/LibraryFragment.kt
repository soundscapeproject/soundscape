package com.example.dinhh.soundscape.presentation.screens.library


import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.presentation.base.RecyclerViewListener
import com.example.dinhh.soundscape.presentation.screens.login.LoginActivity
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LibraryFragment : Fragment() {

    private val libraryViewModel: LibraryViewModel by viewModel()


    private lateinit var adapter: LibararyAdapter
    private lateinit var rvLibrary: RecyclerView

    private lateinit var myLibraryTitle: List<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        libraryViewModel.viewState.observe(this, Observer {
            it?.run(this@LibraryFragment::handleView)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        rvLibrary = view.findViewById(R.id.rvLibrary) as RecyclerView

        myLibraryTitle = listOf(
            getString(R.string.save_soundscapes),
            getString(R.string.recorded_tracks),
            getString(R.string.favorite_sounds)
        )

        setupViews()


        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.profile_screen,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.setting -> {
                libraryViewModel.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    private fun setupViews() {
        adapter = LibararyAdapter(myLibraryTitle)
        adapter.setOnItemClickListener(object : RecyclerViewListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                when (position) {
                    1 -> {
                        goToSoundActivity("Records")
                    }
                }
            }
        })
        val layoutManager = LinearLayoutManager(context!!)
        rvLibrary.layoutManager = layoutManager
        rvLibrary.adapter = adapter
    }

    private fun goToSoundActivity(category: String) {
        val intent = Intent(activity, SoundActivity::class.java)
        intent.putExtra(SoundActivity.KEY_CAME_FROM_SAVED_SOUND, true)
        intent.putExtra(SoundActivity.KEY_CATEGORY, category)
        startActivity(intent)
    }

    private fun handleView(viewState: LibraryViewState) {
        when (viewState) {
            LibraryViewState.Loading -> {
            }

            is LibraryViewState.LogoutSuccess -> {
                gotoLoginActivity()
            }

            is LibraryViewState.Failure -> {
                Toast.makeText(activity, "Error ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gotoLoginActivity() {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LibraryFragment()
    }
}