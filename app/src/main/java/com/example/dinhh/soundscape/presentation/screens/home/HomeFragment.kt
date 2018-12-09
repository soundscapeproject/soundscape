package com.example.dinhh.soundscape.presentation.screens.home


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.presentation.base.RecyclerViewListener
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundActivity

class HomeFragment : Fragment() {



    private lateinit var adapter: LibararyAdapter
    private lateinit var rvLibrary: RecyclerView

    private lateinit var myLibraryTitle: List<String>




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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





    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}