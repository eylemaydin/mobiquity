package com.mobiquity.packer.fileprocessor.model;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.*;
import com.mobiquity.packer.model.Package;
import com.mobiquity.util.ConsumerWrapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Line {
    private Package pack;
    private List<Item> items;

    public Line(String line) throws APIException {
        validateParameters(line);
        String[] lineComponents = line.split(":");
        this.pack = initializePackage(lineComponents[0]);
        this.items = initializeItems(lineComponents[1]);
    }

    private void validateParameters(String line) throws APIException {
        if (!line.contains(":")) {
            throw new APIException("Invalid line in file!");
        }
    }

    public Package getPackage() {
        return this.pack;
    }

    public List<Item> getItems() {
        return this.items;
    }

    private Package initializePackage(String packageWeight) throws APIException {
        try {
            Double weightLimit = Double.parseDouble(packageWeight);
            return new Package(weightLimit);
        } catch (NumberFormatException nfExp) {
            throw new APIException("Invalid weight limit for package!", nfExp);
        }
    }

    private List<Item> initializeItems(String itemsAsString) throws APIException {
        List<Item> items = Pattern.compile("\\((.*?)\\)")
                .matcher(itemsAsString)
                .results()
                .map(ConsumerWrapper.wrapper(mr -> resolveItem(mr.group(1))))
                .collect(Collectors.toList());

        if (items.size() > 15) {
            throw new APIException("There might be up to 15 items maximum!");
        }
        return items;
    }

    private Item resolveItem(String componentString) throws APIException {
        String[] components = componentString.split(",");
        return new Item(Integer.parseInt(components[0].trim()), Double.parseDouble(components[1].trim()), new BigDecimal(components[2].trim().replace("€", "")));
    }

    @Override
    public String toString() {
        return "Line[package=" + getPackage() + ", items=" + getItems() + "]";
    }
}
