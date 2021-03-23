package pl.kurs.java.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Coordinate {
    private int x;
    private int y;
    private Coordinate parent;
}