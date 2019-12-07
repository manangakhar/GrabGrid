package com.example.grabgrid.Entities;

import com.example.grabgrid.Enums.BoxType;
import com.example.grabgrid.Enums.MazeType;
import com.example.grabgrid.Interfaces.BoxBuilder;
import com.example.grabgrid.Interfaces.MazeBuilder;

import static com.example.grabgrid.Enums.BoxType.*;

public class SimpleMazeBuilder implements MazeBuilder {
    protected Maze maze;
    private static final BoxType[][] initialGrid = new BoxType[][]
            {
                    {UNVISITED_END, LEFT, UNVISITED, LEFT, UNVISITED, RIGHT, UNVISITED, RIGHT, UNVISITED_END},
                    {UP, BLANK, UP, BLANK, UP, BLANK, UP, BLANK, UP},
                    {UNVISITED, LEFT, UNVISITED, LEFT, UNVISITED, RIGHT, UNVISITED, RIGHT, UNVISITED},
                    {UP, BLANK, UP, BLANK, UP, BLANK, UP, BLANK, UP},
                    {UNVISITED, LEFT, VISITED, LEFT, START, RIGHT, UNVISITED, RIGHT, UNVISITED},
                    {DOWN, BLANK, DOWN, BLANK, DOWN, BLANK, DOWN, BLANK, DOWN},
                    {UNVISITED, LEFT, UNVISITED, LEFT, UNVISITED, RIGHT, UNVISITED, RIGHT, UNVISITED},
                    {DOWN, BLANK, DOWN, BLANK, DOWN, BLANK, DOWN, BLANK, DOWN},
                    {UNVISITED_END, LEFT, UNVISITED, LEFT, UNVISITED, RIGHT, UNVISITED, RIGHT, UNVISITED_END}
            };

    @Override
    public void createMaze(Size size) {
        int cl = size.getColumns();
        int row = size.getRows();
        Box[][] boxes = new Box[row][cl];
        Coordinate midCoordinate = Coordinate.builder().row(size.getRows() / 2)
                .column(size.getColumns() / 2).build();

        for (int i = 0; i < size.getRows(); ++i) {
            for (int j = 0; j < size.getColumns(); ++j) {
                switch (initialGrid[i][j]) {
                    case START:
                        boxes[i][j] = buildEmptyBox(i, j, START);
                        break;
                    case UNVISITED_END:
                        boxes[i][j] = buildTerminalEndBox(i, j, UNVISITED_END);
                        break;
                    case UP:
                        boxes[i][j] = buildArrowBox(i, j, UP);
                        break;
                    case DOWN:
                        boxes[i][j] = buildArrowBox(i, j, DOWN);
                        break;
                    case LEFT:
                        boxes[i][j] = buildArrowBox(i, j, LEFT);
                        break;
                    case RIGHT:
                        boxes[i][j] = buildArrowBox(i, j, RIGHT);
                        break;
                    case BLANK:
                        boxes[i][j] = buildEmptyBox(i, j, BLANK);
                        break;
                    case UNVISITED:
                        boxes[i][j] = buildEmptyBox(i, j, UNVISITED);
                        break;
                    case VISITED:
                        boxes[i][j] = buildEmptyBox(i, j, VISITED);
                        break;
                    default:
                        boxes[i][j] = buildEmptyBox(i, j, BLANK);
                }
            }
        }

        this.maze = Maze.builder().currentPosition(midCoordinate).mazeType(MazeType.SIMPLE).boxes(boxes)
                .size(size).build();
    }

    @Override
    public Maze getMaze() {
        return this.maze;
    }

    private Box buildEmptyBox(int r, int c, BoxType boxType) {
        return new EmptyBoxBuilder().createBox(Coordinate.builder().row(r).column(c).build(), boxType);
    }

    private Box buildTerminalEndBox(int r, int c, BoxType boxType) {
        return new TerminalBoxBuilder().createBox(Coordinate.builder().row(r).column(c).build(), boxType);
    }

    private Box buildArrowBox(int r, int c, BoxType boxType) {
        return new ArrowBoxBuilder().createBox(Coordinate.builder().row(r).column(c).build(), boxType);
    }
}
