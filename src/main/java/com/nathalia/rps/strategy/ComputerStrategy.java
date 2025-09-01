package com.nathalia.rps.strategy;

import com.nathalia.rps.model.Move;

public interface ComputerStrategy {
    Move nextMove();
    String name();
}
