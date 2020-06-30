package com.trustar.interview.question4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.trustar.interview.exception.APTExtractorException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class APTExtractor {
    public List<IntrusionSetObject> extract() throws IOException {

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

        final Map<String, Object> graph = objectMapper.readValue(src, Map.class);

        final List<Map<String, Object>> objects =
                (List<Map<String, Object>>) graph.getOrDefault("objects", ImmutableList.of(ImmutableMap.of()));

        final Map<String, Object> stringObjectMap = objects.get(0);

        final String name = (String) stringObjectMap.getOrDefault("name", "");

        final List<String> aliases = (List<String>) stringObjectMap.getOrDefault("aliases", ImmutableList.of());

        final List<Map<String, Object>> externalReferencesMap =
                (List<Map<String, Object>>) stringObjectMap.getOrDefault("external_references", ImmutableMap.of());

        final List<String> relatedUrls = externalReferencesMap.stream()
                .map(externalReferences -> (String) externalReferences.get("url"))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new IntrusionSetObject(name, aliases, relatedUrls);
    }
}
