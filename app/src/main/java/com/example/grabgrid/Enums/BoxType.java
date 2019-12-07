package com.example.grabgrid.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoxType {
    START("0"),
    RIGHT(">"),
    DOWN("v"),
    LEFT("<"),
    UP("^"),
    VISITED("X"),
    UNVISITED("O"),
    UNVISITED_END("T"),
    TRANSPORT("R"),
    PAY("P"),
    FOOD("F"),
    MOVIE("M"),
    OPTION("?"),
    BLANK(" ");

    private final String representation;
}

