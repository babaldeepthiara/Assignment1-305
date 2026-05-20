package org.example;
import javiergs.tulip.github.GitHubHandler;
import javiergs.tulip.github.URLFactory;
import javiergs.tulip.github.URLObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Fetches java source files from a public GitHub repository using the TULIP library.
 * After retrieval, delegates all metric computation to metrics calculator.
 * Updates blackboard status bar during loading to inform the UI.
 *
 * @author babaldeep and yaneli
 * @version 3.0
 *
 */

public class GitHubLoader {

    private final GitHubHandler handler;
    private final MetricsCalculator calculator;

    public GitHubLoader() {
        handler = new GitHubHandler();
        calculator = new MetricsCalculator();
    }

    public List<SourceFileInfo> loadFiles(String repoUrl) throws Exception {
        Blackboard bb = Blackboard.getInstance();
        bb.setStatusMessage("Fetching repository…");

        URLObject u = URLFactory.parseGitHubUrl(repoUrl);
        List<String> allPaths = handler.listFilesRecursive(u.owner, u.repository, u.revision, u.path);
        List<SourceFileInfo> result = new ArrayList<>();

        int total = 0;
        for (String path : allPaths) {
            if (!path.endsWith(".java")) continue;
            String content = handler.getFileContent(u.owner, u.repository, path, u.revision);
            String fileName = extractFileName(path);
            result.add(new SourceFileInfo(fileName, path, content));
            total++;
            bb.setStatusMessage("Fetching files… (" + total + " found)");
        }

        bb.setStatusMessage("Analyzing files…");
        calculator.computeAll(result);
        bb.setStatusMessage(total + " file(s) processed.");
        return result;
    }

    private String extractFileName(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
