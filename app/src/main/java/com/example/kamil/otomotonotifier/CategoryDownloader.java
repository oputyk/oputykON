package com.example.kamil.otomotonotifier;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamil on 31/08/2017.
 */

public class CategoryDownloader {
    static List<Category> downloadCategories() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readValue(new URL("definitions/parameters/?json=1"), ObjectNode.class).findValue("categories");
        Categories categories = objectMapper.convertValue(node, Categories.class);
        return categories.getCategories();
    }
}
