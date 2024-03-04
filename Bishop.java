import java.util.ArrayList;

public class Bishop extends SidePiece {
	static int[][] whiteEval = {{-20, -10, -10, -10, -10, -10, -10, -20},
			{-10, -5, -5, -5, -5, -5, -5, -10},
			{-10, 0, 5, 10, 10, 5, 0, -10},
			{-10, 5, 5, 10, 10, 5, 5, -10},
			{-10, 0, 10, 10, 10, 10, 0, -10},
			{-10, 10, 10, 10, 10, 10, 10, -10},
			{-10, 5, 0, 0, 0, 0, 5, -10},
			{-20, -10, -10, -10, -10, -10, -10, -20}};
	static int[][] blackEval = {{+20, +10, +10, +10, +10, +10, +10, +20},
			{+10, 5, 0, 0, 0, 0, 5, +10},
			{+10, -10, -10, -10, -10, -10, -10, +10},
			{+10, 0, -10, -10, -10, -10, 0, +10},
			{+10, -5, -5, -10, -10, -5, -5, +10},
			{+10, 0, -5, -10, -10, -5, 0, +10},
			{+10, -5, -5, -5, -5, -5, -5, +10},
			{+20, +10, +10, +10, +10, +10, +10, +20}};

	public Bishop(PlaySide side, int line, int column) {
		super(side, line, column);
		setPiece(Piece.BISHOP);

	}

	public ArrayList<CandidateMove> get_valid_moves(Board BOARD, int no_use) {
		SidePiece[][] board = BOARD.getBoard();
		//Board copyBoard = new Board();
		//copyBoard.copyBoard(BOARD, copyBoard);
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

		int cand_L;
		int cand_C;

		// 4 cazuri
		// cazul 1: sus stanga

		cand_L = line + 1;
		cand_C = column - 1;

		while (cand_L <= 8 && cand_C >= 1) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
			if (board[cand_L][cand_C] != null) {
				break;
			}
			cand_L++;
			cand_C--;
		}

		// cazul 2: sus dreapta

		cand_L = line + 1;
		cand_C = column + 1;

		while (cand_L <= 8 && cand_C <= 8) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
			if (board[cand_L][cand_C] != null) {
				break;
			}
			cand_L++;
			cand_C++;
		}

		// cazul 3: jos stanga

		cand_L = line - 1;
		cand_C = column - 1;

		while (cand_L >= 1 && cand_C >= 1) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
			if (board[cand_L][cand_C] != null) {
				break;
			}
			cand_L--;
			cand_C--;
		}

		// cazul 4: jos dreapta

		cand_L = line - 1;
		cand_C = column + 1;

		while (cand_L >= 1 && cand_C <= 8) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
			if (board[cand_L][cand_C] != null) {
				break;
			}
			cand_L--;
			cand_C++;
		}
		return result;
	}

	public double getPriority(SidePiece[][] board, int line, int column, int cand_L, int cand_C) {
		if (board[cand_L][cand_C] != null) { // captura
			return 0.94 * 2;
		}
		else if (getSide() == PlaySide.BLACK) {
			if (line <= cand_L) // fata pentru negru
			{
				return 0.5;
			} else return 0.35; // spate
		} else {
			if (line >= cand_L) // fata pentru alb
			{
				return 0.5;
			} else return 0.35; // spate
		}
	}

	public int getEval(int line, int column) {
		if (getSide() == PlaySide.WHITE) {
			return whiteEval[line][column];
		} else {
			return blackEval[line][column];
		}
	}
}