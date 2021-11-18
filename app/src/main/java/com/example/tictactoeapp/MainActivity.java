package com.example.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button [] buttons = new Button[9];
    private Button restGame;

    private int playerOneScoreCount, playerTwoScoreCount, roundCount;
    boolean activePlayer;
    boolean startAgain;
    boolean isDone;

    //  p1 => 0
    //  p2 => 1
    // empty => 2
    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},    // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},    // columns
            {0, 4, 8}, {2, 4, 6}                // cross

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        restGame = (Button) findViewById(R.id.rest_btn);
        restGame.setOnClickListener(this);

        for (int i=0; i<9; i++) {
            String buttonId = "btn_" + i;
            int resourceId = getResources().getIdentifier(buttonId, "id",getPackageName());
            buttons[i] = (Button) findViewById(resourceId);
            buttons[i].setOnClickListener(this);
        }

        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
        startAgain = false;      //  true == play again, false == reset game
        isDone = false;         //   true == game is finished. no actions until reset.

    }

    @Override
    public void onClick(View v) {

        if (v.getId() != R.id.rest_btn) {
            if (!((Button) v).getText().toString().equals("")) {
                return;
            }

            else if (isDone) {
                return;
            }

            startAgain = false;     // game is not initialize

            String buttonId = v.getResources().getResourceEntryName(v.getId()); // btn_2
            int id = Integer.parseInt(buttonId.substring(buttonId.length() - 1, buttonId.length()));    // 2
            if (activePlayer) { // player 1 is active
                System.out.println("player 1");
                ((Button) v).setText("X");
                ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
                gameState[id] = 0;
            } else {            //  player 2 is active
                System.out.println("player 2");
                ((Button) v).setText("O");
                ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
                gameState[id] = 1;
            }

            roundCount++;

            if (isWinner()) {

                if (activePlayer) {
                    playerOneScoreCount++;
                    updatePlayerScore("One");
                } else {
                    playerTwoScoreCount++;
                    updatePlayerScore("Two");
                }
                isDone = true;
                return;

            } else if (roundCount == 9) {
                updatePlayerScore("Draw");
                isDone = true;
                return;

            } else {
                activePlayer = !activePlayer;
            }

        }

        else {        // Rest Button
            if (!startAgain)
                playAgain();
            else
                startAgain();
        }
    }

    public boolean isWinner() {
        for (int [] indexArr : winningPositions) {
            if (    gameState[indexArr[0]] == gameState[indexArr[1]]
                    && gameState[indexArr[1]] == gameState[indexArr[2]]
                    && gameState[indexArr[0]] != 2) {
                return true;
            }
        }
        return false;
    }

    public void updatePlayerScore(String playerNum) {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
        if (!(playerNum.equals("Draw")))
            playerStatus.setText("Player " + playerNum + " won!");
        else
            playerStatus.setText("Draw!");
    }

    public void playAgain() {
        isDone = false;
        startAgain = true;
        roundCount = 0;
        activePlayer = true;

        for (int i=0; i<9; i++) {
            buttons[i].setText("");
            gameState[i] = 2;
        }

        playerStatus.setText("");
    }

    public void startAgain() {
        playAgain();
        playerOneScore.setText("0");
        playerTwoScore.setText("0");

        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;

    }
}