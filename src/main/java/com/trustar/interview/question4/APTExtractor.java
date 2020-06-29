package com.trustar.interview.question4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustar.interview.exception.APTExtractorException;
import com.trustar.interview.question4.model.IntrusionSetInfoInterface;
import com.trustar.interview.question4.model.IntrusionSetObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class APTExtractor {
    public List<IntrusionSetInfoInterface> extract() throws IOException {

        Document doc = Jsoup.connect("https://github.com/mitre/cti/tree/master/enterprise-attack/intrusion-set").get();
        final List<String> urlsOfJsonsIntrusionSets = doc.select("a").stream()
                .map(anchor -> anchor.attr("href"))
                .filter(href -> href.startsWith("/mitre/cti/blob/master/enterprise-attack/intrusion-set/intrusion-set--")
                        && href.endsWith(".json"))
                .map(href -> href.replaceFirst("/mitre/cti/blob", "https://raw.githubusercontent.com/mitre/cti"))
                .collect(Collectors.toList());


        return urlsOfJsonsIntrusionSets.parallelStream()
                .map(aUrl -> {
                    try {
                        return mapUrlToIntrusionSetObject(aUrl);
                    } catch (IOException error) {
                        throw new APTExtractorException("Problems retrieving the urls", error);
                    }
                })
                .filter(intrusionSetObject -> intrusionSetObject.getName().matches("APT\\d{2}"))
                .filter(intrusionSetObject -> intrusionSetObject.getUrls().stream()
                        .noneMatch(url -> url.contains("symantec.com") || url.contains("cybereason.com")))
                .collect(Collectors.toList());
    }


    private IntrusionSetObject mapUrlToIntrusionSetObject(String aUrl) throws IOException {

        final ObjectMapper objectMapper = new ObjectMapper();

        final URL src = new URL(aUrl);
        Map<String, Object> graph = objectMapper.readValue(src, Map.class);

        final List<Map<String, Object>> objects = (List<Map<String, Object>>) graph.get("objects");
        final Map<String, Object> stringObjectMap = objects.get(0);

        final String name = (String) stringObjectMap.get("name");
        final List<String> aliases = (List<String>) stringObjectMap.get("aliases");
        final List<Map<String, Object>> externalReferencesMap = (List<Map<String, Object>>) stringObjectMap.get("external_references");
        final List<String> relatedUrls = externalReferencesMap.stream()
                .filter(externalReferences -> externalReferences.get("url") != null)
                .map(externalReferences -> (String) externalReferences.get("url"))
                .collect(Collectors.toList());
        return new IntrusionSetObject(name, aliases, relatedUrls);
    }
}
