package com.example.predictit.ui.main

import androidx.lifecycle.ViewModel

class MatchViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    lateinit var battingTeamViewModel : TeamViewModel
    lateinit var bowlingTeamViewModel : TeamViewModel
    var isRunning : Boolean = false

    fun init() {
        battingTeamViewModel = TeamViewModel()
        bowlingTeamViewModel = TeamViewModel()

        battingTeamViewModel.init()
        isRunning = true
    }

    fun nextBowl() : Int {
        return battingTeamViewModel.nextBowl()
    }

    fun getBattingTeam() : TeamViewModel {
        return battingTeamViewModel
    }

}