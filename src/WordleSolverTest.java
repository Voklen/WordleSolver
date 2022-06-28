import junit.framework.TestCase;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;

public class WordleSolverTest extends TestCase {
	public void testFindFirstAlphabetical() throws Exception {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		File file = new File(path + "testFiles/alphabetical");
		
		long[] result;
		long[] expected;
		// Forward
		expected = new long[]{5, 5};
		result = knownFirstLetter.getRange(file, 'f');
		assertArrayEquals(expected, result);
		// Middle
		expected = new long[]{3, 3};
		result = knownFirstLetter.getRange(file, 'd');
		assertArrayEquals(expected, result);
		// Backward
		expected = new long[]{1, 1};
		result = knownFirstLetter.getRange(file, 'b');
		assertArrayEquals(expected, result);
	}

	public void testFindFirstDoesntExist() throws Exception {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		File file = new File(path + "testFiles/allB");
		
		long[] result;
		long[] error = {-1, -1};
		// Run off start of file
		result = knownFirstLetter.getRange(file, 'a');
		assertArrayEquals(error, result);
		// Run off end of file
		result = knownFirstLetter.getRange(file, 'z');
		assertArrayEquals(error, result);
	}
}
