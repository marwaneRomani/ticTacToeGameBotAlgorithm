package org.marwane;

import java.util.ArrayList;
import java.util.List;

public class Boot {

    private final int[][] WINNING_COMBINATIONS = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 },
            { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 },
            { 1, 5, 9 }, { 3, 5, 7 } };

    private int choice;

    public int setBootChoice(List<Integer> bootChoices, List<Integer> userChoices) {
        if (checkBootWinning(bootChoices, userChoices )) return choice;
        if (checkUserWinning(bootChoices, userChoices)) return choice;

        playBestMove(bootChoices, userChoices);

        return choice;
    }

    private boolean checkBootWinning(List<Integer> bootChoices, List<Integer> userChoices) {
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
                setBootChoice(bootChoices, emptyCell);
                choice = emptyCell;
                System.out.println("Boot chooses " + emptyCell);
                return true;
            }
        }
        return false;
    }

    private boolean checkUserWinning(List<Integer> bootChoices, List<Integer> userChoices) {
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
                setBootChoice(bootChoices, emptyCell);
                choice = emptyCell;
                System.out.println("Boot chooses " + emptyCell);
                return true;
            }
        }
        return false;
    }

    private void playBestMove(List<Integer> bootChoices, List<Integer> userChoices) {
        List<Integer> preferredCells = new ArrayList<>();

        if (!userChoices.contains(5) && !bootChoices.contains(5))
            preferredCells.add(5);
        if (!userChoices.contains(1) && !bootChoices.contains(1))
            preferredCells.add(1);
        if (!userChoices.contains(3) && !bootChoices.contains(3))
            preferredCells.add(3);
        if (!userChoices.contains(7) && !bootChoices.contains(7))
            preferredCells.add(7);
        if (!userChoices.contains(9) && !bootChoices.contains(9))
            preferredCells.add(9);

        if (!userChoices.contains(4) && !bootChoices.contains(4))
            preferredCells.add(4);

        if (!userChoices.contains(8) && !bootChoices.contains(8))
            preferredCells.add(8);

        if (!userChoices.contains(6) && !bootChoices.contains(6))
            preferredCells.add(6);

        if (!userChoices.contains(2) && !bootChoices.contains(2))
            preferredCells.add(2);


        if (preferredCells.isEmpty())
            return;

        int defencePosition = bestDefencePosition(bootChoices, userChoices, preferredCells);

        if (defencePosition != -1 && userChoices.size() > 3) {
            setBootChoice(bootChoices, defencePosition);
            choice = defencePosition;
            System.out.println("Boot defending on " + defencePosition);
            return;
        }

        int attackPosition = bestAttackPosition(bootChoices, userChoices, preferredCells);
        setBootChoice(bootChoices, attackPosition);
        choice = attackPosition;
        System.out.println("Boot attacking on " + attackPosition);

    }

    private int bestAttackPosition(List<Integer> bootChoices, List<Integer> userChoices, List<Integer> preferedCells) {
        for (int corner : preferedCells) {
            List<Integer> simulatedBootChoices = new ArrayList<>();
            for (Integer choice : bootChoices) {
                simulatedBootChoices.add(choice);
            }
            simulatedBootChoices.add(corner);
            int combinationCount = checkBootWinningCombination(simulatedBootChoices, userChoices);
            System.out.println("possible combinations for " + corner + " is " + combinationCount );
            if (combinationCount > 1) {
                System.out.println("chooosed cell : " + corner);
                return corner;
            }

        }

        return preferedCells.get(0);
    }

    private int bestDefencePosition(List<Integer> bootChoices, List<Integer> userChoices, List<Integer> preferedCells) {
        List<Integer> simulatedUserChoices = new ArrayList<>();

        for (Integer choice : userChoices) {
            simulatedUserChoices.add(choice);
        }

        for (int corner : preferedCells) {
            simulatedUserChoices.add(corner);
            int combinationCount = checkBootLoosingCombination(simulatedUserChoices, userChoices);
            if (combinationCount > 1)
                return corner;
        }

        return -1;
    }



    private int checkBootWinningCombination(List<Integer> bootChoices, List<Integer> userChoices) {
        int possibleWinningCombination = 0;

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

            if (count == 2 && emptyCell != 0) possibleWinningCombination++;

        }

        return possibleWinningCombination;
    }

    private int checkBootLoosingCombination(List<Integer> bootChoices, List<Integer> userChoices) {
        int possibleLoosingCombination = 0;

        for (int[] combination : WINNING_COMBINATIONS) {
            int count = 0;
            int emptyCell = 0;
            for (int cell : combination) {
                if (userChoices.contains(cell)) {
                    possibleLoosingCombination++;
                } else if (!bootChoices.contains(cell)) {
                    emptyCell = cell;
                }
            }
            if (count == 2 && emptyCell != 0) possibleLoosingCombination++;
        }

        return possibleLoosingCombination;
    }




    private void setBootChoice(List<Integer> bootChoices, int choice) {
        bootChoices.add(choice);
    }

}
