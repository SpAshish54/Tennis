package com.learning.hello.model;

import com.learning.hello.contoller.exception.UnsupportedActionException;

public class TennisGameModel {
    private static TennisGameModel instance;

    private int scorePlayer1;
    private int scorePlayer2;
    private int setPlayer1;
    private int setPlayer2;
    private int matchPlayer1;
    private int matchPlayer2;

    private TennisGameModel() {
        // Initialize game state
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        setPlayer1 = 0;
        setPlayer2 = 0;
        matchPlayer1 = 0;
        matchPlayer2 = 0;
    }

    public static TennisGameModel get() {
        if (instance == null) {
            instance = new TennisGameModel();
        }
        return instance;
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public int getSetPlayer1() {
        return setPlayer1;
    }

    public int getSetPlayer2() {
        return setPlayer2;
    }

    public int getMatchPlayer1() {
        return matchPlayer1;
    }

    public int getMatchPlayer2() {
        return matchPlayer2;
    }

    public void updateGameState(int player) {
        if (player == 1) {
            scorePlayer1++;
        } else if (player == 2) {
            scorePlayer2++;
        }
    }

    public void updateGameLogic() {
        if (scorePlayer1 >= 4 && scorePlayer1 - scorePlayer2 >= 2) {
            // Player 1 wins the game
            scorePlayer1 = 0;
            scorePlayer2 = 0;
            setPlayer1++;
        } else if (scorePlayer2 >= 4 && scorePlayer2 - scorePlayer1 >= 2) {
            // Player 2 wins the game
            scorePlayer1 = 0;
            scorePlayer2 = 0;
            setPlayer2++;
        }

        if (setPlayer1 >= 6 && setPlayer1 - setPlayer2 >= 2) {
            // Player 1 wins the set
            setPlayer1 = 0;
            setPlayer2 = 0;
            matchPlayer1++;
        } else if (setPlayer2 >= 6 && setPlayer2 - setPlayer1 >= 2) {
            // Player 2 wins the set
            setPlayer1 = 0;
            setPlayer2 = 0;
            matchPlayer2++;
        }

        if (matchPlayer1 >= 2) {
            // Player 1 wins the match
            resetGame();
            matchPlayer1 = 0;
        } else if (matchPlayer2 >= 2) {
            // Player 2 wins the match
            resetGame();
            matchPlayer2 = 0;
        }

        // Implement deuce and advantage logic
        if (scorePlayer1 >= 3 && scorePlayer2 >= 3) {
            if (scorePlayer1 == scorePlayer2) {
                // Deuce
                scorePlayer1 = 3;
                scorePlayer2 = 3;
            } else if (Math.abs(scorePlayer1 - scorePlayer2) == 1) {
                // Advantage
                if (scorePlayer1 > scorePlayer2) {
                    scorePlayer1 = 4;
                    scorePlayer2 = 3;
                } else {
                    scorePlayer1 = 3;
                    scorePlayer2 = 4;
                }
            }
        }
    }

    public void performAction(String action) throws UnsupportedActionException {
        // Handle specific game actions if needed
        throw new UnsupportedActionException("Unsupported action: " + action);
    }

    private void resetGame() {
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        setPlayer1 = 0;
        setPlayer2 = 0;
    }
}
