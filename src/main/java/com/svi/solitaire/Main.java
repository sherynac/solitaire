package com.svi.solitaire;

import com.svi.solitaire.game.GameManager;
import com.svi.solitaire.game.GameRunner;
import com.svi.solitaire.game.SimulationRunner;
import com.svi.solitaire.solver.MoveEvaluator;
import com.svi.solitaire.solver.MoveExecutor;
import com.svi.solitaire.solver.MoveGenerator;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        MoveGenerator moveGenerator = new MoveGenerator();
        MoveEvaluator moveEvaluator = new MoveEvaluator();
        MoveExecutor moveExecutor = new MoveExecutor();

        GameRunner gameRunner = new GameRunner(moveGenerator, moveEvaluator, moveExecutor);
        SimulationRunner simulationRunner = new SimulationRunner(gameRunner, random);

        GameManager gameManager = new GameManager(gameRunner, simulationRunner, random);
        gameManager.run();
    }
}
