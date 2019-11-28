package com.timchentw.loadingview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import kotlinx.android.synthetic.main.view_loading.view.*

/**
 * 網路讀取資料時, 顯示 loading
 * 網路讀取失敗時, 顯示重新整理按鈕
 */
class LoadingView : LinearLayoutCompat {


    var listener: OnLoadingViewListener? = null

    private var progressColor = DEFAULT_PROGRESS_COLOR
    private var refreshTint = DEFAULT_REFRESH_TINT
    private var errorText: String ? = null
    private var loadingText: String ? = null

    constructor(context: Context) : super(context) {
        if(!isInEditMode){
            init(null)
        }
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if(!isInEditMode){
            init(attrs)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        if(!isInEditMode){
            init(attrs)
        }
    }

    private fun init(attrs: AttributeSet?) {

        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.LoadingView, 0, 0)
        try{
            // load attributes
            progressColor = attributes.getColor(R.styleable.LoadingView_progressColor, DEFAULT_PROGRESS_COLOR)
            refreshTint = attributes.getColor(R.styleable.LoadingView_refreshTint, DEFAULT_REFRESH_TINT)
            errorText = attributes.getString(R.styleable.LoadingView_errorText)
            loadingText = attributes.getString(R.styleable.LoadingView_loadingText)
        } finally {
            attributes.recycle()
        }

        // init view
        LayoutInflater.from(context).inflate(R.layout.view_loading, this, true)
        // default visibility is View.GONE
        visibility = View.GONE

        progressBar.indeterminateTintList = ColorStateList.valueOf(progressColor)
        ivReload.imageTintList = ColorStateList.valueOf(refreshTint)

        errorText?.let{
            tvReload.text = it
        }

        loadingText?.let{
            tvLoading.text = it
        }

        ivReload.setOnClickListener {
            ivReload.isEnabled = false
            listener?.onReload()
        }
    }

    /**
     * 開始loading
     */
    fun startLoading() {
        visibility = View.VISIBLE

        // 顯示 progressBar 轉圈圈, and 顯示 loading 文字
        progressBar.visibility = View.VISIBLE
        tvLoading.visibility = View.VISIBLE

        // 隱藏 重新整理按鈕與文字說明
        ivReload.visibility = View.GONE
        tvReload.visibility = View.GONE
    }

    fun finishLoading(isSuccessful: Boolean) {

        // 隱藏 progressBar and loading 文字
        progressBar.visibility = View.GONE
        tvLoading.visibility = View.GONE

        if (isSuccessful) {
            // 如果讀取成功, 都隱藏
            visibility = View.GONE
            ivReload.visibility = View.GONE
            tvReload.visibility = View.GONE
        } else {
            // 如果讀取失敗, 顯示重新整理按鈕與文字說明
            visibility = View.VISIBLE
            ivReload.visibility = View.VISIBLE
            tvReload.visibility = View.VISIBLE
            // enable 重新整理按鈕, 因為按下去之後會 disable
            ivReload.isEnabled = true
        }
    }

    interface OnLoadingViewListener {
        fun onReload()
    }

    companion object {
        private const val DEFAULT_PROGRESS_COLOR = Color.GRAY
        private const val DEFAULT_REFRESH_TINT = Color.GRAY
    }
}