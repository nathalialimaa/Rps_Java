package com.nathalia.rps.model;

public class Scissors implements Move {
    @Override public String name() { return "Tesoura"; }
    @Override public String emoji() { return "✌️ "; }
    @Override public MoveType type() { return MoveType.SCISSORS; }
}
