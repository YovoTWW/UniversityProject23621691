import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JsonReader {
    public static void main(String[] args) {
        {
            try {

                List<PathReference> listPathRef = new ArrayList<>();
                PathReference pathRef = new PathReference(null);


                Scanner scanner = new Scanner(System.in);
                BasicWriter basicWriter = new BasicWriter();
                ContentExtendedWriter contentExtendedWriter = new ContentExtendedWriter();
                LocationExtendedWriter locationExtendedWriter = new LocationExtendedWriter();
                TwoFileWriter twoFileWriter = new TwoFileWriter();
                FullWriter fullWriter = new FullWriter();

                while (true) {
                    //System.out.print("Напишете команда --> : ");
                    fullWriter.FullWrite("Напишете команда --> : ");
                    String input = scanner.nextLine();
                    String [] commands = input.split(" ");
                    switch (commands[0]) {
                        case "Validate":
                            if(commands.length>1) {
                                Command validateCommand = new ValidateCommand(Paths.get("src\\" + commands[1]));
                                validateCommand.ExecuteCommand();
                            }
                            else{
                                //System.out.println("Напишете името на файла след 'Validate'");
                                basicWriter.Write("Validate");
                            }
                            break;
                        case "Open":
                            if(commands.length>1) {
                                //open(pathRef, commands[1], listPathRef);
                                Command openCommand = new OpenCommand(pathRef, commands[1], listPathRef);
                                openCommand.ExecuteCommand();
                            }
                            else{
                                //System.out.println("Напишете името на файла след 'Open'");
                                basicWriter.Write("Open");
                            }
                            break;
                        case "Close":
                            if(commands.length>1) {
                                //close(pathRef, commands[1]);
                                Command closeCommand = new CloseCommand(pathRef,commands[1]);
                                closeCommand.ExecuteCommand();
                            }
                            else{
                                //System.out.println("Напишете името на файла след 'Close'");
                                basicWriter.Write("Close");
                            }
                            break;
                        case "Print":
                            Command printCommand = new PrintCommand(pathRef);
                            printCommand.ExecuteCommand();
                            break;
                        case "Search":
                            if(commands.length>1) {
                                Command searchCommand = new SearchCommand(commands[1],pathRef);
                                searchCommand.ExecuteCommand();
                            }
                            else{
                                fullWriter.FullWrite("Напишете името на ключа след 'Search'");
                            }
                            break;
                        case "Set":
                            if(commands.length>2) {
                                String[] content = Arrays.copyOfRange(commands,2,commands.length);
                                String result = String.join(" ",content);
                                //set(Paths.get("src\\" + commands[1]), result, listPathRef);
                                Command setCommand = new SetCommand(Paths.get("src\\" + commands[1]), result, listPathRef);
                                setCommand.ExecuteCommand();
                            }
                            //Set invalid.json {"name" : "Pesho","age" : "47"}
                            else{
                                //System.out.println("Напишете името на файла и новото съдържание след 'Set'");
                                contentExtendedWriter.Write("Set");
                            }
                            break;
                        case "Save":
                            if(commands.length>1) {
                                //save(commands[1],listPathRef);
                                Command saveCommand = new SaveCommand(commands[1],listPathRef);
                                saveCommand.ExecuteCommand();
                            }
                            else{
                                //System.out.println("Напишете името на файла след 'Save'");
                                basicWriter.Write("Save");
                            }
                            break;
                        case "SaveAs":
                            if(commands.length>2) {
                                //saveAs(commands[2],commands[1],listPathRef);
                                Command saveAsCommand = new SaveAsCommand(commands[2],commands[1],listPathRef);
                                saveAsCommand.ExecuteCommand();
                            }
                            else{
                                //System.out.println("Напишете новата локация и името на файла след 'SaveAs'");
                                locationExtendedWriter.Write("SaveAs");
                            }
                            break;
                        case "Create":
                            if(commands.length>2) {
                                String[] content = Arrays.copyOfRange(commands,2,commands.length);
                                String result = String.join(" ",content);
                                //create(Paths.get("src\\" + commands[1]), result);
                                Command createCommand = new CreateCommand(Paths.get("src\\" + commands[1]), result);
                                createCommand.ExecuteCommand();
                            }
                            //Create new.json {"name" : "Pesho","age" : "47"}
                            else{
                                //System.out.println("Напишете името на файла и новото съдържание след 'Create'");
                                contentExtendedWriter.Write("Create");
                            }
                            break;
                        case "Delete":
                            if(commands.length>1) {
                                //delete(Paths.get("src\\" + commands[1]),listPathRef,true);
                                Command deleteCommand = new DeleteCommand(Paths.get("src\\" + commands[1]),listPathRef,true);
                                deleteCommand.ExecuteCommand();
                            }
                            else{
                                //System.out.println("Напишете името на файла след 'Delete'");
                                basicWriter.Write("Delete");
                            }
                            break;
                        case "Move":
                            if(commands.length>2) {
                                Command moveCommand = new MoveCommand(Paths.get("src\\" + commands[1]),Paths.get("src\\" + commands[2]));
                                moveCommand.ExecuteCommand();
                            }
                            else{
                                //System.out.println("Напишете името на първия и втория файл след 'Move'");
                                twoFileWriter.Write("Move");
                            }
                            //Move JSON_FILES\blah.json JSON_FILES\new.json
                            break;
                        case "Help":
                            Command helpCmnd = new HelpCommand();
                            helpCmnd.ExecuteCommand();
                            break;
                        case "Exit":
                            //System.out.println("Програмата беше затворена");
                            fullWriter.FullWrite("Програмата беше затворена");
                            scanner.close();
                            return; // Exit the program
                        default:
                            //System.out.println("Невалидна опция. Опитайте отново.");
                            fullWriter.FullWrite("Невалидна опция. Опитайте отново.");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
