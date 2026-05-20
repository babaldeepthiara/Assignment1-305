package org.example;

/**
 * Data container for a single .java source file's metadata and computed metrics.
 * Holds LOC, CC, Ca, Ce, I, A, and D values.
 *
 * @author babaldeep and yaneli
 * @version 3.0
 */

public class SourceFileInfo {

    private final String fileName;
    private final String filePath;
    private final String content;
    private int loc;
    private int cc;
    private int ca;
    private int ce;
    private double instability;
    private double abstractness;
    private double distance;

    public SourceFileInfo(String fileName, String filePath, String content) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.content = content;
    }

    public String getFileName()    { return fileName; }
    public String getFilePath()    { return filePath; }
    public String getContent()     { return content; }

    public int getLoc()            { return loc; }
    public void setLoc(int loc)    { this.loc = loc; }

    public int getCc()             { return cc; }
    public void setCc(int cc)      { this.cc = cc; }

    public int getCa()             { return ca; }
    public void setCa(int ca)      { this.ca = ca; }

    public int getCe()             { return ce; }
    public void setCe(int ce)      { this.ce = ce; }

    public double getInstability()                    { return instability; }
    public void setInstability(double instability)    { this.instability = instability; }

    public double getAbstractness()                   { return abstractness; }
    public void setAbstractness(double abstractness)  { this.abstractness = abstractness; }

    public double getDistance()                       { return distance; }
    public void setDistance(double distance)          { this.distance = distance; }
}
