package com.nathalia.rps.model;

/**
 * Representa um movimento do jogo.
 * Métodos: name() e emoji() usados na UI; type() para lógica.
 */
public interface Move {
    String name();
    String emoji();
    MoveType type();
}
