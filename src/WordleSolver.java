import java.io.File;

public class WordleSolver {
	public static final int wordLength = 5;
	public static final int wordLengthWithNewline = wordLength + 1;
	private static Letter[] letterStatuses = new Letter[wordLength];

	public static void main(String[] args) {
		File wordBankFile = new File("wordBank").getAbsoluteFile();
		long[] range = knownFirstLetter.getRange(wordBankFile, 'd');
	}

}