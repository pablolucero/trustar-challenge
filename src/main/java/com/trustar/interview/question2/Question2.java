package com.trustar.interview.question2;

import com.trustar.interview.exception.QuestionException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Question2 {

    private Question2() {
    }

    public static List<String> findPatterns(List<Pattern> patterns, String textInput) {

        if (patterns == null || patterns.isEmpty()) {
            throw new QuestionException("Patterns is null or empty");
        }

        List<String> result = new ArrayList<>();

        for (Pattern pattern : patterns) {

            final Matcher regexMatcher = pattern.matcher(textInput);

            while (regexMatcher.find()) {
                if (regexMatcher.group().length() > 0) {
                    result.add(regexMatcher.group());
                    textInput = textInput.replaceFirst(pattern.pattern(), "");
                }
            }
        }
        return result;
    }

}
