package com.nathalia.rps.strategy;

import com.nathalia.rps.model.*;

import java.util.concurrent.ThreadLocalRandom;

public class RandomStrategy implements ComputerStrategy {
    @Override
    public Move nextMove() {
        int r = ThreadLocalRandom.current().nextInt(3);
        return MoveFactory.create(MoveType.values()[r]);
    }
    @Override public String name() { return "Aleatória"; }
}
