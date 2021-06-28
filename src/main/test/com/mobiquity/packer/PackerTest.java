import com.mobiquity.exception.APIException;
import com.mobiquity.packer.Packer;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PackerTest {

    @Test
    public void pack() throws APIException {
        //Arrange
        String inputPath = new File("src/main/test/resources/example_input").getAbsolutePath();

        //Act
        String actualResult = Packer.pack(inputPath);
        String expectedResult = "4\n-\n2,7\n8,9";

        //Assert
        assertEquals(expectedResult, actualResult);
    }
}