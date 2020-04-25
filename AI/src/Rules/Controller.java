package Rules;

import java.util.LinkedList;
import java.util.Iterator;

public class Controller {


  	public static LinkedList<Coordinate> getPawnMoves(Board b, int i, int j) throws UnexpectedPieceException, BoardOutOfBoundsException {
		LinkedList<Coordinate> movesP = new LinkedList<Coordinate>();
		Coordinate x;
		// Se o peão for Branco:
		if (b.getPiece(i, j) == 'P') {
			// Se a casa na diagonal esquerda tiver alguma peça:
			if (b.getPiece(i-1, j-1) != 'o') {
				x = new Coordinate(i-1, j-1);
				movesP.add(x);
			}
				// Se a casa na diagonal direita tiver alguma peça:
			if (b.getPiece(i-1, j+1) != 'o') {
				x = new Coordinate((i-1), (j+1));
				movesP.add(x);
			}
			// Se a casa a frente estiver vazia:
			if (b.getPiece(i-1, j) == 'o') {
				x = new Coordinate((i-1), (j));
				movesP.add(x);
			}
			// Se no primeiro movimento do peão, a casa na frente da frente estiver vazia:
			if (b.hasPawnMoved(i, j) == false && b.getPiece(i-2, j) == 'o') {
				x = new Coordinate((i-2), (j));
				movesP.add(x);
			}
		}
		if (b.getPiece(i, j) == 'p') {
			if (b.getPiece(i+1, j+1) != 'o') {
				x = new Coordinate((i+1), (j+1));
				movesP.add(x);
			}
	
			if (b.getPiece(i+1, j-1) != 'o' ) {
				x = new Coordinate((i+1), (j-1));
				movesP.add(x);
			}
	
			if (b.getPiece(i+1, j) == 'o') {
				x = new Coordinate((i+1), (j));
				movesP.add(x);
			}
			if (b.hasPawnMoved(i, j) == false && b.getPiece(i+2, j) == 'o') {
				x = new Coordinate((i+2), (j));
				movesP.add(x);
			}
		}
		
		return movesP;
    }

    public static LinkedList<Coordinate> getRookMoves(Board b, int i, int j) throws BoardOutOfBoundsException {
	    LinkedList<Coordinate> movesP = rookGen(b, i, j);
	    return movesP;
    }

  	public static LinkedList<Coordinate> getKnightMoves(Board b, int pos_i, int pos_j) throws Exception{
		LinkedList<Coordinate> movesK = new LinkedList<Coordinate>();
		Coordinate x;
		byte[] addingGeneralCoordinate = {-2, -1, -2, 1, 2, -1, 2, 1, -1, -2, -1, 2, 1, -2, 1, 2};
	    if(b.getPiece(pos_i, pos_j) == 'C' || b.getPiece(pos_i, pos_j) == 'c') {//if serve como uma segurança de que a peça é um cavalo
	    	for(byte i = 0; i < addingGeneralCoordinate.length; i+=2) {
    			if(((pos_i + addingGeneralCoordinate[i]) >= 0 && (pos_i + addingGeneralCoordinate[i]) <= 7) && ((pos_j + addingGeneralCoordinate[i+1]) >= 0 && (pos_j + addingGeneralCoordinate[i+1] <= 7))) {
    				x = new Coordinate((pos_i + addingGeneralCoordinate[i]), (pos_j + addingGeneralCoordinate[i+1]));
        			movesK.add(x);
    			}
    		}
	    }else {
	    	throw new UnexpectedPieceException("Argumentos dados a Controller.getKnightMoves não correspondem a um Cavalo");
	    }

		return movesK;

	}

    public static LinkedList<Coordinate> getBishopMoves(Board b, int pos_i, int pos_j) throws BoardOutOfBoundsException {
    	return bishopGen(b, pos_i, pos_j);
    }

    public static LinkedList<Coordinate> getKingMoves(Board b, int i, int j) {
    	LinkedList<Coordinate> moves = new LinkedList<Coordinate>();
    	Coordinate c;
		for (int x = -1; x<=1 && x>=-1; x++) {
			for (int y = -1; y<=1 && y>=-1; y++) {
				if (y != 0 || x!=0) {
					c = new Coordinate(i + x, j+y);
					moves.add(c);
				}
			}
		}
	
		return moves;
    }

	public static LinkedList<Coordinate> getQueenMoves(Board b, int pos_i, int pos_j) throws BoardOutOfBoundsException {
		LinkedList<Coordinate> moves = bishopGen(b, pos_i, pos_j);
		LinkedList<Coordinate> list = rookGen(b, pos_i, pos_j);
		Iterator<Coordinate> i = list.iterator();

		while (i.hasNext()){
			moves.add(i.next());
		}
		return moves;
	}
		
	private static LinkedList<Coordinate> bishopGen (Board b, int pos_i, int pos_j) throws BoardOutOfBoundsException{
		Coordinate x;
		LinkedList<Coordinate> list = new LinkedList<Coordinate>();
		for (int i = 1; (pos_i + i) < 8; i++) {
			if (pos_j + i < 8 && b.getPiece(pos_i + i, pos_j + i) == 'o') {
				x = new Coordinate(pos_i + i, pos_j + i);
				list.add(x);
			}
			else {
				break;
			}
		}
		for (int i = 1; (pos_i + i) < 8; i++) {
			if (pos_j - i >= 0 && b.getPiece(pos_i + i, pos_j - i) == 'o') {
				x = new Coordinate(pos_i + i, pos_j - i);
				list.add(x);
			}
			else {
				break;
			}
		}
		for (int i = 1; (pos_i - i) >= 0; i++) {
			if (pos_j + i < 8 && b.getPiece(pos_i - i, pos_j + i) == 'o') {
				x = new Coordinate(pos_i - i, pos_j + i);
				list.add(x);
			}
			else {
				break;
			}
		}
		for (int i = 1; (pos_i - i) >= 0; i++) {
			if ((pos_j - i) >= 0 && b.getPiece(pos_i - i, pos_j - i) == 'o') {
				x = new Coordinate(pos_i - i, pos_j - i);
				list.add(x);
			}
			else {
				break;
			}
		}
		return list;
	}

	private static LinkedList<Coordinate> rookGen (Board b, int pos_i, int pos_j) throws BoardOutOfBoundsException{
		LinkedList<Coordinate> moves = new LinkedList<Coordinate>();
		Coordinate x; 
		boolean pathBlocked = false;
		boolean pathBlocked2 = false;
		int t = 0;
		//Para uma fileira
		for (int j = 0; j < pos_j; j++) {
			if(b.getPiece(pos_i, j) != 'o') {
				pathBlocked = true;
				t = j;
			}
		}
		if (pathBlocked == true) {
			if (!(b.hasSameColor(pos_i, pos_j, pos_i, t))) {
				x = new Coordinate(pos_i, t);
				moves.add(x);
			}
			for (int j = (t+1); j < pos_j; j++) {
				x = new Coordinate(pos_i, j);
				moves.add(x);
			}
		}
		else {
			for (int j = 0; j < pos_j; j++) {
				x = new Coordinate(pos_i, j);
				moves.add(x);
			}
		}
		
		for (int j = (pos_j + 1); j < 8; j++) {
			if (b.getPiece(pos_i, j) == 'o') {
				x = new Coordinate(pos_i, j);
				moves.add(x);
			}
			else {
				if (b.hasSameColor(pos_i, pos_j, pos_i, j) == false) {
					x = new Coordinate(pos_i, j);
					moves.add(x);
				}
				break;
			}
		}
		//Para uma coluna 
		for (int i = 0; i<pos_i; i++) {
			if(b.getPiece(i, pos_j) != 'o') {
				pathBlocked2 = true;
				t = i;
			}
		}
		if(pathBlocked2 == true) {
			if (!(b.hasSameColor(pos_i, pos_j, t, pos_j))) {
				x = new Coordinate(t, pos_j);
				moves.add(x);
			}
			for (int i = (t+1); i < pos_i; i++) {
				x = new Coordinate(i, pos_j);
				moves.add(x);
			}
		}
		else {
			for (int i = 0; i < pos_i; i++) {
				x = new Coordinate(i, pos_j);
				moves.add(x);
			}
		}
		for(int i = (pos_i + 1); i<8; i++) {
			if (b.getPiece(i, pos_j) == 'o') {
				x = new Coordinate(i, pos_j);
				moves.add(x);
			}
			else {
				if(!(b.hasSameColor(pos_i, pos_j, i, pos_j))) {
					x = new Coordinate(i, pos_j);
					moves.add(x);
				}
				break;
			}
		}
		return moves;
	}  
	
	public static void uncheckedMovesWhite(Board b) throws Exception {
        for (int i = 0; i<8; i++) {
			for (int j = 0; j<8; j++) {
				if (b.getPiece(i, j) != 'o') {
					if (b.getPiece(i, j) == 'P') {
						b.setStateBoard(Controller.getPawnMoves(b, i, j), i, j);
					}

					if (b.getPiece(i, j) == 'C') {
						b.setStateBoard(Controller.getKnightMoves(b, i, j), i, j);
					}

					if (b.getPiece(i, j) == 'B') {
						b.setStateBoard(Controller.getBishopMoves(b, i, j), i, j);
					}

					if (b.getPiece(i, j) == 'R') {
						b.setStateBoard(Controller.getRookMoves(b, i, j), i, j);
					}

					if (b.getPiece(i, j) == 'Q') {
						b.setStateBoard(Controller.getQueenMoves(b, i, j), i, j);
					}

					if (b.getPiece(i, j) == 'K') {
						b.setStateBoard(Controller.getKingMoves(b, i, j), i, j);
					}
				}
				else {
					continue;
				}
			}
		}
		
	}

	public static void uncheckedMovesBlack(Board b) throws Exception {
        for (int i = 0; i<8; i++) {
			for (int j = 0; j<8; j++) {
				if (b.getPiece(i, j) != 'o') {
					if (b.getPiece(i, j) == 'p') {
						b.setStateBoard(Controller.getPawnMoves(b, i, j), i, j);
					}

					if (b.getPiece(i, j) == 'c') {
						b.setStateBoard(Controller.getKnightMoves(b, i, j), i, j);
					}

					if (b.getPiece(i, j) == 'b') {
						b.setStateBoard(Controller.getBishopMoves(b, i, j), i, j);
					}

					if (b.getPiece(i, j) == 'r') {
						b.setStateBoard(Controller.getRookMoves(b, i, j), i, j);
					}

					if (b.getPiece(i, j) == 'q') {
						b.setStateBoard(Controller.getQueenMoves(b, i, j), i, j);
					}

					if (b.getPiece(i, j) == 'k') {
						b.setStateBoard(Controller.getKingMoves(b, i, j), i, j);
					}
				}
				else {
					continue;
				}
			}
		}
		
	}
		
}

