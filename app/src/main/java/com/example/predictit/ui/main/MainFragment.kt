package com.example.predictit.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.predictit.R

class MainFragment : Fragment(), View.OnClickListener {

    lateinit var bowlBtn: Button
    lateinit var runTxt: TextView
    lateinit var scoreTxt: TextView
    lateinit var overTxt: TextView
    lateinit var strikerPlayer: TextView
    lateinit var nonStrikerPlayer: TextView

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var matchViewModel: MatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var view: View = inflater.inflate(R.layout.main_fragment, container, false)
        bowlBtn = view.findViewById(R.id.buttonBowl)
        bowlBtn.setOnClickListener(this)
        runTxt = view.findViewById(R.id.run)
        scoreTxt = view.findViewById(R.id.scoreLbl)
        strikerPlayer = view.findViewById(R.id.striker)
        nonStrikerPlayer = view.findViewById(R.id.nonStriker)
        overTxt = view.findViewById(R.id.overLbl)

        return view
    }

    private fun initModels() {
        matchViewModel.init()
        strikerPlayer.setText(matchViewModel.getBattingTeam().player1.name + " *")
        nonStrikerPlayer.setText(matchViewModel.getBattingTeam().player2.name)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        matchViewModel = ViewModelProviders.of(requireActivity())
            .get(MatchViewModel::class.java)
        if (!matchViewModel.isRunning) {
            initModels()
        } else {
            updateUI()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonBowl -> {
                Log.d("test", "clicked")
                var decide = matchViewModel.nextBowl()
                if (decide == -1) {
                    Toast.makeText(context, "Chennai Wins...", Toast.LENGTH_LONG).show()
                }
                updateUI()
                decideResult()
            }
        }
    }

    private fun decideResult() {
        if (matchViewModel.battingTeamViewModel.totalWickets == 3) {
            Toast.makeText(context, "Chennai Wins...", Toast.LENGTH_LONG).show()
        }
        if (matchViewModel.battingTeamViewModel.totalRun >= 40) {
            Toast.makeText(context, "Bengluru Wins...", Toast.LENGTH_LONG).show()
        }
        if (matchViewModel.battingTeamViewModel.over == 24) {
            if (matchViewModel.battingTeamViewModel.totalRun >= 40) {
                Toast.makeText(context, "Bengluru Wins...", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Chennai Wins...", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun updateUI() {
        runTxt.setText("" + matchViewModel.getBattingTeam().runs)
        strikerPlayer.setText(matchViewModel.battingTeamViewModel.getStriker()!!.name + " *")
        nonStrikerPlayer.setText(matchViewModel.battingTeamViewModel.getNonStriker()!!.name)
        scoreTxt.setText(
            "Score:" +
                    matchViewModel.battingTeamViewModel.totalRun.toString() + " - " +
                    matchViewModel.battingTeamViewModel.totalWickets
        )
        overTxt.setText("Overs:" + matchViewModel.battingTeamViewModel.overTxt)
    }

}