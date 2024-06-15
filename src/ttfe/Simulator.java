package ttfe;

import java.util.Random;

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
        int zero = 0;

        for (int i = 0; i < this.getBoardWidth(); i++) {
            for (int j = 0; j < this.getBoardHeight(); j++) {
                
                currentPiece = this.getPieceAt(i, j);
                if (currentPiece == 0) {
                    ++zero;
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
            if (zero > 0  && nonZero > 0) {
                return true;
            }  
        }

        return false;
    }

    

    @Override
    public boolean isMovePossible(MoveDirection direction) {
        
        if (direction == null) {
            throw new IllegalArgumentException();

        }
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

        if (direction == null) {
            throw new IllegalArgumentException();
        }

        if (!this.isMovePossible(direction)) {
            return false;
        }

        // First swap number with white spaces (if direction is north => start searching from top, if east => start searching from right)
        // Now add consecutive tiles with same value
        // Again remove the newly created whitespaces

            switch (direction) {
                case MoveDirection.NORTH:
                case MoveDirection.SOUTH:
                    removeWhiteSpaces_NS(direction);
                    addConsecutiveTiles_NS(direction); // Increase score here
                    removeWhiteSpaces_NS(direction);
                    break;

                case MoveDirection.EAST:
                case MoveDirection.WEST:
                    removeWhiteSpaces_EW(direction);
                    addConsecutiveTiles_EW(direction); // Increase score here
                    removeWhiteSpaces_EW(direction);
                    break;
                
            }
            ++this.numMoves;

        return true;
    }
    
    public int[] setZerosAtLast (int oldArr []){

        int n = oldArr.length;
        int[] newArr = new int[n];
        int index = 0;

        // Iterate through the input array and copy positive integers to resultArray
        for (int i = 0; i < n; i++) {
            
            if (oldArr[i] != 0) {
                newArr[index++] = oldArr[i];
            }
        }

        return newArr;

    }
    public int[] setZerosAtStart (int oldArr []){

        int n = oldArr.length;
        int[] newArr = new int[n];
        int index = n-1;

        // Iterate through the input array and copy positive integers to resultArray
        for (int i = n-1; i >= 0; i--) {

            if (oldArr[i] != 0) {
                newArr[index--] = oldArr[i];
            }
        }
        return newArr;

    }

    public void removeWhiteSpaces_NS (MoveDirection direction) {

        int[] transformetColumn = new int [this.getBoardHeight()];

        for (int i = 0; i < this.getBoardWidth(); i++) {

            if (direction == MoveDirection.NORTH) {
            transformetColumn = setZerosAtLast(this.board[i]);
            this.board[i] = transformetColumn;
            }
            else if (direction == MoveDirection.SOUTH) {
            transformetColumn = setZerosAtStart(this.board[i]);
            this.board[i] = transformetColumn;
            }
        }
    }
    public void removeWhiteSpaces_EW (MoveDirection direction) {

        int[] transformedColumn = new int [this.getBoardHeight()];

        for (int j = 0; j < this.getBoardHeight(); j++) {

            for (int i = 0; i < this.getBoardWidth(); i++) {
                transformedColumn[i] = this.board[i][j];    // Get the one-d array
            }
             
            if (direction == MoveDirection.WEST) {
            transformedColumn = setZerosAtLast(transformedColumn);
            }
            else if (direction == MoveDirection.EAST) {
            transformedColumn = setZerosAtStart(transformedColumn);
            }

            for (int i = 0; i < this.getBoardWidth(); i++) {
                this.board[i][j] = transformedColumn[i]; // Put it back after transforming
            }
        }
    }
    
    public int[] addTilesInDirection (int oldArr [] , MoveDirection direction){

        int n = oldArr.length;
        int[] newArr = new int[n];
        int index = 0;

        for (int i = 0; i < n; i++) {
            
            if ((i+1 < n) && (oldArr[i] == oldArr[i+1])) {
                
                switch (direction) {
                    case MoveDirection.NORTH:
                    case MoveDirection.WEST:
                        score += 2*oldArr[i];
                        newArr[index++] = 2*oldArr[i];
                        newArr[index++] = 0;
                        ++i;
                        break;

                    case MoveDirection.SOUTH:
                    case MoveDirection.EAST:
                        score += 2*oldArr[i];
                        newArr[index++] = 0;
                        newArr[index++] = 2*oldArr[i];
                        ++i;
                        break;
                
                }
                
            }
            else {
                newArr[index++] = oldArr[i];
            }
        }

        return newArr;

    }
    
    public void addConsecutiveTiles_NS (MoveDirection direction) {

        int[] transformetColumn = new int [this.getBoardHeight()];

        for (int i = 0; i < this.getBoardWidth(); i++) {
            
            transformetColumn = addTilesInDirection(this.board[i] , direction);
            this.board[i] = transformetColumn;
          
        }
    }

    public void addConsecutiveTiles_EW (MoveDirection direction) {

        int[] transformedColumn = new int [this.getBoardHeight()];

        for (int j = 0; j < this.getBoardHeight(); j++) {

            for (int i = 0; i < this.getBoardWidth(); i++) {
                transformedColumn[i] = this.board[i][j];    // Get the one-d array
            }
             
            if (direction == MoveDirection.WEST) {
            transformedColumn = addTilesInDirection(transformedColumn , direction);
            }
            else if (direction == MoveDirection.EAST) {
            transformedColumn = addTilesInDirection(transformedColumn , direction);
            }

            for (int i = 0; i < this.getBoardWidth(); i++) {
                this.board[i][j] = transformedColumn[i]; // Put it back after transforming
            }
        }
    }


    @Override
    public void run(PlayerInterface player, UserInterface ui) {

        ui.updateScreen(this);

        if (player == null || ui == null) {
            throw new IllegalArgumentException();
        }
        
        MoveDirection direction;

        while (this.isMovePossible()) {

            direction = player.getPlayerMove(this, ui);

            if (this.performMove(direction)) {
                this.addPiece();
                ui.updateScreen(this);
            } 

        }
        
        ui.showGameOverScreen(this); 
        
    }

    @Override
    public void setPieceAt(int x, int y, int piece) {
        
        if (x<0 || y<0 || x>=this.width || y>=this.height || piece<0) {
            throw new IllegalArgumentException ("Arguments out of bound");
        }

        this.board[x][y] = piece;
        
    }
    
    
}
