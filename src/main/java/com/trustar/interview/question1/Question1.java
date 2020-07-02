package com.trustar.interview.question1;

import com.trustar.interview.exception.QuestionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Question1 {

    private Question1() {
    }

    public static List<String> findPatterns(List<Pattern> patterns, String textInput) {

        if (patterns == null || patterns.isEmpty()) {
            throw new QuestionException("Patterns is null or empty");
        }

        if (textInput == null) {
            throw new QuestionException("Text input is null");
        }

        return patterns.parallelStream()
                .map(pattern -> selectMatches(textInput, pattern))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static List<String> selectMatches(String textInput, Pattern pattern) {

        List<String> result = new ArrayList<>();
        final Matcher regexMatcher = pattern.matcher(textInput);

        while (regexMatcher.find()) {
            if (regexMatcher.group().length() > 0) {
                result.add(regexMatcher.group());
            }
        }

        return result;
    }
}
