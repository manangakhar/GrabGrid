package com.example.grabgrid.Event;

import com.example.grabgrid.Entities.Coordinate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

public class ClickEvent extends Event {
    public ClickEvent(Coordinate coordinate) {
        super(coordinate);
    }
}
