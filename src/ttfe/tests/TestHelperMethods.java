package ttfe.tests;

import ttfe.SimulatorInterface;

public class TestHelperMethods implements HelperMethodsInterface{

	public int [][] getCurrentBoardState (SimulatorInterface game) {
		int [][] board = new int[game.getBoardHeight()][game.getBoardWidth()];

		for (int i = 0; i < game.getBoardHeight(); i++) {
			
			for (int j = 0; j < game.getBoardWidth(); j++) {
				
				board[i][j] = game.getPieceAt(j, i);
			}
		}
		return board;
	}

    public int calculateEmptyPositions (int[][] board) {

        
        return 0;
    }
}
