# Assignment 03 – GitHub Repository Analyzer

**Authors:** babaldeep and yaneli  
**Version:** 3.0

## Overview

This application accepts a public GitHub repository URL, fetches all `.java` source files, computes software design metrics per file, and displays them across three views: a color-coded interactive grid, an Abstractness vs Instability scatter plot, and a UML class diagram.

## How to Run

### Prerequisites

- Java 17 or later
- Maven 3.8+
- A GitHub personal access token (required by the TULIP library)

### Setup

Create a `tulip.properties` file in `src/main/resources/` with your token:

```
GITHUB_TOKEN=ghp_yourtokenhere
```

This file is not committed to version control. You must create it yourself.

### Usage

- Use **File → Open from URL…** to enter a public GitHub repository URL.
- Example URL used for testing: `https://github.com/javiergs/TULIP`
- Loading takes a couple seconds; the status bar shows progress.
- Use **Action → Reload** to re-fetch the current repository.
- Use **Action → Clear** to reset all views.
- Use **Help → About** to view application info.

## Views

### Grid Tab

- Hover over any square to see the full metrics tooltip (LOC, CC, Ca, Ce, I, A, D).
- Click a square to highlight it and display its file name in the status bar.

### Metrics Tab

- Displays an Abstractness vs Instability scatter plot.
- Each file is plotted as a dot using the same color and transparency as its Grid square.
- The main sequence diagonal and Painful/Useless zones are marked.

### Diagram Tab

- Displays a UML class diagram of all loaded `.java` files generated via PlantUML.
- Shows inheritance, realization, and association relationships between classes.

## Metrics

All metrics are computed per `.java` file via static text analysis.

| Metric | Description                                                                                   |
| ------ | --------------------------------------------------------------------------------------------- | --------- | --- |
| LOC    | Non-empty lines of source code.                                                               |
| CC     | Cyclomatic Complexity. Starts at 1; increments for each `if`, `for`, `while`, `case` keyword. |
| Ca     | Afferent Coupling. Number of classes in the repo that depend on this class.                   |
| Ce     | Efferent Coupling. Number of classes in the repo this class depends on.                       |
| I      | Instability. `Ce / (Ca + Ce)`. If `Ca + Ce = 0`, then `I = 0`.                                |
| A      | Abstractness. `1.0` if the file declares an interface or abstract class; `0.0` otherwise.     |
| D      | Distance from Main Sequence. `                                                                | A + I - 1 | `.  |

## Visualization

| Visual property | Encodes                    | Rule                                                                           |
| --------------- | -------------------------- | ------------------------------------------------------------------------------ |
| Color           | Cyclomatic Complexity (CC) | Red if CC ≥ 10 · Yellow if CC ≥ 5 · Green otherwise                            |
| Transparency    | Lines of Code (LOC)        | Fully transparent at 0 lines · Fully opaque at max LOC · Scaled proportionally |
