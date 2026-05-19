package org.example;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Computes D, I, and A metrics for a collection of source file info objects.
 *
 * @author babaldeep and yaneli
 * @version 2.0
 *
 */

public class MetricsCalculator {

    private static final Pattern IF_FOR_WHILE_CASE =
            Pattern.compile("\\b(if|for|while|case)\\b");

    public void computeAll(List<SourceFileInfo> files) {
        Map<String, String> classNameToContent = buildClassMap(files);
        Map<String, Integer> cin = computeCin(files, classNameToContent);

        for (SourceFileInfo file : files) {
            file.setLoc(computeLoc(file.getContent()));
            file.setCc(computeCc(file.getContent()));
            file.setAbstractness(computeAbstractness(file.getContent()));

            String className = deriveClassName(file.getFileName());
            int cout = computeCout(file.getContent(), classNameToContent, className);
            int cinCount = cin.getOrDefault(className, 0);
            double instability = (cinCount + cout) == 0 ? 0.0
                    : (double) cout / (cinCount + cout);
            file.setInstability(instability);

            double distance = Math.abs(file.getAbstractness() + instability - 1.0);
            file.setDistance(distance);
        }
    }

    private Map<String, String> buildClassMap(List<SourceFileInfo> files) {
        Map<String, String> map = new HashMap<>();
        for (SourceFileInfo f : files) {
            map.put(deriveClassName(f.getFileName()), f.getContent());
        }
        return map;
    }

    private Map<String, Integer> computeCin(List<SourceFileInfo> files, Map<String, String> classNameToContent) {
        Map<String, Integer> cin = new HashMap<>();

        for (SourceFileInfo file : files) {
            String ownerClass = deriveClassName(file.getFileName());

            for (Map.Entry<String, String> entry : classNameToContent.entrySet()) {
                String otherClass = entry.getKey();
                if (otherClass.equals(ownerClass)) continue;
                String otherContent = entry.getValue();
                if (referencesClass(otherContent, ownerClass, otherClass)) {
                    cin.merge(ownerClass, 1, Integer::sum);
                }
            }
        }
        return cin;
    }

    private boolean referencesClass(String content, String targetClass, String sourceClass) {
        if (Pattern.compile("\\bextends\\s+" + targetClass + "\\b").matcher(content).find()) return true;
        if (Pattern.compile("\\bimplements\\b[^{]*\\b" + targetClass + "\\b").matcher(content).find()) return true;
        if (Pattern.compile("\\b" + targetClass + "\\s+\\w+\\s*[=;,)]").matcher(content).find()) return true;
        if (Pattern.compile("\\b" + targetClass + "\\s+\\w+\\s*\\(").matcher(content).find()) return true;
        if (Pattern.compile("\\(.*\\b" + targetClass + "\\b.*\\)").matcher(content).find()) return true;
        if (content.contains("import") && content.contains(targetClass)) return true;

        return false;
    }

    private int computeCout(String content, Map<String, String> classNameToContent, String selfClass) {
        int count = 0;

        for (String otherClass : classNameToContent.keySet()) {
            if (otherClass.equals(selfClass)) continue;
            if (referencesClass(content, otherClass, selfClass)) {
                count++;
            }
        }

        return count;
    }

    private int computeLoc(String content) {
        if (content == null || content.isEmpty()) return 0;

        int count = 0;

        for (String line : content.split("\n", -1)) {
            if (!line.trim().isEmpty()) count++;
        }

        return count;
    }

    private int computeCc(String content) {
        if (content == null || content.isEmpty()) return 1;

        Matcher m = IF_FOR_WHILE_CASE.matcher(content);
        int count = 1;

        while (m.find()) count++;

        return count;
    }

    private double computeAbstractness(String content) {
        if (content == null) return 0.0;
        if (Pattern.compile("\\binterface\\b").matcher(content).find()) return 1.0;
        if (Pattern.compile("\\babstract\\s+class\\b").matcher(content).find()) return 1.0;

        return 0.0;
    }

    private String deriveClassName(String fileName) {
        if (fileName.endsWith(".java")) {
            return fileName.substring(0, fileName.length() - 5);
        }

        return fileName;
    }
}
