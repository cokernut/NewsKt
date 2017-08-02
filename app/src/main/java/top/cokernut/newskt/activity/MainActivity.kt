package top.cokernut.newskt.activity

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
        var fragments: ArrayList<NewListFragment> = ArrayList<NewListFragment>()
        for (data in datas) {
            var bundle: Bundle = Bundle()
            bundle.putString(NewListFragment.URL_STR, data.url)
            var fragment = NewListFragment()
            fragment.arguments = bundle
            fragments.add(fragment)
        }

        var adapter = ViewPagerAdapter(this, getSupportFragmentManager(), datas, fragments)
        viewpager.adapter = adapter
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewpager)
        for (i in 0 until tabLayout.tabCount) {
            var tab: TabLayout.Tab = tabLayout.getTabAt(i) as TabLayout.Tab
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
                tab?.setCustomView(null);
                tab?.setCustomView(adapter.getDefaultTabView(tab.position))
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.setCustomView(null)
                tab?.setCustomView(adapter.getTabView(tab.position));
                viewpager.currentItem = tab?.position!!
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
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
