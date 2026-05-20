package org.example;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Computes all software metrics (LOC, CC, Ca, Ce, I, A, D) for a collection
 * of source file info objects.
 *
 * @author babaldeep and yaneli
 * @version 3.0
 */

public class MetricsCalculator {

    private static final Pattern IF_FOR_WHILE_CASE = Pattern.compile("\\b(if|for|while|case)\\b");

    public void computeAll(List<SourceFileInfo> files) {
        Map<String, String> classMap = buildClassMap(files);
        Map<String, Integer> caMap   = computeCa(files, classMap);

        for (SourceFileInfo file : files) {
            file.setLoc(computeLoc(file.getContent()));
            file.setCc(computeCc(file.getContent()));
            file.setAbstractness(computeAbstractness(file.getContent()));

            String className = deriveClassName(file.getFileName());
            int ce = computeCe(file.getContent(), classMap, className);
            int ca = caMap.getOrDefault(className, 0);

            file.setCe(ce);
            file.setCa(ca);

            double instability = (ca + ce) == 0 ? 0.0 : (double) ce / (ca + ce);
            file.setInstability(instability);
            file.setDistance(Math.abs(file.getAbstractness() + instability - 1.0));
        }
    }

    private Map<String, String> buildClassMap(List<SourceFileInfo> files) {
        Map<String, String> map = new HashMap<>();
        for (SourceFileInfo f : files) map.put(deriveClassName(f.getFileName()), f.getContent());
        return map;
    }

    private Map<String, Integer> computeCa(List<SourceFileInfo> files, Map<String, String> classMap) {
        Map<String, Integer> ca = new HashMap<>();
        
        for (SourceFileInfo file : files) {
            String owner = deriveClassName(file.getFileName());
            
            for (Map.Entry<String, String> entry : classMap.entrySet()) {
                String other = entry.getKey();
                if (other.equals(owner)) continue;
                
                if (referencesClass(entry.getValue(), owner, other)) {
                    ca.merge(owner, 1, Integer::sum);
                }
            }
        }
        
        return ca;
    }

    private int computeCe(String content, Map<String, String> classMap, String selfClass) {
        int count = 0;
        
        for (String other : classMap.keySet()) {
            if (other.equals(selfClass)) continue;
            if (referencesClass(content, other, selfClass)) count++;
        }
        return count;
    }

    private boolean referencesClass(String content, String targetClass, String sourceClass) {
        if (Pattern.compile("\\bextends\\s+"    + targetClass + "\\b").matcher(content).find()) return true;
        if (Pattern.compile("\\bimplements\\b[^{]*\\b" + targetClass + "\\b").matcher(content).find()) return true;
        if (Pattern.compile("\\b" + targetClass + "\\s+\\w+\\s*[=;,)]").matcher(content).find()) return true;
        if (Pattern.compile("\\b" + targetClass + "\\s+\\w+\\s*\\(").matcher(content).find()) return true;
        if (Pattern.compile("\\(.*\\b" + targetClass + "\\b.*\\)").matcher(content).find()) return true;
        if (content.contains("import") && content.contains(targetClass)) return true;
        return false;
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
        if (Pattern.compile("\\binterface\\b").matcher(content).find())        return 1.0;
        if (Pattern.compile("\\babstract\\s+class\\b").matcher(content).find()) return 1.0;
        return 0.0;
    }

    private String deriveClassName(String fileName) {
        return fileName.endsWith(".java") ? fileName.substring(0, fileName.length() - 5) : fileName;
    }
}
