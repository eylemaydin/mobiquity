package com.mobiquity.packer;

import com.mobiquity.exception.APIException;

import java.util.*;

public class Knapsack {
    private final int[] indexes;
    private final int[] weights;
    private final int[] costs;
    private final int maxWeight;
    private final int costCount;

    public Knapsack(int maxWeight, int[] indexes, int[] weights, int[] costs) throws APIException {
        validateParameters(indexes, weights, costs);
        List<DummyObject> dummyList = sortArrays(indexes, weights, costs);
        this.indexes    = dummyList.stream().mapToInt(DummyObject::getIndex).toArray();
        this.weights    = dummyList.stream().mapToInt(DummyObject::getWeight).toArray();
        this.costs      = dummyList.stream().mapToInt(DummyObject::getCost).toArray();
        this.maxWeight  = maxWeight;
        this.costCount  = costs.length;
    }

    private void validateParameters(int[] indexes, int[] weights, int[] costs) throws APIException {
        if (indexes.length != weights.length || indexes.length != costs.length) {
            throw new APIException("Array lengths should be consistent!");
        }
    }

    private List<DummyObject> sortArrays(int[] indexes, int[] weights, int[] costs) {
        List<DummyObject> dummyList = new ArrayList<>();
        for (int i = 0; i < indexes.length; i++) {
            dummyList.add(new DummyObject(indexes[i], weights[i], costs[i]));
        }
        dummyList.sort(Comparator.comparing(DummyObject::getWeight));
        return dummyList;
    }

    public List<Integer> solve() {
        List<Integer> solution = new ArrayList<>();
        int[][] K = buildTable(new int[costCount + 1][maxWeight + 1]);
        int result = K[costCount][maxWeight];
        int w = maxWeight;

        for (int i = costCount; i > 0 && result > 0; i--) {
            if (result != K[i - 1][w]) {
                solution.add(indexes[i - 1]);
                result = result - costs[i - 1];
                w = w - weights[i - 1];
            }
        }
        Collections.sort(solution);
        return solution;
    }

    private int[][] buildTable(int[][] K) {
        int i, w;

        for (i = 0; i <= costCount; i++) {
            for (w = 0; w <= maxWeight; w++) {
                if (i == 0 || w == 0)
                    K[i][w] = 0;
                else if (weights[i - 1] <= w)
                    K[i][w] = Math.max(costs[i - 1] + K[i - 1][w - weights[i - 1]], K[i - 1][w]);
                else
                    K[i][w] = K[i - 1][w];
            }
        }
        return K;
    }

    private class DummyObject {
        private final int index;
        private final int weight;
        private final int cost;

        DummyObject(int index, int weight, int cost){
            this.index = index;
            this.weight = weight;
            this.cost = cost;
        }

        public int getIndex() {
            return this.index;
        }

        public int getWeight() {
            return this.weight;
        }

        public int getCost() {
            return this.cost;
        }
    }
}
