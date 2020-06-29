package com.trustar.interview.question4.model;

import java.util.Collection;

public class IntrusionSetObject implements IntrusionSetInfoInterface {
    private final String name;
    private final Collection<String> aliases;
    private final Collection<String> urls;

    public IntrusionSetObject(String name, Collection<String> aliases, Collection<String> urls) {
        this.name = name;
        this.aliases = aliases;
        this.urls = urls;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<String> getAliases() {
        return aliases;
    }

    @Override
    public Collection<String> getUrls() {
        return urls;
    }

    @Override
    public String toString() {
        return "IntrusionSetInfo{" +
                "name='" + name + '\'' +
                ", aliases=" + aliases +
                ", urls=" + urls +
                '}';
    }
}
