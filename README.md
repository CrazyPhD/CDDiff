# CDDiff 1.0

A Java application that shows the difference between two files using [LCS](https://en.wikipedia.org/wiki/Longest_common_subsequence_problem)

The resulting diff is represented by the generated HTML-file.

---
## Installation

1. Clone repository
	
    `git clone https://github.com/CrazyPhD/CDDiff.git`
2. Run `mvn install`

---
## Usage

Run `java -jar cddiff.jar <file1> <file2>` (without triangle brackets)

As the result there will be generated diff.html file with diff info:
- ![#b5ffb6](https://via.placeholder.com/15/b5ffb6/000000?text=+) `Added string`
- ![#b5d0ff](https://via.placeholder.com/15/b5d0ff/000000?text=+) `Changed string`
- ![#b1b1b1](https://via.placeholder.com/15/b1b1b1/000000?text=+) `Removed string`

See [example](https://github.com/CrazyPhD/CDDiff/tree/master/example)
![Example](https://raw.githubusercontent.com/CrazyPhD/CDDiff/master/example/diff.html.png)
