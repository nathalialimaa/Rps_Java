package com.nathalia.rps.strategy;

import com.nathalia.rps.model.*;

public class CyclicStrategy implements ComputerStrategy {
    private int idx = 0;
    @Override
    public Move nextMove() {
        Move move = MoveFactory.create(MoveType.values()[idx]);
        idx = (idx + 1) % 3;
        return move;
    }
    @Override public String name() { return "Cíclica"; }
}
