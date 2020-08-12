package wordstream;
import java.util.Scanner;

public class WordStreamFromKeyboard implements WordStream {

	private Scanner scanner = new Scanner(System.in);
	private boolean keepGoing = true;
	
	public String getLine() {
		String word = scanner.next();
		if (word.equals("stop")) {
			keepGoing = false;
			return "";
		}
		return word;
	}
	
	public boolean hasAnother() {
		return keepGoing;
	}

}
