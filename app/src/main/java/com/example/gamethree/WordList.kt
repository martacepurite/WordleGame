package com.example.gamethree

import java.util.Random
// iespējamo vārdu saraksts

object WordList {
    val words = arrayOf("CRANE","CRABS","SALLY","GERMS","CRACK","CREEP"
    ,"JAZZY",
        "FUZZY",
        "WHIZZ",
        "DIZZY",
        "JERKY",
        "CRAZY",
        "WHICH",
        "CABIN",
        "CABAL",
        "EAGLE",
        "EAGER",
        "FACES",
        "GAFFE",
        "HABIT",
        "LABEL",
        "MACES",
        "PACER",
        "RABBI",
        "SABER",
        "TABBY",
        "UDDER",
        "WACKY",
        "XENON",
        "YACHT",
        "DREAM",
        "GUARD",
        "FLOOD",
        "ADULT",
        "SIGHT",
        "ALARM",
        "FORCE",
        "WOUND")

    fun getWord(): String {
//        nejauši izvēlas vārdu no saraksta
        val ln = words.size
        val ch = (0 .. ln-1).random()

        return (words.get(ch))
    }
}