package com.crazyphd.cddiff.HTMLBuilder;
import java.io.*;
import java.util.*;


public class HTMLBuilder {
    private StringBuilder contents = new StringBuilder();
    private LinkedList<HTMLBuilderTag> dom = new LinkedList<HTMLBuilderTag>();
    
    public HTMLBuilder() {}

    public void append(String str) {
        this.contents.append(str).append("");
    }

    public HTMLBuilder newTag(String tagName, HashMap<String, String> attributes, String content) {
        HTMLBuilderTag tag = new HTMLBuilderTag(tagName, attributes, content, "\t");
        dom.add(tag);
        return this;
    }

    public HTMLBuilder newTag(HTMLBuilderTag tag) {
        dom.add(tag);
        return this;
    }

    public HTMLBuilder appendChild(String tagName, HashMap<String, String> attributes, String content, int depth) {
        HTMLBuilderTag lastNew = dom.getLast();
        
        String tabs = "\t\t";

        for (int i = 0; i < depth; i++)
            tabs += "\t";

        HTMLBuilderTag tag = new HTMLBuilderTag(tagName, attributes, content, tabs);
        while (depth > 0) {
            if (lastNew.children.size() > 0) {
                lastNew = lastNew.children.getLast();
            }
            depth--;
        }
        tag.tabs = tabs;
        lastNew.appendChild(tag);
        return this;
    }

    public HTMLBuilder appendChild(HTMLBuilderTag tag, int depth) {
        HTMLBuilderTag lastNew = dom.getLast();
        
        String tabs = "\t";
        for (int i = 0; i < depth; i++)
            tabs += "\t";
        
        while (depth > 0) {
            if (lastNew.children.size() > 0) {
                lastNew = lastNew.children.getLast();
            }
            depth--;
        }
        tag.tabs = tabs;
        lastNew.appendChild(tag);
        return this;
    }

    public String build() {
        append("<!DOCTYPE HTML>\n");
        append("<html>\n");
        while (dom.size() > 0) {
            HTMLBuilderTag tag = dom.pollFirst();
            append(tag.build());
        }
        append("</html>");
        return contents.toString();
    }

    public void save(String filepath) throws IOException {
        try(FileWriter fileWriter = new FileWriter(filepath)) {
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(build());
            printWriter.close();
        }
    }

    public static HashMap<String, String> attr(String args[]) {
        HashMap<String, String> attributes = new HashMap<String, String>();
        if (args.length > 0 && args.length % 2 == 0) {
            for(int i = 0; i < args.length; i++) {
                String key = args[i];
                String val = args[++i];
                attributes.put(key, val);
            }
        }
        return attributes;
    }
}
