package com.weibo.keeplooking.json;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * See more: <br/>
 * <a href="http://wiki.fasterxml.com/JacksonInFiveMinutes">Jackson in Five Minutes</a> <br/>
 * <a href="http://tutorials.jenkov.com/java-json/jackson-annotations.html">Jackson Annotations</a>
 * <br/>
 */
public class JacksonTest {
    private ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

    @Test
    public void testSetString() {

    }

    /**
     * For POJO getters & setters are needed.
     */
    @Test
    public void testPojoBinding() throws JsonParseException, JsonMappingException, IOException {
        User user = mapper.readValue(this.getClass().getResourceAsStream("/user.json"), User.class);
        System.out.println(user.getName().getFirst());
        user.setVerified(true);
        mapper.writeValue(new File("/tmp/user-modified.json"), user);
    }

    @Test
    public void testRawBinding() throws JsonParseException, JsonMappingException, IOException {
        @SuppressWarnings("unchecked")
        Map<String, Object> userData = mapper.readValue(this.getClass().getResourceAsStream("/user.json"), Map.class);
        System.out.println(userData.get("name")); // real type is LinkedHashMap

        userData = new HashMap<String, Object>();
        Map<String, String> nameStruct = new HashMap<String, String>();
        nameStruct.put("first", "Qiang");
        nameStruct.put("last", "Wang");
        userData.put("name", nameStruct);
        userData.put("gender", "MALE");
        userData.put("verified", Boolean.TRUE);
        userData.put("userImage", "Rm9vYmFyIQ==");
        mapper.writeValue(new File("/tmp/user-modified.json"), userData);
    }

    @Test
    public void testTreeModel() throws JsonProcessingException, IOException {
        // can either use mapper.readTree(source), or mapper.readValue(source, JsonNode.class);
        JsonNode rootNode = mapper.readTree(this.getClass().getResourceAsStream("/user.json"));

        // ensure that "last name" isn't "Xmler"; if is, change to "Jsoner"
        JsonNode nameNode = rootNode.path("name");
        String lastName = nameNode.path("last").textValue();
        if ("xmler".equalsIgnoreCase(lastName)) {
            ((ObjectNode) nameNode).put("last", "Jsoner");
        }

        // and write it out
        mapper.writeValue(new File("/tmp/user-modified.json"), rootNode);
    }

    @Test
    public void testTreeModel1() throws JsonProcessingException, IOException {
        String json = "{\"type\":\"JEXL\",\"expression\":\"changeType == 'UPDATE' && Product.CustomFieldX__c > 999 && Product.CustomFieldX__c > Product.CustomFieldX__c_old\"}";
        JsonNode rootNode = mapper.readTree(json);
        JsonNode expressionNode = rootNode.path("expression");
        System.out.println(expressionNode.asText());
    }

    @Test
    public void testTreeConstruct() throws JsonGenerationException, JsonMappingException, IOException {
        ObjectNode userOb = mapper.createObjectNode();
        ObjectNode nameOb = userOb.putObject("name");
        nameOb.put("first", "Joe");
        nameOb.put("last", "Sixpack");
        userOb.put("gender", Gender.MALE.toString());
        userOb.put("verified", false);
        byte[] imageData = "xxxxx".getBytes(); // or wherever it comes from
        userOb.put("userImage", imageData);

        mapper.writeValue(new File("/tmp/user-modified.json"), userOb);
    }
}


class User {
    private Gender gender;
    private Name name;
    private boolean isVerified;
    private byte[] userImage;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }
}


enum Gender {
    MALE, FEMALE;
}


class Name {
    private String first;
    private String last;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
