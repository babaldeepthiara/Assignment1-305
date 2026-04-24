package org.example;

import java.util.ArrayList;
import java.util.List;

public class Blackboard {
    private static Blackboard instance;

    private String repoPath;
    private String selectedFileName;
    private List<SourceFileInfo> files;
    private GridPanel gridPanel;

    private Blackboard() {
        files = new ArrayList<>();
        selectedFileName = "";
    }

    public static Blackboard getInstance() {
        if (instance == null) {
            instance = new Blackboard();
        }
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
}
