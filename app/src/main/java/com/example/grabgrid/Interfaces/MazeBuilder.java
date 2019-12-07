package com.example.grabgrid.Interfaces;

import com.example.grabgrid.Entities.Maze;
import com.example.grabgrid.Entities.Size;
import com.example.grabgrid.Enums.MazeType;

/**
 * Maze builder interface.
 */
public interface MazeBuilder {

    /**
     * Create maze.
     */
    public void createMaze(Size size);

    /**
     * Get a maze
     * @return Maze
     */
    public Maze getMaze();

}
