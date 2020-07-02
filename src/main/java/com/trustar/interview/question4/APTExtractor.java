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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class APTExtractor {

    public List<IntrusionSetObject> extract() throws IOException {

        final Document anHtmlDocument = fetchHtmlFromUrl();

        final List<String> urlsOfJsonsIntrusionSets = extractUrlsOfJsonsIntrusionSets(anHtmlDocument);

        return extractAPTs(urlsOfJsonsIntrusionSets);
    }

    private Document fetchHtmlFromUrl() throws IOException {
        return Jsoup.connect("https://github.com/mitre/cti/tree/master/enterprise-attack/intrusion-set").get();
    }

    private List<String> extractUrlsOfJsonsIntrusionSets(Document anHtmlDocument) {
        return anHtmlDocument.select("a[href]").stream()
                .map(anchor -> anchor.attr("href"))
                .filter(this::hrefCorrespondToAnIntrusionSet)
                .map(this::mapHTMLBlobHrefToARawFileUrl)
                .collect(Collectors.toList());
    }

    private boolean hrefCorrespondToAnIntrusionSet(String href) {
        return href.startsWith("/mitre/cti/blob/master/enterprise-attack/intrusion-set/intrusion-set--")
                && href.endsWith(".json");
    }

    private String mapHTMLBlobHrefToARawFileUrl(String href) {
        return href.replaceFirst("/mitre/cti/blob", "https://raw.githubusercontent.com/mitre/cti");
    }

    private List<IntrusionSetObject> extractAPTs(List<String> urlsOfJsonsIntrusionSets) {
        return urlsOfJsonsIntrusionSets.parallelStream()
                .map(mapUrlToIntrusionSetObjectOrThrowException())
                .filter(intrusionSetsWithNamesThatMatchTheAPTFormat())
                .filter(intrusionSetsWithRelatedUrlsThatCorrespondsToNotIgnoredDomains())
                .collect(Collectors.toList());
    }

    private Predicate<IntrusionSetObject> intrusionSetsWithNamesThatMatchTheAPTFormat() {
        return intrusionSetObject -> intrusionSetObject.getName().matches("APT\\d{2}");
    }

    private Predicate<IntrusionSetObject> intrusionSetsWithRelatedUrlsThatCorrespondsToNotIgnoredDomains() {
        return intrusionSetObject -> intrusionSetObject.getUrls().stream()
                .noneMatch(url -> url.contains("symantec.com") || url.contains("cybereason.com"));
    }

    private Function<String, IntrusionSetObject> mapUrlToIntrusionSetObjectOrThrowException() {
        return aUrl -> {
            try {
                return mapUrlToIntrusionSetObject(aUrl);
            } catch (IOException error) {
                throw new APTExtractorException("Problems retrieving the urls", error);
            }
        };
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
