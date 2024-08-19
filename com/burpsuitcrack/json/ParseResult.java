package com.burpsuitcrack.json;

public class ParseResult
{
    public int len;
    public JSONObject obj;
    
    public ParseResult(final int len, final JSONObject obj) {
        this.len = len;
        this.obj = obj;
    }
    
    public void AddLen(final int len) {
        this.len += len;
    }
}
