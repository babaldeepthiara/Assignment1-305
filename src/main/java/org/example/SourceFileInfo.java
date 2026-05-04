package org.example;

/**
 *
 * @author babaldeep and yaneli
 * @version 2.0
 */
public class SourceFileInfo {

    private final String fileName;
    private final String filePath;
    private final String content;

    // Metrics
    private int loc;
    private int cc;
    private int dependencies;
    private double instability;
    private double abstractness;

    public SourceFileInfo(String fileName, String filePath, String content) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.content = content;
    }

    // Basic info
    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getContent() {
        return content;
    }

    // Metrics getters/setters
    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public int getDependencies() {
        return dependencies;
    }

    public void setDependencies(int dependencies) {
        this.dependencies = dependencies;
    }

    public double getInstability() {
        return instability;
    }

    public void setInstability(double instability) {
        this.instability = instability;
    }

    public double getAbstractness() {
        return abstractness;
    }

    public void setAbstractness(double abstractness) {
        this.abstractness = abstractness;
    }
}