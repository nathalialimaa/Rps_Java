package com.nathalia.rps.strategy;

import com.nathalia.rps.model.*;

public class AlwaysRockStrategy implements ComputerStrategy {
    @Override
    public Move nextMove() {
        return MoveFactory.create(MoveType.ROCK);
    }
    @Override public String name() { return "Sempre Pedra"; }
}
