import java.io.File;
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
			return 0;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("File does not contain any possible words with these restrictions");
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

	private static long findFirstError(final File file, final Character letterToFind, final FileInputStream stream)
			throws IOException, IndexOutOfBoundsException {
		FileChannel fc = stream.getChannel();
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
	}
}