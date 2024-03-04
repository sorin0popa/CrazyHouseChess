import java.util.ArrayList;

public class Knight extends SidePiece {

	static int whiteEval[][] = {{-25,-20,-15,-15,-15,-15,-20,-25},
	{-20,-10,  0,  0,  0,  0,-20,-10},
	{-15,  0, 7, 9, 9, 7,  0,-15},
	{-15,  6, 9, 12, 12, 9,  6,-15},
	{-15,  0, 9, 12, 12, 9,  0,-15},
	{-15,  6, 7, 9, 9, 7,  6,-15},
	{-20,-10,  0,  3,  3,  0,-10,-20},
	{-25,-20,-15,-15,-15,-15,-20,-25}};
	static int [][] blackEval = {{+25,+20,+15,+15,+15,+15,+20,+25},
			{+20,+10, 0, -3, -3, 0, +10,+20},
			{+15, -6, -7, -9, -9, -7, -6,+15},
			{+15, 0, -9, -12, -12, -9, 0,+15},
			{+15, -6, -9, -12, -12, -9, -6,+15},
			{+15, 0, -7, -9, -9, -7, 0,+15},
			{+20, +10, 0, 0, 0, 0, +10,+20},
			{+25,+20,+15,+15,+15,+15,+20,+25}};
	public Knight(PlaySide side, int line, int column) {
		super(side, line, column);
		setPiece(Piece.KNIGHT);

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
		int cand_L;
		int cand_C;

		// 8 cazuri
		/*
		linie coloana
		+2 -1
		+2 +1
		+1 -2
		-1 -2
		-2 -1
		-2 +1
		+1 +2
		-1 +2
		 */
		// caz 1: sus stanga
		cand_L = line + 2;
		cand_C = column - 1;
		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
		}
		// caz 2: sus dreapta
		cand_L = line + 2;
		cand_C = column + 1;
		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
		}
		// caz 3: stanga sus
		cand_L = line + 1;
		cand_C = column - 2;
		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
		}
		// caz 4: stanga jos
		cand_L = line - 1;
		cand_C = column - 2;
		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
		}
		// caz 5: jos stanga
		cand_L = line - 2;
		cand_C = column - 1;
		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
		}
		// caz 6: jos dreapta
		cand_L = line - 2;
		cand_C = column + 1;
		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
		}
		// caz 7: dreapta sus
		cand_L = line + 1;
		cand_C = column + 2;
		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
		}
		// caz 8: dreapta jos
		cand_L = line - 1;
		cand_C = column + 2;
		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null || board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
			}
		}

		return result;
	}
	public double getPriority (SidePiece [][] board, int line, int column, int cand_L, int cand_C) {
		if (board[cand_L][cand_C] != null) { // captura
			return 0.94 * 2;
		}
		/*else if (getSide() == PlaySide.WHITE)
			return (Evaluation.whiteEval.get(getPiece()) * 1.0 / 100 + whiteEval[8 - cand_L][cand_C - 1] * 1.0 / 50);
		else return (Evaluation.blackEval.get(getPiece()) * 1.0 / 100 + blackEval[8 - cand_L][cand_C - 1] * 1.0 / 50);*/

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
