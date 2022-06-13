import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class WordleSolver {
	public static final int wordLength = 5;
	public static final int wordLengthWithNewline = wordLength + 1;
	private static Letter[] letterStatuses = new Letter[wordLength];

	private static void checkAndPrintLine(String line) {
		System.out.println(line);
	}

	public static void main(String[] args) {
		String wordBankPath = new File("wordBank").getAbsolutePath();
		System.out.println(wordBankPath);
		findFirst();
		BufferedReader objReader = null;
		try {

			objReader = new BufferedReader(new FileReader(wordBankPath));

			objReader.lines().forEach(n -> checkAndPrintLine(n));

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {
				if (objReader != null)
					objReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Hello World");
	}

	public static long findFirst() {
		File file = new File("wordBank").getAbsoluteFile();
		final Character letterToFind = 'd';
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
			FileChannel fc = (stream).getChannel();
			RandomAccessFileReader buffer = new RandomAccessFileReader(fc);
			long fileLength = file.length();
			long position = fileLength / 2;
			position += (position % wordLengthWithNewline); // Snap to the start of the next word
			Character candidate = buffer.getChar(position);
			while (!candidate.equals(letterToFind)) {
				if ((int) candidate < (int) letterToFind) {
					position += wordLengthWithNewline;
				} else {
					position -= wordLengthWithNewline;
				}
				candidate = buffer.getChar(position);
			}
			return position;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("File does not contain possible word");
			return 0;
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}