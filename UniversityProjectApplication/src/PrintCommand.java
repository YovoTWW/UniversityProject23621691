import java.io.IOException;
import java.nio.file.Files;

public class PrintCommand extends Command{

    private PathReference pf;

    public PrintCommand(PathReference pf) {
        this.pf = pf;
    }

    @Override
    public void ExecuteCommand() {
    print(this.pf);
    }

    private void print(PathReference path){
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
}
