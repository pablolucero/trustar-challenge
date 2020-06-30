package com.trustar.interview.question2;

import com.google.common.collect.ImmutableList;
import com.trustar.interview.exception.QuestionException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Question2Test {

    @Test
    public void exceptionIsThrownIfThePattersListIsNull() {
        assertThrows(QuestionException.class, () -> Question2.findPatterns(null, "abc"));
    }

    @Test
    public void exceptionIsThrownIfThePattersListIsEmpty() {
        assertThrows(QuestionException.class, () -> Question2.findPatterns(ImmutableList.of(), "abc"));
    }

    @Test
    public void exceptionIsThrownIfTheTextInputIsNull() {
        assertThrows(QuestionException.class, () -> Question2.findPatterns(ImmutableList.of(Pattern.compile(".")), null));
    }

    @Test
    public void emptyTextInputReturnsAnEmptyList() {
        final List<Pattern> patterns = ImmutableList.of(Pattern.compile("."));

        final List<String> actual = Question2.findPatterns(patterns, "");
        final List<String> expected = ImmutableList.of();
        assertEquals(expected, actual);
    }

    @Test
    public void onlyOnePatternMatch() {
        final List<Pattern> patterns = ImmutableList.of(Pattern.compile("(the)"));

        final List<String> actual = Question2.findPatterns(patterns, "The fox jumped over the fence");
        final List<String> expected = ImmutableList.of("the");
        assertEquals(expected, actual);
    }

    @Test
    public void multiplePatternsMatched() {
        final List<Pattern> patterns = ImmutableList.of(
                Pattern.compile("th."),
                Pattern.compile("he"),
                Pattern.compile("fox")
        );

        final List<String> actual = Question2.findPatterns(patterns, "The fox jumped over the fence");
        final List<String> expected = ImmutableList.of("the", "he", "fox");
        assertEquals(expected, actual);
    }

}