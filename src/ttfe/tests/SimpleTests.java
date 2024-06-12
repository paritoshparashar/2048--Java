package ttfe.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import ttfe.SimulatorInterface;
import ttfe.TTFEFactory;

/**
 * This class provides a very simple example of how to write tests for this project.
 * You can implement your own tests within this class or any other class within this package.
 * Tests in other packages will not be run and considered for completion of the project.
 */
public class SimpleTests {

	private SimulatorInterface game;
	private HelperMethodsInterface helper;

	@Before
	public void setUp() {
		game = TTFEFactory.createSimulator(4, 4, new Random(0));
	}
	
	@Test
	public void testInitialGamePoints() {
		assertEquals("The initial game did not have zero points", 0,
				game.getPoints());
	}
	
	@Test
	public void testInitialBoardHeight() {
		assertTrue("The initial game board did not have correct height",
				4 == game.getBoardHeight());
	}

	@Test
	public void testAddPiece () {

		int [][] currentBoardState = helper.getCurrentBoardState(game);
		int currentEmptyTiles = helper.getEmptyPositions(currentBoardState);

		game.addPiece();

		int [][] newBoardState = helper.getCurrentBoardState(game);
		int newEmptyTiles = helper.getEmptyPositions(newBoardState);

		//Check if a new tile was added
		assertEquals(currentEmptyTiles - 1, newEmptyTiles);

		boolean twoOrFour = true;

		for (int i = 0; i < newBoardState.length; i++) {
			
			for (int j = 0; j < newBoardState[i].length; j++) {
				
				if (currentBoardState[i][j] != newBoardState[i][j]) 
				{
					if (newBoardState[i][j] != 2 || newBoardState[i][j] != 4)
					{
						twoOrFour = false;
					}
				}
			}
		}
		// Check if new tile has value of 2 or 4
		assertTrue(twoOrFour); 

	}

	@Test
	public void testNumPieces () { //Check this later
		int num_moves = game.getNumPieces();
		game.addPiece();
		assertEquals( num_moves + 1, game.getNumPieces()); // Tests if the move was made
	}

	

}