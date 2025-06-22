import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ValidateCommand extends Command{
    private Path filePath;

    public ValidateCommand(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void ExecuteCommand() throws IOException {
        validate(this.filePath);
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
            }

            if (curly != 0 || square != 0 || inString) {
                System.out.println("Несъответстващи скоби или незатворен стринг.");
                return;
            }


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
}
