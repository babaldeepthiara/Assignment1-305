package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton shared state that holds the repository URL, loaded file list, selected 
 * file name, a reference to the GridPanel, and a list of RepoLoadedListeners.
 *
 * @author babaldeep and yaneli
 */

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
