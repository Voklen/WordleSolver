import java.io.File;
import java.lang.Math;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class WordleSolver {
	public static final int wordLength = 5;
	public static final int wordLengthWithNewline = wordLength + 1;
	private static Letter[] letterStatuses = new Letter[wordLength];

	public static void main(String[] args) {
		File wordBankFile = new File("wordBank").getAbsoluteFile();
		long instance = findFirstLetterInstance(wordBankFile, 'd');
	}

	public static long findFirstLetterInstance(final File file, final Character letterToFind) {
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
			long position = findFirstLetterInstanceError(file, letterToFind, stream);
			return position;
		} catch (IOException e) {
			System.out.println("Could not read from file");
			return -1;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("File does not contain any possible words with these restrictions");
			return -1;
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static long findFirstLetterInstanceError(final File file, final Character letterToFind,
			final FileInputStream stream)
			throws IOException, IndexOutOfBoundsException {
		FileChannel fc = stream.getChannel();
		RandomAccessFileReader buffer = new RandomAccessFileReader(fc);
		// Binary search
		long min = 0;
		long max = Math.ceilDiv(file.length(), wordLengthWithNewline);
		while (min <= max) {
			long pos = min + (max - min) / 2; // Average, but keeps the numbers lower to prevent integer overflow
			Character currentLetter = buffer.getChar(pos * wordLengthWithNewline);

			if (currentLetter.equals(letterToFind)) {
				return pos;
			}
			if ((int) currentLetter < (int) letterToFind) {
				min = pos + 1;
			} else {
				max = pos - 1;
			}
		}
		return -1;
	}
}