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
		findFirst(wordBankFile, 'd');
	}

	public static long findFirst(final File file, final Character letterToFind) {
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
			long position = findFirstError(file, letterToFind, stream);
			return position;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("File does not contain any possible words with these restrictions");
			return -1;
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static long findFirstError(final File file, final Character letterToFind, final FileInputStream stream)
			throws IOException, IndexOutOfBoundsException {
		FileChannel fc = stream.getChannel();
		RandomAccessFileReader buffer = new RandomAccessFileReader(fc);

		long minSearched = 0;
		long maxSearched = Math.ceilDiv(file.length(), wordLengthWithNewline);
		long position = maxSearched / 2;
		Character candidate = buffer.getChar(position * wordLengthWithNewline);
		while (!candidate.equals(letterToFind)) {
			if ((int) candidate < (int) letterToFind) {
				minSearched = position;
			} else {
				maxSearched = position;
			}
			position = (minSearched + maxSearched) / 2;
			candidate = buffer.getChar(position * wordLengthWithNewline);
		}
		return position;
	}
}