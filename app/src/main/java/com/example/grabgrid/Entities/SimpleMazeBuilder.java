package com.example.grabgrid.Entities;

import com.example.grabgrid.Enums.BoxType;
import com.example.grabgrid.Enums.MazeType;
import com.example.grabgrid.Interfaces.BoxBuilder;
import com.example.grabgrid.Interfaces.MazeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        this.maze = Maze.builder().currentPosition(midCoordinate).mazeType(MazeType.SIMPLE)
                .boxes(buildNeighbours(boxes))
                .size(size).build();
    }

    @Override
    public Maze getMaze() {
        return this.maze;
    }

    private Box[][] buildNeighbours(Box boxes[][]) {
        // Pattern
        // First column
        boxes[2][0].setNeighbours(Arrays.asList(Coordinate.builder().row(0).column(0).build()));
        boxes[4][0].setNeighbours(Arrays.asList(Coordinate.builder().row(2).column(0).build()
                ,Coordinate.builder().row(6).column(0).build()));
        boxes[6][0].setNeighbours(Arrays.asList(Coordinate.builder().row(8).column(0).build()));

        // mid column
        boxes[0][2].setNeighbours(Arrays.asList(Coordinate.builder().row(0).column(0).build()));
        boxes[2][2].setNeighbours(Arrays.asList(Coordinate.builder().row(2).column(0).build(),
                Coordinate.builder().row(0).column(2).build()));
        boxes[4][2].setNeighbours(Arrays.asList(Coordinate.builder().row(4).column(0).build(),
                Coordinate.builder().row(2).column(2).build(),
                Coordinate.builder().row(6).column(2).build()));
        boxes[6][2].setNeighbours(Arrays.asList(Coordinate.builder().row(6).column(0).build(),
                Coordinate.builder().row(8).column(2).build()));
        boxes[8][2].setNeighbours(Arrays.asList(Coordinate.builder().row(8).column(0).build()));

        // mid column
        boxes[0][4].setNeighbours(Arrays.asList(Coordinate.builder().row(0).column(2).build(),
                Coordinate.builder().row(0).column(6).build()));
        boxes[2][4].setNeighbours(Arrays.asList(Coordinate.builder().row(0).column(4).build(),
                Coordinate.builder().row(2).column(2).build(),
                Coordinate.builder().row(2).column(6).build()));
        boxes[4][4].setNeighbours(Arrays.asList(Coordinate.builder().row(4).column(2).build(),
                Coordinate.builder().row(4).column(6).build(),
                Coordinate.builder().row(6).column(4).build(),
                Coordinate.builder().row(2).column(4).build()));
        boxes[6][4].setNeighbours(Arrays.asList(Coordinate.builder().row(6).column(2).build(),
                Coordinate.builder().row(6).column(6).build(),
                Coordinate.builder().row(8).column(4).build()));
        boxes[8][4].setNeighbours(Arrays.asList(Coordinate.builder().row(8).column(2).build(),
                Coordinate.builder().row(8).column(6).build()));

        // mid column
        boxes[0][6].setNeighbours(Arrays.asList(Coordinate.builder().row(0).column(8).build()));
        boxes[2][6].setNeighbours(Arrays.asList(Coordinate.builder().row(0).column(6).build(),
                Coordinate.builder().row(2).column(8).build()));
        boxes[4][6].setNeighbours(Arrays.asList(Coordinate.builder().row(2).column(6).build(),
                Coordinate.builder().row(6).column(6).build(),
                Coordinate.builder().row(4).column(8).build()));
        boxes[6][6].setNeighbours(Arrays.asList(Coordinate.builder().row(6).column(8).build(),
                Coordinate.builder().row(8).column(6).build()));
        boxes[8][6].setNeighbours(Arrays.asList(Coordinate.builder().row(8).column(8).build()));

        // last column
        boxes[2][8].setNeighbours(Arrays.asList(Coordinate.builder().row(0).column(8).build()));
        boxes[4][8].setNeighbours(Arrays.asList(Coordinate.builder().row(2).column(8).build()
                ,Coordinate.builder().row(6).column(8).build()));
        boxes[6][8].setNeighbours(Arrays.asList(Coordinate.builder().row(8).column(8).build()));

        return boxes;
    }


    public List<Coordinate> getAllReachableCoordinates() {
        List<Coordinate> visitedNodes = getVisitedNodes();
        List<Coordinate> reachableNodes = new ArrayList<>();

        for(Coordinate c : visitedNodes) {
            Box box = this.maze.getBoxes()[c.getRow()][c.getColumn()];
            List<Coordinate> notVisitedNodes = getUnVisitedNodes(box.getNeighbours());
            reachableNodes.addAll(notVisitedNodes);
        }
        return reachableNodes;
    }

    private List<Coordinate> getUnVisitedNodes(List<Coordinate> coordinates) {
        List<Coordinate> unVisitedNodes = new ArrayList<>();

        for(Coordinate c: coordinates) {
            Box box = this.maze.getBoxes()[c.getRow()][c.getColumn()];
            if (UNVISITED_END.equals(box.getBoxType())
                    || UNVISITED.equals(box.getBoxType())) {
                unVisitedNodes.add(box.getCoordinate());
            }
        }

        return unVisitedNodes;
    }

    private List<Coordinate> getVisitedNodes() {
        List<Coordinate> visitedNodes = new ArrayList<>();
        Size size = this.maze.getSize();
        for(int i = 0; i < size.getRows(); ++i) {
            for(int j = 0; j <size.getColumns(); ++j) {
                if (VISITED.equals(this.maze.getBoxes()[i][j].getBoxType())) {
                    visitedNodes.add(this.maze.getBoxes()[i][j].getCoordinate());
                }
            }
        }

        return visitedNodes;
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
