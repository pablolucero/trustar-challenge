package com.trustar.interview.question4;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;

public class Question4Test {

    @Test
    public void end2endAPTExtractorTest() throws IOException {
        APTExtractor extractor = new APTExtractor();
        List<IntrusionSetObject> apts = extractor.extract();
        for (IntrusionSetObject apt : apts) {
            assertNameMatchAPTFormat(apt);
            assertUrlsDoNotHaveIgnoredDomains(apt);
        }
    }

    private void assertNameMatchAPTFormat(IntrusionSetObject aptInfo) {
        final String name = aptInfo.getName();
        assertThat(name, matchesPattern("APT\\d{2}"));
    }

    private void assertUrlsDoNotHaveIgnoredDomains(IntrusionSetObject aptInfo) {
        aptInfo.getUrls()
                .forEach(url -> {
                    assertThat(url, not(containsString("symantec.com")));
                    assertThat(url, not(containsString("cybereason.com")));
                });
    }
}
