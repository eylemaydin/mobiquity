package com.mobiquity.packer.fileprocessor;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.fileprocessor.model.InputFile;
import com.mobiquity.packer.fileprocessor.model.Line;
import com.mobiquity.packer.model.Item;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class FileProcessorTest {

    @Test
    public void noFile() {
        //Arrange
        String filePath = System.getProperty("user.dir") + "\\not_existed_file";

        //Act
        APIException error = assertThrows(APIException.class, () -> {
            new FileProcessor(filePath);
        });

        //Assert
        assertEquals("No input file in the " + filePath + "!", error.getMessage());
    }

    @Test
    public void notAbsoluteFilePath() {
        //Arrange
        String filePath = "\\example_input";

        //Act
        APIException error = assertThrows(APIException.class, () -> {
            new FileProcessor(filePath);
        });

        //Assert
        assertEquals("The file path is not an absolute path!", error.getMessage());
    }

    @Test
    public void invalidLineInFile() throws APIException {
        //Arrange
        String filePath = new File("src/main/test/resources/invalid_line").getAbsolutePath();

        //Act
        APIException error = assertThrows(APIException.class, () -> {
            new FileProcessor(filePath);
        });

        //Assert
        assertEquals("Invalid line in file!", error.getMessage());
    }

    @Test
    public void invalidPackageWeightInFile() throws APIException {
        //Arrange
        String filePath = new File("src/main/test/resources/invalid_package_weight").getAbsolutePath();

        //Act
        APIException error = assertThrows(APIException.class, () -> {
            new FileProcessor(filePath);
        });

        //Assert
        assertEquals("Max weight that a package can take cannot be more than 100!", error.getMessage());
    }

    @Test
    public void invalidPackageWeightLimitInFile() throws APIException {
        //Arrange
        String filePath = new File("src/main/test/resources/invalid_package_weight_limit").getAbsolutePath();

        //Act
        APIException error = assertThrows(APIException.class, () -> {
            new FileProcessor(filePath);
        });

        //Assert
        assertEquals("Invalid weight limit for package!", error.getMessage());
    }

    @Test
    public void invalidItemWeightLimitInFile() throws RuntimeException {
        //Arrange
        String filePath = new File("src/main/test/resources/invalid_item_weight").getAbsolutePath();

        //Act
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            new FileProcessor(filePath);
        });

        //Assert
        assertEquals("com.mobiquity.exception.APIException: Max weight and cost of an item cannot be more than 100!", error.getMessage());
    }

    @Test
    public void processInputFile() throws APIException {
        //Arrange
        String filePath = new File("src/main/test/resources/example_input").getAbsolutePath();
        FileProcessor processor = new FileProcessor(filePath);

        //Act
        InputFile inputFile = processor.getInputFile();

        //Assert
        Line line = inputFile.getLines().stream().findFirst().get();
        assertEquals(line.getPackage().getWeightLimit().doubleValue(), 81.0);

        assertEquals(line.getItems().size(), 6);

        Item item = line.getItems().stream().findFirst().get();
        assertEquals(item.getIndex(), 1);
        assertEquals(item.getWeight(), 53.38);
        assertEquals(item.getCost(), new BigDecimal(45));
    }
}