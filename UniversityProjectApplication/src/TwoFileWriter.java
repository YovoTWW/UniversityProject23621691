public class TwoFileWriter extends BaseWriter{
    @Override
    public void Write(String word) {
        System.out.println("Напишете името на първия и втория файл след '"+word+"'");
    }

    public TwoFileWriter() {
    }
}
