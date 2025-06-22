import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MoveCommand extends Command{
    private Path pFrom;
    private Path pTo;

    public MoveCommand(Path pFrom, Path pTo) {
        this.pFrom = pFrom;
        this.pTo = pTo;
    }

    @Override
    public void ExecuteCommand() throws IOException {
        move(this.pFrom,this.pTo);
    }

    private void move(Path pathFrom , Path pathTo){
        try {
            if (!Files.exists(pathFrom) || !Files.exists(pathTo)) {
                System.out.println("JSON файлът с такъв път не е намерен.");
                return;
            }

            String contentFrom = Files.readString(pathFrom).trim();
            String contentTo = Files.readString(pathTo).trim();


            boolean fromIsEmpty = contentFrom.isEmpty() || contentFrom.equals("{}");
            boolean toIsEmpty = contentTo.isEmpty() || contentTo.equals("{}");

            String mergedContent = "";

            if (fromIsEmpty && toIsEmpty) {
                mergedContent = "{}";
            } else if (fromIsEmpty) {
                mergedContent = contentTo;
            } else if (toIsEmpty) {
                mergedContent = contentFrom;
            } else {

                if (contentFrom.endsWith("}")) {
                    contentFrom = contentFrom.substring(0, contentFrom.length() - 1);
                }
                if (contentTo.startsWith("{")) {
                    contentTo = "," + contentTo.substring(1);
                }
                mergedContent = contentFrom + contentTo;
            }


            byte[] mergedBytes = mergedContent.getBytes();
            Files.write(pathTo, mergedBytes);
            Files.write(pathFrom, new byte[0]);

            System.out.println("Данните бяха успешно преместени.");
        } catch (IOException e) {
            System.out.println("Грешка при преместването на информация.");
        }
    }
}
