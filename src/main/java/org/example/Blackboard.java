package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton shared state that holds the repository URL, loaded file list, selected
 * file name, a reference to the GridPanel, and a list of RepoLoadedListeners.
 *
 * @author babaldeep and yaneli
 * @version 1.0
 */


// TO SEE THE NAMES OF THE FILES AFTER UPLOADING THE GITHUB LINK, CLICK ON A SQUARE AND LOOK AT THE BOTTOM OF THE WINDOW.
// OR YOU CAN HOVER YOUR MOUSE OVER THE SQUARES TO SEE THE FILE NAMES AND # OF LINES IN THE FILE.
// I used the following GitHub repository to test the application: https://github.com/javiergs/TULIP

public class Blackboard {

    private static final Blackboard instance = new Blackboard();

    private String repoPath;
    private String selectedFileName;
    private List<SourceFileInfo> files;
    private GridPanel gridPanel;
    private final List<RepoLoadedListener> listeners;

    private Blackboard() {
        files = new ArrayList<>();
        selectedFileName = "";
        listeners = new ArrayList<>();
    }

    public static Blackboard getInstance() {
        return instance;
    }

    public String getRepoPath() {
        return repoPath;
    }

    public void setRepoPath(String repoPath) {
        this.repoPath = repoPath;
    }

    public String getSelectedFileName() {
        return selectedFileName;
    }

    public void setSelectedFileName(String selectedFileName) {
        this.selectedFileName = selectedFileName;
    }

    public List<SourceFileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<SourceFileInfo> files) {
        this.files = files;
    }

    public GridPanel getGridPanel() {
        return gridPanel;
    }

    public void setGridPanel(GridPanel gridPanel) {
        this.gridPanel = gridPanel;
    }

    public void addRepoLoadedListener(RepoLoadedListener listener) {
        listeners.add(listener);
    }

    public void notifyRepoLoaded() {
        for (RepoLoadedListener listener : listeners) {
            listener.onRepoLoaded();
        }
    }
}
