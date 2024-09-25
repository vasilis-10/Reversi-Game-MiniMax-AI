import java.util.ArrayList;

public class Board {
    public static final int X = 1; // X is the maximizer
    public static final int O = -1;// O is the minimizer
    public static final int EMPTY = 0;// EMPTY is the empty cell

    //The initial game board contains 2Black and 2 White pieces so we initialise the score accordingly
    public int player1Score = 2;
    public int player2Score = 2;

    private int[][] gameBoard;

    private int lastPlayer;

    //Immediate move that lead to this board
    private Move lastMove;

    //default constructor
    public Board(){
        this.gameBoard = new int[8][8];
        for(int row=0; row<8; row++){
            for(int col=0; col<8; col++){
                this.gameBoard[row][col] = EMPTY;
            }
        }
    }

    //Initialise the GameBoard with the initial positions of the pieces
    public void initBoard(){
        this.gameBoard[3][3] = O;
        this.gameBoard[3][4] = X;
        this.gameBoard[4][3] = X;
        this.gameBoard[4][4] = O;

    }
    //Copy Constructor
    Board(Board board)
    {
        this.lastMove = board.lastMove;
        this.lastPlayer = board.lastPlayer;
        this.gameBoard = new int[8][8];
        for(int i = 0; i < this.gameBoard.length; i++)
        {
            for(int j = 0; j < this.gameBoard.length; j++)
            {
                this.gameBoard[i][j] = board.gameBoard[i][j];
            }
        }
    }
    //Prints the board for the user
    public void print(){
        System.out.println("|*|1|2|3|4|5|6|7|8|||");
        for(int row=0; row<8; row++)
        {
            System.out.print("|"+(row+1)+"|");
            for(int col=0; col<8; col++)
            {
                switch (this.gameBoard[row][col]) {
                    case X -> System.out.print("X ");
                    case O -> System.out.print("O ");
                    case EMPTY -> System.out.print("- ");
                    default -> {
                    }
                }
            }
            System.out.println("||");
        }
        System.out.println("*********************");
    }
    //Makes a move
    void makeMove(int row, int col, int letter)
    {
        this.gameBoard[row][col] = letter;
        this.lastMove = new Move(row, col);
        this.lastPlayer = letter;
    }
    //Checks whether a move is valid
    boolean isValidMove(int row, int col)
    {
        //If a move is out of board bounds it is invalid
        if((row > 7) || (col > 7) || (row < 0) || (col < 0)) return false;
        //If a move is on a non-empty cell it is invalid
        if(this.gameBoard[row][col] != EMPTY) return false;


        //checks for validity of a move  by checking if there is a neighbour of the opposing player
        if(this.lastPlayer == O){//If player X plays
            if(checkNeighbor(row,col,X)==false) return false;
            return true;
        }

        if(this.lastPlayer == X){//If player O plays
            if(checkNeighbor(row,col,O)==false) return false;
            return true;
        }

        return true;
    }



    //Checks neighbor cells for enemy and if it finds one one enemy it expands the search to the next cells with the checkExpandedCells method
    boolean checkNeighbor(int row, int col , int currentValue){
        boolean canPlace = false;//If a move is valid it will be set to true
        //Checks the 8 cells around the current cell
        for(int i = -1 ; i <= 1 ; i++ ){
            for(int j = -1 ; j<=1 ; j++){
                //If one neighbor goes out of bounds , dont check him get to the next for iteration
                if((row + i > 7) || (col + j> 7) || (row + i < 0) || (col + j < 0)){ continue;}

                /*
                  If we find an enemy cell in one direction
                  we expand the search to the next cells of this direction till we find a friendly cell
                  using the checkExpandedCells method
                */
                if(isEnemy(row+i,col+j,currentValue) ){
                    if(checkExpandedCells(row+i,col+j,i,j,currentValue)){
                        canPlace = true;
                    }
                }
            }
        }
        return canPlace;
    }
    //Checks the cells in the direction of the enemy cell
    boolean checkExpandedCells(int row , int col , int rowOffset, int colOffset , int currentValue){

        boolean canPut = false;
        int checkRow = row;
        int checkCol = col;
        //Checks the cells in the direction of the enemy cell until we find a friendly cell
        while(isEnemy(checkRow,checkCol,currentValue)==true){

            if((checkCol+colOffset)>7||(checkRow+rowOffset)>7  ||  (checkCol+colOffset)<0 ||(checkRow+rowOffset)<0  ){break;}
            //If the next cell of the enemy cell is a friendly cell we can go and flip the enemy  cells between our check place and the found friendly cell
            if(isFriendly(checkRow+rowOffset,checkCol+colOffset,currentValue)==true){
                canPut = true;
                //If we find a friendly cell we can go and flip the enemy  cells between our check place and the found friendly cell
                flip(row,col,checkRow+rowOffset,checkCol+colOffset,rowOffset,colOffset,currentValue);

            }

            checkRow = checkRow + rowOffset;
            checkCol = checkCol + colOffset;

        }
        //If we find a friendly cell we can go and flip the enemy  cells between our check place and the found friendly cell
        return canPut;
    }
    //Flips the enemy cells between the check place and the friendly cell
    void flip(int rowStart, int colStart , int rowFinish , int colFinish ,int rowOffset,int colOffset,int currentValue){
        int currentRow = rowStart;
        int currentCol = colStart;

        while(true){

            this.gameBoard[currentRow][currentCol] = currentValue;//
            currentRow += rowOffset;
            currentCol += colOffset;
            if(currentRow == rowFinish && currentCol == colFinish)break;
        }
    }
    //Checks if a cell is an enemy cell
    boolean isEnemy(int row, int col, int currentValue){
        //If the cell is empty it is not an enemy cell
        //If the cell is a friendly cell it is not an enemy cell
        if(this.gameBoard[row][col]!= currentValue && this.gameBoard[row][col]!=EMPTY) return true;
        return false;
    }
    //Checks if a cell is a friendly cell
    boolean isFriendly(int row, int col, int currentValue){
        if(this.gameBoard[row][col]== currentValue) return true;
        return false;
    }



    //Setters and Getters

    Move getLastMove()
    {
        return this.lastMove;
    }

    int getLastPlayer()
    {
        return this.lastPlayer;
    }

    int[][] getGameBoard()
    {
        return this.gameBoard;
    }



    void setLastMove(Move lastMove)
    {
        this.lastMove.setRow(lastMove.getRow());
        this.lastMove.setCol(lastMove.getCol());
        this.lastMove.setValue(lastMove.getValue());
    }

    void setLastPlayer(int lastPlayer)
    {
        this.lastPlayer = lastPlayer;
    }

    void playerScoreCount(){
        player1Score = 0;
        player2Score = 0;
        for(int row=0; row<8; row++){
            for(int col=0; col<8; col++){
                if(this.gameBoard[row][col] == X)player1Score++;
                if(this.gameBoard[row][col]==O)player2Score++;
            }
        }

    }
    boolean isTerminal(){
        if(player1Score + player2Score == 64)return true;
        return false;
    }

    //Heuristic
    int evaluate() {
        lastPlayer = getLastPlayer();
        int f1; //difference of 2 player scores
        int f2; //difference of 2 player sum of pieces in corners
        int f3; //difference of 2 player sum of pieces in non-corner edges
        int sumX = 0;//number of player X pieces at corner cells
        int sumO = 0;//number of player O pieces at corner cells
        int sumX2 = 0;//number of player X pieces at non-corner edge cells
        int sumO2 = 0;//number of player O pieces at non-corner edge cells

        //f2
        if(this.gameBoard[0][0]== X){
            sumX++;
        }
        else if(this.gameBoard[0][0]== O){
            sumO++;
        }

        if(this.gameBoard[0][7]== X){
            sumX++;
        }
        else if(this.gameBoard[0][7]== O){
            sumO++;
        }

        if(this.gameBoard[7][0]== X){
            sumX++;
        }
        else if(this.gameBoard[7][0]== O){
            sumO++;
        }
        if(this.gameBoard[7][7]== X){
            sumX++;
        }
        else if(this.gameBoard[7][7]== O){
            sumO++;
        }

        //f3
        for(int i=1;i<7;i++){
            if(this.gameBoard[i][0]== X){
                sumX2++;
            }
            else if(this.gameBoard[i][0]== O){
                sumO2++;
            }
        }
        for(int i=1;i<7;i++){
            if(this.gameBoard[i][7]== X){
                sumX2++;
            }
            else if(this.gameBoard[i][7]== O){
                sumO2++;
            }
        }
        for(int j=1;j<7;j++){
            if(this.gameBoard[0][j]== X){
                sumX2++;
            }
            else if(this.gameBoard[0][j]== O){
                sumO2++;
            }
        }
        for(int j=1;j<7;j++){
            if(this.gameBoard[7][j]== X){
                sumX2++;
            }
            else if(this.gameBoard[7][j]== O){
                sumO2++;
            }
        }

        f1 = player1Score - player2Score;
        f2=sumX-sumO;
        f3=sumX2-sumO2;

        return f1+ (3*f2) + (2*f3);
    }

    /* Generates the children of the state
     * Any square in the board that is empty results to a child
     */
    ArrayList<Board> getChildren(int letter) {
        ArrayList<Board> children = new ArrayList<>();

        for(int row = 0; row < this.gameBoard.length; row++)
        {
            for(int col = 0; col < this.gameBoard.length; col++)
            {
                Board child = new Board(this);
                //we create children only for valid moves
                if(child.isValidMove(row, col))
                {

                    child.makeMove(row, col, letter);
                    children.add(child);
                }
            }
        }
        return children;
    }
    //Check for available moves for the player
    boolean availableMoves(){
        int i=0;
        for(int row = 0; row < this.gameBoard.length; row++) {
            for (int col = 0; col < this.gameBoard.length; col++) {
                Board board1 = new Board(this);
                if (this.gameBoard[row][col] == EMPTY) {
                    if (board1.isValidMove(row, col)==true) {
                        i++;
                    }
                }
            }
        }
        if(i==0)
            return false;
        else
            return true;
    }
}

