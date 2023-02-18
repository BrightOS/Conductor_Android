package ru.myrosmol.conductor.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import ru.myrosmol.conductor.R
import ru.myrosmol.conductor.data.repository.PreferenceRepository
import ru.myrosmol.conductor.databinding.ActivityMainBinding
import ru.myrosmol.conductor.network.RetrofitService
import ru.myrosmol.conductor.ui.auth.AuthActivity
import ru.myrosmol.conductor.utils.NavigationUtil.setupWithNavController
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    @Inject
    lateinit var retrofitService: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        } else {
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                decorView.systemUiVisibility =
                    decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                statusBarColor = Color.TRANSPARENT
            }
        }

        super.onCreate(savedInstanceState)

        println("token: ${preferenceRepository.token}")
        if (preferenceRepository.token.isBlank()) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

        retrofitService.myProfile(preferenceRepository.token) { response, code ->
            if (code != 200) {
                preferenceRepository.token = ""
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null)
            setupBottomNavigationBar()
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.nav_roadmap,
            R.navigation.nav_events,
            R.navigation.nav_network,
            R.navigation.nav_profile
        )

        val controller = binding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.main_container,
            intent = intent
        )

        currentNavController = controller
    }

}