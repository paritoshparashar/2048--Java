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

		
		
		int num_pieces = game.getNumPieces();
		game.addPiece();
        assertEquals("The number of pieces should be 1 after adding a piece", num_pieces + 1, game.getNumPieces());

	
		int [][] currentBoardState = this.getCurrentBoardState(game);
		int [][] newBoardState = this.getCurrentBoardState(game);

		// int currentEmptyTiles = this.getEmptyPositions(currentBoardState);
		// game.addPiece();
		// int newEmptyTiles = this.getEmptyPositions(newBoardState);

		//Check if a new tile was added only at empty position
		// assertEquals(currentEmptyTiles - 1, newEmptyTiles);


		
		boolean twoOrFour = true;

		this.printBoard(currentBoardState);

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
	public void addPieceTest () {

		int [][] currentBoardState = this.getCurrentBoardState(game);

		int num_pieces = game.getNumPieces();
		game.addPiece();
		assertEquals( num_pieces + 1, game.getNumPieces()); // Tests if the move was made in an empty position

		int [][] newBoardState = this.getCurrentBoardState(game);

		boolean twoOrFour = true;

		this.printBoard(currentBoardState);

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
		int num_pieces = game.getNumPieces();
		game.addPiece();
		assertEquals( num_pieces + 1, game.getNumPieces()); // Tests if the move was made
	}
	

	/*
	 * 
	 * Helper Functions below
	 * 
	 */

	 
	public int [][] getCurrentBoardState (SimulatorInterface game) {
		int [][] board = new int[game.getBoardHeight()][game.getBoardWidth()];

		for (int i = 0; i < game.getBoardHeight(); i++) {
			
			for (int j = 0; j < game.getBoardWidth(); j++) {
				
				board[i][j] = game.getPieceAt(j, i);
			}
		}
		return board;
	}

    
    public int getEmptyPositions (int[][] gameBoard) {
        
        int emptyCount = 0;

        for (int i = 0; i < gameBoard.length; i++) {

            for (int j = 0; j < gameBoard[i].length; j++) {
                
                if (gameBoard[i][j] == 0) {
                    emptyCount++;
                }
            }
        }
        return emptyCount;
    }

    
    public void printBoard(int[][] gameBoard) {
        
        for (int i = 0; i < gameBoard.length; i++) {
            
            for (int j = 0; j < gameBoard[i].length; j++) {
                
                System.out.println(gameBoard[i][j]);
            }
        }
        
    }

}