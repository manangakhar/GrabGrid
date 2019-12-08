package com.example.grabgrid.Entities;

import com.example.grabgrid.Enums.MazeType;
import com.example.grabgrid.Handler.MazeFileHandler;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.grabgrid.Enums.BoxType.UNVISITED;
import static com.example.grabgrid.Enums.BoxType.UNVISITED_END;
import static com.example.grabgrid.Enums.BoxType.VISITED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Maze {
    private Size size;
    private Coordinate currentPosition;
    private MazeType mazeType;
    private Box[][] boxes;

    public List<Coordinate> getAllReachableCoordinates() {
        List<Coordinate> visitedNodes = getVisitedNodes();
        List<Coordinate> reachableNodes = new ArrayList<>();

        for (Coordinate c : visitedNodes) {
            Box box = this.getBoxes()[c.getRow()][c.getColumn()];
            List<Coordinate> notVisitedNodes = getUnVisitedNodes(box.getNeighbours());
            reachableNodes.addAll(notVisitedNodes);
        }
        return reachableNodes;
    }

    public Reward handleClickEvent(Coordinate coordinate) {
        if (UNVISITED.equals(this.boxes[coordinate.getRow()][coordinate.getColumn()].getBoxType())
                || UNVISITED_END.equals(this.boxes[coordinate.getRow()][coordinate.getColumn()].getBoxType())) {
            this.boxes[coordinate.getRow()][coordinate.getColumn()].setBoxType(VISITED);
        }
        Reward reward = Reward.createReward();
        this.boxes[coordinate.getRow()][coordinate.getColumn()].setReward(reward);
        persist();
        return reward;
    }

    private List<Coordinate> getUnVisitedNodes(List<Coordinate> coordinates) {
        List<Coordinate> unVisitedNodes = new ArrayList<>();

        for (Coordinate c : coordinates) {
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
        visitedNodes.add(new Coordinate(4, 4));
        Size size = this.getSize();
        for (int i = 0; i < size.getRows(); ++i) {
            for (int j = 0; j < size.getColumns(); ++j) {
                if (VISITED.equals(this.getBoxes()[i][j].getBoxType())) {
                    visitedNodes.add(this.getBoxes()[i][j].getCoordinate());
                }
            }
        }

        return visitedNodes;
    }

    public void persist() {
        MazeFileHandler mazeFileHandler = new MazeFileHandler();
        mazeFileHandler.save(this);
    }

    public static Maze fromFile() {
        MazeFileHandler mazeFileHandler = new MazeFileHandler();
        return mazeFileHandler.load();
    }

}
