package com.crazyphd.cddiff.HTMLBuilder;
import java.util.*;

public class CSSBuilder {
    private LinkedList<CSSEntity> entities;
    public CSSBuilder() {
        entities = new LinkedList<CSSEntity>();
    }

    public CSSBuilder newEntity(String name) {
        entities.add(new CSSEntity(name));
        return this;
    }

    public CSSBuilder addProp(String prop, String val) {
        entities.getLast().addProp(prop, val);
        return this;
    }

    public String build() {
        StringBuilder result = new StringBuilder();
        while(entities.size() > 0) {
            result.append(entities.pollFirst().build());
        }
        return result.toString();
    }
}

class CSSEntity {
    private HashMap<String, String> characteristics = new HashMap<String, String>();
    private String name;

    CSSEntity(String name) {
        this.name = name;
    }

    public void addProp(String prop, String val) {
        this.characteristics.put(prop, val);
    }

    public StringBuilder build() {
        StringBuilder out = new StringBuilder();
        out.append(name)
            .append(" {\n");
        for (Map.Entry<String, String> entry : characteristics.entrySet()) {
            out.append("\t")
                .append(entry.getKey())
                .append(": ")
                .append(entry.getValue())
                .append(";")
                .append("\n");
        }
        out.append("}\n");
        return out;
    }
}