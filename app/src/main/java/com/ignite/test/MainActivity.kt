package com.ignite.test

import adapter.CategoryAdapter
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var category_array: Array<String> = arrayOf(
        "Adventure",
        "Drama",
        "Fiction",
        "History",
        "Humour",
        "Philosophy",
        "Politics"
    )

    var image_array= arrayOf(
        R.drawable.ic_adventure,
        R.drawable.ic_drama,
        R.drawable.ic_fiction,
        R.drawable.ic_history,
        R.drawable.ic_humour,
        R.drawable.ic_philosophy,
        R.drawable.ic_politics
    );

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //Set adapter
        setAdapter();



    }

    fun setAdapter()
    {
        recyclerCategory.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        val adapter = CategoryAdapter(image_array, category_array)
        recyclerCategory.setAdapter(adapter)

        setFadeAnimation(recyclerCategory)
    }


    fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 1000
        view.startAnimation(anim)
    }
}