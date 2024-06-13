package ttfe.tests;

import static org.junit.Assert.assertEquals;
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
	public void testAddPieceAtEmpty() {
		int currentTiles = game.getNumPieces();
		game.addPiece();
		assertEquals( currentTiles + 1, game.getNumPieces());
	}

    @Test
    public void testAddPieceValue() {
        for (int i = 0; i < 14; i++) { 
            game.addPiece(); // Add piece in each iteration

            boolean isTwoOrFour = false;
            for (int x = 0; x < game.getBoardWidth(); x++) {

                for (int y = 0; y < game.getBoardHeight(); y++) {

                    int piece_val = game.getPieceAt(x, y);

                    // Board should not have anything else that 2 || 4 || 0
                    if (piece_val == 2 || piece_val == 4) {
                        isTwoOrFour = true;
                    }
                    assertTrue("Add Pieces adds something other than 2,4", piece_val == 0 || piece_val == 2 || piece_val == 4);
                }
            }
            assertTrue("No new piece with value 2 or 4 was added", isTwoOrFour);
        }
    }

    @Test
	public void testInitialBoardHeight() {
		assertTrue("The initial game board did not have correct height",
				4 == game.getBoardHeight());
	}
    @Test
	public void testInitialBoardWidth() {
		assertTrue("The initial game board did not have correct height",
				4 == game.getBoardWidth());
	}

    /*
     * Tests for Number of Moves
     */
    @Test
    public void testInitialNumOfMoves () {
        assertTrue ("Initial moves not equal to zero", 0 == game.getNumMoves());
    }

    @Test
    public void testNumOfMovesAfterMovingNORTH (){
        if (game.isMovePossible(MoveDirection.NORTH))
        {
            game.performMove (MoveDirection.NORTH);
            assertEquals(1, game.getNumMoves());
        }
    }
    @Test
    public void testNumOfMovesAfterMovingSOUTH (){
        if (game.isMovePossible(MoveDirection.SOUTH))
        {
            game.performMove (MoveDirection.SOUTH);
            assertEquals(1, game.getNumMoves());
        }
    }
    @Test
    public void testNumOfMovesAfterMovingEAST (){
        if (game.isMovePossible(MoveDirection.EAST))
        {
            game.performMove (MoveDirection.EAST);
            assertEquals(1, game.getNumMoves());
        }
    }
    @Test
    public void testNumOfMovesAfterMovingWEST (){
        if (game.isMovePossible(MoveDirection.WEST))
        {
            game.performMove (MoveDirection.WEST);
            assertEquals(1, game.getNumMoves());
        }
    }
    // __________________________________________ //

    /*
     * Test for Number of Pieces
     */

    @Test
    public void testInitialNumberOfPieces () {
        assertEquals(2, game.getNumPieces());
    }

    //@Test
    // public void addPieceTest () {

    //  int [][] currentBoardState = this.getCurrentBoardState(game);

    //  int num_pieces = game.getNumPieces();
    //  game.addPiece();
    //  assertEquals( num_pieces + 1, game.getNumPieces()); // Tests if the move was made in an empty position

    //  int [][] newBoardState = this.getCurrentBoardState(game);

    //  boolean twoOrFour = true;

    //  this.printBoard(currentBoardState);

    //  for (int i = 0; i < newBoardState.length; i++) {
            
    //      for (int j = 0; j < newBoardState[i].length; j++) {
                
    //          if (currentBoardState[i][j] != newBoardState[i][j]) 
    //          {
    //              if (newBoardState[i][j] != 2 || newBoardState[i][j] != 4)
    //              {
    //                  twoOrFour = false;
    //              }
    //          }
    //      }
    //  }
    //  // Check if new tile has value of 2 or 4
    //  assertTrue(twoOrFour);  

    // }

    // @Test
    // public void testNumMoves () {
        
    // }

    // @Test
    // public void testAddPiece() {
    //     game.addPiece();
    //     assertEquals("The number of pieces should be 1 after adding a piece", 1, game.getNumPieces());
    // }

    
    

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