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
                //String content1 = new String(Files.readAllBytes(Paths.get("src/JSON_Files/data.json")));
                //String content2 = new String(Files.readAllBytes(Paths.get("src/JSON_Files/invalid.json")));
                //String content3 = new  String(Files.readAllBytes(Paths.get("src/JSON_Files/created.json")));
                //Path path1 = Paths.get("src/JSON_Files/data.json");
                //Path path = null;


               // Map<String, String> jsonMap = parseJson(content3);

                    //for (String key : jsonMap.keySet()) {
                      //String value = jsonMap.get(key);
                      // System.out.println(key + ": "+value);
                   // }
                String newContent = "{\n" +
                        "  \"name\": \"Vasil Bojilov\",\n" +
                        "  \"age\": 55,\n" +
                        "  \"city\": \"Burgas\",\n" +
                        "  \"country\": \"Bulgaria\"\n" +
                        "}";

                List<PathReference> listPathRef = new ArrayList<>();
                PathReference pathRef = new PathReference(null);

                //open(pathRef,"data.json");
                //System.out.println(pathRef.location);
                //close(pathRef,"data.json");
                    //create(Paths.get("src/JSON_Files/created.json"),newContent);
                //set(path1,newContent);
                //validate(content2);
                //set(jsonMap,"name","Kiril Petrov");
                //createPath(jsonMap,"city","Sofia");
                //deletePath(jsonMap,"name");
                //search(jsonMap,"name");
                //search(jsonMap,"city");
                Scanner scanner = new Scanner(System.in);
                System.out.println("\nИзберете опция:");
                System.out.println("Open FileName");
                System.out.println("Close FileName");
                System.out.println("Print");
                System.out.println("Search KeyName");
                System.out.println("Set FileName NewContent");
                System.out.println("Save FileName");
                System.out.println("Exit");

                while (true) {
                    // Print menu options
                    System.out.print("Напишете команда --> : ");
                    String input = scanner.nextLine();
                    String [] commands = input.split(" ");
                    switch (commands[0]) {
                        case "Open":
                            if(commands.length>1) {
                                open(pathRef, commands[1], listPathRef);
                            }
                            else{
                                System.out.println("Напишете името на файла след 'Open'");
                            }
                            break;
                        case "Close":
                            if(commands.length>1) {
                                close(pathRef, commands[1]);
                            }
                            else{
                                System.out.println("Напишете името на файла след 'Close'");
                            }
                            break;
                        case "Print":
                            print(pathRef);
                            break;
                        case "Search":
                            search(pathRef,commands[1]);
                            break;
                        case "Set":
                            if(commands.length>2) {
                                String[] content = Arrays.copyOfRange(commands,2,commands.length);
                                String result = String.join(" ",content);
                                set(Paths.get("src\\JSON_Files\\" + commands[1]), result, listPathRef);
                            }
                            //Set invalid.json {"name" : "Pesho","age" : "47"}
                            else{
                                System.out.println("Напишете името на файла и новото съдържание след 'Set'");
                            }
                            break;
                        case "Save":
                            if(commands.length>1) {
                                save(commands[1],listPathRef);
                            }
                            else{
                                System.out.println("Напишете името на файла след 'Save'");
                            }
                            break;
                        case "Exit":
                            System.out.println("Програмата беше затворена");
                            scanner.close();
                            return; // Exit the program
                        default:
                            System.out.println("Invalid option. Try again.");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void print(PathReference path) throws IOException {
        /*String content = new  String(Files.readAllBytes(path.location));
        Map<String, String> jsonMap = parseJson(content);
        for (String key : jsonMap.keySet()) {
            String value = jsonMap.get(key);
            System.out.println(key + ": "+value);
        }*/

        System.out.println(path.content);
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

    public static void search(PathReference path , String keyName) throws IOException {
        String content = new  String(Files.readAllBytes(path.location));
        Map<String, String> jsonMap = parseJson(content);
        for (String key : jsonMap.keySet()) {
            if(keyName.equals(key))
            {
            String value = jsonMap.get(keyName);
            System.out.println(key+ ": "+value);
            }
        }
    }

    public static void set(Path filePath , String newContent,List<PathReference> list)
    {
        /*if(jsonMap.containsKey(keyName)){
            jsonMap.put(keyName,newValue);
        }
        else {
            System.out.println("Ключ с име : '" + keyName + "' не беше намерен.");
        }*/
        PathReference pathRef = new PathReference(filePath);
        if (Files.exists(filePath)) {
            /*try {*/
                for(PathReference pr : list) {
                    if (pr.location.equals(filePath)) {
                        pr.content = newContent;
                        System.out.println("JSON файлът беше пренаписан успешно 1.");
                        return ;
                    }
                }
                //Files.write(filePath, newContent.getBytes());
                pathRef.content = newContent;
                list.add(pathRef);
                System.out.println("JSON файлът беше пренаписан успешно 2.");
            } /*catch (IOException e) {
                System.out.println("Грешка при презаписването на JSON файлът.");
            }*/
        //}
        else{
            System.out.println("JSON файлът с такъв път не е намерен.");
        }
    }

    public static void save(String fileName,List<PathReference> list){
        Path filePath = null;
        String fileContent = null;
        for(PathReference pr : list){
            if(pr.location.equals(Paths.get("src\\JSON_Files\\" + fileName))){
                filePath = pr.location;
                fileContent = pr.content;
            }
        }
        if (filePath != null && fileContent != null) {
            try {
                Files.write(filePath, fileContent.getBytes());
                System.out.println("Промените бяха запазени във файла.");
            } catch (IOException e) {
                System.out.println("Грешка при запазването на файла.");
            }
        } else {
            System.out.println("Няма файл за запазване.");
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

    public static void open(PathReference path , String fileName,List<PathReference>list) throws IOException {

        Boolean ExistsInList = false;

        if(path.location == null) {
            for(PathReference pr : list){
                if(pr.location.equals(Paths.get("src\\JSON_Files\\" + fileName))){
                    path.location = pr.location;
                    path.content = pr.content;
                    ExistsInList = true;
                    System.out.println("Called");
                }
            }
            if(!ExistsInList) {
                path.location = Paths.get("src/JSON_Files/" + fileName);
                path.content = Files.readString(path.location);
            }
        }
        else{
            System.out.println("Дръг файл е отворен . Затворете отвореният файл , за да може да отворите друг файл.");
            return;
        }

        if (Files.exists(path.location) && Files.isReadable(path.location)) {
            try {
                String content = Files.readString(path.location);  // Opens and reads the file
                System.out.println("Файлът е успешно отворен");
                System.out.println("Съдържание:\n" + content);
            } catch (IOException e) {
                System.out.println("Файлът съществува , но не беше успешно отворен");
            }
        } else {
            System.out.println("Файлът не съществува или не може да бъде прочетен");
        }

    }

    public static void close(PathReference path , String fileName){
        if(path.location!=null) {
            if (path.location.equals(Paths.get("src\\JSON_Files\\" + fileName))) {
                path.location = null;
                System.out.println("Файлът е успешно затворен");
            } else {
                //System.out.println("Given path: " + path.location);
                //System.out.println("Expected path: "+"src\\JSON_Files\\" + fileName);
                System.out.println("Няма отворен файл с такова наименование");
            }
        }
        else{
            System.out.println("Няма отворен файл с такова наименование");
        }

    }
}
