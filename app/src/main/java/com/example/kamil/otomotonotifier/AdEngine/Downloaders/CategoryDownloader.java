package com.example.kamil.otomotonotifier.AdEngine.Downloaders;

import com.example.kamil.otomotonotifier.AdEngine.Models.Categories;
import com.example.kamil.otomotonotifier.AdEngine.Models.Category;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by kamil on 31/08/2017.
 */

public class CategoryDownloader {
    static List<Category> downloadCategories() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readValue(new URL("https://www.otomoto.pl/i2/definitions/categories/?json=1"), ObjectNode.class).findValue("categories");
        Categories categories = objectMapper.convertValue(node, Categories.class);
        return categories.getCategoryList();
    }
}
