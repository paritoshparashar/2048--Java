package ttfe.tests;

import ttfe.SimulatorInterface;

public class TestHelperMethods implements HelperMethodsInterface{

    

    @Override
	public int [][] getCurrentBoardState (SimulatorInterface game) {
		int [][] board = new int[game.getBoardHeight()][game.getBoardWidth()];

		for (int i = 0; i < game.getBoardHeight(); i++) {
			
			for (int j = 0; j < game.getBoardWidth(); j++) {
				
				board[i][j] = game.getPieceAt(j, i);
			}
		}
		return board;
	}

    @Override
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

    @Override
    public void printBoard(int[][] gameBoard) {
        
        for (int i = 0; i < gameBoard.length; i++) {
            
            for (int j = 0; j < gameBoard[i].length; j++) {
                
                System.out.println(gameBoard[i][j]);
            }
        }
        
    }
}
