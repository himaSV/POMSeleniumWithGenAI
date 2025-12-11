package genai.app.Utils;

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class jsonReader {
    private JSONObject json;

    public jsonReader(String path) throws Exception {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(path);
        Object obj = parser.parse(reader);
        json = (JSONObject) obj;
    }

    public Object get(String key) {
        return json.get(key);
    }
}
