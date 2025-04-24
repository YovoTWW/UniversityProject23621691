import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JsonReader {
    public static void main(String[] args) {
        {
            try {
                String content1 = new String(Files.readAllBytes(Paths.get("src/JSON_Files/data.json")));
                String content2 = new String(Files.readAllBytes(Paths.get("src/JSON_Files/invalid.json")));
                String content3 = new  String(Files.readAllBytes(Paths.get("src/JSON_Files/created.json")));
                Path path1 = Paths.get("src/JSON_Files/data.json");
                Path path = null;


                Map<String, String> jsonMap = parseJson(content3);

                    for (String key : jsonMap.keySet()) {
                       String value = jsonMap.get(key);
                       System.out.println(key + ": "+value);
                    }
                String newContent = "{\n" +
                        "  \"name\": \"Vasil Bojilov\",\n" +
                        "  \"age\": 55,\n" +
                        "  \"city\": \"Burgas\",\n" +
                        "  \"country\": \"Bulgaria\"\n" +
                        "}";
                open(path,"data.json");
                close(path,"data.json");
                    //create(Paths.get("src/JSON_Files/created.json"),newContent);
                //set(path1,newContent);
                //validate(content2);
                //set(jsonMap,"name","Kiril Petrov");
                //createPath(jsonMap,"city","Sofia");
                //deletePath(jsonMap,"name");
                //search(jsonMap,"name");
                //search(jsonMap,"city");


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

    public static void validate(String json){

        json = json.trim().replaceAll("[{}\"]", "");
        String[] pairs = json.split(",");


        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length != 2) {
               System.out.println("Invalid Json format at row : "+pair);
            }

        }
    }

    public static void search(Map<String,String> jsonMap , String keyName)
    {
        for (String key : jsonMap.keySet()) {
            if(keyName.equals(key))
            {
            String value = jsonMap.get(keyName);
            System.out.println(key+ ": "+value);
            }
        }
    }

    public static void set(Path filePath , String newContent)
    {
        /*if(jsonMap.containsKey(keyName)){
            jsonMap.put(keyName,newValue);
        }
        else {
            System.out.println("Ключ с име : '" + keyName + "' не беше намерен.");
        }*/
        if (Files.exists(filePath)) {
            try {
                Files.write(filePath, newContent.getBytes());
                System.out.println("JSON файлът беше пренаписан успешно.");
            } catch (IOException e) {
                System.out.println("Грешка при презаписването на JSON файлът.");
            }
        }
        else{
            System.out.println("JSON файлът с такъв път не е намерен.");
        }
    }

    public static void create(Path filePath , String newContent){
        if (Files.exists(filePath)) {
            System.out.println("JSON файлът с тавък път вече съществува.");
        }
        else {
            try {
                Files.write(filePath, newContent.getBytes());
                System.out.println("JSON файлът беше създаден успешно.");
            } catch (IOException e) {
                System.out.println("Грешка при създаването на JSON файлът.");
            }
        }
    }

    public static void delete(Path filePath ){
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
                System.out.println("JSON файлът беше изтрит успешно.");
            } catch (IOException e) {
                System.out.println("Грешка при изтриването на JSON файлът.");
            }
        } else {
            System.out.println("JSON файлът с такъв път не е намерен.");
        }
    }

    public static void move(Path pathFrom , Path pathTo){
        try{
            if(!Files.exists(pathFrom) || !Files.exists(pathTo)){
                System.out.println("JSON файлът с такъв път не е намерен.");
                return;
            }
            byte[] content = Files.readAllBytes(pathFrom);
            Files.write(pathTo,content);
            Files.write(pathFrom,new byte[0]);
        }
        catch (IOException e) {
            System.out.println("Грешка при преместването на информация.");
        }
    }

    public static void open(Path path , String fileName){
        if(path == null) {
            path = Paths.get("src/JSON_Files/" + fileName);
        }
        else{
            System.out.println("Дръг файл е отворен . Затворете отвореният файл , за да може да отворите друг файл.");
            return;
        }

        if (Files.exists(path) && Files.isReadable(path)) {
            try {
                String content = Files.readString(path);  // Opens and reads the file
                System.out.println("Файлът е успешно отворен");
                System.out.println("Съдържание:\n" + content);
            } catch (IOException e) {
                System.out.println("Файлът съществува , но не беше успешно отворен");
            }
        } else {
            System.out.println("Файлът не съществува или не може да бъде прочетен");
        }

    }

    public static void close(Path path , String fileName){
        if(path == Paths.get("src/JSON_Files/" + fileName)) {
            path = null;
            System.out.println("Файлът е успешно затворен");
        }
        else{
            System.out.println("Няма отворен файл с такова наименование");
        }

    }
}
