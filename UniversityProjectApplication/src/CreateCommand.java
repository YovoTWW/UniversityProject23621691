import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CreateCommand extends Command{
    private Path filePath;
    private String newContent;

    public CreateCommand(Path filePath, String newContent) {
        this.filePath = filePath;
        this.newContent = newContent;
    }

    @Override
    public void ExecuteCommand() throws IOException {
        create(this.filePath,this.newContent);
    }

    private void create(Path filePath , String newContent){
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
}
