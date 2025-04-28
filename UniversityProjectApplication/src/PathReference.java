import java.nio.file.Path;

public class PathReference {
    public Path location;
    public String content;

    public PathReference(Path path){
            this.location = path;
            content = null;
    }
}
