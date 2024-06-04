package com.example.project4_firebase_crud

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project4_firebase_crud.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //Su dung intent de chuyen giua cac Activity
        goInsertionScreen()

        goFetchingScreen()

    }

    private fun goFetchingScreen() {
        binding.btnFetch.setOnClickListener {
            val intent = Intent(this,FetchingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goInsertionScreen() {
        binding.btnInsert.setOnClickListener {
            val intent = Intent(this,InsertionActivity::class.java)
            startActivity(intent)
        }
    }
}