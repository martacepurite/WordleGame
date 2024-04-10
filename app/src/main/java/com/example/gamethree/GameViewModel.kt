package com.example.gamethree

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// glabā spēles stāvokli - mēģinājumu skaitu un iepriekšējos minējumus
// kods veidots pēc kursa mācību materiāliem un StackOverflow atrasta koda:
// https://stackoverflow.com/questions/69137842/how-to-add-data-to-list-in-mutablelivedata
class GameViewModel : ViewModel() {
    var tries : Int = 0

    private val _guesses = MutableLiveData<List<String>>()
    val guesses: LiveData<List<String>> get() = _guesses

    fun incrementTries() {
        tries++
    }
    fun addGuess(Guess: String){
        _guesses.value = _guesses.value?.plus(Guess) ?: listOf(Guess)


    }
}
