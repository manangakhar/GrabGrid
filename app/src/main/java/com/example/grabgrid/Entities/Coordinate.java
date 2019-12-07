package com.example.grabgrid.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Coordinate {
    private int row;
    private int column;
}
