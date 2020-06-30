package com.trustar.interview;

import com.trustar.interview.question4.APTExtractor;
import com.trustar.interview.question4.IntrusionSetObject;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        final APTExtractor aptExtractor = new APTExtractor();
        final List<IntrusionSetObject> intrusionSetObjects = aptExtractor.extract();
        for (IntrusionSetObject intrusionSetObject : intrusionSetObjects) {

            System.out.println("- NAME");
            System.out.println("\t" + intrusionSetObject.getName());
            System.out.println("- ALIASES");
            intrusionSetObject.getAliases().forEach(x -> System.out.println("\t" + x));
            System.out.println("- RELATED URLS");
            intrusionSetObject.getUrls().forEach(x -> System.out.println("\t" + x));
            System.out.println();
        }
    }
}
