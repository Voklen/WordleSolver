import junit.framework.TestCase;

import java.io.File;

public class WordleSolverTest extends TestCase {
	public void testFindFirstAlphabetical() throws Exception {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		File file = new File(path + "testFiles/alphabetical");
		
		long result;
		// Forward
		result = WordleSolver.findFirstLetterInstance(file, 'f');
		assertEquals(5, result);
		// Middle
		result = WordleSolver.findFirstLetterInstance(file, 'd');
		assertEquals(3, result);
		// Backward
		result = WordleSolver.findFirstLetterInstance(file, 'b');
		assertEquals(1, result);
	}

	public void testFindFirstDoesntExist() throws Exception {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		File file = new File(path + "testFiles/allB");
		
		long result;
		// Run off start of file
		result = WordleSolver.findFirstLetterInstance(file, 'a');
		assertEquals(-1, result);
		// Run off end of file
		result = WordleSolver.findFirstLetterInstance(file, 'z');
		assertEquals(-1, result);
	}
}
