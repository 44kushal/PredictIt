package com.example.predictit.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel

class TeamViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var runs: Int = 0
    var wicketsLeft: Int = 0
    var totalWickets : Int = 0
    var targetLeft: Int = 0
    var totalRun: Int = 0
    var ballsLeft: Int = 0
    var over : Int = 0
    var overTxt : String = ""
    var name: String = ""
    lateinit var players: ArrayList<PlayerViewModel>
    lateinit var player1: PlayerViewModel
    lateinit var player2: PlayerViewModel
    var nextPlayer: Int = 0

    fun init() {
        players = ArrayList(4)
        name = "Bengluru"
        wicketsLeft = 3
        targetLeft = 40
        ballsLeft = 24

        var player = PlayerViewModel()
        player.name = "Kirat"
        player.prob = arrayListOf(5, 30, 25, 10, 15, 1, 9, 5)
        player.isStriker = true
        players.add(player)
        player1 = player

        player = PlayerViewModel()
        player.name = "Nodhi"
        player.isNonStriker = true
        player.prob = arrayListOf(10, 40, 20, 5, 10, 1, 4, 10)
        players.add(player)
        player2 = player
        nextPlayer = 1

        player = PlayerViewModel()
        player.name = "Rumrah"
        player.prob = arrayListOf(20, 30, 15, 5, 5, 1, 4, 20)
        players.add(player)

        player = PlayerViewModel()
        player.name = "Henra"
        player.prob = arrayListOf(30, 25, 5, 0, 5, 1, 4, 30)
        players.add(player)


    }

    fun nextBowl() : Int {
        var decide = 0
        if (player1 != null) {
            var prob = player1.nextBowl()
            Log.d("test", "player1 prob : " + prob)
            decide = processBowl(prob)
            incrementOver()
        }
        return decide
    }

    fun incrementOver() {
        over++
        overTxt =  (over / 6).toString() + "." + (over % 6).toString()
    }

    private fun processBowl(prob: Int) : Int {
        when (prob) {
            0 -> { // Dot ball
                ballsLeft--
                runs = 0

            }
            1 -> {
                // 1
                ballsLeft--
                player1.runs++
                runs = 1
                totalRun++
                targetLeft--
                player1.isStriker = !player1.isStriker
                player2.isStriker = !player2.isStriker
            }
            2 -> {
                // 2
                ballsLeft--
                player1.runs += 2
                runs = 2
                totalRun += 2
                targetLeft -= 2
            }
            3 -> {
                // 3
                ballsLeft--
                player1.runs += 3
                runs = 3
                totalRun += 3
                targetLeft -= 3
                player1.isStriker = !player1.isStriker
                player2.isStriker = !player2.isStriker

            }
            4 -> {
                // Four 4
                ballsLeft--
                player1.runs += 4
                runs = 4
                totalRun += 4
                targetLeft -= 4
            }
            5 -> {
                // Four 5
                ballsLeft--
                player1.runs += 5
                runs = 5
                totalRun += 5
                targetLeft -= 5
                player1.isStriker = !player1.isStriker
                player2.isStriker = !player2.isStriker
            }
            6 -> {
                // Six 6
                ballsLeft--
                player1.runs += 6
                runs = 6
                totalRun += 6
                targetLeft -= 6
            }
            7 -> {
                // OUT
                ballsLeft--
                runs = 0
                wicketsLeft--
                totalWickets++
                if(totalWickets == 3) {
                    // All out
                    return -1
                } else {
                    if (player1.isStriker) {
                        player1 = nextPlayer()!!
                    } else if (player2.isStriker) {
                        player2 = nextPlayer()!!
                    }
                }

            }
        }
        return 0
    }

    fun nextPlayer(): PlayerViewModel? {
        nextPlayer++
        if (nextPlayer < players.size) {
            var player = players.get(nextPlayer)
            player.isStriker = true
            return player
        } else {
            // All out
        }
        return null
    }

    fun setStriker(player: PlayerViewModel) {
        if (player != null) {
            player1 = player
        }
    }

    fun setNonStriker(player: PlayerViewModel) {
        if (player != null) {
            player2 = player
        }
    }

    fun getStriker(): PlayerViewModel? {
        if (player1.isStriker) {
            return player1
        } else if (player2.isStriker) {
            return player2
        }
        return null
    }

    fun getNonStriker(): PlayerViewModel? {
        if (player1.isStriker) {
            return player2
        } else if (player2.isStriker) {
            return player1
        }
        return null
    }


}