package com.example.dinhh.soundscape.presentation.screens.main

import android.Manifest
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.presentation.screens.library.LibraryFragment
import com.example.dinhh.soundscape.presentation.screens.home.HomeFragment
import com.example.dinhh.soundscape.presentation.screens.login.LoginActivity
import com.example.dinhh.soundscape.presentation.screens.mixer.MixerActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.topbar.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var homeFragment: Fragment
    private lateinit var libraryFragment: Fragment
    private val RECORD_AUDIO_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFragments()

        setupButtomNavigation()

        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermissionToRecordAudio()
        }

        mainViewModel.viewState.observe(this, Observer {
            it?.run(this@MainActivity::handleView)
        })

        btnMixer.setOnClickListener {
            toolbar_title.text = "Soundscapes"
            gotoMixerActivity()
        }
    }

    private fun setupFragments() {
        homeFragment = LibraryFragment.newInstance()
        libraryFragment = HomeFragment.newInstance()
    }

    private fun setupButtomNavigation() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
                item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    toolbar_title.text = "Home"
                    openFragment(libraryFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_record -> {
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_library -> {
                    toolbar_title.text = "Library"
                    openFragment(homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

        // Initial tab
        bottomNavigation.setSelectedItemId(R.id.navigation_home)
        toolbar_title.text = "Home"
    }

    private fun handleView(viewState: MainViewState) {
        when (viewState) {
            MainViewState.Loading -> {
            }

            is MainViewState.Success -> {
                gotoLoginActivity()
            }

            is MainViewState.Failure -> {
                Toast.makeText(this, "Error ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gotoLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun gotoMixerActivity() {
        val intent = Intent(this, MixerActivity::class.java)
        startActivity(intent)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private fun getPermissionToRecordAudio() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE), RECORD_AUDIO_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }
    }
}
