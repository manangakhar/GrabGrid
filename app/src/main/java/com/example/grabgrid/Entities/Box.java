package com.example.grabgrid.Entities;

import com.example.grabgrid.Enums.BoxType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Box {
    private Coordinate coordinate;
    private BoxType boxType;
}
