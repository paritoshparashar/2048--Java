package ttfe.tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import ttfe.GUI;
import ttfe.HumanPlayer;
import ttfe.MoveDirection;
import ttfe.PlayerInterface;
import ttfe.SimulatorInterface;
import ttfe.TTFEFactory;
import ttfe.UserInterface;

/*
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
                    assertTrue(piece_val == 0 || piece_val == 2 || piece_val == 4);
                }
            }
            assertTrue("No new piece with value 2 or 4 was added", isTwoOrFour);
        }
    }

    @Test
    public void testAddPieceOnFullBoard () {

        this.setBoardToNum(8);
        
        assertThrows(IllegalStateException.class, () -> {game.addPiece();});

        for (int i = 0; i < game.getBoardWidth(); i++) {
            
            for (int j = 0; j < game.getBoardHeight(); j++) {
                
                assertTrue(game.getPieceAt(i, j) == 8);
            }
        }
    }
    // __________________________________________________ //

    /*
     * Tests for Board Dimensions
     */

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

    @Test
    public void testBoardIsSquare () {
        assertTrue (game.getBoardHeight() == game.getBoardWidth());
    }


    // __________________________________________________ //

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

    @Test
    public void testNumOfMovesAfterMergingMultipleTiles () {

        this.setBoardToNum(8);
        game.performMove(MoveDirection.EAST);
        game.performMove(MoveDirection.SOUTH);

        assertTrue (2 == game.getNumMoves());
    }
    // __________________________________________________ //

    /*
     * Test for Number of Pieces
     */

    @Test
    public void testInitialNumberOfPieces () {
        assertEquals(2, game.getNumPieces());
    }
    @Test
    public void testNumberOfPiecesFullBoard () {

        this.setBoardToNum(4);
        assertEquals(16, game.getNumPieces());
    }

    // __________________________________________________ //

    /*
     * Tests for the value of pieces (getPieceAt(int x, int y))
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
    
    @Test
    public void testRandomBoardValues () {

        this.setBoardWithNoMovePossible();

        assertEquals(1024, game.getPieceAt(1, 2));
        assertEquals(8, game.getPieceAt(3, 3));
        assertEquals(128, game.getPieceAt(2, 1));
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
    public void testPointsAfterMove (){

        this.setBoardToNum(0);

        game.setPieceAt(0, 0, 2);
        game.setPieceAt(0, 2, 2);
        
        if (game.isMovePossible(MoveDirection.NORTH)) {
            assertTrue(game.performMove(MoveDirection.NORTH));
        }
        if (game.isMovePossible(MoveDirection.SOUTH)) {
            assertTrue(game.performMove(MoveDirection.SOUTH));
        }
        assertTrue(4 == game.getPoints());

    }

    @Test
    public void testPointsAfterComplexMove (){

        this.setBoardToNum(0);

        game.setPieceAt(0, 0, 16);
        game.setPieceAt(0, 1, 16);
        game.setPieceAt(0, 2, 8);
        game.setPieceAt(0, 3, 8);
        
        game.setPieceAt(1, 0, 16);
        game.setPieceAt(1, 1, 0);
        game.setPieceAt(1, 2, 16);
        game.setPieceAt(1, 3, 4);

        game.setPieceAt(2, 0, 2);
        game.setPieceAt(2, 1, 2);
        game.setPieceAt(2, 2, 2);
        game.setPieceAt(2, 3, 4);

        assertTrue(game.isMovePossible());

        for (MoveDirection direction : MoveDirection.values()) {
            assertTrue (game.isMovePossible(direction)); // Move possible in all directions
        }

            assertTrue(game.performMove(MoveDirection.SOUTH));
            assertTrue(game.performMove(MoveDirection.WEST)); 

        assertTrue(156 == game.getPoints());

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
        
        this.setBoardWithNoMovePossible();

        assertFalse(game.isMovePossible());
    }

    @Test
    public void testIsMovePossibleInAFullBoardWithoutDirection2 () {

        this.setBoardToNum(2);
        
        assertTrue(game.isMovePossible());
    }

    @Test
    public void testIsMovePossibleInOneDirection () {

        this.setBoardWithNoMovePossible();
        /*
         *  {2, 4, 8, 4},
            {32, 64, 128, 256},
            {2, 1024, 2, 4},
            {8, 16, 32, 8}
         */

        assertFalse( game.isMovePossible(MoveDirection.NORTH));
        assertFalse(game.isMovePossible(MoveDirection.EAST));
        assertFalse( game.isMovePossible(MoveDirection.SOUTH));
        assertFalse(game.isMovePossible(MoveDirection.WEST));

        // Attempting to perform a move in any direction should not change the board state
        assertFalse( game.performMove(MoveDirection.NORTH));
        assertFalse(game.performMove(MoveDirection.EAST));
        assertFalse( game.performMove(MoveDirection.SOUTH));
        assertFalse(game.performMove(MoveDirection.WEST));

        // Ensure the board state remains unchanged
        assertEquals( 2, game.getPieceAt(0, 0)); 
        assertEquals( 1024, game.getPieceAt(1, 2));

        game.setPieceAt(2, 0, 4);
        assertTrue( game.isMovePossible());
        assertTrue( game.isMovePossible(MoveDirection.EAST));

    }


    // __________________With Direction__________________ //

    @Test(expected = IllegalArgumentException.class)
    public void testPreconditionError() {

        MoveDirection direction = null;

        game.isMovePossible(direction);
    }

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

    @Test
    public void testIsMovePossibleInAFullBoardWithDirection () {
        
        this.setBoardWithNoMovePossible();


        boolean movePossible = true;

        for (MoveDirection direction : MoveDirection.values()) {
            
            movePossible = game.isMovePossible(direction);
            if (movePossible) {
                break;
            }
        }
        
        assertTrue("Game should be over", !game.isSpaceLeft() && !game.isMovePossible(MoveDirection.EAST) && 
                   !game.isMovePossible(MoveDirection.NORTH) && !game.isMovePossible(MoveDirection.WEST) && 
                   !game.isMovePossible(MoveDirection.SOUTH));
        
        assertFalse(movePossible);
    }

    @Test
    public void testIsMovePossibleInAFullBoardWithDirection2 () {

        this.setBoardToNum(2);

        boolean movePossible = false;
        
        for (MoveDirection direction : MoveDirection.values()) {
            
            movePossible = game.isMovePossible(direction); // Should be able to move in all directions
            
        }
        
        assertTrue(movePossible);
    }

    /*
     * Extra Tests
     */


    @Test
    public void testIsMovePossibleEmptyBoard() {
        
        this.setBoardToNum(0);
        assertFalse (game.isMovePossible());

        for (MoveDirection direction : MoveDirection.values()) {
            assertFalse(game.isMovePossible(direction));
        }
        
    }

    @Test
    public void testIsMovePossibleWithOnePossibleMove() {

        this.setBoardToNum(2);

        assertTrue( game.isMovePossible());
        assertTrue( game.isMovePossible(MoveDirection.NORTH));
        assertTrue( game.isMovePossible(MoveDirection.EAST));
        assertTrue( game.isMovePossible(MoveDirection.SOUTH));
        assertTrue( game.isMovePossible(MoveDirection.WEST));
    }

    @Test
    public void testIsMovePossibleWithMergeableTiles() {

        this.setBoardToNum(0);

        // Set up the board so that merges are possible
        game.setPieceAt(0, 0, 2);
        game.setPieceAt(0, 1, 2);
        game.setPieceAt(1, 0, 4);
        game.setPieceAt(1, 1, 4);

        assertTrue( game.isMovePossible());
        assertTrue(game.isMovePossible(MoveDirection.NORTH));
        assertTrue(game.isMovePossible(MoveDirection.EAST));
        assertTrue(game.isMovePossible(MoveDirection.SOUTH));
        assertFalse(game.isMovePossible(MoveDirection.WEST));
    }

    // __________________________________________________ //


    /*
     * Tests for an empty space
     */

    @Test
    public void testInitialIsSpaceLeft () {
        assertTrue(game.isSpaceLeft()); // Empty space must be there at starting
    }

    
    @Test
    public void testWithAFullBoard () {

        this.setBoardToNum(8);

        assertTrue (!game.isSpaceLeft());
    }

    // __________________________________________________ //

    /*
     * Tests for Perform Move
     */

    @Test
    public void testInitialPerformMove () {

        // Initially, it should be possible to move in atleast one direction
        boolean movePossible = false;

        for (MoveDirection direction : MoveDirection.values()) {
            
            movePossible = game.performMove(direction);
            if (movePossible) {
                break;
            }
        }
        assertTrue (movePossible);
    }


    @Test
     public void testPerformMoveNorth() {

        this.setBoardToNum(0);

        game.setPieceAt(0, 0, 2);
        game.setPieceAt(0, 1, 2);
        game.setPieceAt(0, 2, 4);
        assertTrue( game.performMove(MoveDirection.NORTH));
        assertEquals( 4, game.getPieceAt(0, 0));
        assertEquals( 4, game.getPieceAt(0, 1));
        assertEquals( 0, game.getPieceAt(0, 2));
    }

    @Test
    public void testPerformMoveToEast() {

        this.setBoardToNum(0);

        game.setPieceAt(1, 0, 2);
        game.setPieceAt(2, 0, 2);
        game.setPieceAt(3, 0, 4);
        assertTrue(game.performMove(MoveDirection.EAST));
        assertEquals( 4, game.getPieceAt(3, 0));
        assertEquals( 4, game.getPieceAt(2, 0));
        assertEquals( 0, game.getPieceAt(1, 0));
    }

    @Test
    public void testPerformMoveToSouth() {

        this.setBoardToNum(0);

        game.setPieceAt(0, 3, 4);
        game.setPieceAt(0, 2, 4);
        game.setPieceAt(0, 1, 4);
        assertTrue( game.performMove(MoveDirection.SOUTH));
        assertEquals( 8, game.getPieceAt(0, 3));
        assertEquals( 4, game.getPieceAt(0, 2));
        assertEquals( 0, game.getPieceAt(0, 1));
    }

    @Test
    public void testPerformMoveToWest() {

        this.setBoardToNum(0);

        game.setPieceAt(3, 0, 2);
        game.setPieceAt(2, 0, 2);
        game.setPieceAt(1, 0, 4);
        assertTrue( game.performMove(MoveDirection.WEST));
        assertEquals( 4, game.getPieceAt(0, 0));
    }
    // __________________________________________________ //

    @Test
    public void isMovePossibleSpecial () {

        this.setBoardToNum(0);

        game.setPieceAt(3, 0, 4);
        assertTrue( game.isMovePossible());
    }
    
    @Test
    public void testIsMovePossibleWithDirectionSpecial1 () {

        this.setBoardToNum(0);

        game.setPieceAt(0, 0, 2);
        game.setPieceAt(0, 1, 2);
        game.setPieceAt(0, 2, 16);
        game.setPieceAt(0, 3, 8);

        game.setPieceAt(1, 0, 2);
        game.setPieceAt(1, 1, 4);
        game.setPieceAt(1, 2, 8);
        game.setPieceAt(1, 3, 64);

        assertTrue( game.performMove(MoveDirection.SOUTH));
        assertTrue(4 == game.getPieceAt(0, 1));
    }
    @Test
    public void testPerformMove3sameValueColumn () {

        this.setBoardToNum(0);
        game.setPieceAt(0, 0, 2);
        game.setPieceAt(0, 1, 2);
        game.setPieceAt(0, 2, 2);
        game.setPieceAt(0, 3, 8);

        assertTrue( game.performMove(MoveDirection.SOUTH));
        assertTrue(4 == game.getPieceAt(0, 2));
        assertTrue(2 == game.getPieceAt(0, 1));
    }

    // __________________________________________________ //

    /*
     * Test for setPieceAt
     */

    @Test
    public void testSetPieceAt () {

        this.setBoardToNum(2);

        game.setPieceAt(0, 0, 0);
        game.setPieceAt(0, 1, 16);

        assertTrue (game.getPieceAt(0, 0) == 0 && game.getPieceAt(0, 1) == 16);

    }

    // __________________________________________________ //

    /*
     * Test for run
     */

     @Test
    public void testRunGameSimulation() {

        PlayerInterface myPlayer = TTFEFactory.createPlayer(false);
        UserInterface mykUI = TTFEFactory.createUserInterface(game);
        
        // Run the game
        game.run(myPlayer, mykUI);
        
        // Verify game end conditions
        assertTrue(!game.isMovePossible());
        assertTrue(game.getNumMoves() !=  0);
        assertTrue(!game.isSpaceLeft());
        assertTrue(!game.isMovePossible());
        assertTrue(game.getPoints() != 0);
    }
    


    // __________________________________________________ //

    @Test
    public void testGameWin () {

        this.setBoardToNum(0);
        game.setPieceAt(0, 0, 1024);
        game.setPieceAt(0, 1, 1024);
        game.setPieceAt(0, 2, 2);
        game.setPieceAt(0, 3, 8);

        assertTrue( game.performMove(MoveDirection.SOUTH));
        assertTrue(2048 == game.getPieceAt(0, 1));
    }

    public void setBoardToNum (int num) {

        for (int i = 0; i < game.getBoardHeight(); i++) {
            
            for (int j = 0; j < game.getBoardWidth(); j++) {
                
                game.setPieceAt(i, j, num); // Set every piece to be zero

            }
        }
    }

    public void setBoardWithNoMovePossible () {

        int[][] array = {
            {2, 4, 8, 4},
            {32, 64, 128, 256},
            {2, 1024, 2, 4},
            {8, 16, 32, 8}
        };

        for (int i = 0; i < game.getBoardWidth(); i++) {

            for (int j = 0; j < game.getBoardHeight(); j++) {
                
                game.setPieceAt(i, j, array[j][i]);
            }
        }
    }

    public static void main(String[] args){
        SimpleTests test = new SimpleTests();
        test.setUp();
        test.testPerformMove3sameValueColumn();
    }
}
