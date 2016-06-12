package com.kyanro.viewpagerunsubscribesample


import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kyanro.viewpagerunsubscribesample.databinding.FragmentAutoRefreshBinding
import rx.Observable
import rx.Subscription
import rx.subscriptions.Subscriptions
import java.util.concurrent.TimeUnit


class AutoRefreshFragment : Fragment() {

    private var interval: Int = 20;
    lateinit private var bind: FragmentAutoRefreshBinding;

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("mylog", "onAttach:" + interval + ":" + hashCode())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("mylog", "onCreate:" + interval + ":" + hashCode())

        if (arguments != null) {
            interval = arguments.getInt(ARG_INTERVAL);
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d("mylog", "onCreateView:" + interval + ":" + hashCode())

        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_auto_refresh, container, false)
        return bind.root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("mylog", "onActivityCreated:" + interval + ":" + hashCode())
        bind.helloText.text = "Hello:" + interval
    }

    override fun onStart() {
        super.onStart()
        Log.d("mylog", "onStart:" + interval + ":" + hashCode())
    }

    override fun onResume() {
        super.onResume()
        Log.d("mylog", "onResume:" + interval + ":" + hashCode())
        if (userVisibleHint) {
            Log.d("mylog", "startAutoRefresh in onResume:" + interval + ":" + hashCode())
            startAutoRefresh()
        }
    }

    override fun onStop() {
        super.onStop()
        stopAutoRefresh()
        Log.d("mylog", "onStop:" + interval + ":" + hashCode())
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d("mylog", "setUserVisibleHint:" + isVisibleToUser + ":" + interval + ":" + hashCode())
        if (isVisibleToUser) {
            if (view != null) {
                // view構築完了後 == スワイプでの切り替えによる呼び出し
                // 初回起動時は onResume での自動更新にまかせる
                //
                startAutoRefresh()
            } else {
                Log.d("mylog", "initial called from view pager:" + interval + ":" + hashCode())
            }
        } else {
            stopAutoRefresh()
        }
    }

    companion object {
        private val ARG_INTERVAL = "arg_interval"

        fun newInstance(interval: Int): AutoRefreshFragment {
            Log.d("mylog", "newInstance:" + interval)
            val fragment = AutoRefreshFragment()
            val args = Bundle()
            args.putInt(ARG_INTERVAL, interval)
            fragment.arguments = args
            return fragment
        }
    }

    var subscription: Subscription = Subscriptions.empty();

    fun startAutoRefresh() {
        Log.d("mylog", "startAutoRefresh:" + interval)
        subscription.unsubscribe()
        Log.d("mylog", "startAutoRefresh unsubscribe:" + interval + ":" + hashCode())

        subscription = Observable.interval(interval.toLong(), TimeUnit.SECONDS)
                .doOnUnsubscribe { -> Log.d("mylog", "unsubscribe:" + interval + ":" + hashCode()) }
                .subscribe({ event ->
                    Log.d("mylog", "interval:" + interval + ":" + hashCode())
                }, { e ->
                    Log.d("mylog", "error")
                }, { ->
                    Log.d("mylog", "complete:" + interval)
                })
    }

    fun stopAutoRefresh() {
        Log.d("mylog", "stopAutoRefresh:" + interval + ":" + hashCode())

        subscription.unsubscribe()
    }
}
