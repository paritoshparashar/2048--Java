package ttfe.tests;

import ttfe.SimulatorInterface;

public interface HelperMethodsInterface {
    

    /* 
     * @param -> Game Simulator instance
     * 
     * @reutrn -> 2D array of the current state of the game
    */
    public int[][] getCurrentBoardState (SimulatorInterface game);


    /* 
     * @param -> 2D array of a gameBoard
     * 
     * @reutrn -> Number of Empty Tiles
    */
    public int getEmptyPositions (int[][] gameBoard);
    //public int compareBoards (int [][]board1 , int[][] board2);
}
