package ttfe.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import ttfe.MoveDirection;
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
    public void testInitialBoardWidth() {
        assertEquals("The initial game board did not have correct width", 4, game.getBoardWidth());
    }

    @Test
    public void testInitialNumMoves() {
        assertEquals("The initial number of moves is not zero", 0, game.getNumMoves());
    }

    @Test
    public void testInitialNumPieces() {
        assertEquals("The initial number of pieces is not zero", 0, game.getNumPieces());
    }

    @Test
    public void testAddPiece() {
        game.addPiece();
        assertEquals("The number of pieces should be 1 after adding a piece", 1, game.getNumPieces());
    }

    @Test(expected = IllegalStateException.class)
    public void testAddPieceToFullBoard() {
        fillBoard();
        game.addPiece();  // Should throw IllegalStateException
    }

    @Test
    public void testGetPieceAt() {
        game.setPieceAt(0, 0, 2);
        assertEquals("The piece at (0, 0) should be 2", 2, game.getPieceAt(0, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPieceAtInvalidPosition() {
        game.getPieceAt(-1, 0);  // Should throw IllegalArgumentException
    }

    @Test
    public void testIsMovePossible() {
        game.setPieceAt(0, 0, 2);
        game.setPieceAt(0, 1, 2);
        assertTrue("Move should be possible", game.isMovePossible(MoveDirection.NORTH));
    }

    @Test
    public void testIsMovePossibleGeneral() {
        game.setPieceAt(0, 0, 2);
        game.setPieceAt(0, 1, 2);
        assertTrue("There should be at least one possible move", game.isMovePossible());
    }

    @Test
    public void testIsSpaceLeft() {
        assertTrue("There should be space left initially", game.isSpaceLeft());
        fillBoard();
        assertFalse("There should be no space left after filling the board", game.isSpaceLeft());
    }

    @Test
    public void testPerformMove() {
        game.setPieceAt(0, 0, 2);
        game.setPieceAt(0, 1, 2);
        assertTrue("The move should be performed", game.performMove(MoveDirection.NORTH));
        assertEquals("The number of moves should be 1", 1, game.getNumMoves());
    }

    

    @Test
    public void testSetPieceAt() {
        game.setPieceAt(0, 0, 2);
        assertEquals("The piece at (0, 0) should be 2", 2, game.getPieceAt(0, 0));
        game.setPieceAt(0, 0, 0);
        assertEquals("The piece at (0, 0) should be removed", 0, game.getPieceAt(0, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPieceAtInvalidPosition() {
        game.setPieceAt(-1, 0, 2);  // Should throw IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPieceAtNegativeValue() {
        game.setPieceAt(0, 0, -2);  // Should throw IllegalArgumentException
    }

    private void fillBoard() {
        for (int i = 0; i < game.getBoardHeight(); i++) {
            for (int j = 0; j < game.getBoardWidth(); j++) {
                game.setPieceAt(i, j, 2);
            }
        }
    }

	

	// @Test
	// public void addPieceTest () {

	// 	int [][] currentBoardState = this.getCurrentBoardState(game);

	// 	int num_pieces = game.getNumPieces();
	// 	game.addPiece();
	// 	assertEquals( num_pieces + 1, game.getNumPieces()); // Tests if the move was made in an empty position

	// 	int [][] newBoardState = this.getCurrentBoardState(game);

	// 	boolean twoOrFour = true;

	// 	this.printBoard(currentBoardState);

	// 	for (int i = 0; i < newBoardState.length; i++) {
			
	// 		for (int j = 0; j < newBoardState[i].length; j++) {
				
	// 			if (currentBoardState[i][j] != newBoardState[i][j]) 
	// 			{
	// 				if (newBoardState[i][j] != 2 || newBoardState[i][j] != 4)
	// 				{
	// 					twoOrFour = false;
	// 				}
	// 			}
	// 		}
	// 	}
	// 	// Check if new tile has value of 2 or 4
	// 	assertTrue(twoOrFour);	

	// }

	// @Test
	// public void testNumMoves () {
		
	// }

	// @Test
	// public void testAddPiece() {
    //     game.addPiece();
    //     assertEquals("The number of pieces should be 1 after adding a piece", 1, game.getNumPieces());
    // }

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