package com.example.vlad.navigation.calculation.coleration;


import math.geom2d.Point2D;
import math.geom2d.Vector2D;
import math.geom2d.line.Line2D;

import static java.lang.Math.PI;

public class LineSegmentCustom {
    private Line2D line_segment;
    //point for stor information about start point for current vector
    Point2D start_point;

    //creating vector for two points
    static Line2D transforPointToVector(Point2D point1, Point2D point2) {
        Line2D line = new Line2D(point1, point2);
        return line;
    }

    public LineSegmentCustom(Line2D vector, Point2D point) {
        this.line_segment = vector;
        this.start_point = point;
    }

    public LineSegmentCustom(Point2D point1, Point2D point2) {
        this.start_point = point1;
        this.line_segment = LineSegmentCustom.transforPointToVector(point1, point2);
    }

    public LineSegmentCustom(Line2D line) {
        this.start_point = new Point2D(line.getX1(), line.getY1());
        this.line_segment = line.clone();
    }

    //standart geometry algorithm for calculating distance between to vectors(http://forum.algolist.ru/algorithm-geometry/661-ishu-algoritm-kratchaishee-rasstoianie-mejdu-otrezkami.html)
    //for current task we have assumption that start points qual for both vectors
    public double getDistanceBetweenVectors(Line2D vector) {
        Vector2D neg_line_segment = line_segment.direction().opposite();
        Vector2D supVector = this.line_segment.direction().minus(vector.direction());
        if (vector.direction().dot(line_segment.direction()) <= 0) {
            return vector.length();
        }
        else if (supVector.dot(neg_line_segment) <= 0) {
            return supVector.norm();
        }
        return vector.direction().dot(line_segment.direction())/line_segment.direction().norm();
    }

    public double getAngleBetweenVectores(Line2D vector) {
        return PI * (line_segment.direction().angle() - vector.direction().angle()) / 180.0;
    }

    public Line2D getVector() {
        return line_segment;
    }

    public double getDistanceBetweenVectors(LineSegmentCustom source) {
        Line2D vector = source.getVector();
        return this.getDistanceBetweenVectors(vector);
    }

    public double getAngleBetweenVectores(LineSegmentCustom source) {
        Line2D vector = source.getVector();
        return this.getAngleBetweenVectores(vector);
    }

    Point2D getStartPoint() {
        return start_point;
    }

    void setStartPoint(Point2D point) {
        this.start_point = point;
    }

    double getModule() {
        return line_segment.length();
    }
    //turning current vector by angles matrix
    LineSegmentCustom turnVector(double angle) {
        Line2D result = this.line_segment.clone();
        result.direction().rotate(angle);
        return new LineSegmentCustom(result, this.start_point);
    }
    //move current point to the vector
    Point2D shiftPointToVector(Point2D point) {
        return new Point2D(point.x() + line_segment.getX1(), point.y() + line_segment.getY1());
    }

    LineSegmentCustom subtruck(LineSegmentCustom segment) {
        Vector2D thisVector = line_segment.direction();
        Vector2D inputVector = segment.getVector().direction();
        Vector2D result = thisVector.minus(inputVector);
        Point2D endPoint = new Point2D(result.x() + start_point.x(), result.y() + start_point.y());
        return new LineSegmentCustom(new Line2D(start_point, endPoint), this.start_point);
    }

    //using for calculating destination between vectors. calclulating mapping to current path
    LineSegmentCustom procFromVector(LineSegmentCustom segment) {
        LineSegmentCustom proc2way = segment.turnVector(segment.getAngleBetweenVectores(this));
        this.start_point = proc2way.shiftPointToVector(this.start_point);
        return this.subtruck(proc2way);
    }

    Line2D getLine() {
        Line2D result = new Line2D(
                start_point.x(),
                start_point.y(),
                start_point.x() + line_segment.getX1(),
                start_point.y() + line_segment.getY1()
        );

        return result;
    }
}
