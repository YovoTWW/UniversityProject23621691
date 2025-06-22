import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class OpenCommand extends Command{
    private PathReference path;
    private String FileName;
    private List<PathReference> list;

    public OpenCommand(PathReference path, String fileName, List<PathReference> list) {
        this.path = path;
        FileName = fileName;
        this.list = list;
    }

    @Override
    public void ExecuteCommand() throws IOException {
        open(this.path,this.FileName,this.list);
    }

    private void open(PathReference path , String fileName, List<PathReference> list) throws IOException {

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
}
