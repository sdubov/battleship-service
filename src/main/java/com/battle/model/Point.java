package com.battle.model;

public class Point implements Comparable<Point> {

    private int _x;

    private int _y;

    public Point(int x, int y) {
        _x = x;
        _y = y;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    @Override
    public int compareTo(Point o) {
        int cmp = Integer.compare(_x, o.getX());
        if (cmp != 0) { return cmp; }
        return Integer.compare(_y, o.getY());
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) { return true; }
        if (!(obj instanceof Point)) { return false; }

        Point that = (Point)obj;
        return _x == that.getX() && _y == that.getY();
    }
}
