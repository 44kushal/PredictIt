package com.example.predictit.ui.main

import androidx.lifecycle.ViewModel

class PlayerViewModel : ViewModel() {

    var isStriker: Boolean = false
    var isNonStriker: Boolean = false
    var runs: Int = 0
    lateinit var prob: ArrayList<Int>
    var name: String = ""

    fun nextBowl(): Int {
//        return 7
        var sum: Int = 0;

        for (p in prob) {
            sum += p
        }

        var random = (Math.random()*100).toInt()
        for (i in 0..prob.size) {
            random = random - prob.get(i)
            if (random < 0) {
                return i
            }
        }
        return 3;  // TODO : need to apply random number generator and return here
    }

}