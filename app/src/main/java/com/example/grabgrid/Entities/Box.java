package com.example.grabgrid.Entities;

import com.example.grabgrid.Enums.BoxType;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Box {
    private Coordinate coordinate;
    private BoxType boxType;
    private List<Coordinate> neighbours;
}

