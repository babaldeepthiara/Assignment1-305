package org.example;

/**
 * Enumerates the types of application events that observers can receive.
 * Separating event types prevents file-selection from triggering a full grid refresh
 * and keeps the observer pattern precise.
 *
 * @author babaldeep and yaneli
 * @version 2.0
 * 
 */

public enum AppEvent {
    REPO_LOADED,
    FILE_SELECTED,
    STATUS_CHANGED
}
