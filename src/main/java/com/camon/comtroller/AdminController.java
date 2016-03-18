package com.camon.comtroller;

import com.google.gson.JsonObject;
import com.mongodb.CommandResult;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jooyong on 2016-03-07.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MongoTemplate adminMongoTemplate;

    @Value("${spring.data.mongodb.host}")
    private String mongodbDataHost;

    @Value("${spring.data.mongodb.port}")
    private int mongodbDataPort;


    @RequestMapping(method = RequestMethod.GET)
    public CommandResult stats(@RequestParam String databaseName) throws UnknownHostException {
        MongoTemplate mongoTemplate = createMongoTemplate(databaseName);
        return mongoTemplate.getDb().getStats();
    }

    /**
     * database 내의 전체 콜렉션명 조회
     * @param databaseName
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/{databaseName}",method = RequestMethod.GET)
    public Set<String> collections(@PathVariable String databaseName) throws UnknownHostException {
        MongoTemplate mongoTemplate = createMongoTemplate(databaseName);
        Set<String> collectionNames = mongoTemplate.getCollectionNames();
        return collectionNames;
    }

    /**
     * 특정 컬렉션 rename
     * @param from
     * @param to
     * @param collectionName
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/renameCollection", method = RequestMethod.GET)
    public CommandResult renameCollection(@RequestParam String from, @RequestParam String to, @RequestParam String collectionName) throws UnknownHostException {
        JsonObject connandJson = new JsonObject();
        connandJson.addProperty("renameCollection", from + "." + collectionName);
        connandJson.addProperty("to", to + "." + collectionName);
        CommandResult commandResult = adminMongoTemplate.executeCommand(connandJson.toString());
        return commandResult;
    }

    /**
     * database내의 전체 컬렉션 rename
     * @param from
     * @param to
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/renameCollections", method = RequestMethod.GET)
    public List<CommandResult> renameCollections(@RequestParam String from, @RequestParam String to) throws UnknownHostException {
        List<CommandResult> results = new ArrayList<>();

        Set<String> collections = this.collections(from);
        for (String collectionName : collections) {
            JsonObject connandJson = new JsonObject();
            connandJson.addProperty("renameCollection", from + "." + collectionName);
            connandJson.addProperty("to", to + "." + collectionName);
            CommandResult commandResult = adminMongoTemplate.executeCommand(connandJson.toString());
            results.add(commandResult);
        }

        return results;
    }

    /**
     * database drop
     * @param databaseName
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/dropDatabase", method = RequestMethod.GET)
    public Set<String> dropDatabase(@RequestParam String databaseName) throws UnknownHostException {
        MongoTemplate mongoTemplate = createMongoTemplate(databaseName);
        mongoTemplate.getDb().dropDatabase();
        return this.collections(databaseName);
    }

    /**
     * MongoTemplate 생성
     * @param databaseName
     * @return
     * @throws UnknownHostException
     */
    private MongoTemplate createMongoTemplate(String databaseName) throws UnknownHostException {
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(new MongoClient(mongodbDataHost, mongodbDataPort), databaseName);
        MongoTemplate template = new MongoTemplate(mongoDbFactory);
        return template;
    }

}
