package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.fileprocessor.FileProcessor;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KnapsackTest {

    @Test
    public void invalidKnapsackInputs() {
        //Arrange
        int[] indexes = {1, 2};
        int[] weights = {9072, 3380, 4315, 3797, 4681};
        int[] costs = {13, 40, 10, 16, 36, 79, 45, 79, 64};
        int maxWeight = 5600;

        //Act
        APIException error = assertThrows(APIException.class, () -> {
            new Knapsack(maxWeight, indexes, weights, costs);
        });

        //Assert
        assertEquals("Array lengths should be consistent!", error.getMessage());
    }

    @Test
    public void solveKnapsackProblem() throws APIException {
        //Arrange
        int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] weights = {9072, 3380, 4315, 3797, 4681, 4877, 8180, 1936, 676};
        int[] costs = {13, 40, 10, 16, 36, 79, 45, 79, 64};
        int maxWeight = 5600;

        //Act
        Knapsack knapsack = new Knapsack(maxWeight, indexes, weights, costs);
        List<Integer> actualSolution = knapsack.solve();

        //Assert
        List<Integer> expectedSolution = Arrays.asList(8,9);
        assertEquals(expectedSolution, actualSolution);
    }
}