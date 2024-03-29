package com.example.grabgrid;

import com.example.grabgrid.Entities.Box;
import com.example.grabgrid.Entities.Maze;
import com.example.grabgrid.Entities.SimpleMazeBuilder;
import com.example.grabgrid.Entities.Size;
import com.example.grabgrid.Enums.MazeType;
import com.example.grabgrid.Handler.MazeFileHandler;
import com.example.grabgrid.Interfaces.MazeBuilder;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MazeGenerator {
    private final static Size size = Size.builder().rows(9).columns(9).build();

    public static Maze getOrCreateMaze(Size size, MazeType mazeType) {
        Maze maze = null;
        try {
            return Maze.fromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createMaze(size, mazeType);
    }

    public static Maze createMaze(Size size, MazeType mazeType) {

        MazeBuilder mazeBuilder;
        if (MazeType.SIMPLE.equals(mazeType)) {
            mazeBuilder = new SimpleMazeBuilder();
        } else {
            throw new UnsupportedOperationException();
        }

        mazeBuilder.createMaze(size);
        return mazeBuilder.getMaze();
    }

    public static void main(String[] args) {
        final Size size = Size.builder().rows(9).columns(9).build();
        Maze maze = MazeGenerator.createMaze(size, MazeType.SIMPLE);
        Box[][] boxes = maze.getBoxes();

        for (int i = 0; i < maze.getSize().getRows(); ++i) {
            System.out.println();
            for (int j = 0; j < maze.getSize().getColumns(); ++j) {
                if (boxes[i][j].getNeighbours() == null) {
                    System.out.print(" - ");
                } else {
                    System.out.println(boxes[i][j].getNeighbours());
                }
            }
        }
    }
}
