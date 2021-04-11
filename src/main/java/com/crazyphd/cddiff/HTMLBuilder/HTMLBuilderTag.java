package com.crazyphd.cddiff.HTMLBuilder;
import java.util.*;

public class HTMLBuilderTag {
    private StringBuilder openTag = new StringBuilder();
    private StringBuilder content = new StringBuilder();
    private StringBuilder closeTag = new StringBuilder();
    private String tagName = "";
    public LinkedList<HTMLBuilderTag> children = new LinkedList<HTMLBuilderTag>();
    public String tabs = "";

    public HTMLBuilderTag(String tagName, HashMap<String, String> attributes, String content, String tabs) {
        String attributesString = "";
        this.tabs = tabs;
        if (attributes != null) {
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                String attr = entry.getKey();
                String value = entry.getValue();
                attributesString += " " + attr + "=\"" + value + "\"";
            }
        }
        this.tagName = tagName;
        openTag.append(tabs + "<")
                .append(tagName)
                .append(attributesString)
                .append(">");
        if (content != null && content.length() > 0) {
            for (String cont : content.split("\\r?\\n")) {
                this.content.append(tabs + "\t" + cont + "\n");
            }
        }
        closeTag.append(tabs + "</").append(tagName).append(">\n");
    }

    public HTMLBuilderTag addContent(String content) {
        for (String cont : content.split("\\r?\\n")) {
            this.content.append(tabs + "\t" + cont);
        }
        return this;
    }

    public HTMLBuilderTag appendChild(HTMLBuilderTag tag) {
        this.children.add(tag);
        return tag;
    }

    public void changeAttributes(HashMap<String, String> attributes) {
        String attributesString = "";
        if (attributes != null) {
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                String attr = entry.getKey();
                String value = entry.getValue();
                attributesString += " " + attr + "=\"" + value + "\"";
            }
        }
        openTag = new StringBuilder();
        openTag.append(tabs + "<")
                .append(tagName)
                .append(attributesString)
                .append(">");
    }

    public String build() {
        while (children.size() > 0) {
            HTMLBuilderTag tag = children.pollFirst();
            content.append(tag.build());
        }
        String contents = content.toString();
        return openTag.toString() + "\n" + contents + closeTag.toString();
    }
}
