import java.util.ArrayList;

public class Rook extends SidePiece {
	private int moved;
	static int [][] whiteEval =  {{ 0,  0,  0,  0,  0,  0,  0,  0},
			{ 5, 10, 10, 10, 10, 10, 10,  5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{ 0,  0,  4,  5,  5,  4,  0,  0}};
	static int [][] blackEval = {{0, 0, -4, -5, -5, -5, -4, 0},
			{+5, 0, 0, 0, 0, 0, 0, +5},
			{+5, 0, 0, 0, 0, 0, 0, +5},
			{+5, 0, 0, 0, 0, 0, 0, +5},
			{+5, 0, 0, 0, 0, 0, 0, +5},
			{+5, 0, 0, 0, 0, 0, 0, +5},
			{-5, -10, -10, -10, -10, -10, -10, -5},
			{0, 0, 0, 0, 0, 0, 0, 0}};
	public Rook(PlaySide side, int line, int column) {
		super(side, line, column);
		setPiece(Piece.ROOK);
	}

	public void setMoved(int moved) {
		this.moved = moved;
	}

	public int getMoved() {
		return moved;
	}

	public ArrayList<CandidateMove> get_valid_moves(Board BOARD, int no_use) {
		SidePiece[][] board = BOARD.getBoard();
		int valid;
		King copyKing;
		if (getSide() == PlaySide.WHITE) {
			copyKing = BOARD.getWhiteKing();
		} else {
			copyKing = BOARD.getBlackKing();
		}
		ArrayList<CandidateMove> result = new ArrayList<>();
		int line = getLine();
		int column = getColumn();
		int cand_L, cand_C;

		// cele 4 cazuri de la tura
		// caz 1 sus
		cand_L = line + 1;
		cand_C = column;
		while (cand_L <= 8) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
			if (board[cand_L][cand_C] != null) {
				break;
			}
			cand_L++;
		}

		// caz 2 dreapta
		cand_L = line;
		cand_C = column + 1;
		while (cand_C <= 8) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
			if (board[cand_L][cand_C] != null) {
				break;
			}
			cand_C++;
		}

		// caz 3 jos
		cand_L = line - 1;
		cand_C = column;
		while (cand_L >= 1) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
			if (board[cand_L][cand_C] != null) {
				break;
			}
			cand_L--;
		}

		// caz 4 stanga
		cand_L = line;
		cand_C = column - 1;
		while (cand_C >= 1) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
			if (board[cand_L][cand_C] != null) {
				break;
			}
			cand_C--;
		}

		return result;
	}

	public double getPriority (SidePiece [][] board, int line, int column, int cand_L, int cand_C) {
		if (board[cand_L][cand_C] != null) { // captura
			return 0.92 * 2;
		}
		/*else if (getSide() == PlaySide.WHITE)
			return (Evaluation.whiteEval.get(getPiece()) * 1.0 / 100 + whiteEval[8 - cand_L][cand_C - 1] * 1.0 / 50);
		else return (Evaluation.blackEval.get(getPiece()) * 1.0 / 100 + blackEval[8 - cand_L][cand_C - 1] * 1.0 / 50);
*/
			else if (getSide() == PlaySide.BLACK)
		{
			if(line <= cand_L) // fata pentru negru
			{
				return 0.5;
			}
			else return 0.35; // spate
		}
		else {
			if(line >= cand_L) // fata pentru alb
			{
				return 0.5;
			}
			else return 0.35; // spate
		}
	}
	public int getEval (int line, int column) {
		if (getSide() == PlaySide.WHITE)
		{
			return whiteEval[line][column];
		}
		else
		{
			return blackEval[line][column];
		}
	}
}