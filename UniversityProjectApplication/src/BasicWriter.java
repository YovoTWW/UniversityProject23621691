public class BasicWriter extends BaseWriter{
    @Override
    public void Write(String word) {
        System.out.println("Напишете името на файла след '"+word+"'");
    }

    public BasicWriter() {
    }
}
