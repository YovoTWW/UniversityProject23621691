import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JsonReader {
    public static void main(String[] args) {
        {
            try {

                List<PathReference> listPathRef = new ArrayList<>();
                PathReference pathRef = new PathReference(null);


                Scanner scanner = new Scanner(System.in);
                BasicWriter basicWriter = new BasicWriter();
                ContentExtendedWriter contentExtendedWriter = new ContentExtendedWriter();
                LocationExtendedWriter locationExtendedWriter = new LocationExtendedWriter();
                TwoFileWriter twoFileWriter = new TwoFileWriter();
                FullWriter fullWriter = new FullWriter();

                while (true) {
                    //System.out.print("Напишете команда --> : ");
                    fullWriter.FullWrite("Напишете команда --> : ");
                    String input = scanner.nextLine();
                    String [] commands = input.split(" ");
                    switch (commands[0]) {
                        case "Validate":
                            if(commands.length>1) {
                                validate(Paths.get("src\\" + commands[1]));
                            }
                            else{
                                //System.out.println("Напишете името на файла след 'Validate'");
                                basicWriter.Write("Validate");
                            }
                            break;
                        case "Open":
                            if(commands.length>1) {
                                open(pathRef, commands[1], listPathRef);
                            }
                            else{
                                //System.out.println("Напишете името на файла след 'Open'");
                                basicWriter.Write("Open");
                            }
                            break;
                        case "Close":
                            if(commands.length>1) {
                                close(pathRef, commands[1]);
                            }
                            else{
                                //System.out.println("Напишете името на файла след 'Close'");
                                basicWriter.Write("Close");
                            }
                            break;
                        case "Print":
                            print(pathRef);
                            break;
                        case "Search":
                            if(commands.length>1) {
                                search(pathRef,commands[1]);
                            }
                            else{
                                fullWriter.FullWrite("Напишете името на ключа след 'Search'");
                            }
                            break;
                        case "Set":
                            if(commands.length>2) {
                                String[] content = Arrays.copyOfRange(commands,2,commands.length);
                                String result = String.join(" ",content);
                                set(Paths.get("src\\" + commands[1]), result, listPathRef);
                            }
                            //Set invalid.json {"name" : "Pesho","age" : "47"}
                            else{
                                //System.out.println("Напишете името на файла и новото съдържание след 'Set'");
                                contentExtendedWriter.Write("Set");
                            }
                            break;
                        case "Save":
                            if(commands.length>1) {
                                save(commands[1],listPathRef);
                            }
                            else{
                                //System.out.println("Напишете името на файла след 'Save'");
                                basicWriter.Write("Save");
                            }
                            break;
                        case "SaveAs":
                            if(commands.length>2) {
                                saveAs(commands[2],commands[1],listPathRef);
                            }
                            else{
                                //System.out.println("Напишете новата локация и името на файла след 'SaveAs'");
                                locationExtendedWriter.Write("SaveAs");
                            }
                            break;
                        case "Create":
                            if(commands.length>2) {
                                String[] content = Arrays.copyOfRange(commands,2,commands.length);
                                String result = String.join(" ",content);
                                create(Paths.get("src\\" + commands[1]), result);
                            }
                            //Create new.json {"name" : "Pesho","age" : "47"}
                            else{
                                //System.out.println("Напишете името на файла и новото съдържание след 'Create'");
                                contentExtendedWriter.Write("Create");
                            }
                            break;
                        case "Delete":
                            if(commands.length>1) {
                                delete(Paths.get("src\\" + commands[1]),listPathRef,true);
                            }
                            else{
                                //System.out.println("Напишете името на файла след 'Delete'");
                                basicWriter.Write("Delete");
                            }
                            break;
                        case "Move":
                            if(commands.length>2) {
                                move(Paths.get("src\\" + commands[1]),Paths.get("src\\" + commands[2]));
                            }
                            else{
                                //System.out.println("Напишете името на първия и втория файл след 'Move'");
                                twoFileWriter.Write("Move");
                            }
                            //Move JSON_FILES\blah.json JSON_FILES\new.json
                            break;
                        case "Help":
                            help();
                            break;
                        case "Exit":
                            //System.out.println("Програмата беше затворена");
                            fullWriter.FullWrite("Програмата беше затворена");
                            scanner.close();
                            return; // Exit the program
                        default:
                            //System.out.println("Невалидна опция. Опитайте отново.");
                            fullWriter.FullWrite("Невалидна опция. Опитайте отново.");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void print(PathReference path){
        if(path.getContent()==null || path.getLocation()==null){
            System.out.println("Няма отворен файл все още");
        }
        else {
            try {
                //System.out.println(path.content);
                System.out.println(Files.readString(path.getLocation()).trim());
            } catch (IOException e) {
                System.out.println("Грешка при изписването на съдържанието на отвореният файл");
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

    public static void help(){
        System.out.println("Когато пишете името на файла се пише целия път към файла");
        System.out.println("Пример : 'JSON_Files/file.json' или 'FilesFolder2/file.json' .");
        System.out.println("Open FileName -> Отваря файл с дадено име , не може да се отвори файл ако друг файл е вече отворен");
        System.out.println("Close FileName -> Затваря отворен файл по име");
        System.out.println("Print -> Принтира съдържанието на отвореният файл");
        System.out.println("Search KeyName -> Търси и връща ключ и неговата стойност по дадено име от отвореният файл");
        System.out.println("Set FileName NewContent -> Задава се ново съдържание на файл по дадено име на файл и по дадено съдържание");
        System.out.println("Save FileName -> Запазват се промените направени във файл със Set функцията по дадено име на файл");
        System.out.println("SaveAs NewPathName FileName -> Запазват се промените направени във файл със Set функцията в нова локация  по пълно дадено име на нова локация и дадено име на файл");
        System.out.println("(функцията също работи и за преместване на файлове в други папки , не е задължително да са били променени чрез Set функцията");
        System.out.println("Create FileName NewContent -> Създава се нов файл с ново съдържание по дадено име на файл и по дадено съдържание");
        System.out.println("Delete FileName -> Изтрива се файл по дадено име на файл");
        System.out.println("Move FromFileName ToFileName -> Данните от първия файл даден по име се преместват във втория файл по дадено име");
        System.out.println("Validate FileName -> Валидира се съдържанието на файл са дадно име , като се проверява дали има правилен JSON синтаксис");
        System.out.println("Help -> Отваря менюто с обяснение на командите");
        System.out.println("Exit -> Излиза от програмата");
    }

    public static void validate(Path filePath){
        if (!Files.exists(filePath)) {
            System.out.println("Файлът не съществува.");
            return;
        }

        try {
            String content = Files.readString(filePath).trim();
            content = content.replaceAll("\\s+","");

            if (content.isEmpty()) {
                System.out.println("Файлът няма съдържание.");
                return;
            }


            boolean isObject = content.startsWith("{") && content.endsWith("}");
            boolean isArray = content.startsWith("[") && content.endsWith("]");

            if (!(isObject || isArray)) {
                System.out.println("Файлът трябва да започва и да завършва с еднакви знаци ('{' и '}' или '[' и ']')");
                return;
            }


            int curly = 0;
            int square = 0;
            boolean inString = false;
            //boolean expectingKey = false;
            //boolean expectingColon = false;
            //boolean expectingValue = false;
            //boolean insideObject = false;

            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);

                if (c == '"') {
                    inString = !inString;
                }

                if (!inString) {
                    if (c == '{') {
                        curly++;
                        //insideObject = true;
                        //expectingKey = true;
                    } else if (c == '}') {
                        curly--;
                        //insideObject = false;
                    } else if (c == '[') {
                        square++;
                    } else if (c == ']') {
                        square--;
                    }
                }
            }/*else if (c == ':') {
                        if (!expectingColon) {
                            System.out.println("Невалидна употреба на ':' на знак номер " + i);
                            return ;
                        }
                        expectingColon = false;
                        expectingValue = true;
                    } else if (c == ',') {
                        if (expectingColon) {
                            System.out.println("Очаквана стойност преди ',' на знак номер " + i);
                            return ;
                        }
                        if (insideObject) {
                            expectingKey = true;
                        }
                    }
                } else {
                    if (expectingKey) {
                        expectingKey = false;
                        expectingColon = true;
                    } else if (expectingValue) {
                        expectingValue = false;
                    }
                }
            }*/

                    if (curly != 0 || square != 0 || inString) {
                        System.out.println("Несъответстващи скоби или незатворен стринг.");
                        return;
                    }

                    //content = content.replaceAll("\\s+","");
                    //System.out.println(content.charAt(1));
                    //System.out.println(content.charAt(content.length()-2));
                    if (content.charAt(1) == ',' || content.charAt(content.length() - 2) == ',') {
                        System.out.println("Файла не може да има ',' като втори или предпоследен знак.");
                        return;
                    }


        } catch (IOException e) {
            System.out.println("Грешка при четенето на файла.");
            return ;
        }

            System.out.println("Файлът е във валиден JSON формат");
    }




    public static void search(PathReference path , String keyName) throws IOException {
        if(path==null || path.getLocation()==null){
            System.out.println("Няма Отворен файл");
            return;
        }
        Boolean printed = false;
        String content = new  String(Files.readAllBytes(path.getLocation()));
        Map<String, String> jsonMap = parseJson(content);
        for (String key : jsonMap.keySet()) {
            if(keyName.equals(key))
            {
            String value = jsonMap.get(keyName);
            System.out.println(key+ ": "+value);
            printed = true;
            }
        }
        if(!printed){
            System.out.println("Не бешен намерен ключ с такова име");
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
                    if (pr.getLocation().equals(filePath)) {
                        pr.setContent(newContent);
                        System.out.println("JSON файлът беше пренаписан успешно.");
                        return ;
                    }
                }
                //Files.write(filePath, newContent.getBytes());
                pathRef.setContent(newContent);
                list.add(pathRef);
                System.out.println("JSON файлът беше пренаписан успешно.");
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
            if(pr.getLocation().equals(Paths.get("src\\" + fileName))){
                filePath = pr.getLocation();
                fileContent = pr.getContent();
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

    public static void saveAs(String fileName,String newPathName,List<PathReference> list) throws IOException {
        Path filePath = Paths.get("src\\"+newPathName);
        Path originalLocation = Paths.get("src\\" + fileName);
        String fileContent = Files.readString(originalLocation);
        for(PathReference pr : list){
            if(pr.getLocation().equals(originalLocation)){
                //filePath = pr.location;
                fileContent = pr.getContent();
            }
        }
        if (filePath != null && fileContent != null) {
            try {
                delete(originalLocation,list,false);
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

    public static void delete(Path filePath , List<PathReference> list ,Boolean sendMessage){
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
                if(sendMessage) {
                    System.out.println("JSON файлът беше изтрит успешно.");
                }
                list.removeIf(PathRef -> PathRef.getLocation().equals(filePath));
            } catch (IOException e) {
                System.out.println("Грешка при изтриването на JSON файлът.");
            }
        } else {
            System.out.println("JSON файлът с такъв път не е намерен.");
        }
    }

    public static void move(Path pathFrom , Path pathTo){
        try {
            if (!Files.exists(pathFrom) || !Files.exists(pathTo)) {
                System.out.println("JSON файлът с такъв път не е намерен.");
                return;
            }

            String contentFrom = Files.readString(pathFrom).trim();
            String contentTo = Files.readString(pathTo).trim();


            boolean fromIsEmpty = contentFrom.isEmpty() || contentFrom.equals("{}");
            boolean toIsEmpty = contentTo.isEmpty() || contentTo.equals("{}");

            String mergedContent = "";

            if (fromIsEmpty && toIsEmpty) {
                mergedContent = "{}";
            } else if (fromIsEmpty) {
                mergedContent = contentTo;
            } else if (toIsEmpty) {
                mergedContent = contentFrom;
            } else {

                if (contentFrom.endsWith("}")) {
                    contentFrom = contentFrom.substring(0, contentFrom.length() - 1);
                }
                if (contentTo.startsWith("{")) {
                    contentTo = "," + contentTo.substring(1);
                }
                mergedContent = contentFrom + contentTo;
            }


            byte[] mergedBytes = mergedContent.getBytes();
            Files.write(pathTo, mergedBytes);
            Files.write(pathFrom, new byte[0]);

            System.out.println("Данните бяха успешно преместени.");
        } catch (IOException e) {
            System.out.println("Грешка при преместването на информация.");
        }
    }

    public static void open(PathReference path , String fileName,List<PathReference>list) throws IOException {

        Boolean ExistsInList = false;

        if(path.getLocation() == null) {
            for(PathReference pr : list){
                if(pr.getLocation().equals(Paths.get("src\\" + fileName))){
                    path.setLocation(pr.getLocation());
                    path.setContent(pr.getContent());
                    ExistsInList = true;
                    System.out.println("Файлът е успешно отворен");
                }
            }
            if(!ExistsInList) {
                if (Files.exists(Paths.get("src/" + fileName)) && Files.isReadable(Paths.get("src/" + fileName))) {
                    try {
                        path.setLocation(Paths.get("src/" + fileName));
                        path.setContent(Files.readString(path.getLocation()));
                        //String content = Files.readString(path.location);  // Opens and reads the file
                        System.out.println("Файлът е успешно отворен");
                        //System.out.println("Съдържание:\n" + content);
                    } catch (IOException e) {
                        System.out.println("Файлът съществува , но не беше успешно отворен");
                    }
                } else {
                    System.out.println("Файлът не съществува или не може да бъде прочетен");
                    return;
                }
            }
        }
        else{
            System.out.println("Дръг файл е отворен . Затворете отвореният файл , за да може да отворите друг файл.");
            return;
        }

    }

    public static void close(PathReference path , String fileName){
        if(path.getLocation()!=null) {
            if (path.getLocation().equals(Paths.get("src\\" + fileName))) {
                path.setLocation(null);
                path.setContent(null);
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
