package com.trustar.interview.question4;

import java.util.Collection;

public class IntrusionSetObject {
    private final String name;
    private final Collection<String> aliases;
    private final Collection<String> urls;

    public IntrusionSetObject(String name, Collection<String> aliases, Collection<String> urls) {
        this.name = name;
        this.aliases = aliases;
        this.urls = urls;
    }

    public String getName() {
        return name;
    }

    public Collection<String> getAliases() {
        return aliases;
    }

    public Collection<String> getUrls() {
        return urls;
    }

    public String toString() {
        return "IntrusionSetInfo{" +
                "name='" + name + '\'' +
                ", aliases=" + aliases +
                ", urls=" + urls +
                '}';
    }
}
