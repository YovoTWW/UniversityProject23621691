import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JsonReader {
    public static void main(String[] args) {
        {
            try {
                String content = new String(Files.readAllBytes(Paths.get("src/JSON_Files/data.json")));

                Map<String, String> jsonMap = parseJson(content);

                System.out.println("Name: " + jsonMap.get("name"));
                System.out.println("Age: " + jsonMap.get("age"));
                System.out.println("City: " + jsonMap.get("city"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }







    public static Map<String, String> parseJson(String json) {
        Map<String, String> map = new HashMap<>();

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
}
