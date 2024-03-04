public class PieceFactory {
	public static SidePiece getPiece(Piece piece, PlaySide side, int line, int column) {
		if (piece == Piece.PAWN) {
			return new Pawn(side, line, column);
		} else if (piece == Piece.BISHOP) {
			return new Bishop(side, line, column);
		} else if (piece == Piece.KNIGHT) {
			return new Knight(side, line, column);
		} else if (piece == Piece.KING) {
			return new King(side, line, column);
		} else if (piece == Piece.ROOK) {
			return new Rook(side, line, column);
		} else if (piece == Piece.QUEEN) {
			return new Queen(side, line, column);
		}
		return null;
	}
}
