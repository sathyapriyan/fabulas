package com.sandsindia.fabulas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        supportActionBar?.hide()

    }

    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this)
            .setMessage("Are you sure want to Exit?")
            .setIcon(R.drawable.ic_question_mark)
            .setCancelable(false)
            .setPositiveButton("YES") { _, _ -> finishAffinity() }
            .setNegativeButton("CANCEL") {  dialog, _ -> dialog.dismiss() }
            .show()
    }
}