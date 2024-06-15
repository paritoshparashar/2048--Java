package ttfe;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class Simulator implements SimulatorInterface  {

    int width;
    int height;
    int board[][];
    int numMoves;
    int score;

    Random r;

    // Constructor
    Simulator (int width, int height, Random r) throws Exception{

        if (width < 2 || height < 2 || r == null) {
            throw new IllegalArgumentException ("Invalid command line arguments");
        }

        this.width = width;
        this.height = height;
        this.board = new int[width][height];
        this.numMoves = 0;
        this.score = 0;
        this.r = r;

        // Fill the board with empty tiles
        for (int i = 0; i < this.board.length; i++) {

            for (int j = 0; j < this.board[i].length; j++) {

                this.board[i][j] = 0;
            }
        }

    }


    @Override
    public void addPiece() {

        if (!this.isSpaceLeft()) {
            throw new IllegalStateException("Cannot add to a full board");
        }
        
        int randomX = r.nextInt(this.width);
        int randomY = r.nextInt(this.height);
        int tileValue = r.nextInt(10);
        
        if (tileValue < 1) {
            tileValue = 4; // 0.1 Probability
        }
        else {
            tileValue = 2; // 0.9 Probability
        }


        this.setPieceAt(randomX, randomY, tileValue);
        
    }

    @Override
    public int getBoardHeight() {
        return this.height;
    }

    @Override
    public int getBoardWidth() {
        return this.width;
    }

    @Override
    public int getNumMoves() {
        
        return this.numMoves;
    }

    @Override
    public int getNumPieces() {
        
        int count = 0;

        for (int i = 0; i < this.board.length; i++) {
            
            for (int j = 0; j < this.board[i].length; j++) {
                
                if (board[i][j] != 0) {
                    ++count;
                }
            }
        }
        return count;
    }

    @Override
    public int getPieceAt(int x, int y) {
        
        if (x<0 || y<0 || x>=this.width || y>=this.height) {
            throw new IllegalArgumentException ("Arguments out of bound");
        }
        
        return this.board[x][y];
    }

    @Override
    public int getPoints() {
        
        return this.score;
    }

    @Override
    public boolean isMovePossible() {
        
        int currentPiece = 0;
        int nonZero = 0;

        for (int i = 0; i < this.getBoardWidth(); i++) {
            for (int j = 0; j < this.getBoardHeight(); j++) {
                
                currentPiece = this.getPieceAt(i, j);
                if (currentPiece == 0) {
                    // If we found a number perviously and found an empty tile now (inner if protects us from empty board) 
                    if (nonZero != 0) {
                        return true;
                    }
                }

                // If adjacent tiles are same
                else if (j+1 < this.getBoardWidth() && currentPiece == this.getPieceAt(i, j+1)) {
                    return true;
                }
                else if (i+1 < this.getBoardHeight() && currentPiece == this.getPieceAt(i+1, j)) {
                    return true;
                }
                // Increment when found something non-similar and non zero
                else {
                    ++nonZero;
                }
                
            }  
        }

        return false;
    }

    

    @Override
    public boolean isMovePossible(MoveDirection direction) {
        
        int currentPiece = 0;
        boolean inZero = false;

        for (int i = 0; i < this.getBoardWidth(); i++) {
            
            for (int j = 0; j < this.getBoardHeight(); j++) {
                
                currentPiece = this.getPieceAt(i, j);
    
                // If we found a number perviously and found an empty tile now (inner if protects us from empty board) 
                if (currentPiece == 0) {

                    inZero = true;

                    switch (direction) 
                    {
                        case MoveDirection.EAST:
                            
                            for (int i2 = i-1; i2 >= 0; i2--) { // Check left of empty tile

                                if ( !(i2 < 0 ) && this.board[i2][j] != 0) {
                                    return true;
                                }
                            }
                            break;

                        case MoveDirection.WEST:
                            for (int i2 = i+1; i2 < this.getBoardWidth(); i2++) { // Check right of empty tile

                                if ( !(i2 > this.getBoardWidth()) && this.board[i2][j] != 0) {
                                    return true;
                                }
                            }
                            break;

                        case MoveDirection.NORTH:
                            for (int j2 = j+1; j2 < this.getBoardHeight(); j2++) { // Check below the empty tile

                                if ( !(j2 > this.getBoardWidth()) && this.board[i][j2] != 0) {
                                    return true;
                                }
                            }
                            break;

                        case MoveDirection.SOUTH:
                            for (int j2 = j-1; j2 >= 0; j2--) { // Check above the empty tile

                                if ( !(j2 < 0 ) && this.board[i][j2] != 0) {
                                    return true;
                                }
                            }
                            break;
                    }
                    
                }

                if (inZero) {
                    inZero = false;
                    continue;
                }

                // If adjacent tiles are same
                 if (i+1 < this.getBoardWidth() && currentPiece == this.getPieceAt(i+1, j)) 
                {
                    if (direction == MoveDirection.EAST || direction == MoveDirection.WEST) 
                    {
                        return true;
                    }
                }
                else if (j+1 < this.getBoardHeight() && currentPiece == this.getPieceAt(i, j+1)) 
                {
                    if (direction == MoveDirection.NORTH || direction == MoveDirection.SOUTH) 
                    {
                        return true;
                    }
                }

            }
                
        }  
        
        return false;

    }

    @Override
    public boolean isSpaceLeft() {

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                
                if (this.board[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean performMove(MoveDirection direction) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void run(PlayerInterface player, UserInterface ui) {
        
        MoveDirection direction;
        direction = ui.getUserMove();

        if (this.isMovePossible()) {

            if (this.isMovePossible(direction)) {
                
            }

        }
        
    }

    @Override
    public void setPieceAt(int x, int y, int piece) {
        
        if (x<0 || y<0 || x>=this.width || y>=this.height || piece<0) {
            throw new IllegalArgumentException ("Arguments out of bound");
        }

        this.board[x][y] = piece;
        
    }
    
    
}
