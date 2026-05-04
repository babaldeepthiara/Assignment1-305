package org.example;

import javiergs.tulip.github.GitHubHandler;
import javiergs.tulip.github.URLFactory;
import javiergs.tulip.github.URLObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author babaldeep and yaneli
 * @version 2.0
 */
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
            if (isJavaSourceFile(path)) {
                String content = handler.getFileContent(
                        u.owner,
                        u.repository,
                        path,
                        u.revision
                );

                String fileName = extractFileName(path);
                result.add(new SourceFileInfo(fileName, path, content));
            }
        }

        return result;
    }

    /**
     * Only include .java files inside src directory (recommended by assignment)
     */
    private boolean isJavaSourceFile(String path) {
        return path != null
                && path.endsWith(".java")
                && path.contains("/src/");
    }

    private String extractFileName(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }
}