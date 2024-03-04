public class GameMove {
  SidePiece piece;
	int srcLine;
	int srcCol;
	int destLine;
	int destCol;
	int is_moved;
	boolean replacement;
	int dropped;
	char r;
	char pawnCaptured = 'Z';
	public GameMove() {
		is_moved = -1;
		replacement = false;
	}

	public GameMove(SidePiece piece, int srcLine, int srcCol, int destLine, int destCol, int is_moved) {
		// is_moved pentru tura sau rege

		this.piece = piece;
		this.srcLine = srcLine;
		this.srcCol = srcCol;
		this.destLine = destLine;
		this.destCol = destCol;
		this.is_moved = is_moved;
		this.replacement = false;
	}
	public GameMove(SidePiece piece, int srcLine, int srcCol, int destLine, int destCol) {
		this.piece = piece;
		this.srcLine = srcLine;
		this.srcCol = srcCol;
		this.destLine = destLine;
		this.destCol = destCol;
		this.is_moved = -1;
		this.replacement = false;
	}
	public GameMove(SidePiece piece, int srcLine, int srcCol, int destLine, int destCol, boolean replacement) {
		this.piece = piece;
		this.srcLine = srcLine;
		this.srcCol = srcCol;
		this.destLine = destLine;
		this.destCol = destCol;
		this.is_moved = -1;
		this.replacement = true;
	}
	public GameMove(SidePiece piece, int srcLine, int srcCol, int destLine, int destCol, boolean replacement, char r) {
		this.piece = piece;
		this.srcLine = srcLine;
		this.srcCol = srcCol;
		this.destLine = destLine;
		this.destCol = destCol;
		this.is_moved = -1;
		this.replacement = true;
		this.r = r;
	}
	public GameMove(int dropped, SidePiece piece, int srcLine, int srcCol, int destLine, int destCol) {
		// pentru dropped moves, -1 cand este dropped, e nevoie in principal in pawn
		this.piece = piece;
		this.srcLine = srcLine;
		this.srcCol = srcCol;
		this.destLine = destLine;
		this.destCol = destCol;
		this.is_moved = -1;
		this.replacement = false;
		this.dropped = dropped;
	}
	public GameMove(SidePiece piece, int destLine, int destCol) { // pt piese capturate
		this.piece = piece;
		this.srcLine = srcLine;
		this.srcCol = srcCol;
		this.destLine = destLine;
		this.destCol = destCol;
		this.is_moved = -1;
		this.replacement = replacement;
		this.replacement = false;
	}
	public String toString ()
	{
		if (this == null)
			return "null";
		else return piece + " " + srcLine + " " + srcCol + " " + destLine + " " + destCol;
	}
}
