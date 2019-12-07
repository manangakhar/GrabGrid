package com.example.grabgrid.Entities;

import com.example.grabgrid.Enums.MazeType;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Maze {
    private Size size;
    private Coordinate currentPosition;
    private MazeType mazeType;
    private Box[][] boxes;
}
