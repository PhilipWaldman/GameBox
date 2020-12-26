package com.gamebox.ui.games;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.gamebox.R;

public class TicTacToeActivity extends AppCompatActivity {

    /**
     * A 2D array with the {@code ImageButton}s that represent each cell.
     */
    private ImageButton[][] boardButtons;
    /**
     * The {@code TextView} that shows whose turn it is.
     */
    private TextView turnView;
    /**
     * The {@code TextView} that shows who won.
     */
    private TextView winnerView;
    /**
     * Whether it is the turn of O.
     */
    private boolean turnO;
    /**
     * Whether the game has ended.
     */
    private boolean gameEnded = false;
    /**
     * A 2D array with the values that are in each cell. {@code null} means the cell is empty, and "X" and "O" mean, well that's kind of obvious.
     */
    private String[][] gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        // Find winnerView
        winnerView = findViewById(R.id.tic_tac_toe_winner);

        // It is randomized who starts
        turnO = (int) (Math.random() * 2) == 0;
        turnView = findViewById(R.id.tic_tac_toe_turn);
        turnView.setText(turnO ? R.string.ttt_turn_o : R.string.ttt_turn_x);

        gameBoard = new String[3][3];

        // Initialize boardButtons
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

        for (int i = 0; i < boardButtons.length; i++) {
            for (int j = 0; j < boardButtons[i].length; j++) {
                int r = i;
                int c = j;
                boardButtons[r][c].setOnClickListener(v -> {
                    if (!gameEnded) {
                        makeMark(r, c);
                    }
                });
            }
        }

        // Initialize play again button
        Button playAgain = findViewById(R.id.ttt_again);
        playAgain.setOnClickListener(v -> playAgain());
    }

    /**
     * Resets the board to start a new game.
     */
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

    /**
     * Attempt to make a mark on the specified cell. If the cell already has a mark, nothing happens. Otherwise, the appropriate mark is made, it is checked whether this move ends the game, and the turn is switched.
     *
     * @param row The row to make the mark on.
     * @param col The column to make the mark on.
     */
    private void makeMark(int row, int col) {
        if (gameBoard[row][col] == null) {
            gameBoard[row][col] = turnO ? "O" : "X";
            boardButtons[row][col].setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                    turnO ? R.drawable.ic_tic_tac_toe_o : R.drawable.ic_tic_tac_toe_x, getTheme()));
            hasGameEnded();
            switchTurn();
        }
    }

    /**
     * Checks whether there are marks that are in the correct places to win the game.
     *
     * @return Whether someone has won.
     */
    private boolean checkWin() {
        // Check whether there is a row where all marks are of the same type.
        for (String[] row : gameBoard) {
            if (row[0] != null && row[1] != null && row[2] != null
                    && row[0].equals(row[1]) && row[1].equals(row[2])) {
                return true;
            }
        }

        // Check whether there is a column where all marks are of the same type.
        for (int col = 0; col < gameBoard[0].length; col++) {
            if (gameBoard[0][col] != null && gameBoard[1][col] != null && gameBoard[2][col] != null
                    && gameBoard[0][col].equals(gameBoard[1][col]) && gameBoard[1][col].equals(gameBoard[2][col])) {
                return true;
            }
        }

        // Check whether there is a diagonal where all marks are of the same type.
        return gameBoard[0][0] != null && gameBoard[1][1] != null && gameBoard[2][2] != null
                && gameBoard[0][0].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][2])
                || gameBoard[2][0] != null && gameBoard[1][1] != null && gameBoard[0][2] != null
                && gameBoard[2][0].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[0][2]);
    }

    /**
     * Checks whether the board is full.
     *
     * @return Whether the board is full.
     */
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

    /**
     * Checks is the game has ended. If there was a winning move, it sets the winner. If the board is full it's a tie.
     */
    private void hasGameEnded() {
        if (checkWin()) {
            winnerView.setText(turnO ? R.string.ttt_win_o : R.string.ttt_win_x);
            gameEnded = true;
        } else if (isBoardFull()) {
            winnerView.setText(R.string.ttt_tie);
            gameEnded = true;
        }
    }

    /**
     * Switches the turn to the other.
     */
    private void switchTurn() {
        turnO = !turnO;
        turnView.setText(turnO ? R.string.ttt_turn_o : R.string.ttt_turn_x);
    }
}