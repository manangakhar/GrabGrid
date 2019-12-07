package com.example.grabgrid.Event;

import com.example.grabgrid.Entities.Coordinate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor
public abstract class Event {
    private final Coordinate coordinate;
}
