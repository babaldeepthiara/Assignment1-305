package org.example;

import java.util.List;

/**
 * Responsible for computing all software design metrics for each source file.
 * This class does NOT handle UI or data storage.
 *
 * @author babaldeep and yaneli
 * @version 2.0
 */
public class Calculator {

    public void computeMetrics(List<SourceFileInfo> files) {

        // First pass: basic metrics
        for (SourceFileInfo file : files) {
            file.setLoc(computeLOC(file));
            file.setCc(computeCC(file));
            file.setDependencies(computeDependencies(file, files));
            file.setAbstractness(computeAbstractness(file));
        }

        // Second pass: instability
        for (SourceFileInfo file : files) {
            file.setInstability(computeInstability(file, files));
        }
    }

    private int computeLOC(SourceFileInfo file) {
        String content = file.getContent();
        if (content == null || content.isEmpty()) return 0;

        String[] lines = content.split("\\R");
        int count = 0;

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                count++;
            }
        }

        return count;
    }

    private int computeCC(SourceFileInfo file) {
        String content = file.getContent();
        int cc = 1;

        cc += countKeyword(content, "if");
        cc += countKeyword(content, "for");
        cc += countKeyword(content, "while");
        cc += countKeyword(content, "switch");

        return cc;
    }

    private int countKeyword(String content, String keyword) {
        if (content == null) return 0;
        return content.split("\\b" + keyword + "\\b").length - 1;
    }

    private int computeDependencies(SourceFileInfo file, List<SourceFileInfo> allFiles) {
        String content = file.getContent();
        int count = 0;

        for (SourceFileInfo other : allFiles) {
            if (file == other) continue;

            String className = other.getFileName().replace(".java", "");

            if (content.contains(className)) {
                count++;
            }
        }

        return count;
    }

    private double computeAbstractness(SourceFileInfo file) {
        String content = file.getContent();

        int abstractClasses = countKeyword(content, "abstract class");
        int totalClasses = countKeyword(content, "class");

        if (totalClasses == 0) return 0;

        return (double) abstractClasses / totalClasses;
    }

    private double computeInstability(SourceFileInfo file, List<SourceFileInfo> allFiles) {

        int Ce = file.getDependencies();
        int Ca = 0;

        String fileName = file.getFileName().replace(".java", "");

        for (SourceFileInfo other : allFiles) {
            if (other == file) continue;

            if (other.getContent().contains(fileName)) {
                Ca++;
            }
        }

        if (Ce + Ca == 0) return 0;

        return (double) Ce / (Ce + Ca);
    }
}