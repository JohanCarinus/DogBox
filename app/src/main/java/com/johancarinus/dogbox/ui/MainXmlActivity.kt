package com.johancarinus.dogbox.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.johancarinus.dogbox.util.onlyOnFirstCreate
import dagger.hilt.android.AndroidEntryPoint
import johancarinus.dogbox.R

@AndroidEntryPoint
class MainXmlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_xml_activity)
        this.onlyOnFirstCreate(savedInstanceState) {
//            this.replaceFragment(R.id.nav_host_fragment, HomeFragment.newInstance())
        }
    }
}