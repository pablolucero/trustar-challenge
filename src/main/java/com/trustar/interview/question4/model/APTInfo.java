package com.trustar.interview.question4.model;

import com.trustar.interview.exception.APTExtractorException;

import java.util.Collection;

public class APTInfo {
    private final String name;
    private final Collection<String> aliases;
    private final Collection<String> urls;

    public APTInfo(String name, Collection<String> aliases, Collection<String> urls) {
        if (!name.matches("APT\\d{2}")) {
            throw new APTExtractorException("APT name must use the format APNnn, where nn is a number. "
                    + name + " is not valid");
        }

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

    @Override
    public String toString() {
        return "APTInfo{" +
                "name='" + name + '\'' +
                ", aliases=" + aliases +
                ", urls=" + urls +
                '}';
    }
}
