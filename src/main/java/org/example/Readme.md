# Assignment 02 – GitHub Repository Analyzer

**Authors:** babaldeep and yaneli  
**Version:** 2.0

---

## Overview

This application accepts a public GitHub repository URL, fetches all `.java` source files,
computes software design metrics per file, and displays them as a color-coded, transparency-scaled
grid of interactive squares.

---

## How to Run

### Prerequisites

- Java 17 or later
- Maven 3.8+
- A GitHub personal access token (required by the TULIP library)

### Setup

1. Create a `tulip.properties` file in `src/main/resources/` with your token:
   ```
   GITHUB_TOKEN=ghp_yourtokenhere
   ```
   This file is **not** committed to version control. You must create it yourself.

### Usage

- Use **File → Open from URL…** to enter a public GitHub repository URL.
- Example URL used for testing: `https://github.com/javiergs/TULIP`
- Loading takes a couple seconds, so the status bar shows progress.
- **Hover over any square to see the full metrics tooltip.**
- Click a square to highlight it and display its file name in the status bar.
- Use **Action → Reload** to re-fetch the current repository.
- Use **Action → Clear** to reset the grid.

---

## Metrics

All metrics are computed per `.java` file via static text analysis.

| Metric | Description |
|--------|-------------|
| **LOC** | Non-empty, non-blank lines of source code. Comment-only lines **are excluded** from LOC. |
| **CC** | Cyclomatic Complexity. Starts at 1; increments for each `if`, `for`, `while`, `case` keyword found in the file. |
| **A** | Abstractness. `1.0` if the file declares an `interface` or `abstract class`; `0.0` otherwise. |
| **I** | Instability. `Cout / (Cin + Cout)`. Cout = outgoing relationships (this class depends on others); Cin = incoming relationships (others depend on this class). Six relationship types are detected: inheritance (`extends`), realization (`implements`), aggregation (field declarations), composition (initialized field declarations), association (method calls returning/taking the type), and dependency (parameter types and imports). |
| **D** | Distance from Main Sequence. `|A + I - 1|`. Closer to 0 is better. |

---

## Visualization

| Visual property | Encodes | Rule |
|-----------------|---------|------|
| **Color** | Cyclomatic Complexity (CC) | Red if CC ≥ 10 · Yellow if CC ≥ 5 · Green otherwise |
| **Transparency** | Lines of Code (LOC) | Fully transparent at 0 lines · Fully opaque at max LOC · Scaled proportionally |
