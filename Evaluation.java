import java.util.ArrayList;
import java.util.HashMap;

public class Evaluation {

	static HashMap <Piece, Integer> whiteEval = new HashMap<>();
	static HashMap <Piece, Integer> blackEval = new HashMap<>();

	public Evaluation () {
		whiteEval.put(Piece.PAWN, 10);
		whiteEval.put(Piece.KNIGHT, 30);
		whiteEval.put(Piece.BISHOP, 30);
		whiteEval.put(Piece.ROOK, 50);
		whiteEval.put(Piece.QUEEN, 90);
		whiteEval.put(Piece.KING, 0);

		blackEval.put(Piece.PAWN, -10);
		blackEval.put(Piece.KNIGHT, -30);
		blackEval.put(Piece.BISHOP, -30);
		blackEval.put(Piece.ROOK, -50);
		blackEval.put(Piece.QUEEN, -90);
		blackEval.put(Piece.KING, 0);
	}
	public static double getBoardEval (Board board) {
		SidePiece [][] board_pieces = board.getBoard();
		double material_eval = 0;
		double strategical_eval = 0;

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				if (board_pieces[i][j] != null) {
					if (board_pieces[i][j].getSide() == PlaySide.WHITE)
					{
						strategical_eval += board_pieces[i][j].getEval(8-i, j-1);
						if (board_pieces[i][j].getPiece() == Piece.PAWN) {
							char r = ((Pawn) board_pieces[i][j]).checkIfPromoted();
							if (r == 'Q')
								material_eval += whiteEval.get(Piece.QUEEN);
							else if (r == 'B')
								material_eval += whiteEval.get(Piece.BISHOP);
							else if (r == 'K')
								material_eval += whiteEval.get(Piece.KNIGHT);
							else if (r == 'R')
								material_eval += whiteEval.get(Piece.ROOK);
							else
								material_eval += whiteEval.get(Piece.PAWN);
						}
						else
							material_eval += whiteEval.get(board_pieces[i][j].getPiece());
					}
					else if (board_pieces[i][j].getSide() == PlaySide.BLACK)
					{
						strategical_eval += board_pieces[i][j].getEval(8-i, j-1);
						if (board_pieces[i][j].getPiece() == Piece.PAWN) {
							char r = ((Pawn) board_pieces[i][j]).checkIfPromoted();
							if (r == 'Q')
								material_eval += blackEval.get(Piece.QUEEN);
							else if (r == 'B')
								material_eval += blackEval.get(Piece.BISHOP);
							else if (r == 'K')
								material_eval += blackEval.get(Piece.KNIGHT);
							else if (r == 'R')
								material_eval += blackEval.get(Piece.ROOK);
							else
								material_eval += blackEval.get(Piece.PAWN);
						}
						else
							material_eval += blackEval.get(board_pieces[i][j].getPiece());
					}
				}
			}
		}

		return material_eval + (board.outPiecesWhiteEval + board.outPiecesBlackEval)  * 1.0 / 2 + strategical_eval * 1.0 / 100;
	}
}
