package view;

import model.Game;
import model.Player;
import model.PlayerType;

import java.util.Scanner;

public class GameView {
    Scanner scanner;
    public GameView() {
        scanner = new Scanner(System.in);
    }
    public Game makeGame() {
        System.out.println("Welcome to Connect Four!");
        System.out.println("Enter the first player's name: ");
        String playerOneName = scanner.nextLine();
        System.out.println("Enter the second player's name: ");
        String playerTwoName = scanner.nextLine();
        Player playerOne = new Player(PlayerType.PLAYER_ONE, playerOneName.toUpperCase()),
                playerTwo = new Player(PlayerType.PLAYER_TWO, playerTwoName.toUpperCase());
        Game game = new Game(playerOne, playerTwo);
        return game;
    }
}
