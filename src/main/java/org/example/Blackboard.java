package org.example;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton shared data store for the application.
 * Holds the repository URL, loaded file list, selected file name, and status message.
 * Notifies registered app observers with typed app events.
 *
 * @author babaldeep and yaneli
 * @version 2.0
 * 
 */

public class Blackboard {

    private static final Blackboard INSTANCE = new Blackboard();

    private String repoUrl;
    private String selectedFileName;
    private String statusMessage;
    private List<SourceFileInfo> files;
    private final List<AppObserver> observers;

    private Blackboard() {
        files = new ArrayList<>();
        selectedFileName = "";
        statusMessage = "";
        observers = new ArrayList<>();
    }

    public static Blackboard getInstance() {
        return INSTANCE;
    }

    public String getRepoUrl() { 
        return repoUrl; 
    }

    public void setRepoUrl(String repoUrl) { 
        this.repoUrl = repoUrl; 
    }

    public String getSelectedFileName() { 
        return selectedFileName; 
    }

    public void setSelectedFileName(String selectedFileName) {
        this.selectedFileName = selectedFileName;
        notify(AppEvent.FILE_SELECTED);
    }

    public String getStatusMessage() { 
        return statusMessage; 
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        notify(AppEvent.STATUS_CHANGED);
    }

    public List<SourceFileInfo> getFiles() { 
        return files; 
    }

    public void setFiles(List<SourceFileInfo> files) {
        this.files = files;
        notify(AppEvent.REPO_LOADED);
    }

    public void addObserver(AppObserver observer) {
        observers.add(observer);
    }

    private void notify(AppEvent event) {
        for (AppObserver observer : observers) {
            observer.onEvent(event);
        }
    }
}
