package com.richyeoh.app.dynamicloading

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.richyeoh.app.dynamicloading.model.ScreenWidget
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val root: FrameLayout = findViewById(R.id.fl_root)
        val input = assets.open("component.json")
        val reader = InputStreamReader(input)
        val gson = Gson()
        root.post {
            val screenWidget = gson.fromJson(reader, ScreenWidget::class.java)
            Log.e("MainActivity", "screen=$screenWidget")
            makeComponent(root, screenWidget)
        }
    }

    private fun makeComponent(root: FrameLayout, screenWidget: ScreenWidget) {
        val screenWidth = screenWidget.screen_width
        val screenHeight = screenWidget.screen_height

        val view = screenWidget.view

        val viewWidth = view.width
        val viewHeight = view.height

        val viewScale = viewWidth * 1.0 / viewHeight
        Log.e("ManActivity", "viewScale=$viewScale")
        val viewWidthScale = viewWidth * 1.0 / screenWidth
        Log.e("ManActivity", "viewWidthScale=$viewWidthScale")
        val viewHeightScale = viewHeight * 1.0 / screenHeight
        Log.e("ManActivity", "viewHeightScale=$viewHeightScale")

        val deviceWidth = root.width
        val deviceWidthScale = deviceWidth * 1.0 / screenWidth
        Log.e("ManActivity", "deviceWidthScale=$deviceWidthScale")
        val deviceHeight = root.height
        val deviceHeightScale = deviceHeight * 1.0 / screenHeight
        Log.e("ManActivity", "deviceHeightScale=$deviceHeightScale")

        val componentWidth = (deviceWidth * viewWidthScale).toInt()
        Log.e("ManActivity", "componentWidth=$componentWidth")
        val componentHeight = (deviceHeight * viewHeightScale).toInt()
        Log.e("ManActivity", "componentHeight=$componentHeight")
        val componentScale = componentWidth * 1.0 / componentHeight
        Log.e("ManActivity", "componentScale=$componentScale")
        val component = View(this)
        component.setBackgroundColor(Color.parseColor(view.background))
        val params = FrameLayout.LayoutParams(componentWidth, componentHeight)
        root.addView(component, params)
    }
}
