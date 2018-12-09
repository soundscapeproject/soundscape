package com.example.dinhh.soundscape.presentation.screens.library

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.data.entity.SoundCategory
import com.example.dinhh.soundscape.presentation.screens.login.LoginActivity
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel


class LibraryFragment : Fragment() {

    private val libraryViewModel: LibraryViewModel by viewModel()


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
        val view =  inflater.inflate(R.layout.fragment_library_2, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons(){

        natureBtn.setOnClickListener {
            goToSoundsActivity(SoundCategory.NATURE.description)
        }
        humanBtn.setOnClickListener {
            goToSoundsActivity(SoundCategory.HUMAN.description)
        }
        machineBtn.setOnClickListener {
            goToSoundsActivity(SoundCategory.MACHINE.description)
        }
        storyBtn.setOnClickListener {
            goToSoundsActivity(SoundCategory.STORY.description)
        }
    }

    private fun goToSoundsActivity(category: String) {
        val intent = Intent(activity, SoundActivity::class.java)
        intent.putExtra(SoundActivity.KEY_CATEGORY, category)
        startActivity(intent)
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