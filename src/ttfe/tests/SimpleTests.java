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
    /*
     * Tests for Add Piece
     */
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
    // __________________________________________________ //

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
    // __________________________________________________ //

    /*
     * Test for Number of Pieces
     */

    @Test
    public void testInitialNumberOfPieces () {
        assertEquals(2, game.getNumPieces());
    }

    // __________________________________________________ //

    /*
     * Test for the initial value of pieces
     */

    @Test
    public void testInitialValueOfPieces () {

        int nonZeroCount = 0;
        boolean isTwoOrFour = true;

        for (int i = 0; i < game.getBoardHeight(); i++) {
            
            for (int j = 0; j < game.getBoardWidth(); j++) {
                
                if (game.getPieceAt(i, j) != 0) {

                    if (game.getPieceAt(i, j) == 2 || game.getPieceAt(i, j) == 4){
                        ++nonZeroCount;
                    }
                    else {
                        isTwoOrFour = false;
                        break;
                    }
                    
                }
            }
            if (!isTwoOrFour) {
                break;
            }
        }

        assertTrue(nonZeroCount == 2 && isTwoOrFour);
    }

    // __________________________________________________ //

    /*
     * Tests for the number of points
     */

    @Test
    public void testInitialPoints () {
        assertTrue("Get Points should 0 as intial points",0 == game.getPoints());
    }

    @Test
    public void testPointsAfterAMove (){

        for (int i = 0; i < game.getBoardHeight(); i++) {
            
            for (int j = 0; j < game.getBoardWidth(); j++) {
                
                game.setPieceAt(i, j, 0); // Remove all the pieces
            }
        }

        game.setPieceAt(0, 0, 2);
        game.setPieceAt(2, 0, 2);
        if (game.isMovePossible(MoveDirection.EAST)) {
            game.performMove(MoveDirection.EAST);
        }
        assertTrue(4 == game.getPoints());

    }

    // __________________________________________________ //

    /*
     * Tests to check if a move is possible
     */

    // Without Direction

    @Test 
    public void testInitialIsMovePossibleWithoutDirection () {

        assertTrue (game.isMovePossible()); // Initially there should be at least one possible move

    }

    @Test
    public void testIsMovePossibleInAFullBoardWithoutDirection () {
        int[][] array = {
            {2, 4, 8, 16},
            {32, 64, 128, 256},
            {512, 1024, 2, 4},
            {8, 16, 32, 64}
        };

        for (int i = 0; i < game.getBoardHeight(); i++) {

            for (int j = 0; j < game.getBoardWidth(); j++) {
                
                game.setPieceAt(i, j, array[i][j]);
            }
        }

        assertFalse(game.isMovePossible());
    }
    public void testIsMovePossibleInAFullBoardWithoutDirection2 () {

        for (int i = 0; i < game.getBoardHeight(); i++) {

            for (int j = 0; j < game.getBoardWidth(); j++) {
                
                game.setPieceAt(i, j, 2);
            }
        }
        
        assertTrue(game.isMovePossible());
    }




    // With Direction

    @Test
    public void testInitialIsMovePossibleWithDirection () {

        // Initially, it should be possible to move in atleast one direction
        boolean movePossible = false;

        for (MoveDirection direction : MoveDirection.values()) {
            
            movePossible = game.isMovePossible(direction);
            if (movePossible) {
                break;
            }
        }
        assertTrue (movePossible);
    }


    // __________________________________________________ //

    /*
     * Test for an empty space
     */

    @Test
    public void testInitialIsSpaceLeft () {
        assertTrue(game.isSpaceLeft()); // Empty space must be there at starting
    }

    
    @Test
    public void testWithAFullBoard () {

        for (int i = 0; i < game.getBoardHeight(); i++) {
            
            for (int j = 0; j < game.getBoardWidth(); j++) {
                
                game.setPieceAt(i, j, 8); // Set every piece to be non zero

            }
        }

        assertTrue (!game.isSpaceLeft());

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