package com.trustar.interview.question3;

import com.google.common.collect.ImmutableList;
import com.trustar.interview.exception.QuestionException;
import com.trustar.interview.question1.Question1;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Question3Test {

    @Test
    public void exceptionIsThrownIfThePattersListIsNull() {
        assertThrows(QuestionException.class, () -> Question3.findPatterns(null, "abc"));
    }

    @Test
    public void exceptionIsThrownIfThePattersListIsEmpty() {
        assertThrows(QuestionException.class, () -> Question3.findPatterns(ImmutableList.of(), "abc"));
    }

    @Test
    public void exceptionIsThrownIfTheTextInputIsNull() {
        assertThrows(QuestionException.class, () -> Question1.findPatterns(ImmutableList.of(Pattern.compile(".")), null));
    }

    @Test
    public void emptyTextInputReturnsAnEmptyList() {
        List<Pattern> patterns = ImmutableList.of(Pattern.compile("."));

        final List<String> actual = Question3.findPatterns(patterns, "");
        final List<String> expected = ImmutableList.of();
        assertEquals(expected, actual);
    }

    @Test
    public void onlyOnePatternMatch() {
        List<Pattern> patterns = ImmutableList.of(Pattern.compile("(the)"));

        final List<String> actual = Question3.findPatterns(patterns, "The fox jumped over the fence");
        final List<String> expected = ImmutableList.of("the");
        assertEquals(expected, actual);
    }

    @Test
    public void multiplePatternsMatched() {
        final List<Pattern> patterns = ImmutableList.of(
                Pattern.compile("(the)"),
                Pattern.compile("(fox|fence)"),
                Pattern.compile("(abc)")
        );

        final Pattern patternToIgnore = Pattern.compile("f.x");
        final List<String> actual = Question3.findPatterns(patterns, "The fox jumped over the fence", patternToIgnore);
        final List<String> expected = ImmutableList.of("the", "fence");
        assertEquals(expected, actual);
    }

}