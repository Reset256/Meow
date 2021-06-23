package com.llv.meow

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val meowMap by lazy {
        MeowMap().apply {
            addRes(Mood.ANGRY, R.drawable.angry_cat, R.raw.angry_meow)
            addRes(Mood.GRUMPY, R.drawable.grumpy_cat, R.raw.grumpy_meow)
            addRes(Mood.SAD, R.drawable.sad_cat, R.raw.sad_meow)
            addRes(Mood.SMILEY, R.drawable.smiley_cat, R.raw.usual_meow)
            addRes(Mood.PURR, R.drawable.purr_purr, R.raw.purr)
        }
    }

    private val animationOnClick: (View) -> Unit = {
        val animator = it.animate()
        animator.scaleXBy(0.15f).scaleYBy(0.15f).setDuration(500L)
            .withEndAction { animator.scaleX(1f).scaleY(1f) }
    }

    private val playerPool: MediaPlayerPool by lazy {
        MediaPlayerPool(this, 4)
    }

    private lateinit var vList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vList = findViewById(R.id.act1)
//        findViewById<View>(R.id.add_meow_button).setOnClickListener {
//            fragmentManager.beginTransaction().replace(R.id.cat_lib_frame, CatLibFragment()).addToBackStack(null).commit()
//        }
        findViewById<View>(R.id.add_meow_button).setOnClickListener {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.cat_lib_frame, CatLibFragment())
                commit()
            }
        }
        showCatLayout(meowMap)


    }

    private fun showCatLayout(catMap: MeowMap) {
        val inflater = layoutInflater
        for (mood in Mood.values()) {
            val view = inflater.inflate(R.layout.cat_item, vList, false)
            val catImageView = view.findViewById<ImageView>(R.id.item_cat)
            catImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    catMap.getPicId(mood)
                )
            )
            val resId = catMap.getAudioId(mood) ?: 0
            catImageView.setOnClickListener {
                animationOnClick(it)
                playerPool.playSound(resId)
            }
            vList.addView(view)
        }
    }

}
