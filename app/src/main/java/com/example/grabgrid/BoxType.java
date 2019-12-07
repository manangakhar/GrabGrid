package com.example.grabgrid;

//@Getter
//@AllArgsConstructor
public enum BoxType {
    START(0, "0"),
    RIGHT(1, ">"),
    DOWN(2, "v"),
    LEFT(3, "<"),
    UP(4, "^"),
    VISITED(0, "X"),
    UNVISITED(0, "O"),
    UNVISITED_END(0, "T"),
    TRANSPORT(0, "R"),
    PAY(0, "P"),
    FOOD(0, "FOOD"),
    BLANK(0," ");

    BoxType(int helperData, String representation) {
        this.helperData = helperData;
        this.representation = representation;
    }

    public int getHelperData() {
        return helperData;
    }

    public String getRepresentation() {
        return representation;
    }

    private final int helperData;
    private final String representation;
    }
