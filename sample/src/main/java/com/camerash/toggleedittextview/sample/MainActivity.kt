package com.camerash.toggleedittextview.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBinding()
    }

    private fun initBinding() {
        toggleEditButton.bind(tetv1, tetv2, tetv3, tetv4)
    }

}
