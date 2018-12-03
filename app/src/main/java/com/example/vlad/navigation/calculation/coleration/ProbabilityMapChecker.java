package com.example.vlad.navigation.calculation.coleration;

import com.example.vlad.navigation.database.model.NavigationMap;
import com.example.vlad.navigation.database.model.NavigationWay;
import com.example.vlad.navigation.utils.NormalizedDistribution;
import com.example.vlad.navigation.utils.messageSystem.ResultMapCheck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.line.Line2D;

public class ProbabilityMapChecker implements MapChecker {

    private static final int OPT_WAYS_NUMBER = 6;

    List<CurrentStateWay> current_states;

    NavigationMap map;
    NavigationWay way;

    CalculatorProbability calculater_probability;

    public ProbabilityMapChecker(NavigationMap map, NavigationWay way) {
        this.map = map;
        this.way = way;

        calculater_probability = new CalculatorProbability(3, 1, new NormalizedDistribution(0, 2), new NormalizedDistribution(0, 0.6));

        current_states = new ArrayList<CurrentStateWay>(OPT_WAYS_NUMBER);
        LineSegmentCustom primary_way = new LineSegmentCustom(way.getStartLine());
        current_states.add(new CurrentStateWay(primary_way, 1, true, this.map, this.way, this.calculater_probability));
    }

    @Override
    public ResultMapCheck checkOnMap(float angle, float length) {
        for (int i = 0; i < current_states.size(); i++) {
            current_states.get(i).updateCurrentState(angle, length);
            if (current_states.get(i).isLineUpdated()) {
                current_states.addAll(current_states.get(i).getNebourses());
            }
        }

        normalize();
        return takeSolution();
    }

    private void normalizeState() {
        double sum = 0;

        for (int i = 0; i < OPT_WAYS_NUMBER && i < current_states.size(); i++)
            sum += current_states.get(i).getProbability();
        for (int i = 0; i < OPT_WAYS_NUMBER && i < current_states.size(); i++)
            current_states.get(i).setProbability(current_states.get(i).getProbability() / sum);
    }

    private void normalize() {
        Collections.sort(current_states);
        if (current_states.size() > OPT_WAYS_NUMBER)
            current_states = current_states.subList(0, OPT_WAYS_NUMBER);
        normalizeState();
    };

    // get max from array, get start point and return them.
    // also provide information abaut way
    private ResultMapCheck takeSolution() {
        CurrentStateWay currentSate = current_states.get(0);
        Point2D result = currentSate.getCurrentPoint();
        return new ResultMapCheck(result, false/*!currentSate->getPrimaryWay()*/, false/*currentSate->isLastStep(result)*/);
    };

    public void refreshChecker(List<Line2D> way) {
        current_states.clear();
        LineSegmentCustom primary_way = new LineSegmentCustom(way.get(0));
        current_states.add(new CurrentStateWay(primary_way, 1, true, this.map, this.way, this.calculater_probability));
    }

    //getting all current point(in additionals wais too)
    public List<Point2D> getAllCurrentPoints() {
        List<Point2D> points = new ArrayList<>();
        for (int i = 0; i < current_states.size(); i++) {
            points.add(current_states.get(i).getCurrentPoint());
        }
        return points;
    }

    public Point2D getNearestPoint(Point2D source_point) {
        return map.getNearestPoint(source_point);
    }

    public Point2D getLastPointOnWay() {
        return way.getLastPoint();
    }
}
