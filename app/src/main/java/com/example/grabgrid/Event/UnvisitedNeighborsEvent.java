package com.example.grabgrid.Event;


import com.example.grabgrid.Entities.Coordinate;

import java.util.List;

import lombok.Getter;

@Getter
public class UnvisitedNeighborsEvent extends Event {
    private final List<Coordinate> neighbors;

    public UnvisitedNeighborsEvent(Coordinate coordinate, List<Coordinate> neighbors) {
        super(coordinate);
        this.neighbors = neighbors;
    }
}
