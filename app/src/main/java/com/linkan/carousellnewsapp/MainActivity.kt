package com.linkan.carousellnewsapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.linkan.carousellnewsapp.databinding.ActivityMainBinding
import com.linkan.carousellnewsapp.util.setStatusBarColorCompat
import com.linkan.carousellnewsapp.util.setSystemBarsColor

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSystemBarsColor(colorResId = R.color.status_bar_color, darkIcons = true)
        setupHomeBackButton()
        changeDefaultMenuIcon()
    }

    private fun changeDefaultMenuIcon() {
        val horizontalDotIcon = ContextCompat.getDrawable(this, R.drawable.ic_horizontal_three_dot)
        binding.toolbar.apply {
            setOverflowIcon(horizontalDotIcon)
        }
    }

    private fun setupHomeBackButton() {
        val color = ContextCompat.getColor(this, R.color.white)
        binding.toolbar.apply {
            setSupportActionBar(binding.toolbar)

            // Change toolbar title & icon color
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            navigationIcon =
                ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_placeholder_transparent)
           // navigationIcon?.setTint(color)
            setTitleTextColor(color)
        }
    }


override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_toolbar, menu)
    return true
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        R.id.action_recent -> {
            Toast.makeText(this, "Recent news...", Toast.LENGTH_SHORT).show()
            true
        }

        R.id.action_popular -> {
            Toast.makeText(this, "Popular news clicked", Toast.LENGTH_SHORT).show()
            true
        }

        /*android.R.id.home -> {
            onBackPressedDispatcher.onBackPressed()
            true
        }*/

        else -> super.onOptionsItemSelected(item)
    }
}
}