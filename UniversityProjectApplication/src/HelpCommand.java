public class HelpCommand extends  Command{

    @Override
    public void ExecuteCommand() {
        help();
    }

    private void help(){
        System.out.println("Когато пишете името на файла се пише целия път към файла");
        System.out.println("Пример : 'JSON_Files/file.json' или 'FilesFolder2/file.json' .");
        System.out.println("Open FileName -> Отваря файл с дадено име , не може да се отвори файл ако друг файл е вече отворен");
        System.out.println("Close FileName -> Затваря отворен файл по име");
        System.out.println("Print -> Принтира съдържанието на отвореният файл");
        System.out.println("Search KeyName -> Търси и връща ключ и неговата стойност по дадено име от отвореният файл");
        System.out.println("Set FileName NewContent -> Задава се ново съдържание на файл по дадено име на файл и по дадено съдържание");
        System.out.println("Save FileName -> Запазват се промените направени във файл със Set функцията по дадено име на файл");
        System.out.println("SaveAs NewPathName FileName -> Запазват се промените направени във файл със Set функцията в нова локация  по пълно дадено име на нова локация и дадено име на файл");
        System.out.println("(функцията също работи и за преместване на файлове в други папки , не е задължително да са били променени чрез Set функцията");
        System.out.println("Create FileName NewContent -> Създава се нов файл с ново съдържание по дадено име на файл и по дадено съдържание");
        System.out.println("Delete FileName -> Изтрива се файл по дадено име на файл");
        System.out.println("Move FromFileName ToFileName -> Данните от първия файл даден по име се преместват във втория файл по дадено име");
        System.out.println("Validate FileName -> Валидира се съдържанието на файл са дадно име , като се проверява дали има правилен JSON синтаксис");
        System.out.println("Help -> Отваря менюто с обяснение на командите");
        System.out.println("Exit -> Излиза от програмата");
    }
}
