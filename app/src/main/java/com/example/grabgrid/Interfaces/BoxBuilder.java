package com.example.grabgrid.Interfaces;

import com.example.grabgrid.Entities.Box;
import com.example.grabgrid.Entities.Coordinate;
import com.example.grabgrid.Enums.BoxType;

public interface BoxBuilder {

    /**
     * Create box.
     */
    public Box createBox(Coordinate coordinate, BoxType boxType);
}
