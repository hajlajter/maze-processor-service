package pl.kurs.java.service;

import org.springframework.stereotype.Service;
import pl.kurs.java.model.Coordinate;
import pl.kurs.java.model.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class MazePathFinder {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public List<Coordinate> solve(Maze maze) {
        LinkedList<Coordinate> nextToVisit = new LinkedList<>();
        Coordinate start = maze.getStart();
        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {
            Coordinate cur = nextToVisit.remove();

            if (!maze.isValidLocation(cur.getX(), cur.getY()) || maze.isExplored(cur.getX(), cur.getY())) {
                continue;
            }

            if (maze.isWall(cur.getX(), cur.getY())) {
                maze.setVisited(cur.getX(), cur.getY(), true);
                continue;
            }

            if (maze.isExit(cur.getX(), cur.getY())) {
                return backtrackPath(cur);
            }

            for (int[] direction : DIRECTIONS) {
                Coordinate coordinate = Coordinate.builder().x(cur.getX() + direction[0]).y(cur.getY() + direction[1]).parent(cur).build();
                nextToVisit.add(coordinate);
                maze.setVisited(cur.getX(), cur.getY(), true);
            }
        }
        return Collections.emptyList();
    }

    private List<Coordinate> backtrackPath(Coordinate cur) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.getParent();
        }

        return path;
    }
}