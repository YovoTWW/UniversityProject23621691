public class ContentExtendedWriter extends BaseWriter{
    @Override
    public void Write(String word) {
        System.out.println("Напишете името на файла и новото съдържание след '"+word+"'");
    }

    public ContentExtendedWriter() {
    }
}
