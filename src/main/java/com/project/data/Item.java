package com.project.data;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "item", type = "article")
public class Item {

}
