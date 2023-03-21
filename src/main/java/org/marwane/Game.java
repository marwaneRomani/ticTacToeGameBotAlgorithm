package org.marwane;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private final int[][] WINNING_COMBINATIONS = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 },
                                                   { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 },
                                                   { 1, 5, 9 }, { 3, 5, 7 } };

    private ArrayList<Integer> userChoices = new ArrayList<>();
    private ArrayList<Integer> bootChoices = new ArrayList<>();

    private Random random = new Random();

    private Scanner scanner = new Scanner(System.in);

    public void play() {
        int counter = 0;
        while (counter < 9) {
            if (userChoices.size() > 2 || bootChoices.size() > 2 ) {
                checkForWinner();
            }

            if (counter % 2 == 0) {
                System.out.println("user round.");
                int choice = Integer.parseInt(scanner.nextLine());
                setUserChoice(choice);
                System.out.println(userChoices);
            }
            else {
                System.out.println("boot round.");
                setBootChoice();
                System.out.println(bootChoices);
            }
            counter++;
        }
    }



    public void setUserChoice(int choice) {
        userChoices.add(choice);
    }

    private void setBootChoice(int choice) {
        bootChoices.add(choice);
    }

    private void checkForWinner() {
        for (int[] combination : WINNING_COMBINATIONS) {
            System.out.println(combination);
        }
    }

    public void setBootChoice() {
        if (checkBootWinning()) return;
        if (checkUserWinning()) return;
        playBestMove();
    }

    private boolean checkBootWinning() {
        for (int[] combination : WINNING_COMBINATIONS) {
            int count = 0;
            int emptyCell = 0;
            for (int cell : combination) {
                if (bootChoices.contains(cell)) {
                    count++;
                } else if (!userChoices.contains(cell)) {
                    emptyCell = cell;
                }
            }
            if (count == 2 && emptyCell != 0) {
                setBootChoice(emptyCell);
                System.out.println("Boot chooses " + emptyCell);
                return true;
            }
        }
        return false;
    }

    private boolean checkUserWinning() {
        for (int[] combination : WINNING_COMBINATIONS) {
            int count = 0;
            int emptyCell = 0;
            for (int cell : combination) {
                if (userChoices.contains(cell)) {
                    count++;
                } else if (!bootChoices.contains(cell)) {
                    emptyCell = cell;
                }
            }
            if (count == 2 && emptyCell != 0) {
                setBootChoice(emptyCell);
                System.out.println("Boot chooses " + emptyCell);
                return true;
            }
        }
        return false;
    }

    private void playBestMove() {
        int[] preferredCells = { 5, 1, 3, 7, 9, 2, 4, 6, 8 };
        for (int cell : preferredCells) {
            if (!userChoices.contains(cell) && !bootChoices.contains(cell)) {
                setBootChoice(cell);
                System.out.println("Boot chooses " + cell);
                return;
            }
        }
    }
}

