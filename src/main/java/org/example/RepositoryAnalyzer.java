package org.example;
import java.util.ArrayList;
import java.util.List;

public class RepositoryAnalyzer {

    public List<SourceFileInfo> readFiles(String folderPath) {
        return new ArrayList<>();
    }

    public double calculateAverageLines(List<SourceFileInfo> files) {
        if (files.isEmpty()) {
            return 0;
        }

        int total = 0;
        for (SourceFileInfo file : files) {
            total += file.getLineCount();
        }

        return (double) total / files.size();
    }

    public FileColorCategory getCategory(SourceFileInfo file, double average) {
        double lowerBound = average * 0.8;
        double upperBound = average * 1.2;

        if (file.getLineCount() < lowerBound) {
            return FileColorCategory.GREEN;
        } else if (file.getLineCount() <= upperBound) {
            return FileColorCategory.YELLOW;
        } else {
            return FileColorCategory.RED;
        }
    }
}
