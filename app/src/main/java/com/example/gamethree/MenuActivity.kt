package com.example.gamethree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// skats, kas tiek ielādēts atverot aplikāciju
// veidots pēc oficiālās dokumentācijas koda piemēriem

class MenuActivity : AppCompatActivity() {

    private lateinit var userDao : UserDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var users_list : List<User>
        userDao = AppDatabase.getInstance(application).userDao()
        // ielādē datubāzi ar spēlētāju kontiem un parāda to rezultātus RecyclerView tabulā
        GlobalScope.launch {
            users_list = userDao.getAll()
            var customAdapter = CustomAdapter(users_list)
            recyclerView.adapter = customAdapter

            customAdapter.notifyDataSetChanged()
        }

        val startgamebutton = findViewById<Button>(R.id.startbtn)
        val entrusr = findViewById<EditText>(R.id.enter)


//  palaiž spēli, nodod spēles aktivitātei spēlētāja lietotājvārdu
        startgamebutton.setOnClickListener {
            val str = entrusr.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message_key", str)
            finish()
            startActivity(intent)

        }
    }
}