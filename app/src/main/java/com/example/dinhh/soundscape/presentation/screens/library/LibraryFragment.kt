package com.example.dinhh.soundscape.presentation.screens.library

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.presentation.ListAdapter
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundFragment
import kotlinx.android.synthetic.main.fragment_sounds.*

private val categoryList = mutableListOf(
    "Nature", "Human", "Machine", "Story"
)

class LibraryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
    }

    private fun setupListView() {
        listView.adapter = ListAdapter(context!!, categoryList)
        listView.setOnItemClickListener{ _, _, position, _ ->
            val selectedCategory = listView.getItemAtPosition(position) as String
            goToSoundsFragment(selectedCategory)
        }
    }

    fun goToSoundsFragment(category: String){
        val fragManager = fragmentManager
        val fragmentTransaction = fragManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.container, SoundFragment.newInstance(category))
        fragmentTransaction?.addToBackStack(null)?.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LibraryFragment()
    }

}
