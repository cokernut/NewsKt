package top.cokernut.newskt.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import top.cokernut.newskt.R
import top.cokernut.newskt.adapter.ViewPagerAdapter
import top.cokernut.newskt.base.BaseActivity
import top.cokernut.newskt.config.URLConfig
import top.cokernut.newskt.fragment.NewListFragment
import top.cokernut.newskt.model.URLModel
import java.util.ArrayList

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        fab.visibility = View.GONE

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        initView()
    }

    fun initView() {
        val datas: ArrayList<URLModel> = URLConfig.getUrls()
        val fragments: ArrayList<NewListFragment> = ArrayList<NewListFragment>()
        for (data in datas) {
            val bundle: Bundle = Bundle()
            bundle.putString(NewListFragment.URL_STR, data.url)
            val fragment = NewListFragment()
            fragment.arguments = bundle
            fragments.add(fragment)
        }

        val adapter = ViewPagerAdapter(this, supportFragmentManager, datas, fragments)
        viewpager.adapter = adapter
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewpager)
        for (i in 0 until tabLayout.tabCount) {
            val tab: TabLayout.Tab = tabLayout.getTabAt(i) as TabLayout.Tab
            if (i == 0) {
                tab.customView = adapter.getTabView(i)
            } else {
                tab.customView = adapter.getDefaultTabView(i)
            }
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView = null
                tab?.customView = adapter.getDefaultTabView(tab!!.position)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView = null
                tab?.customView = adapter.getTabView(tab!!.position)
                viewpager.currentItem = tab.position
            }
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_setting -> {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            }
            R.id.nav_about -> {
                startActivity(Intent(this@MainActivity, AboutActivity::class.java))
            }
            R.id.nav_share -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
