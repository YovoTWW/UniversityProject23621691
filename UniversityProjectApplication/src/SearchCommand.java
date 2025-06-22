import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class SearchCommand extends Command{

    private PathReference pf;
    private String keyName;

    public SearchCommand(String keyName, PathReference pf) {
        this.keyName = keyName;
        this.pf = pf;
    }

    @Override
    public void ExecuteCommand() throws IOException{
        search(this.pf,this.keyName);
    }

    private ArrayList<JsonObj> parseJson(String json) {
        ArrayList<JsonObj> objects = new ArrayList<JsonObj>();

        json = json.trim().replaceAll("[{}\"]", "");
        String[] pairs = json.split(",");

        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                objects.add(new JsonObj(keyValue[0].trim(),keyValue[1].trim()));
            }
        }
        return objects;
    }

    private void search(PathReference path , String keyName) throws IOException {
        if(path==null || path.getLocation()==null){
            System.out.println("Няма Отворен файл");
            return;
        }
        Boolean printed = false;
        String content = new  String(Files.readAllBytes(path.getLocation()));
        ArrayList<JsonObj> objects = parseJson(content);
        for(JsonObj obj : objects){
            if(obj.Key.equals(keyName)){
                String value = obj.Value;
                System.out.println(obj.Key+ ": "+value);
                printed = true;
            }
        }

        if(!printed){
            System.out.println("Не бешен намерен ключ с такова име");
        }
    }
}
