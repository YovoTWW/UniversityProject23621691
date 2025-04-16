import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JsonReader {
    public static void main(String[] args) {
        {
            try {
                String content1 = new String(Files.readAllBytes(Paths.get("src/JSON_Files/data.json")));
                String content2 = new String(Files.readAllBytes(Paths.get("src/JSON_Files/invalid.json")));

                Map<String, String> jsonMap = parseJson(content2);

                    for (String key : jsonMap.keySet()) {
                        String value = jsonMap.get(key);
                        System.out.println(key + ": "+value);
                    }

                validateJson(content2);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }







    public static Map<String, String> parseJson(String json) {
        Map<String, String> map = new LinkedHashMap<>();

        json = json.trim().replaceAll("[{}\"]", "");
        String[] pairs = json.split(",");


        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return map;
    }

    public static void validateJson(String json){

        json = json.trim().replaceAll("[{}\"]", "");
        String[] pairs = json.split(",");


        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length != 2) {
               System.out.println("Invalid Json format at row : "+pair);
            }

        }
    }


}
