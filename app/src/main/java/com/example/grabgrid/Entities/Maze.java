package com.example.grabgrid.Entities;

import com.example.grabgrid.Enums.MazeType;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.example.grabgrid.Enums.BoxType.UNVISITED;
import static com.example.grabgrid.Enums.BoxType.UNVISITED_END;
import static com.example.grabgrid.Enums.BoxType.VISITED;

@Getter
@Builder
public class Maze {
    private Size size;
    private Coordinate currentPosition;
    private MazeType mazeType;
    private Box[][] boxes;

    public List<Coordinate> getAllReachableCoordinates() {
        List<Coordinate> visitedNodes = getVisitedNodes();
        List<Coordinate> reachableNodes = new ArrayList<>();

        for(Coordinate c : visitedNodes) {
            Box box = this.getBoxes()[c.getRow()][c.getColumn()];
            List<Coordinate> notVisitedNodes = getUnVisitedNodes(box.getNeighbours());
            reachableNodes.addAll(notVisitedNodes);
        }
        return reachableNodes;
    }

    public Reward handleClickEvent(Coordinate coordinate) {
        return Reward.createReward();
    }

    private List<Coordinate> getUnVisitedNodes(List<Coordinate> coordinates) {
        List<Coordinate> unVisitedNodes = new ArrayList<>();

        for(Coordinate c: coordinates) {
            Box box = this.getBoxes()[c.getRow()][c.getColumn()];
            if (UNVISITED_END.equals(box.getBoxType())
                    || UNVISITED.equals(box.getBoxType())) {
                unVisitedNodes.add(box.getCoordinate());
            }
        }

        return unVisitedNodes;
    }

    private List<Coordinate> getVisitedNodes() {
        List<Coordinate> visitedNodes = new ArrayList<>();
        Size size = this.getSize();
        for(int i = 0; i < size.getRows(); ++i) {
            for(int j = 0; j <size.getColumns(); ++j) {
                if (VISITED.equals(this.getBoxes()[i][j].getBoxType())) {
                    visitedNodes.add(this.getBoxes()[i][j].getCoordinate());
                }
            }
        }

        return visitedNodes;
    }
}
