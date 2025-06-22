import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SaveAsCommand extends Command{

    private String fileName;
    private String newPathName;
    private List<PathReference> list;

    public SaveAsCommand(String fileName, String newPathName, List<PathReference> list) {
        this.fileName = fileName;
        this.newPathName = newPathName;
        this.list = list;
    }

    @Override
    public void ExecuteCommand() throws IOException {
        saveAs(this.fileName,this.newPathName,this.list);
    }

    private void saveAs(String fileName, String newPathName, List<PathReference> list) throws IOException {
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
                //delete(originalLocation,list,false);
                Command deleteCommand = new DeleteCommand(originalLocation,list,false);
                deleteCommand.ExecuteCommand();
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
