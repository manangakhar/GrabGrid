package com.example.grabgrid.Entities;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Coordinate {
    private int row;
    private int column;
}
