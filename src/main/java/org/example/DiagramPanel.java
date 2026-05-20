package org.example;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.FileFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Renders a UML class diagram for the loaded repository using PlantUML.
 * Reads SourceFileInfo from the Blackboard, generates PlantUML source,
 * renders it to a BufferedImage, and displays it in a scrollable panel.
 * Pure rendering component — observer wiring handled by DiagramTab.
 *
 * @author babaldeep and yaneli
 * @version 3.0
 */

public class DiagramPanel extends JPanel {

    private BufferedImage diagramImage;
    private String errorMessage;

    public DiagramPanel() {
        setBackground(Color.WHITE);
    }

    public void refresh() {
        List<SourceFileInfo> files = Blackboard.getInstance().getFiles();

        if (files == null || files.isEmpty()) {
            diagramImage = null;
            errorMessage = null;
            repaint();
            return;
        }

        try {
            String uml = buildPlantUmlSource(files);
            diagramImage = renderToImage(uml);
            errorMessage = null;
        } 
        
        catch (Exception e) {
            diagramImage = null;
            errorMessage = "Failed to render diagram: " + e.getMessage();
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (errorMessage != null) {
            g.setColor(Color.RED);
            g.drawString(errorMessage, 20, 40);
            return;
        }

        if (diagramImage == null) {
            g.setColor(Color.GRAY);
            g.setFont(new Font("SansSerif", Font.ITALIC, 13));
            g.drawString("Load a repository to see the diagram.", 20, 40);
            return;
        }

        int x = Math.max(0, (getWidth()  - diagramImage.getWidth())  / 2);
        int y = Math.max(0, (getHeight() - diagramImage.getHeight()) / 2);
        g.drawImage(diagramImage, x, y, this);
    }

    @Override
    public Dimension getPreferredSize() {
        if (diagramImage != null) {
            return new Dimension(diagramImage.getWidth(), diagramImage.getHeight());
        }

        return super.getPreferredSize();
    }

    private String buildPlantUmlSource(List<SourceFileInfo> files) {
        StringBuilder sb = new StringBuilder();
        sb.append("@startuml\n");
        sb.append("skinparam classBackgroundColor #f5f0e8\n");
        sb.append("skinparam classBorderColor #999\n");
        sb.append("skinparam arrowColor #555\n");
        sb.append("skinparam shadowing false\n\n");

        for (SourceFileInfo file : files) {
            String name = className(file.getFileName());
            String content = file.getContent();

            if (isInterface(content)) {
                sb.append("interface ").append(name).append(" {\n}\n\n");
            } 
            
            else if (isAbstract(content)) {
                sb.append("abstract class ").append(name).append(" {\n}\n\n");
            } 
            
            else {
                sb.append("class ").append(name).append(" {\n}\n\n");
            }
        }

        java.util.Set<String> classNames = new java.util.HashSet<>();
        for (SourceFileInfo f : files) classNames.add(className(f.getFileName()));

        for (SourceFileInfo file : files) {
            String src = className(file.getFileName());
            String content = file.getContent();

            java.util.regex.Matcher extM = java.util.regex.Pattern
                    .compile("\\bextends\\s+(\\w+)").matcher(content);

            while (extM.find()) {
                String target = extM.group(1);
                if (classNames.contains(target)) {
                    sb.append(target).append(" <|-- ").append(src).append("\n");
                }
            }

            java.util.regex.Matcher implM = java.util.regex.Pattern
                    .compile("\\bimplements\\b([^{]+)").matcher(content);

            while (implM.find()) {
                for (String iface : implM.group(1).split(",")) {
                    String target = iface.trim().replaceAll("<.*>", "");
                    
                    if (classNames.contains(target)) {
                        sb.append(target).append(" <|.. ").append(src).append("\n");
                    }
                }
            }

            java.util.Set<String> alreadyLinked = new java.util.HashSet<>();
            for (String other : classNames) {
                
                if (other.equals(src) || alreadyLinked.contains(other)) continue;
                java.util.regex.Pattern fieldPat = java.util.regex.Pattern
                        .compile("\\b" + other + "\\b[^(=\n]*;");
                
                        if (fieldPat.matcher(content).find()) {
                    sb.append(src).append(" --> ").append(other).append("\n");
                    alreadyLinked.add(other);
                }
            }
        }

        sb.append("@enduml\n");
        return sb.toString();
    }

    private BufferedImage renderToImage(String umlSource) throws Exception {
        SourceStringReader reader = new SourceStringReader(umlSource);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        reader.outputImage(os, new FileFormatOption(FileFormat.PNG));
        os.close();
        return ImageIO.read(new ByteArrayInputStream(os.toByteArray()));
    }

    private boolean isInterface(String content) {
        return java.util.regex.Pattern.compile("\\binterface\\b").matcher(content).find();
    }

    private boolean isAbstract(String content) {
        return java.util.regex.Pattern.compile("\\babstract\\s+class\\b").matcher(content).find();
    }

    private String className(String fileName) {
        return fileName.endsWith(".java") ? fileName.substring(0, fileName.length() - 5) : fileName;
    }
}
