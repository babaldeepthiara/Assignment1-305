package org.example;

/**
 * Represents metadata for a single source file retrieved from a GitHub repository.
 * Acts as a plain data container with no logic or UI concerns.
 *
 * @author babaldeep and yaneli
 * @version 1.0
 */


// TO SEE THE NAMES OF THE FILES AFTER UPLOADING THE GITHUB LINK, CLICK ON A SQUARE AND LOOK AT THE BOTTOM OF THE WINDOW.
// OR YOU CAN HOVER YOUR MOUSE OVER THE SQUARES TO SEE THE FILE NAMES AND # OF LINES IN THE FILE.
// I used the following github repository to test the application: https://github.com/javiergs/TULIP

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
