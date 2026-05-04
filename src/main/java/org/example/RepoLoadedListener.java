package org.example;

/**
 * Listener interface to notify UI components when a repository has been
 * successfully loaded and the file list has been updated on the Blackboard.
 *
 * @author babaldeep and yaneli
 * @version 1.0
 */


// TO SEE THE NAMES OF THE FILES AFTER UPLOADING THE GITHUB LINK, CLICK ON A SQUARE AND LOOK AT THE BOTTOM OF THE WINDOW.
// OR YOU CAN HOVER YOUR MOUSE OVER THE SQUARES TO SEE THE FILE NAMES AND # OF LINES IN THE FILE.
// I used the following GitHub repository to test the application: https://github.com/javiergs/TULIP

public interface RepoLoadedListener {
    void onRepoLoaded();
}
