package Rules;

import java.util.Iterator;
import java.util.LinkedList; 

public class Game {
    private Board board;
    // true = brancas; false = pretas.
    private boolean endOfGame = false;
    private boolean isCheckMateBlack = false;
    private boolean isCheckMateWhite = false;

    private static boolean hasBlackKingMoved = false;
    private static boolean hasWhiteKingMoved = false;
    
    private static boolean hasBlackLeftRookMoved = false;
    private static boolean hasBlackRightRookMoved = false;
    private static boolean hasWhiteLeftRookMoved = false;
    private static boolean hasWhiteRightRookMoved = false;

    private int moves = 0;
    @SuppressWarnings("unchecked")
    private LinkedList<Coordinate>[][] stateBoard =  new LinkedList[8][8]; 

    public Game(Board board) {
        this.board = board;
        for (int i = 0; i<8; i++) {
            for (int j = 0; j<8; j++) {
                stateBoard[i][j] = new LinkedList<Coordinate>();
            }
        }
    }
    public Game clone() throws CloneNotSupportedException {
        Board b = this.board.clone();
        Game game = new Game(b);
        game.endOfGame = this.endOfGame;
        game.isCheckMateBlack = this.isCheckMateBlack;
        game.isCheckMateWhite = this.isCheckMateWhite;
        game.moves = this.moves;
        for (int i= 0; i<8; i++) {
            for (int j= 0; j<8; j++) {
                game.stateBoard[i][j] = (LinkedList<Coordinate>)this.stateBoard[i][j].clone();
            }
        }
        return game;
    }

    public boolean equals (Game g) {
        if (this.board.equals(g.board)) {
            return true;
        }
        return false;
    }

    public Board getBoard() {
        return board;
    }


    public boolean hasEnded() {
        return endOfGame;
    }

    public int moveNumber() {
        return moves;
    } 
    
    public void move(int i, int j, int final_i, int final_j)
            throws IllegalMoveException, BoardOutOfBoundsException, UnexpectedPieceException {
        Coordinate c = new Coordinate(final_i, final_j);
        if(i == 7 && j == 4 && final_i == 7 && final_j == 2){
            board.changePos(i, j, c);
            board.changePos(7, 0, new Coordinate(7, 3));
        }else if(i == 7 && j == 4 && final_i == 7 && final_j == 6){
            board.changePos(i, j, c);
            board.changePos(7, 7, new Coordinate(7, 5));
        }else if(i == 0 && j == 4 && final_i == 0 && final_j == 2){
            board.changePos(i, j, c);
            board.changePos(0, 0, new Coordinate(0, 3));
        }else if(i == 0 && j == 4 && final_i == 0 && final_j == 6){
            board.changePos(i, j, c);
            board.changePos(0, 7, new Coordinate(0, 5));
        }else{
            
            if (isLegal(i, j, c) && (!board.isBlack(i,j) == board.turn || board.isWhite(i,j) == board.turn)) {
                board.changePos(i, j, c);
                board.turn = !board.turn;
                moves++;
            }
            else {
                throw new IllegalMoveException("Movimento ilegal");
            }
        }
    }

    public void allLegal() throws BoardOutOfBoundsException, UnexpectedPieceException, IllegalMoveException, CloneNotSupportedException {
		LinkedList<Coordinate>[][] list = Controller.uncheckedMoves(board);
		for (int i = 0; i<8; i++) {
			for (int j = 0; j<8; j++){
				LinkedList<Coordinate> teste = list[i][j];
				LinkedList<Coordinate> letra = (LinkedList<Coordinate>)teste.clone();
				for (Coordinate c : teste) {
					Board copy = board.clone();
                    copy.changePos(i, j, c);
                    if (board.turn == true) {
                        if (board.hasSameColor(i, j, c.getPos_i(), c.getPos_j())) {
                            letra.remove(c);
                        }
                        else if (copy.isWhiteKingInCheck()) {
                            letra.remove(c);
                        } 
                        
                    }
                    if (board.turn == false) {
                        if (board.hasSameColor(i, j, c.getPos_i(), c.getPos_j())) {
                            letra.remove(c);
                        }
                        if (copy.isBlackKingInCheck()) {
                            letra.remove(c);
                        } 
                        
                    }
				}
				list[i][j] = letra;
			}
		}
		stateBoard = list;
	}

    public boolean isLegal(int i, int j, Coordinate c) throws IllegalMoveException, BoardOutOfBoundsException, UnexpectedPieceException {
        LinkedList<Coordinate> list = stateBoard[i][j];
        for (Coordinate x : list) {
            if (x.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<Coordinate>[][] getStateBoard() {
        return stateBoard;
    }

    public void isCheckMateWhite() throws IllegalMoveException, BoardOutOfBoundsException, UnexpectedPieceException,
            CloneNotSupportedException {
        int legal = 0;        
        if (board.isWhiteKingInCheck()) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Iterator<Coordinate> x = stateBoard[i][j].iterator();
                    while (x.hasNext() && legal == 0) {
                        Board copy = board.clone();
                        Coordinate c = x.next();
                        copy.changePos(i, j, c);
                        if (!copy.isWhiteKingInCheck()) {
                            legal++;
                        }
                        
                    }

                    if (legal != 0) {
                        break;
                    }
                }

                if (legal != 0) {
                    break;
                }
            }

            if (legal == 0) {
                board.endOfGame = true;
                board.isCheckmateWhite = true;
            }
        }
        
    }

    public void isCheckMateBlack() throws IllegalMoveException, BoardOutOfBoundsException, UnexpectedPieceException, CloneNotSupportedException {
        int legal = 0;
        
        
        if (board.isBlackKingInCheck()) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Iterator<Coordinate> x = stateBoard[i][j].iterator();
                    while (x.hasNext() && legal == 0) {
                        Board copy = board.clone();
                        Coordinate c = x.next();
                        copy.changePos(i, j, c);
                        if (!copy.isBlackKingInCheck()) {
                            legal++;
                        }
                        
                    }

                    if (legal != 0) {
                        break;
                    }
                }

                if (legal != 0) {
                    break;
                }
            }

            if (legal == 0) {
                board.endOfGame = true;
                board.isCheckmateBlack = true;
            }
        }
    }
    public boolean getIsCheckMateBlack(){
        return isCheckMateBlack;
    }
    public boolean getIsCheckMateWhite(){
        return isCheckMateWhite;
    }
    public void isBlackPromotion(){
        char[][] ourBoard = board.getBoard();
        for(int i=0;i<ourBoard[7].length;i++){
            if(ourBoard[7][i] == 'p'){
                ourBoard[7][i] = 'q';
            }
        }
    }
    public void isWhitePromotion(){
        char[][] ourBoard = board.getBoard();
        for(int i=0;i<ourBoard[0].length;i++){
            if(ourBoard[0][i] == 'P'){
                ourBoard[0][i] = 'Q';
            }
        }
        
    }
    public void getHasBlackKingMoved(){
        char[][] ourBoard = board.getBoard();
        if(ourBoard[0][4] == 'o'){
            hasBlackKingMoved = true;
        }
    }
    public void getHasWhiteKingMoved(){
        char[][] ourBoard = board.getBoard();
        if(ourBoard[7][4] == 'o'){
            hasWhiteKingMoved = true;
        }
    }
    public void getHasWhiteLeftRookMoved(){
        char[][] ourBoard = board.getBoard();
        if(ourBoard[7][0] == 'o'){
            hasWhiteLeftRookMoved = true;
        }
    }
    public void getHasWhiteRightRookMoved(){
        char[][] ourBoard = board.getBoard();
        if(ourBoard[7][7] != 'T'){
            hasWhiteRightRookMoved = true;
        }
    }
    public void getHasBlackLeftRookMoved(){
        char[][] ourBoard = board.getBoard();
        if(ourBoard[0][0] != 't'){
            hasBlackLeftRookMoved = true;
        }
    }
    public void getHasBlackRightRookMoved(){
        char[][] ourBoard = board.getBoard();
        if(ourBoard[0][7] == 'o'){
            hasBlackRightRookMoved = true;
        }
    }
    public void BlacksCastling(){
        if(!hasBlackKingMoved && !hasBlackLeftRookMoved){
            stateBoard[0][4].add(new Coordinate(0, 2));
            //stateBoard[0][0].add(new Coordinate(0, 3));
        }
        if(!hasBlackKingMoved && !hasBlackRightRookMoved){
            stateBoard[0][4].add(new Coordinate(0, 6));
            //stateBoard[0][7].add(new Coordinate(0, 5));
        }
    }
    public void WhitesCastling(){
        if(!hasWhiteKingMoved && !hasWhiteLeftRookMoved){
            stateBoard[7][4].add(new Coordinate(7, 2));
            //stateBoard[7][0].add(new Coordinate(7, 3));
        }
        if(!hasWhiteKingMoved && !hasWhiteRightRookMoved){
            stateBoard[7][4].add(new Coordinate(7, 6));
            //stateBoard[7][7].add(new Coordinate(7, 5));
        }

    }
}