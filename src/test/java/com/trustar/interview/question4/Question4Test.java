package com.trustar.interview.question4;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Question4Test {

    @Test
    public void triggerAPTExtractorTest() throws IOException {
        APTExtractor extractor = new APTExtractor();
        List<IntrusionSetObject> list = extractor.extract();
        for (IntrusionSetObject aptInfo : list) {
            final String name = aptInfo.getName();
            if (!name.matches("APT\\d{2}")) {
                System.out.println(name);
            }
            assertTrue(name.matches("APT\\d{2}"));
            aptInfo.getUrls()
                    .forEach(url -> {
                        System.out.println(url);
                        assertFalse(url.contains("symantec.com"));
                        assertFalse(url.contains("cybereason.com"));
                    });
        }
    }
}
