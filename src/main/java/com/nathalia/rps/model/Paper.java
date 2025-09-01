package com.nathalia.rps.model;

public class Paper implements Move {
    @Override public String name() { return "Papel"; }
    @Override public String emoji() { return "✋ "; }
    @Override public MoveType type() { return MoveType.PAPER; }
}
