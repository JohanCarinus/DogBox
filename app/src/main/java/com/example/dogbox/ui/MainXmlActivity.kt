package com.example.dogbox.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dogbox.R
import com.example.dogbox.ui.fragments.HomeFragment
import com.example.dogbox.util.onlyOnFirstCreate
import com.example.dogbox.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainXmlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_xml_activity)
        this.onlyOnFirstCreate(savedInstanceState) {
            this.replaceFragment(R.id.main_fragment_container, HomeFragment.newInstance())
        }
    }
}