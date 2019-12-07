package com.example.grabgrid.Entities;

import com.example.grabgrid.Enums.BoxType;
import com.example.grabgrid.Interfaces.BoxBuilder;

public class TerminalBoxBuilder implements BoxBuilder {
    protected Box box;

    /**
     * Create box.
     */
    public Box createBox(Coordinate coordinate, BoxType boxType) {
        this.box = Box.builder().boxType(boxType).coordinate(coordinate).build();
        return this.box;
    }
}
