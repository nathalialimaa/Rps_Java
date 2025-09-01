package com.nathalia.rps.model;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Factory central para criar Moves.
 * Fornece tanto create(MoveType) quanto randomMove() para compatibilidade com variações de MainFrame.
 */
public class MoveFactory {

    public static Move create(MoveType type) {
        return switch (type) {
            case ROCK -> new Rock();
            case PAPER -> new Paper();
            case SCISSORS -> new Scissors();
        };
    }

    /** Retorna um Move aleatório. */
    public static Move randomMove() {
        MoveType[] values = MoveType.values();
        int idx = ThreadLocalRandom.current().nextInt(values.length);
        return create(values[idx]);
    }
}
