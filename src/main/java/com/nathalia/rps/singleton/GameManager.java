package com.nathalia.rps.singleton;

import com.nathalia.rps.model.Move;
import com.nathalia.rps.model.MoveType;

public class GameManager {
    private static final GameManager INSTANCE = new GameManager();
    private int userScore = 0;
    private int cpuScore = 0;
    private int roundCount = 0;
    private final int maxRounds = 5;

    private GameManager() {}

    public static GameManager getInstance() {
        return INSTANCE;
    }

    public int decide(Move user, Move cpu) {
        if (user.type() == cpu.type()) return 0;
        boolean userWins =
                (user.type() == MoveType.ROCK && cpu.type() == MoveType.SCISSORS) ||
                        (user.type() == MoveType.PAPER && cpu.type() == MoveType.ROCK) ||
                        (user.type() == MoveType.SCISSORS && cpu.type() == MoveType.PAPER);
        if (userWins) {
            userScore++;
            roundCount++;
            return 1;
        } else {
            cpuScore++;
            roundCount++;
            return -1;
        }
    }

    public void reset() {
        userScore = 0;
        cpuScore = 0;
        roundCount = 0;
    }

    public int getUserScore() { return userScore; }
    public int getCpuScore() { return cpuScore; }
    public int getRoundCount() { return roundCount; }
    public int getMaxRounds() { return maxRounds; }
}
