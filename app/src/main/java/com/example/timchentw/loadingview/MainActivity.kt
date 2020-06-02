package com.example.timchentw.loadingview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.timchentw.loadingview.LoadingView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), LoadingView.OnLoadingViewListener {

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadingView.listener = this
        buttonLoad.setOnClickListener {
            loadingView.startLoading()
            startTimer()
        }

    }

    private fun startTimer() {
        disposable = Observable.timer(2000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                // do something data received
                loadingView.finishLoading(false)
            }
    }

    override fun onStop() {
        super.onStop()
        // 取消跳轉頁面
        disposable?.dispose()
    }

    override fun onReload() {
        loadingView.startLoading()
        startTimer()
    }
}
