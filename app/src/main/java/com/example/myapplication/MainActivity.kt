@file:Suppress("DEPRECATION")

package com.example.myapplication

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat


class MainActivity : AppCompatActivity() {

    private lateinit var canvasView: CanvasView

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var color: String
        var frameLayout: FrameLayout

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val editText = EditText(this).apply {
            hint = "Your draw or highlight color in hex without #"
        }

        val submitButton = Button(this).apply {
            text = "Submit Color"
            setOnClickListener {
                color = editText.text.toString()
            }
        }

        canvasView = CanvasView(this)


        val button = Button(this).apply {
            text = "Clear"
            setOnClickListener {
                canvasView.clearAll()
            }
        }

        val myText = TextView(this).apply {
            text =
                "Each algorithm has two inputs, the source, which is the image being drawn, and the destination, which is the image into which the source image is being composited. The destination is often thought of as the background. The source and destination both have four color channels, the red, green, blue, and alpha channels. These are typically represented as numbers in the range 0.0 to 1.0. The output of the algorithm also has these same four channels, with values computed from the source and destination."
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
        }

        val link = TextView(this).apply {
            text =
                "https://favpng.com/png_view/emoji-face-t-shirt-face-with-tears-of-joy-emoji-redbubble-sticker-png/4TyKQE9d"
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
        }

        val bgImg = ImageView(this).apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            setImageResource(R.drawable.smile)
        }

        frameLayout = FrameLayout(this).apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
        }

        var buttonText = Button(this).apply {
            text = "Highlight Text"
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                0.5F
            )
            setOnClickListener {
                if (color.length == 7){
                    color = "#55${color.substring(1)}"
                    Log.d("Color", color)
                } else {
                    color = "#55$color"
                }
                Log.d("Color", color)
                canvasView.setDrawcolor(color)
                frameLayout.removeView(bgImg)
                frameLayout.removeView(canvasView)
                frameLayout.removeView(link)

                frameLayout.addView(myText)
                frameLayout.addView(canvasView)
            }

        }
        var buttonDraw = Button(this).apply {
            text = "Draw"
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                0.5F
            )
            setOnClickListener {
                if (color.length == 9){
                    color = "#${color.substring(3)}"
                    Log.d("Color", color)
                }
                canvasView.setDrawcolor(color)
                frameLayout.removeView(myText)
                frameLayout.removeView(canvasView)

                frameLayout.addView(bgImg)
                frameLayout.addView(canvasView)
                frameLayout.addView(link)
            }
        }

        val buttonCon = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.HORIZONTAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            addView(buttonText)
            addView(buttonDraw)
        }

        val ll = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
            addView(editText)
            addView(submitButton)
            addView(buttonCon)
            addView(button)
            addView(frameLayout)
        }

        setContentView(ll)
        hideSystemUI(ll)
    }


    private fun hideSystemUI(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
        }
    }
}