package com.nathalia.rps.model;

public class Rock implements Move {
    @Override public String name() { return "Pedra"; }
    @Override public String emoji() { return "✊ "; }
    @Override public MoveType type() { return MoveType.ROCK; }
}
