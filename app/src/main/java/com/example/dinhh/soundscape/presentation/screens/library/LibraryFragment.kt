package com.example.dinhh.soundscape.presentation.screens.library


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.presentation.base.RecyclerViewListener
import com.example.dinhh.soundscape.presentation.screens.main.MainActivity
import com.example.dinhh.soundscape.presentation.screens.savedrecord.SavedRecord
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.topbar.*
import java.util.zip.Inflater

class LibraryFragment : Fragment() {

    private lateinit var adapter: LibararyAdapter
    private lateinit var rvLibrary: RecyclerView

    private lateinit var myLibraryTitle: List<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        menu!!.clear()
        inflater!!.inflate(R.menu.profile_screen,menu)
    }



    private fun setupViews() {
        adapter = LibararyAdapter(myLibraryTitle)
        adapter.setOnItemClickListener(object : RecyclerViewListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        goToSavedSoundscapes()
                    }
                }
            }
        })
        val layoutManager = LinearLayoutManager(context!!)
        rvLibrary.layoutManager = layoutManager
        rvLibrary.adapter = adapter
    }

    private fun goToSavedSoundscapes() {
        val intent = Intent(activity, SavedRecord::class.java)
        startActivity(intent)
    }
    companion object {
        @JvmStatic
        fun newInstance() = LibraryFragment()
    }
}
