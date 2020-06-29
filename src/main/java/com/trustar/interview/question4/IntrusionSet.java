package com.trustar.interview.question4;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(value = {"type", "id", "spec_version"})
public class IntrusionSet {
    public List<Map<String, Object>> objects;

}
