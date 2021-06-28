package com.mobiquity.packer.model;

import com.mobiquity.exception.APIException;

import java.util.ArrayList;
import java.util.List;

/*
    No setter is defined on purpose due to preserving class immutability
*/
public class Package {
    private Integer maximumWeightLimit = 100;
    private Double weightLimit;
    public List<Item> items;

    public Package(Double weightLimit) throws APIException {
        validateParameters(weightLimit);
        this.weightLimit = weightLimit;
        this.items = new ArrayList<>();
    }

    private void validateParameters(Double weightLimit) throws APIException {
        if (weightLimit > this.maximumWeightLimit) {
            throw new APIException("Max weight that a package can take cannot be more than " + this.maximumWeightLimit + "!");
        }
    }

    public List<Item> getItems() {
        return this.items;
    }

    public Integer getItemCount() {
        return this.items.size();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public Double getWeightLimit() {
        return this.weightLimit;
    }

    @Override
    public String toString() {
        return "Package[maximumWeight=" + getWeightLimit() + ", items=" + getItems() + "]";
    }
}
