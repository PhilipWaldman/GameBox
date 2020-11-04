package com.gamebox.ui.games;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.gamebox.R;

public class TicTacToeActivity extends AppCompatActivity {

    private ImageButton[][] boardButtons;
    private TextView turnView, winnerView;
    private boolean turnO, gameEnded = false;
    private String[][] gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        winnerView = findViewById(R.id.tic_tac_toe_winner);

        turnO = (int) (Math.random() * 2) == 0;
        turnView = findViewById(R.id.tic_tac_toe_turn);
        turnView.setText(turnO ? R.string.ttt_turn_o : R.string.ttt_turn_x);

        gameBoard = new String[3][3];

        boardButtons = new ImageButton[3][3];
        boardButtons[0][0] = findViewById(R.id.TTT00);
        boardButtons[0][1] = findViewById(R.id.TTT01);
        boardButtons[0][2] = findViewById(R.id.TTT02);
        boardButtons[1][0] = findViewById(R.id.TTT10);
        boardButtons[1][1] = findViewById(R.id.TTT11);
        boardButtons[1][2] = findViewById(R.id.TTT12);
        boardButtons[2][0] = findViewById(R.id.TTT20);
        boardButtons[2][1] = findViewById(R.id.TTT21);
        boardButtons[2][2] = findViewById(R.id.TTT22);

        boardButtons[0][0].setOnClickListener(v -> {
            if (!gameEnded) {
                makeMark(0, 0);
            }
        });
        boardButtons[0][1].setOnClickListener(v -> {
            if (!gameEnded) {
                makeMark(0, 1);
            }
        });
        boardButtons[0][2].setOnClickListener(v -> {
            if (!gameEnded) {
                makeMark(0, 2);
            }
        });
        boardButtons[1][0].setOnClickListener(v -> {
            if (!gameEnded) {
                makeMark(1, 0);
            }
        });
        boardButtons[1][1].setOnClickListener(v -> {
            if (!gameEnded) {
                makeMark(1, 1);
            }
        });
        boardButtons[1][2].setOnClickListener(v -> {
            if (!gameEnded) {
                makeMark(1, 2);
            }
        });
        boardButtons[2][0].setOnClickListener(v -> {
            if (!gameEnded) {
                makeMark(2, 0);
            }
        });
        boardButtons[2][1].setOnClickListener(v -> {
            if (!gameEnded) {
                makeMark(2, 1);
            }
        });
        boardButtons[2][2].setOnClickListener(v -> {
            if (!gameEnded) {
                makeMark(2, 2);
            }
        });

        Button playAgain = findViewById(R.id.ttt_again);
        playAgain.setOnClickListener(v -> playAgain());
    }

    private void playAgain() {
        gameEnded = false;

        gameBoard = new String[3][3];

        turnO = (int) (Math.random() * 2) == 0;
        turnView.setText(turnO ? R.string.ttt_turn_o : R.string.ttt_turn_x);

        winnerView.setText("");

        for (ImageButton[] row : boardButtons) {
            for (ImageButton square : row) {
                square.setImageDrawable(null);
            }
        }
    }

    private void makeMark(int row, int col) {
        if (gameBoard[row][col] == null) {
            gameBoard[row][col] = turnO ? "O" : "X";
//            boardButtons[row][col].setImageResource(turnO ? R.drawable.ic_tic_tac_toe_o : R.drawable.ic_tic_tac_toe_x);
            boardButtons[row][col].setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                    turnO ? R.drawable.ic_tic_tac_toe_o : R.drawable.ic_tic_tac_toe_x, getTheme()));
            hasGameEnded();
            switchTurn();
        }
    }

    private boolean checkWin() {
        for (String[] row : gameBoard) {
            if (row[0] != null && row[1] != null && row[2] != null
                    && row[0].equals(row[1]) && row[1].equals(row[2])) {
                return true;
            }
        }

        for (int col = 0; col < gameBoard[0].length; col++) {
            if (gameBoard[0][col] != null && gameBoard[1][col] != null && gameBoard[2][col] != null
                    && gameBoard[0][col].equals(gameBoard[1][col]) && gameBoard[1][col].equals(gameBoard[2][col])) {
                return true;
            }
        }

        return gameBoard[0][0] != null && gameBoard[1][1] != null && gameBoard[2][2] != null
                && gameBoard[0][0].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][2])
                || gameBoard[2][0] != null && gameBoard[1][1] != null && gameBoard[0][2] != null
                && gameBoard[2][0].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[0][2]);
    }

    private void setWinner() {
        winnerView.setText(turnO ? R.string.ttt_win_o : R.string.ttt_win_x);
    }

    private boolean isBoardFull() {
        for (String[] row : gameBoard) {
            for (String square : row) {
                if (square == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void hasGameEnded() {
        if (checkWin()) {
            setWinner();
            gameEnded = true;
        } else if (isBoardFull()) {
            winnerView.setText(R.string.ttt_tie);
            gameEnded = true;
        }
    }

    private void switchTurn() {
        turnO = !turnO;
        turnView.setText(turnO ? R.string.ttt_turn_o : R.string.ttt_turn_x);
    }
}