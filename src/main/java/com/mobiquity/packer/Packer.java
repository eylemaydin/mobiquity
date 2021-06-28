package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.fileprocessor.FileProcessor;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Package;
import com.mobiquity.util.ConsumerWrapper;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Packer {

  private Packer() {
  }

  public static String pack(String filePath) throws APIException {
    FileProcessor fileProcessor = new FileProcessor(filePath);
    List<Package> results = preparePackages(fileProcessor);
    return extractItemIndexes(results);
  }

  private static List<Package> preparePackages(FileProcessor fileProcessor) {
    return fileProcessor.getInputFile().getLines()
            .stream()
            .map(ConsumerWrapper.wrapper(line -> packItemsIntoPackage(line.getPackage(), line.getItems())))
            .collect(Collectors.toList());
  }

  private static Package packItemsIntoPackage(Package pack, List<Item> items) throws APIException {
    final int CONSTANT_MULTIPLIER = 100;
    int[] costs   = items.stream().mapToInt(item -> item.getCost().intValue()).toArray();
    int[] weights = items.stream().mapToInt(item -> (int) (item.getWeight() * CONSTANT_MULTIPLIER)).toArray();
    int[] indexes = items.stream().mapToInt(Item::getIndex).toArray();
    int packageMaxWeight = (int)(pack.getWeightLimit() * CONSTANT_MULTIPLIER);

    List<Integer> solution = new Knapsack(packageMaxWeight, indexes, weights, costs).solve();
    for (Integer itemIndex : solution) {
      pack.addItem(items.stream().filter(item -> item.getIndex() == itemIndex).findAny().orElse(null));
    }
    return pack;
  }

  private static String extractItemIndexes(List<Package> results) {
    return results
            .stream()
            .map(pack -> pack.getItems()
                    .stream()
                    .map(item-> Integer.toString(item.getIndex()))
                    .collect(Collector.of(
                            () -> new StringJoiner(",").setEmptyValue("-"),
                            StringJoiner::add,
                            StringJoiner::merge,
                            StringJoiner::toString
                            )
                    )
            )
            .collect(Collectors.joining("\n"));
  }
}
