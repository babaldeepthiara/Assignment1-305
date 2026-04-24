package org.example;

/**
 * Represents metadata for a single source file retrieved from a GitHub repository.
 * Acts as a plain data container with no logic or UI concerns.
 *
 * @author babaldeep and yaneli
 */

public class SourceFileInfo {
    private final String fileName;
    private final String filePath;
    private final int lineCount;

    public SourceFileInfo(String fileName, String filePath, int lineCount) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.lineCount = lineCount;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getLineCount() {
        return lineCount;
    }
}
