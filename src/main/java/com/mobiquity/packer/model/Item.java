package com.mobiquity.packer.model;

import com.mobiquity.exception.APIException;

import java.math.BigDecimal;

/*
    No setter is defined on purpose due to preserving class immutability
*/
public class Item {
    private Integer maximumWeightLimit = 100;
    private BigDecimal maximumCostLimit = new BigDecimal(100);
    private int index;
    private double weight;
    private BigDecimal cost;

    public Item(int index, double weight, BigDecimal cost) throws APIException {
        validateParameters(weight, cost);
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    private void validateParameters(double weight, BigDecimal cost) throws APIException {
        if (weight > maximumWeightLimit || cost.compareTo(maximumCostLimit) > 0) {
            throw new APIException("Max weight and cost of an item cannot be more than " + this.maximumWeightLimit + "!");
        }
    }

    public int getIndex() {
        return this.index;
    }

    public double getWeight() {
        return this.weight;
    }

    public BigDecimal getCost() {
        return this.cost;
    }

    @Override
    public String toString() {
        return "Item[index=" + this.index + ", weight=" + this.weight + ", cost=" + this.cost + "]";
    }
}
