/*
-------------------------------------------------------------------------------------
| Authors:   Dimosthenis-Marios Karagiannis p3140069  |  Vasilis Loizidis p3200223  |
-------------------------------------------------------------------------------------
*/
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //We utilise an input scanner allowing the user to choose his/her turn and the depth of the search.
        Scanner sc = new Scanner(System.in);
        System.out.println("Type 1 if you want to play first and be X or 2 if you want to play second and be O");
        int choice = sc.nextInt();
        while(choice != 1 && choice != 2){
            System.out.println("Wrong choice. Type 1 if you want to play first and be X or 2 if you want to play second and be O");
            choice = sc.nextInt();
        }


        System.out.println("Type max depth for the MiniMax algorithm(1-10)");
        //Max depth of the search
        int maxDepth = sc.nextInt();
        while (maxDepth < 1 || maxDepth > 10){
            System.out.println("Wrong choice. Type max depth for the MiniMax algorithm(1-10)");
            maxDepth = sc.nextInt();
        }
        //We create an AI player using the Computer class constructor.
        //The AI player is either the minimizing or the maximizing player depending on the choice of the user's turn.
        Computer computer = null;
        if(choice == 1) {
             computer = new Computer(maxDepth, Board.O);
        }else if(choice == 2) {
             computer = new Computer(maxDepth, Board.X);
        }

        //We create a new Board object which initialises the gameboard.
        Board boarding = new Board();
        boarding.initBoard();
        //We set the last player move as "0" to indicate that player X plays first.
        boarding.setLastPlayer(Board.O);
        boarding.print();


        //Game loop
        while(!boarding.isTerminal()) {

             sc = new Scanner(System.in);

            if(boarding.getLastPlayer()==Board.O){//X PLAYS NOW
                //We check for available moves for the player. If there is none Turn is skipped
                if (boarding.availableMoves()==false){
                    System.out.println("No available moves for X. Turn skipped");
                    boarding.setLastPlayer(Board.X);
                    continue;
                }

                if(choice == 1){
                    //Depending on the turn of the player, the user is asked to input the row and column of his/her move.
                    System.out.println("Player X  please ");
                    System.out.println("enter row: ");
                    int row = sc.nextInt() - 1;
                    System.out.println("enter column: ");
                    int column = sc.nextInt() - 1;


                    if (boarding.isValidMove(row, column)) { // We check if the move is valid
                        boarding.makeMove(row, column, Board.X);
                        boarding.print();
                        boarding.playerScoreCount();
                        System.out.println("Score: Player = " + boarding.player1Score + "|| Computer = "+ boarding.player2Score);

                    } else {
                        System.out.println("Not A Valid Move");
                    }
                }
                else{
                     System.out.println("computer Plays as X");
                     //We call the minimax algorithm to find the best move for the AI player.
                     Move moveO = computer.MiniMax(boarding);
                     //We check if the move of the AI is valid.
                     boarding.isValidMove(moveO.getRow(),moveO.getCol());
                     boarding.makeMove(moveO.getRow(), moveO.getCol(), Board.X);

                     boarding.playerScoreCount();
                     boarding.print();
                     //We print computer's move.
                     System.out.println("Computer play as X: (" + (moveO.getRow()+1)+ "," + (moveO.getCol()+1)+")");
                     //We print the score of the game.
                     System.out.println("Score: Computer = " + boarding.player1Score + "|| Player = "+ boarding.player2Score);
                }

            } else {//O Plays now
                //We check for available moves for the player. If there is none Turn is skipped
                if (boarding.availableMoves()==false){
                    System.out.println("No available moves for O. Turn skipped");
                    boarding.setLastPlayer(Board.O);
                    continue;
                }
                //Depending on the turn of the player, the user is asked to input the row and column of his/her move.
                if(choice == 2){
                    System.out.println("Player O please");
                    System.out.println("enter row: ");
                    int row = sc.nextInt() - 1;
                    System.out.println("enter column: ");
                    int column = sc.nextInt() - 1;

                    //We check if the move is valid
                    if (boarding.isValidMove(row, column)) {
                        boarding.makeMove(row, column, Board.O);
                        boarding.print();

                        boarding.playerScoreCount();
                        System.out.println("Score: Computer = " + boarding.player1Score + " || Player = "+ boarding.player2Score);
                    } else {
                        System.out.println("Not A Valid Move");
                    }
                }
                else{
                    //We call the minimax algorithm to find the best move for the AI player.
                    System.out.println("computer Plays as O");
                    Move moveO = computer.MiniMax(boarding);
                    //We check if the move of the AI is valid.
                    boarding.isValidMove(moveO.getRow(),moveO.getCol());
                    boarding.makeMove(moveO.getRow(), moveO.getCol(), Board.O);

                    boarding.playerScoreCount();
                    boarding.print();
                    //We print computer's move.
                    System.out.println("Computer play as O: (" + (moveO.getRow()+1) + "," + (moveO.getCol()+1)+")");
                    //We print the score of the game.
                    System.out.println("Score: Player = " + boarding.player1Score + "|| Computer = "+ boarding.player2Score);


                }
            }
        }
        System.out.println();
        System.out.println("Game Over");
        if (choice == 1){
            if(boarding.player1Score > boarding.player2Score){
                System.out.println("Player Wins!! :) ");
            }else if(boarding.player1Score < boarding.player2Score){
                System.out.println("Computer Wins :( ");
            }else{
                System.out.println("Draw");
            }
        }else{ //choice == 2
            if(boarding.player1Score > boarding.player2Score){
                System.out.println("Computer Wins :( ");
            }else if(boarding.player1Score < boarding.player2Score){
                System.out.println("Player Wins :) ");
            }else{
                System.out.println("Draw");
            }
        }
    }
}
