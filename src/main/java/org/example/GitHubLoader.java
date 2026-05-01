package org.example;

import javiergs.tulip.github.GitHubHandler;
import javiergs.tulip.github.URLFactory;
import javiergs.tulip.github.URLObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Fetches and parses source files from a public GitHub repository.
 * Uses the TULIP library to handle all GitHub API communication and file retrieval.
 *
 * @author babaldeep and yaneli
 * @version 1.0
 */


// TO SEE THE NAMES OF THE FILES AFTER UPLOADING THE GITHUB LINK, CLICK ON A SQUARE AND LOOK AT THE BOTTOM OF THE WINDOW.
// OR YOU CAN HOVER YOUR MOUSE OVER THE SQUARES TO SEE THE FILE NAMES AND # OF LINES IN THE FILE.
// I used the following github repository to test the application: https://github.com/javiergs/TULIP

public class GitHubLoader {

    private final GitHubHandler handler;

    public GitHubLoader() {
        handler = new GitHubHandler();
    }

    public List<SourceFileInfo> loadFiles(String repoUrl) throws Exception {
        URLObject u = URLFactory.parseGitHubUrl(repoUrl);
        List<String> allPaths = handler.listFilesRecursive(repoUrl);
        List<SourceFileInfo> result = new ArrayList<>();

        for (String path : allPaths) {
            if (path.endsWith(".java")) {
                String content = handler.getFileContent(u.owner, u.repository, path, u.revision);
                int lineCount = countLines(content);
                String fileName = extractFileName(path);
                result.add(new SourceFileInfo(fileName, path, lineCount));
            }
        }
        return result;
    }

    private int countLines(String content) {
        if (content == null || content.isEmpty()) return 0;
        return content.split("\n", -1).length;
    }

    private String extractFileName(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
