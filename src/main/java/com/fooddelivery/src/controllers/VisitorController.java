package com.fooddelivery.src.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
public class VisitorController {

    private final DynamoDbClient db;
    private static final String TABLE = "eb-demo-visitors";
    private static final String KEY = "count";

    @Autowired
    public VisitorController(DynamoDbClient db) {
        this.db = db;
    }

    @GetMapping("/visit")
    public String visit() {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", AttributeValue.builder().s(KEY).build());

        GetItemRequest getReq = GetItemRequest.builder()
                .tableName(TABLE).key(key).build();

        Map<String, AttributeValue> item = db.getItem(getReq).item();

        long current = 0;
        if (item != null && item.containsKey("visits")) {
            current = Long.parseLong(item.get("visits").n());
        }

        long updated = current + 1;

        Map<String, AttributeValue> newItem = new HashMap<>();
        newItem.put("id", AttributeValue.builder().s(KEY).build());
        newItem.put("visits", AttributeValue.builder().n(String.valueOf(updated)).build());

        db.putItem(PutItemRequest.builder()
                .tableName(TABLE).item(newItem).build());

        return "Visitor count: " + updated;
    }
}
