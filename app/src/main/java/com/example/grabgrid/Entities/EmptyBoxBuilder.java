package com.example.grabgrid.Entities;

import com.example.grabgrid.Enums.BoxType;
import com.example.grabgrid.Interfaces.BoxBuilder;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

public class EmptyBoxBuilder implements BoxBuilder {
    protected Box box;
    /**
     * Create box.
     */
    @Override
    public Box createBox(Coordinate coordinate, BoxType boxType) {
        this.box = Box.builder().boxType(boxType).coordinate(coordinate).build();
        return this.box;
    }
}

