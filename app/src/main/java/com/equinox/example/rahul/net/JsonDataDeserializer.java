package com.equinox.example.rahul.net;

import android.util.Log;

import com.equinox.example.rahul.model.Data;
import com.equinox.example.rahul.model.DataLists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonDataDeserializer implements JsonDeserializer<DataLists> {
    @Override
    public DataLists deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DataLists dataLists = new DataLists();
        if (json.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                if (entry.getKey().equals("status")) {
                    Log.d("Test", "Primitive: " + entry.getKey() + " = " + entry.getValue().getAsString());
                    dataLists.setStatus(entry.getValue().getAsString());
                } else if (entry.getKey().equals("DATA")) {
                    Log.d("Test", "Object: key: " + entry.getKey() + " = " + entry.getValue());
                    JsonArray jsonArray = entry.getValue().getAsJsonArray();
                    for (int i =0;i<jsonArray.size();i++) {

                        Data db = new Data();

                        JsonObject actor = jsonArray.get(i).getAsJsonObject();
                        String name = actor.get("name").toString();
                        String dept_name = actor.get("dept_name").toString();
                        db.setName(name.replace("\"", ""));
                        db.setDept_name(dept_name.replace("\"", ""));
                        dataLists.addData(db);
                    }
                }
            }
        }
        return dataLists;
    }
}
