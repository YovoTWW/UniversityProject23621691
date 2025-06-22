import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DeleteCommand extends Command{
    private Path filePath;
    private List<PathReference> list;
    private Boolean sent;

    public DeleteCommand(Path filePath, List<PathReference> list, Boolean sent) {
        this.filePath = filePath;
        this.list = list;
        this.sent = sent;
    }

    @Override
    public void ExecuteCommand() throws IOException {
        delete(this.filePath,this.list,this.sent);
    }

    private void delete(Path filePath , List<PathReference> list , Boolean sendMessage){
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
}
