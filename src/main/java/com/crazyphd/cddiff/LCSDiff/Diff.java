package com.crazyphd.cddiff.LCSDiff;
import java.util.*;
import com.crazyphd.cddiff.HTMLBuilder.HTMLBuilder;
import com.crazyphd.cddiff.HTMLBuilder.HTMLBuilderTag;

public class Diff {

    // Lists of all types of string modificaions for current files
    private static HashMap<String, String> changes = new HashMap<>();
    private static HashMap<String, String> additions = new HashMap<>();
    private static HashMap<String, String> deletions = new HashMap<>();

    // Lists of lines of files.
    private static List<HTMLBuilderTag> firstFile = new ArrayList<>();
    private static List<HTMLBuilderTag> secondFile = new ArrayList<>();
    
    public static void get(String[] x, String[] y, int m, int n, int[][] lcs, HTMLBuilderTag first, HTMLBuilderTag second) {
        firstFile.add(null);
        secondFile.add(null);

        boolean changed = false;
        List<Integer> moveM = new ArrayList<Integer>(m);
        List<Integer> moveN = new ArrayList<Integer>(n);
        
        int i = 0;
        int j = 0;

        while (i < m && j < n) {
            if (x[i].equals(y[j])) {
                firstFile.add(i + 1, addString(first, x, i));
                secondFile.add(j + 1, addString(second, y, j));

                if (moveN.size() > 0) {
                    if (i == j) {
                        // string changed
                        changes.put(getRange(moveM), getRange(moveN));
                    } else {
                        // string added
                        additions.put(String.valueOf(i), getRange(moveN));
                    }
                } else if (moveN.size() == 0 && moveM.size() > 0) {
                    // string deleted
                    deletions.put(getRange(moveM), String.valueOf(j));
                }
                // clean-up and move to next diagonal
                moveM.clear();
                moveN.clear();
                changed = false;
                i++;
                j++;
            } else if (lcs[i + 1][j] >= lcs[i][j + 1]) {
                // a line was removed or changed
                firstFile.add(i + 1, addString(first, x, i));
                moveM.add(++i);
                changed = true;
            } else {
                // a line was added or changed
                secondFile.add(j + 1, addString(second, y, j));
                moveN.add(++j);
                changed = false;
            }
        }

        moveM.clear();
        moveN.clear();

        // If reached the end of one of files
        while (i < m || j < n) {
            if (i == m) {
                secondFile.add(j + 1, addString(second, y, j));
                moveN.add(++j);
            } else if (j == n) {
                firstFile.add(i + 1, addString(first, x, i));
                moveM.add(++i);
            }
        }

        if (moveM.isEmpty()) {
            if (!changed) {
                additions.put(String.valueOf(i), getRange(moveN));
            } else {
                moveM.add(i);
                changes.put(getRange(moveM), getRange(moveN));
            }
        } else {
            deletions.put(getRange(moveM), String.valueOf(j));
        }

        applyStyles();
    }

    /**
     * Change styles of strings containers
     */
    private static void applyStyles() {
        for (Map.Entry<String, String> entry : changes.entrySet()) {
            String[] firstChanges = entry.getKey().split(":");
            String[] secondChanges = entry.getValue().split(":");
            if (firstChanges[0] != "") {
            int f1 = Integer.parseInt(firstChanges[0]);
            int f2 = Integer.parseInt(firstChanges[1]);
            int s1 = Integer.parseInt(secondChanges[0]);
            int s2 = Integer.parseInt(secondChanges[1]);
            for (int i = f1; i <= f2; i++)
                addStyle(firstFile.get(i), "changed");
            for (int i = s1; i <= s2; i++)
                addStyle(secondFile.get(i), "changed");
            }
        }
        for (Map.Entry<String, String> entry : additions.entrySet()) {
            String[] secondChanges = entry.getValue().split(":");
            if (secondChanges[0] != "") {
            int s1 = Integer.parseInt(secondChanges[0]);
            int s2 = Integer.parseInt(secondChanges[1]);
            for (int i = s1; i <= s2; i++)
                addStyle(secondFile.get(i), "added");
            }
        }
        for (Map.Entry<String, String> entry : deletions.entrySet()) {
            String[] firstChanges = entry.getKey().split(":");
            if (firstChanges[0] != "") {
            int f1 = Integer.parseInt(firstChanges[0]);
            int f2 = Integer.parseInt(firstChanges[1]);
            for (int i = f1; i <= f2; i++)
                addStyle(firstFile.get(i), "deleted");
            }
        }
    }

    private static void addStyle(HTMLBuilderTag cont, String style) {
        cont.children.getFirst().changeAttributes(HTMLBuilder.attr(new String[]{"class", "stringNumber " + style}));
        cont.children.getLast().changeAttributes(HTMLBuilder.attr(new String[]{"class", "stringContent " + style}));
    }

    private static HTMLBuilderTag addString(HTMLBuilderTag parentTag, String[] file, int index) {
        HTMLBuilderTag cont = parentTag.appendChild(new HTMLBuilderTag("div", HTMLBuilder.attr(new String[]{"class", "stringContainer"}), null, "\t\t"));
        cont.appendChild(new HTMLBuilderTag("div", HTMLBuilder.attr(new String[]{"class", "stringNumber"}), String.valueOf(index + 1), "\t\t\t"));
        cont.appendChild(new HTMLBuilderTag("div", HTMLBuilder.attr(new String[]{"class", "stringContent"}), file[index], "\t\t\t"));
        return cont;
    }

    /**
     * Get range of strings that have been changed/added/deleted.
     * @param moves
     * @return
     */
    private static String getRange(List<Integer> moves) {
        int nMoves = moves.size();
        String range = "";
        if (nMoves > 0) {
            range += moves.get(0) + ":" + moves.get(nMoves-1);
        }
        return range;
    }
}
