import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SaveCommand extends Command{

    private String fileName;
    private List<PathReference> list;

    public SaveCommand(String fileName, List<PathReference> list) {
        this.fileName = fileName;
        this.list = list;
    }

    @Override
    public void ExecuteCommand() throws IOException {
        save(this.fileName,this.list);
    }

    private void save(String fileName, List<PathReference> list){
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
}
