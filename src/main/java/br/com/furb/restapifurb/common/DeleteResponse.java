package br.com.furb.restapifurb.common;

import org.json.JSONObject;

public class DeleteResponse extends JSONObject {

    private static final String SUCCESS = "success";
    private static final String TEXT = "text";

    public DeleteResponse() {
        this.put(SUCCESS, new JSONObject());
    }

    public DeleteResponse(String text) {
        this.put(SUCCESS, new JSONObject());
        this.setText(text);
    }

    public void setText(String text) {
        ((JSONObject) this.get(SUCCESS)).put(TEXT, text);
    }

    public String getText() {
        return (String) ((JSONObject) this.get(SUCCESS)).get(TEXT);
    }

}
