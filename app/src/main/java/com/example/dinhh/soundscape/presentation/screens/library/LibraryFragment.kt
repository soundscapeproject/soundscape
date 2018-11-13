package com.example.dinhh.soundscape.presentation.screens.library

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.data.Model
import com.example.dinhh.soundscape.presentation.ListAdapter
import com.example.dinhh.soundscape.presentation.ListItem
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundsFragment
import org.koin.android.viewmodel.ext.android.viewModel

class LibraryFragment : Fragment() {

    private val libraryViewModel: LibraryViewModel by viewModel()
    private var isClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<ListView>(R.id.listView)
        listView.adapter = ListAdapter(context!!, Model.category)
        listView.setOnItemClickListener{ _, _, position, _ ->
            isClicked = true
            val selectedCategory = listView.getItemAtPosition(position) as ListItem
            libraryViewModel.beginSearch(selectedCategory.toString())
        }

        libraryViewModel.viewState.observe(this, Observer {
            it?.run(this@LibraryFragment::handleView)
        })
    }

    private fun handleView(viewState: LibraryViewState) = when (viewState) {
        is LibraryViewState.Success -> {
            isClicked = if (isClicked) {
                goToSoundsFragment()
                false
            }else{
                false
            }
        }
        is LibraryViewState.Failure -> {
            Toast.makeText(activity, "Error: ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun goToSoundsFragment(){
        val fragManager = fragmentManager
        val fragmentTransaction = fragManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.container, SoundsFragment())
        fragmentTransaction?.addToBackStack(null)?.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LibraryFragment()
    }

}
