package com.example.vlad.navigation.calculation.inertialSystem;

import com.example.vlad.navigation.calculation.inertialSystem.alphaBettaAlgorithm.CounterAlphaBetta;
import com.example.vlad.navigation.calculation.inertialSystem.fourPartAlgorithm.ExecuterFourPartAlgorithm;
import com.example.vlad.navigation.calculation.inertialSystem.threePhaseAlgorithm.ThreePhaseAlgorithm;

/**
 * Created by RoMka on 03.05.2016.
 */
public class AlgorithmFactory {
    private static Counter[] counters = {new CounterAlphaBetta(), new ExecuterFourPartAlgorithm(), new ThreePhaseAlgorithm()};

    public static Counter getAlgorithmOnIndex(int i){
        return counters[i];
    }

}
