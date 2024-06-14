package ttfe.tests;


import org.junit.Test;
import static org.junit.Assert.*;

import ttfe.MoveDirection;
import ttfe.PlayerInterface;
import ttfe.SimulatorInterface;
import ttfe.TTFEFactory;
import ttfe.UserInterface;

import java.util.Random;
public class SimulatorTests {
    @Test
    public void testInitialBoardState() {
        SimulatorInterface simulator = TTFEFactory.createSimulator(4, 4, new Random());
        int numPieces = simulator.getNumPieces();
        assertEquals("The board should start with exactly 2 pieces.", 2, numPieces);
    }
    @Test
    public void testBoardDimensions() {
        SimulatorInterface simulator = TTFEFactory.createSimulator(4, 4, new Random());
        assertEquals("Board height should be 4.", 4, simulator.getBoardHeight());
        assertEquals("Board width should be 4.", 4, simulator.getBoardWidth());
    }
    
  
    @Test
    public void testAddPiece() {
        SimulatorInterface simulator = TTFEFactory.createSimulator(4, 4, new Random());
        int initialPieces = simulator.getNumPieces();
        simulator.addPiece();
        assertEquals("A new piece should be added.", initialPieces + 1, simulator.getNumPieces());
    }

    @Test(expected = IllegalStateException.class)
    public void testAddPieceWhenFull() {
        SimulatorInterface simulator = TTFEFactory.createSimulator(4, 4, new Random());
        // Fill the board
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                simulator.setPieceAt(i, j, 2);
            }
        }
        // Attempt to add a piece to the full board
        simulator.addPiece();
    }

   

    @Test(expected = IllegalArgumentException.class)
    public void testPerformMoveInvalidDirection() {
        SimulatorInterface simulator = TTFEFactory.createSimulator(4, 4, new Random());
        simulator.performMove(null);
    } 
    
    
    

    @Test
    public void testGetNumMoves() {
        SimulatorInterface simulator = TTFEFactory.createSimulator(4, 4, new Random());
        assertEquals("Initial number of moves should be 0.", 0, simulator.getNumMoves());
        simulator.performMove(MoveDirection.NORTH);
        assertEquals("Number of moves should be 1 after one move.", 1, simulator.getNumMoves());
        simulator.performMove(MoveDirection.SOUTH);
        assertEquals("Number of moves should be 2 after two moves.", 2, simulator.getNumMoves());
    }

    @Test
    public void testIsSpaceLeft() {
        SimulatorInterface simulator = TTFEFactory.createSimulator(4, 4, new Random());
        assertTrue("Initially, there should be space left on the board.", simulator.isSpaceLeft());
        // Fill the board
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                simulator.setPieceAt(i, j, 2);
            }
        }
        assertFalse("There should be no space left on the board.", simulator.isSpaceLeft());
    }

    @Test
    public void testPoints() {
        SimulatorInterface simulator = TTFEFactory.createSimulator(4, 4, new Random());
        simulator.setPieceAt(0, 0, 2);
        simulator.setPieceAt(0, 1, 2);
        simulator.performMove(MoveDirection.WEST);
        assertEquals("Points should be 4 after merging two 2 tiles.", 4, simulator.getPoints());
    }
    @Test
    public void testRunGame() {
        SimulatorInterface simulator = TTFEFactory.createSimulator(4, 4, new Random());
        PlayerInterface mockPlayer = new MockPlayer();
        UserInterface mockUI = new MockUI();
        
        // Run the game
        simulator.run(mockPlayer, mockUI);
        
        // Add assertions based on expected game end conditions
        assertFalse("No moves should be possible, game should end.", simulator.isMovePossible());
        assertTrue("Game should be over.", !simulator.isSpaceLeft() && !simulator.isMovePossible(MoveDirection.NORTH) && 
                   !simulator.isMovePossible(MoveDirection.SOUTH) && !simulator.isMovePossible(MoveDirection.WEST) && 
                   !simulator.isMovePossible(MoveDirection.EAST));
        assertTrue("Game should have made some moves.", simulator.getNumMoves() > 0);
        assertTrue("Score should be greater than zero if any merges occurred.", simulator.getPoints() >= 0);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRunWithNullPlayer() {
        SimulatorInterface simulator = TTFEFactory.createSimulator(4, 4, new Random());
        UserInterface mockUI = new MockUI();
        // Run the game with null player
        simulator.run(null, mockUI);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRunWithNullUI() {
        SimulatorInterface simulator = TTFEFactory.createSimulator(4, 4, new Random());
        PlayerInterface mockPlayer = new MockPlayer();
        // Run the game with null UI
        simulator.run(mockPlayer, null);
    }
   
    @Test
    public void testNumMoves() {
        SimulatorInterface simulator = TTFEFactory.createSimulator(4, 4, new Random());
        int moveCount = 0;

        while (simulator.performMove(MoveDirection.NORTH)) {
            moveCount++;
        }
        
        while (simulator.performMove(MoveDirection.WEST)) {
            moveCount++;
        }
        
        while (simulator.performMove(MoveDirection.EAST)) {
            moveCount++;
        }
        
        while (simulator.performMove(MoveDirection.SOUTH)) {
            moveCount++;
        }

        assertEquals("Number of moves should match.", moveCount, simulator.getNumMoves());
    }


    // Mock classes for testing
    public class MockPlayer implements PlayerInterface {
        private int moveCount = 0;

        @Override
        public MoveDirection getPlayerMove(SimulatorInterface game, UserInterface ui) {
            MoveDirection[] directions = MoveDirection.values();
            return directions[moveCount++ % directions.length];
        }
    }

    public class MockUI implements UserInterface {
        @Override
        public String getUserInput(String question, String[] possibleAnswers) {
            // No-op for testing
            return possibleAnswers[0];
        }

        @Override
        public MoveDirection getUserMove() {
            // No-op for testing
            return MoveDirection.NORTH;
        }

        @Override
        public void showGameOverScreen(SimulatorInterface game) {
            System.out.println("Game Over! Score: " + game.getPoints());
        }

        @Override
        public void showMessage(String msg) {
            // No-op for testing
        }

        @Override
        public void updateScreen(SimulatorInterface game) {
            // No-op for testing
        }
    }
}
