package com.kyanro.viewpagerunsubscribesample


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kyanro.viewpagerunsubscribesample.databinding.FragmentAutoRefreshBinding


class AutoRefreshFragment : Fragment() {

    private var interval: Int = 30;
    lateinit private var bind: FragmentAutoRefreshBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            interval = arguments.getInt(ARG_INTERVAL);
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_auto_refresh, container, false)
        return bind.root;
    }

    companion object {
        private val ARG_INTERVAL = "arg_interval"

        fun newInstance(interval: Int): AutoRefreshFragment {
            val fragment = AutoRefreshFragment()
            val args = Bundle()
            args.putInt(ARG_INTERVAL, interval)
            fragment.arguments = args
            return fragment
        }
    }
}
