package com.burpsuitcrack.json;

import java.util.*;

public class JSONObject
{
    private JSONType type;
    private JSONObject[] list;
    public String string;
    private String number;
    private Boolean bool;
    private HashMap<String, JSONObject> dict;
    
    public JSONObject(final JSONType type, final Object value) {
        this.type = type;
        switch (type) {
            case List: {
                this.list = (JSONObject[])value;
                break;
            }
            case Dict: {
                this.dict = (HashMap<String, JSONObject>)value;
                break;
            }
            case String: {
                this.string = (String)value;
                break;
            }
            case Number: {
                this.number = (String)value;
                break;
            }
            case Boolean: {
                this.bool = (Boolean)value;
            }
        }
    }
    
    public void set(final String key, final JSONObject value) {
        if (this.type != JSONType.Dict) {
            throw new RuntimeException("Not a dict");
        }
        this.dict.put(key, value);
    }
    
    public JSONObject get(final String key) {
        if (this.type != JSONType.Dict) {
            throw new RuntimeException("Not a dict");
        }
        return this.dict.get(key);
    }
    
    public String getString(final String key) {
        if (this.dict.get(key) == null) {
            throw new RuntimeException("Unknown key");
        }
        if (this.dict.get(key).type == JSONType.String) {
            return this.dict.get(key).string;
        }
        throw new RuntimeException("Not a string");
    }
    
    public String getNumber(final String key) {
        if (this.dict.get(key) == null) {
            throw new RuntimeException("Unknown key");
        }
        if (this.dict.get(key).type == JSONType.Number) {
            return this.dict.get(key).number;
        }
        throw new RuntimeException("Not a number");
    }
    
    public Boolean getBoolean(final String key) {
        if (this.dict.get(key) == null) {
            throw new RuntimeException("Unknown key");
        }
        if (this.dict.get(key).type == JSONType.Boolean) {
            return this.dict.get(key).bool;
        }
        throw new RuntimeException("Not a boolean");
    }
    
    public JSONObject[] getList(final String key) {
        if (this.dict.get(key) == null) {
            throw new RuntimeException("Unknown key");
        }
        if (this.dict.get(key).type == JSONType.List) {
            return this.dict.get(key).list;
        }
        throw new RuntimeException("Not a list");
    }
    
    public Boolean Boolean() {
        if (this.type == JSONType.Boolean) {
            return this.bool;
        }
        throw new RuntimeException("Not a boolean");
    }
    
    public String Number() {
        if (this.type == JSONType.Number) {
            return this.number;
        }
        throw new RuntimeException("Not a number");
    }
    
    public String String() {
        if (this.type == JSONType.String) {
            return this.string;
        }
        throw new RuntimeException("Not a string");
    }
}
