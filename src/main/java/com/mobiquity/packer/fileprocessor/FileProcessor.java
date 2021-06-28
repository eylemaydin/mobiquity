package com.mobiquity.packer.fileprocessor;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.fileprocessor.model.InputFile;
import com.mobiquity.packer.fileprocessor.model.Line;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FileProcessor {
    private InputFile inputFile;

    public FileProcessor(String filePath) throws APIException {
        this.inputFile = new InputFile(filePath);
        processFile();
    }

    public InputFile getInputFile() {
        return this.inputFile;
    }

    private void processFile() throws APIException {
        for (String lineString : readLinesFromFile()) {
            Line line = new Line(lineString);
            this.inputFile.addLine(line);
        }
    }

    private List<String> readLinesFromFile() throws APIException {
        List<String> lines;
        try {
            lines = Files.readAllLines(this.inputFile.getPath(), StandardCharsets.UTF_8);
        } catch (IOException ioExp) {
            throw new APIException("No input file in the " + this.inputFile.getPath() + "!", ioExp);
        } catch (Exception exp) {
            throw new APIException("The file is malformed!", exp);
        }
        return lines;
    }
}
