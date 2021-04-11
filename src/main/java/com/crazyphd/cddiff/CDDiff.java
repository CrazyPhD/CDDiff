package com.crazyphd.cddiff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.crazyphd.cddiff.HTMLBuilder.HTMLBuilder;
import com.crazyphd.cddiff.HTMLBuilder.HTMLBuilderTag;
import com.crazyphd.cddiff.HTMLBuilder.CSSBuilder;
import com.crazyphd.cddiff.LCSDiff.Diff;
import com.crazyphd.cddiff.LCSDiff.LCS;

public class CDDiff {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        File file1 = new File(args[0]);
        File file2 = new File(args[1]);
        
        String[] x = getLinesFromFile(file1);
        String[] y = getLinesFromFile(file2);

        HTMLBuilder resultPage = new HTMLBuilder();
        CSSBuilder css = makeCSS();

        // Initialization of files div-containters.
        HTMLBuilderTag first = new HTMLBuilderTag("div", HTMLBuilder.attr(new String[]{"id", "left"}), null, "\t");
        HTMLBuilderTag second = new HTMLBuilderTag("div", HTMLBuilder.attr(new String[]{"id", "right"}), null, "\t");
        
        // Get diff of files and modify containers for files contents
        Diff.get(x, y, x.length, y.length, LCS.build(x, y), first, second);
        
        // Form HTML
        resultPage.newTag("head", null, null)
                .appendChild("title", null, "Diff", 0)
                .appendChild("style", null, css.build(), 0)
            .newTag("body", null, null)
                .appendChild("div", HTMLBuilder.attr(new String[]{"id", "header"}), null, 0)
                .appendChild("div", HTMLBuilder.attr(new String[]{"id", "header_left"}), file1.getName(), 1)
                .appendChild("div", HTMLBuilder.attr(new String[]{"id", "header_right"}), file2.getName(), 1)
                .appendChild(first, 0)
                .appendChild(second, 0);

        resultPage.save("diff.html");
    }

    /**
     * Read file line by line.
     * @param file the desired file.
     * @return file contents as List of its lines.
     * @throws FileNotFoundException
     */
    private static String[] getLinesFromFile(File file) throws FileNotFoundException {
        List<String> lines;
        try(Scanner sc = new Scanner(file)) {
            lines = new ArrayList<String>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
        }
        return lines.toArray(new String[lines.size()]);
    }

    /**
     * Create specific CSS.
     * @return CSSBuilder object
     */
    private static CSSBuilder makeCSS() {
        return new CSSBuilder().newEntity("#left")
                .addProp("height", "100%")
                .addProp("left", "0")
                .addProp("overflow", "auto")
                .addProp("position", "absolute")
                .addProp("width", "50%")
            .newEntity("#right")
                .addProp("border-left", "1px solid #000")
                .addProp("height", "100%")
                .addProp("overflow", "auto")
                .addProp("position", "absolute")
                .addProp("right", "0")
                .addProp("width", "50%")
            .newEntity("html")
                .addProp("height", "100%")
            .newEntity("body")
                .addProp("margin", "0")
                .addProp("min-height", "100%")
                .addProp("padding", "0")
            .newEntity(".stringContainer")
                .addProp("border-bottom", "1px solid #bbb")
                .addProp("min-width", "100%")
                .addProp("overflow", "hidden")
                .addProp("width", "fit-content")
            .newEntity(".stringNumber")
                .addProp("background-color", "#eee")
                .addProp("border-right", "1px solid #bbb")
                .addProp("float", "left")
                .addProp("height", "20px")
                .addProp("padding-right", "5px")
                .addProp("text-align", "right")
                .addProp("width", "35px")
            .newEntity(".stringContent")
                .addProp("height", "20px")
                .addProp("overflow", "hidden")
                .addProp("white-space", "nowrap")
            .newEntity(".stringNumber.added")
                .addProp("background-color", "#76e876")
            .newEntity(".stringNumber.changed")
                .addProp("background-color", "#76a0e8")
            .newEntity(".stringNumber.deleted")
                .addProp("background-color", "#808080")
            .newEntity(".stringContent.added")
                .addProp("background-color", "#b5ffb6")
            .newEntity(".stringContent.changed")
                .addProp("background-color", "#b5d0ff")
            .newEntity(".stringContent.deleted")
                .addProp("background-color", "#b1b1b1")
            .newEntity("#header")
                .addProp("background", "linear-gradient(0deg, #ccc, #eee, #ccc)")
                .addProp("border-bottom", "1px solid #000")
                .addProp("font-size", "22px")
                .addProp("height", "25px")
                .addProp("overflow", "hidden")
                .addProp("white-space", "nowrap")
            .newEntity("#header_left")
                .addProp("left", "0")
                .addProp("position", "absolute")
                .addProp("text-align", "center")
                .addProp("width", "50%")
            .newEntity("#header_right")
                .addProp("border-left", "1px solid #000")
                .addProp("position", "absolute")
                .addProp("right", "0")
                .addProp("text-align", "center")
                .addProp("width", "50%");
    }
}