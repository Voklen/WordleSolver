import junit.framework.TestCase;

import java.io.File;

public class WordleSolverTest extends TestCase {
	public void testFindFirstAlphabetical() throws Exception {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		File file = new File(path + "testFiles/alphabetical");
		
		long result;
		// Forward
		result = WordleSolver.findFirst(file, 'f');
		assertEquals(5, result);
		// Middle
		result = WordleSolver.findFirst(file, 'd');
		assertEquals(3, result);
		// Backward
		result = WordleSolver.findFirst(file, 'b');
		assertEquals(1, result);
	}

	public void testFindFirstDoesntExist() throws Exception {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		File file = new File(path + "testFiles/allB");
		
		long result;
		// Run off start of file
		result = WordleSolver.findFirst(file, 'a');
		assertEquals(-1, result);
		// Run off end of file
		result = WordleSolver.findFirst(file, 'z');
		assertEquals(-1, result);
	}
}
