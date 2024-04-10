package com.example.gamethree

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.allViews
import androidx.core.view.children
import androidx.core.view.get
import com.example.gamethree.databinding.ActivityMainBinding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var correctword : String
    private var gameover = false
    private var gamewon = false
    private lateinit var userDao : UserDao
    lateinit var user_name : String

    val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

// iegūst atminamo vārdu no saraksta
        correctword = WordList.getWord()


        userDao = AppDatabase.getInstance(application).userDao()
        val intent = intent

        user_name = intent.getStringExtra("message_key").toString()

        binding.submbtn.setOnClickListener {

//     pārbauda, vai minējums ir pareizs
            var thisguess = binding.guesstext.text.toString().uppercase()
            if ((thisguess.length == 5) and (gameover == false)) {
                viewModel.addGuess(thisguess)

//              iekrāso pareizos burtus
                checkCorrectLetters(viewModel.tries)
                viewModel.incrementTries()

                if (correctword.equals(thisguess)) {
                    gameover = true
                    gamewon = true

                } else if (viewModel.tries >= 5) {
                    gameover = true

                }
                val inputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
// paslēpj klaviatūru
                inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
                binding.guesstext.text.clear()

            }
            if (gameover == true) {
                endGame(gamewon)
            }
        }

        binding.againbtn.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message_key", user_name)
            finish()
            startActivity(intent)

        }

        binding.backbutton.setOnClickListener {

            val intent = Intent(this, MenuActivity::class.java)
            finish()
//     aktivitātes tiek manuāli restartētas, lai punkti 'highscores' tabulā tiktu atjaunināti (slikti...)

            startActivity(intent)

        }

        val guesses_Observer = Observer<List<String>> { newValue ->

//     sadala minējumus pa burtiem un ieliek tos atbilstošajos TextView elementos
            for (i in 0..4) {
                var row: View = binding.allrows.children.elementAt(i)
                var bb = findViewById<LinearLayout>(row.id)

                for (x in 0..4) {
                    var ch: View = bb.children.elementAt(x)
                    var ch2 = findViewById<TextView>(ch.id)
                    ch2.text = newValue?.getOrElse(i) { "     " }?.subSequence(x, x + 1)
                }
            }

        }
        viewModel.guesses.observe(this, guesses_Observer)


    }
// iekrāso pēdējā minējuma burtus tā kā to dara Wordle
    fun checkCorrectLetters(currentRowNum:Int){
        var row : View = binding.allrows.children.elementAt(currentRowNum)
        var bb= findViewById<LinearLayout>(row.id)
        for(x in 0 .. 4){
            var ch : View = bb.children.elementAt(x)
            var ch2 = findViewById<TextView>(ch.id)

            var letter = ch2.text.toString().uppercase()
            if(correctword[x] == letter.get(0)){
                ch2.setTextColor(Color.GREEN)
            }else if(correctword.contains(letter)){
                ch2.setTextColor(Color.YELLOW)
            }else{
                ch2.setTextColor(Color.LTGRAY)
            }

        }
    }

    fun endGame(isWon:Boolean){
        val guessentry = findViewById<EditText>(R.id.guesstext)
        val submitbutton = findViewById<Button>(R.id.submbtn)
        val againbutton = findViewById<Button>(R.id.againbtn)
        val backbutton = findViewById<Button>(R.id.backbutton)
        againbutton.visibility = View.VISIBLE
        backbutton.visibility = View.VISIBLE
        guessentry.visibility = View.GONE
        submitbutton.visibility = View.GONE
        if(isWon){
            binding.infotext.text = getString(R.string.gamewon)
            recordPoints(user_name)
        }else{
            binding.infotext.text = getString(R.string.gamelost)
        }
    }
// atjaunina patreizējam spēlētājam atbilstošo datubāzes ierakstu, +1 punkts ja vārds uzminēts
    fun recordPoints(usr_id: String){
        lifecycleScope.launch {

            val newUser = User(uid = usr_id, points = 0)
            userDao.insertAll(newUser)
            val users1 = userDao.getAll()
            println(users1)
            var pts = userDao.getPoints(usr_id)
            pts += 1
            userDao.updatePoints(usr_id, pts)
            val users = userDao.getAll()
            println(users)
        }

    }



}

