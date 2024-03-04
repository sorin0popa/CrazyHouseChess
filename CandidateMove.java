public class CandidateMove implements Comparable<CandidateMove> {
	SidePiece piece;
	int destLine, destCol;
	Piece dropPiece;
	char replacement;
	double priority; // prioritatea mutarii
				  // mutarile sunt sortate in ordinea prioritatii pentru a elimina ramuri din tree

	public CandidateMove(SidePiece piece, int destLine, int destCol, double priority) { // daca nu e pion
		this.piece = piece;
		this.destLine = destLine;
		this.destCol = destCol;
		dropPiece = null;
		replacement = 'Q';
		this.priority = priority;
	}

	public CandidateMove(int destLine, int destCol, Piece dropPiece, double priority) {
		this.piece = null;
		this.destLine = destLine;
		this.destCol = destCol;
		this.dropPiece = dropPiece;
		replacement = 'Q';
		this.priority = priority;
	}

	public CandidateMove(SidePiece piece, int destLine, int destCol, char replacement, double priority) { // pentru pion
		this.piece = piece;
		this.destLine = destLine;
		this.destCol = destCol;
		this.dropPiece = null;
		this.replacement = replacement;
		this.priority = priority;
	}

	public void changeCandidateMove(int srcLine, int srcCol, int destLine, int destCol, char replacement) { // pt pion
		if (piece != null) {
			this.piece.setLocation(srcLine, srcCol);
		}
		this.destLine = destLine;
		this.destCol = destCol;
		this.dropPiece = null;
		this.replacement = replacement;
	}
	public void changeCandidateMove(int srcLine, int srcCol, int destLine, int destCol) { // nu e pion
		if (piece != null) {
			this.piece.setLocation(srcLine, srcCol);
		}
		this.destLine = destLine;
		this.destCol = destCol;
		this.dropPiece = null;
	}
	@Override
	public int compareTo(CandidateMove o) {
		return Double.compare(o.priority, this.priority); // descrescator dupa ordinea prioritatilor
	}
	public String toString() {
		if (piece != null)
			return piece.getPiece() + " " + priority + " " +  destLine + " " + destCol;
		else return "Se adauga " + dropPiece + " " + destLine + " " + destCol;
	}
}
