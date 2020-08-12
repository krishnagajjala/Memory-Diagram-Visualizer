package wordstream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class WordStreamFromFile implements WordStream {

	private Scanner scanner;
	
	public WordStreamFromFile(String fileName) throws FileNotFoundException {
		FileInputStream stream = new FileInputStream(fileName);
		scanner = new Scanner(stream);
	}
	
	public String getLine() {
		return scanner.nextLine();
	}

	public boolean hasAnother() {
		return scanner.hasNext();
	}

}
