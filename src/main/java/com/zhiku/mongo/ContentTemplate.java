package com.zhiku.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ContentTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;



}
