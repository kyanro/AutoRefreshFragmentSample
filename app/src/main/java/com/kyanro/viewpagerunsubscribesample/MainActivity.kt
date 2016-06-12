package com.kyanro.viewpagerunsubscribesample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kyanro.viewpagerunsubscribesample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding

    lateinit private var adapter: AutoRefreshFragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        adapter = AutoRefreshFragmentPagerAdapter(supportFragmentManager)
        bind.viewPager.adapter = adapter
    }

    class AutoRefreshFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            Log.d("mylog", "getItem:" + position)
            return AutoRefreshFragment.newInstance(2 * (position + 1))
        }

        override fun getCount(): Int {
            return 5
        }

        fun findFragmentByPosition(viewPager: ViewPager, position: Int): AutoRefreshFragment? {
            if (position < count) {
                return instantiateItem(viewPager, position) as AutoRefreshFragment
            } else {
                return null
            }
        }
    }
}
