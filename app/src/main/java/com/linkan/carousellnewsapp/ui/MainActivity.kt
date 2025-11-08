package com.linkan.carousellnewsapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.linkan.carousellnewsapp.R
import com.linkan.carousellnewsapp.databinding.ActivityMainBinding
import com.linkan.carousellnewsapp.util.FilterType
import com.linkan.carousellnewsapp.util.ResultEvent
import com.linkan.carousellnewsapp.util.setStatusBarColorAndIcons
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject lateinit var newsAdapter : NewsAdapter

    private val mViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarColorAndIcons(R.color.status_bar_color, false)
        collectFlows()
        setupHomeBackButton()
        changeDefaultMenuIcon()
        initRecyclerView()
        setupRetryButton()
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

    private fun initRecyclerView() {
        binding?.apply {
            rvNews.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = newsAdapter
            }
        }
    }


    private fun collectFlows() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.newsFeedState.collect { result ->
                    when (result) {
                        is ResultEvent.Loading -> showMainLoader(true)
                        is ResultEvent.Success -> {
                            val list = result.data
                            showMainLoader(false)
                            val message =
                                if (list.isEmpty()) "No Data Found" else "Something went wrong!"
                            showErrorLayout(
                                show = list.isEmpty(),
                                message = message
                            )
                        }

                        is ResultEvent.Error -> {
                            showMainLoader(false)
                            showErrorLayout(true, result.errorMessage)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.filterNews.collect { newsArticleList ->
                    newsAdapter.newArticleList = newsArticleList
                }
            }
        }
    }

    private fun setupRetryButton() {
        binding.apply {
            btnRetry.setOnClickListener {
                showErrorLayout(false)
                mViewModel.retry()
            }
        }
    }

    private fun showMainLoader(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showErrorLayout(
        show: Boolean,
        message: String? = "Something went wrong",
        visibleRetryButton: Boolean = true
    ) {
        binding.apply {
            errorLayout.visibility = if (show) View.VISIBLE else View.GONE
            tvError.text = message
            btnRetry.visibility = if (visibleRetryButton) View.VISIBLE else View.GONE
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_recent -> {
                mViewModel.setFilterType(FilterType.RECENT)
                true
            }

            R.id.action_popular -> {
                mViewModel.setFilterType(FilterType.POPULAR)
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