import java.io.IOException;
import java.nio.file.Paths;

public class CloseCommand extends Command{
    private PathReference path;
    private String fileName;

    public CloseCommand(PathReference path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    @Override
    public void ExecuteCommand() throws IOException {
        close(this.path,this.fileName);
    }

    private void close(PathReference path , String fileName){
        if(path.getLocation()!=null) {
            if (path.getLocation().equals(Paths.get("src\\" + fileName))) {
                path.setLocation(null);
                path.setContent(null);
                System.out.println("Файлът е успешно затворен");
            } else {

                System.out.println("Няма отворен файл с такова наименование");
            }
        }
        else{
            System.out.println("Няма отворен файл с такова наименование");
        }

    }
}
