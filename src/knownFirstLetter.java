import java.io.File;
import java.lang.Math;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class knownFirstLetter {
	public static final int wordLength = 5;
	public static final int wordLengthWithNewline = wordLength + 1;

	public static long[] getRange(final File file, final Character letterToFind) {
		long[] error = { -1, -1 };
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
			long[] position = findFirstLetterInstanceError(file, letterToFind, stream);
			return position;
		} catch (IOException e) {
			System.out.println("Could not read from file");
			return error;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("File does not contain any possible words with these restrictions");
			return error;
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

	private static long[] findFirstLetterInstanceError(final File file, final Character letterToFind,
			final FileInputStream stream)
			throws IOException, IndexOutOfBoundsException {
		FileChannel fc = stream.getChannel();
		RandomAccessFileReader buffer = new RandomAccessFileReader(fc);
		long instance = binarySearch(file, letterToFind, buffer);
		long max = instance;
		while (buffer.getChar(max * wordLengthWithNewline) == letterToFind) {
			max += 1;
		}
		long min = instance;
		while (buffer.getChar(min * wordLengthWithNewline) == letterToFind) {
			min -= 1;
		}
		long[] range = { (min + 1), (max - 1) };
		return range;
	}

	private static long binarySearch(final File file, final Character letterToFind,
			final RandomAccessFileReader buffer)
			throws IOException, IndexOutOfBoundsException {
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
