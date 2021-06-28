package com.mobiquity.packer.fileprocessor.model;

import com.mobiquity.exception.APIException;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InputFile {
    private Path path;
    private List<Line> lines;

    public InputFile(String filePath) throws APIException {
        validateParameters(filePath);
        this.path = Path.of(filePath);
        this.lines = new ArrayList<>();
    }

    private void validateParameters(String filePath) throws APIException {
        Path path;
        try {
            path = Path.of(filePath);
        } catch (Exception ex) {
            throw new APIException("The file path is not valid!", ex);
        }
        if (!path.isAbsolute()) {
            throw new APIException("The file path is not an absolute path!");
        }
    }

    public Path getPath() {
        return this.path;
    }

    public List<Line> getLines () {
        return this.lines;
    }

    public void addLine(Line line) {
        this.lines.add(line);
    }

    @Override
    public String toString() {
        return "InputFile[path=" + getPath() + ", lines=" + getLines() + "]";
    }
}
