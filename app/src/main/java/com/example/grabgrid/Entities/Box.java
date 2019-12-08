package com.example.grabgrid.Entities;

import com.example.grabgrid.Enums.BoxType;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Box {
    private Coordinate coordinate;
    private BoxType boxType;
    private List<Coordinate> neighbours;
    private Reward reward;
}

