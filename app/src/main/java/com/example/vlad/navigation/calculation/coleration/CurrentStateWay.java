package com.example.vlad.navigation.calculation.coleration;

import com.example.vlad.navigation.database.model.NavigationMap;
import com.example.vlad.navigation.database.model.NavigationWay;

import java.util.ArrayList;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.Vector2D;
import math.geom2d.line.Line2D;

import static java.lang.Math.PI;

public class CurrentStateWay implements Comparable {
    private static final double ERR = 0.001;
    private static final int CLOSED_ENVIRONS = 10;

    //value probability of current way is corrected
    private double probability;

    //current line when user presumably is
    private LineSegmentCustom line_segment;
    private boolean is_primary_way;
    private NavigationMap map;
    private NavigationWay way;

    //nearest of current nebourses (lines which have one equaled point)
    private List<CurrentStateWay> nebourses;

    //service for calculating and update probability in each step
    private CalculatorProbability calculatorProbability;

    private boolean is_updated;


    private double getCurrentProbability(LineSegmentCustom way_line_segment, LineSegmentCustom current_line_segment, double current_probability) {
            double angle = way_line_segment.getAngleBetweenVectores(current_line_segment);
            double distance = way_line_segment.getDistanceBetweenVectors(current_line_segment);
            //correcting current probability acording to information about current angle between ideal position of user and current position
            return current_probability * calculatorProbability.getCurrentValue(angle, distance);
        }

        //method zeroning current error on turn
        //checker: if angle between current line and next line = pi that shift start point.
        //if it is false then get start point form current line
    private Line2D zeroning_error(Line2D current_line) {

            LineSegmentCustom way_line = new LineSegmentCustom(current_line);
            if (line_segment.getAngleBetweenVectores(way_line) > PI - ERR && line_segment.getAngleBetweenVectores(way_line) < PI + ERR) {
                return new Line2D(
                        line_segment.getStartPoint(),
                        way_line.getLine().p2
                );
            }
            return current_line;
        }

    private Line2D getNextline(List<Line2D> lines) {
            if (is_primary_way)
                return way.getNext(lines);
            if (lines.size() != 0)
                return lines.get(0);
            return null;
        }

    private LineSegmentCustom findCurrentLine(float angle, float length) {

            if (line_segment.getModule() > length)
            return line_segment;

            //get next, getting neibours, work with flag

            is_updated = true;
            List<Line2D> lines = map.getRelatedEges(line_segment.getLine());

            //selecting next lines among related enges
            Line2D result = getNextline(lines);
            lines.remove(result);

            //it is neccessary for corrected probability
            result = zeroning_error(result);

            nebourses = new ArrayList<CurrentStateWay>();
            for (int i = 1; i < lines.size(); i++) {
                LineSegmentCustom segment = new LineSegmentCustom(lines.get(i));
                nebourses.add(new CurrentStateWay(segment, probability, false, map, way, calculatorProbability));
            }

            return new LineSegmentCustom(result);
        }

    private Point2D buildPoint(float angle, float length) {
        Point2D pointStart = new Point2D(0, 0);
        Point2D pointFinish = new Point2D(length, 0);
        Line2D vector = new Line2D(pointStart,pointFinish);
        LineSegmentCustom line = new LineSegmentCustom(vector, line_segment.getStartPoint());
        line = line.turnVector(angle);
        return line.shiftPointToVector(line.getStartPoint());
    }

    public CurrentStateWay(LineSegmentCustom line, double probability, boolean is_primary_way, NavigationMap map, NavigationWay way, CalculatorProbability calculatorProbability) {
        this.line_segment = line;
        // FOR_TEST:
        //this->line_segment = gcnew LineSegmentCustom(Windows::Point(1,1), Windows::Point(1, 5));

        this.probability = probability;
        this.is_primary_way = is_primary_way;
        this.map = map;
        this.way = way;
        this.calculatorProbability = calculatorProbability;
    }

    //update probability and line if it is necessary line equals (first point  = last point(input point) in line segment, second point = second point from interval)
    public void updateCurrentState(float angle, float length) {
        // FOR_TEST:
        //point.X = 4;
        //point.Y = 5;
        //way->getNext(gcnew List<Line^>());

        is_updated = false;

        this.line_segment = findCurrentLine(angle, length);
        Point2D point = buildPoint(angle, length);

        LineSegmentCustom current_segment = new LineSegmentCustom(line_segment.getStartPoint(), point);
        this.probability = getCurrentProbability(line_segment, current_segment, probability);

        //shift start point from line_segment to modelue from current vector
        this.line_segment = this.line_segment.procFromVector(current_segment);
    }
        //flag for true if line is updated
    public boolean isLineUpdated() { return is_updated; };

        //get nebourses without update for current point
    public List<CurrentStateWay> getNebourses() {
        return nebourses;
    }

    public double getProbability() {
        return probability;
    }

    public boolean getPrimaryWay() {
        return is_primary_way;
    }

    // get first point from current line
    public Point2D getCurrentPoint() {
        return line_segment.getStartPoint();
    }

    public void setProbability(double probability) {
        this.probability = probability;
    };

    public void setPrimaryWay(boolean flag) {
        this.is_primary_way = flag;
    };

    @Override
    public int compareTo(Object ob) {
        CurrentStateWay stateS = (CurrentStateWay) ob;
        Double this_porbability = new Double(probability);
        Double state_porbability = new Double(stateS.getProbability());
        return this_porbability.compareTo(state_porbability);
    }

    //check if current line not has nebourses
    // and current point belongs to e-environs
    public boolean isLastStep(Point2D point) {
        List<Line2D> cur_nebourses = map.getRelatedEges(line_segment.getLine());
        boolean is_last_segment = way.getNext(cur_nebourses)==null;

        Vector2D currentVector = line_segment.getVector().direction();
        Vector2D pointVector = new Vector2D(point.x(), point.y());

        boolean is_last_step = currentVector.minus(pointVector).norm() < CLOSED_ENVIRONS;
		return is_last_segment && is_last_step;
    }
}
