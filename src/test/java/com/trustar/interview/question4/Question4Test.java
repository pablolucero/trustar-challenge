package com.trustar.interview.question4;

import com.trustar.interview.question4.model.IntrusionSetInfoInterface;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Question4Test {

    //        @Ignore
    @Test
    public void triggerAPTExtractorTest() throws IOException {
        APTExtractor extractor = new APTExtractor();
        List<IntrusionSetInfoInterface> list = extractor.extract();
//        list.stream().forEach(System.out::println);
        for (IntrusionSetInfoInterface aptInfo : list) {
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
//            System.out.println(aptInfo.getName());
//            System.out.println(aptInfo.getAliases());
//            System.out.println(aptInfo.getUrls());
//            System.out.println(aptInfo);
        }
    }
}
