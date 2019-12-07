package com.example.grabgrid;


import com.example.grabgrid.Enums.BoxType;

import static com.example.grabgrid.Enums.BoxType.*;

public class Utils {

    public static final BoxType[][] initialGrid = new BoxType[][]
            {
                    {UNVISITED_END, LEFT, UNVISITED, LEFT, UNVISITED, RIGHT, UNVISITED, RIGHT, UNVISITED_END},
                    {UP, BLANK, UP, BLANK, UP, BLANK, UP, BLANK, UP},
                    {UNVISITED, LEFT, UNVISITED, LEFT, UNVISITED, RIGHT, UNVISITED, RIGHT, UNVISITED},
                    {UP, BLANK, UP, BLANK, UP, BLANK, UP, BLANK, UP},
                    {UNVISITED, LEFT, UNVISITED, LEFT, START, RIGHT, UNVISITED, RIGHT, UNVISITED},
                    {DOWN, BLANK, DOWN, BLANK, DOWN, BLANK, DOWN, BLANK, DOWN},
                    {UNVISITED, LEFT, UNVISITED, LEFT, UNVISITED, RIGHT, UNVISITED, RIGHT, UNVISITED},
                    {DOWN, BLANK, DOWN, BLANK, DOWN, BLANK, DOWN, BLANK, DOWN},
                    {UNVISITED_END, LEFT, UNVISITED, LEFT, UNVISITED, RIGHT, UNVISITED, RIGHT, UNVISITED_END}
            };

    public static void main(String[] args) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                BoxType boxType = initialGrid[i][j];
                System.out.print(boxType.getRepresentation());
            }
            System.out.println();
        }
    }
}
