import java.nio.file.Path;

public class PathReference {
    private Path location;
    private String content;

    public PathReference(Path path){
            this.location = path;
            content = null;
    }

    public Path getLocation() {
        return location;
    }

    public void setLocation(Path location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
