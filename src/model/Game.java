package model;

import exceptions.ColumnIsFullException;
import exceptions.InvalidColumnException;

public class Game {
    private Disc[][] board;
    private Player playerOne;
    private Player playerTwo;
    private final int numRows = 6;
    private final int numCols = 7;
    private boolean turn;

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        // make sure the player who will start is a random player
        randomizePlayers();
        this.board = new Disc[numRows][numCols];
        this.turn = true;
    }

    public boolean checkIfGameIsOver() {
        // Check if the game is over
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void randomizePlayers() {
        if (Math.random() < 0.5) {
            Player tempPlayer = playerOne;
            playerOne = playerTwo;
            playerTwo = tempPlayer;
        }
    }

    public void endTurn() {
        turn = !turn;
    }

    public Player getTurnPlayer() {
        return turn ? playerOne : playerTwo;
    }

    private boolean checkHorizontal(int row, Player turnPlayer) {
        int count = 0;
        for (int i = 0; i < this.numCols; i++) {
            if (this.board[row][i] != null && this.board[row][i].getPlayer().equals(turnPlayer)) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    private boolean checkVertical(int col, Player turnPlayer) {
        int count = 0;
        for (int i = 0; i < this.numRows; i++) {
            if (this.board[i][col] != null && this.board[i][col].getPlayer().equals(turnPlayer)) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    private boolean checkDiagonal(int row, int col, Player turnPlayer) {
        // check the diagonal from bottom left to top right
        int count = 0;
        int i = row;
        int j = col;
        while (i > 0 && j > 0) {
            i--;
            j--;
        }
        while (i < numRows && j < numCols) {
            if (this.board[i][j] != null && this.board[i][j].getPlayer().equals(turnPlayer)) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
            i++;
            j++;
        }

        // check the diagonal from bottom right to top left
        count = 0;
        i = row;
        j = col;
        while (i < numRows - 1 && j > 0) {
            i++;
            j--;
        }
        while (i >= 0 && j < numCols) {
            if (this.board[i][j] != null && this.board[i][j].getPlayer().equals(turnPlayer)) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
            i--;
            j++;
        }

        // if no diagonal win was found
        return false;
    }

    public boolean checkWin(int row, int col, Player turnPlayer) {
        // Check if the player has won
        return checkHorizontal(row, turnPlayer) || checkVertical(col, turnPlayer) || checkDiagonal(row, col, turnPlayer);
    }

    public boolean insertDisc(int col) throws ColumnIsFullException, InvalidColumnException {
        // Whose disc to insert?
        Disc disc = getTurnPlayer().getType() == PlayerType.PLAYER_ONE ? new PlayerOneDisc(getTurnPlayer()) : new PlayerTwoDisc(getTurnPlayer());
        // Check if the column is valid
        if (col < 0 || col >= numCols) {
            throw new InvalidColumnException();
        }
        // Check if the column is full
        if (board[0][col] != null) {
            throw new ColumnIsFullException();
        }
        // Insert the disc
        for (int i = 0; i < numRows; i++) {
            if (board[i][col] != null) {
                board[i - 1][col] = disc;
                return checkWin(i - 1, col, getTurnPlayer());
            }
        }
        board[numRows - 1][col] = disc;
        return checkWin(numRows - 1, col, getTurnPlayer());
    }
}
