import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SetCommand extends Command{


    private Path fp;
    private String nc;
    private List<PathReference> list;

    public SetCommand(Path fp, String nc, List<PathReference> list) {
        this.fp = fp;
        this.nc = nc;
        this.list = list;
    }

    @Override
    public void ExecuteCommand() throws IOException {
         set(this.fp,this.nc,this.list);
    }

    private void set(Path filePath , String newContent, List<PathReference> list)
    {
        PathReference pathRef = new PathReference(filePath);
        if (Files.exists(filePath)) {
            for(PathReference pr : list) {
                if (pr.getLocation().equals(filePath)) {
                    pr.setContent(newContent);
                    System.out.println("JSON файлът беше пренаписан успешно.");
                    return ;
                }
            }
            pathRef.setContent(newContent);
            list.add(pathRef);
            System.out.println("JSON файлът беше пренаписан успешно.");
        }
        else{
            System.out.println("JSON файлът с такъв път не е намерен.");
        }
    }
}
