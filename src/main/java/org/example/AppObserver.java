package org.example;

/**
 * Observer interface for application-level events.
 * UI components implement this to react only to the events they care about,
 * preventing unnecessary refreshes (e.g., file selection must not rebuild the grid).
 *
 * @author babaldeep and yaneli
 * @version 3.0
 *
 */

public interface AppObserver {
    void onEvent(AppEvent event);
}
