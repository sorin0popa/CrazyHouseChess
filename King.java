import java.util.ArrayList;
import java.util.Vector;

public class King extends SidePiece {

	static int[][] whiteEval =
			{{-15, -20, -20, -25, -25, -20, -20, -15},
					{-15, -20, -20, -25, -25, -20, -20, -15},
					{-15, -20, -20, -25, -25, -20, -20, -15},
					{-15, -20, -20, -25, -25, -20, -20, -15},
					{-10, -15, -15, -20, -20, -15, -15, -10},
					{-10, -20, -20, -20, -20, -10, -10, -5},
					{5, 5, -5, -5, -5, -5, 5, 5},
					{5, 5, 15, -5, -5, 5, 15, 10}};
	static int[][] blackEval = {{-5, -5, -15, 5, 5, -5, -15, -10},
			{-5, -5, 5, 5, 5, 5, -5, -5},
			{+10, +20, +20, +20, +20, +20, +20, +10},
			{+10, +15, +15, +20, +20, +15, +15, +10},
			{+15, +20, +20, +25, +20, +20, +20, +15},
			{+15, +20, +20, +25, +25, +20, +20, +15},
			{+15, +20, +20, +25, +25, +20, +20, +15},
			{+15, +20, +20, +25, +25, +20, +20, +15}};
	private int moved = 0;

	public King(PlaySide side, int line, int column) {
		super(side, line, column);
		setPiece(Piece.KING);

	}

	public void setMoved(int moved) {
		this.moved = moved;
	}

	public boolean is_check(Board Board) {
		SidePiece board[][] = Board.getBoard();

		int line = getLine();
		int column = getColumn();
		int cand_L;
		int cand_C;

		// verificam daca regele este in sah

		//caz 1 : ma duc in sus
		cand_L = line + 1;
		cand_C = column;

		while (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null) {
				cand_L++;
				continue;
			} else if (board[cand_L][cand_C].getSide() == getSide()) {
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.ROOK || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'R') {

					return true;
				}
				break;
			}
			cand_L++;
		}

		//caz 2 : ma duc in jos
		cand_L = line - 1;
		cand_C = column;

		while (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null) {
				cand_L--;
				continue;
			} else if (board[cand_L][cand_C].getSide() == getSide()) {
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.ROOK || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'R') {
					return true;
				}
				break;
			}
			cand_L--;
		}

		//caz 3 : ma duc in dreapta
		cand_L = line;
		cand_C = column + 1;

		while (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null) {
				cand_C++;
				continue;
			} else if (board[cand_L][cand_C].getSide() == getSide()) {
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.ROOK || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'R') {
					return true;
				}
				break;
			}
			cand_C++;
		}

		//caz 4 : ma duc in stanga
		cand_L = line;
		cand_C = column - 1;

		while (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null) {
				cand_C--;
				continue;
			} else if (board[cand_L][cand_C].getSide() == getSide()) {
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.ROOK || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'R') {
					return true;
				}
				break;
			}
			cand_C--;
		}

		//caz 5 : ma duc in diagonala sus-dreapta
		cand_L = line + 1;
		cand_C = column + 1;

		while (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null) {
				cand_L++;
				cand_C++;
				continue;
			} else if (board[cand_L][cand_C].getSide() == getSide()) {
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.BISHOP || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'B') {
					return true;
				}
				break;
			}
			cand_L++;
			cand_C++;
		}

		//caz 6 : ma duc in diagonala sus-stanga
		cand_L = line + 1;
		cand_C = column - 1;

		while (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null) {
				cand_L++;
				cand_C--;
				continue;
			} else if (board[cand_L][cand_C].getSide() == getSide()) {
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.BISHOP || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'B') {
					return true;
				}
				break;
			}
			cand_L++;
			cand_C--;
		}

		//caz 7 : ma duc in diagonala jos-dreapta
		cand_L = line - 1;
		cand_C = column + 1;

		while (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null) {
				cand_L--;
				cand_C++;
				continue;
			} else if (board[cand_L][cand_C].getSide() == getSide()) {
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.BISHOP || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'B') {
					return true;
				}
				break;
			}
			cand_L--;
			cand_C++;
		}

		//caz 8 : ma duc in diagonala jos-stanga
		cand_L = line - 1;
		cand_C = column - 1;

		while (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null) {
				cand_L--;
				cand_C--;
				continue;
			} else if (board[cand_L][cand_C].getSide() == getSide()) {
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.BISHOP || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'B') {
					return true;
				}
				break;
			}
			cand_L--;
			cand_C--;
		}

		//caz 9 : verific daca am sus-dreapta un pion
		cand_L = line + 1;
		cand_C = column + 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getPiece() == Piece.PAWN && board[cand_L][cand_C].getSide() == PlaySide.BLACK && getSide() == PlaySide.WHITE && board[cand_L][cand_C].checkIfPromoted() != 'R' && board[cand_L][cand_C].checkIfPromoted() != 'K') {
					return true;
				}
			}
		}

		//caz 10 : verific daca am sus-stanga un pion
		cand_L = line + 1;
		cand_C = column - 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getPiece() == Piece.PAWN && board[cand_L][cand_C].getSide() == PlaySide.BLACK && getSide() == PlaySide.WHITE && board[cand_L][cand_C].checkIfPromoted() != 'R' && board[cand_L][cand_C].checkIfPromoted() != 'K') {
					return true;
				}
			}
		}

		//caz 11 : verific daca am jos-dreapta un pion
		cand_L = line - 1;
		cand_C = column + 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getPiece() == Piece.PAWN && board[cand_L][cand_C].getSide() == PlaySide.WHITE && getSide() == PlaySide.BLACK && board[cand_L][cand_C].checkIfPromoted() != 'R' && board[cand_L][cand_C].checkIfPromoted() != 'K') {
					return true;
				}
			}
		}

		//caz 12 : verific daca am jos-stanga un pion
		cand_L = line - 1;
		cand_C = column - 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getPiece() == Piece.PAWN && board[cand_L][cand_C].getSide() == PlaySide.WHITE && getSide() == PlaySide.BLACK && board[cand_L][cand_C].checkIfPromoted() != 'R' && board[cand_L][cand_C].checkIfPromoted() != 'K') {
					return true;
				}
			}
		}

		//caz 13 : verific daca am un cal la stanga-sus
		cand_L = line + 1;
		cand_C = column - 2;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() != getSide())
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						return true;
					}
			}
		}

		//caz 14 : verific daca am un cal la sus-stanga
		cand_L = line + 2;
		cand_C = column - 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() != getSide())
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						return true;
					}
			}
		}

		//caz 15 : verific daca am un cal la sus-dreapta
		cand_L = line + 2;
		cand_C = column + 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() != getSide())
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						return true;
					}
			}
		}

		//caz 16 : verific daca am un cal la dreapta-sus
		cand_L = line + 1;
		cand_C = column + 2;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() != getSide())
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						return true;
					}
			}
		}

		//caz 17 : verific daca am un cal la dreapta-jos
		cand_L = line - 1;
		cand_C = column + 2;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() != getSide())
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						return true;
					}
			}
		}

		//caz 18 : verific daca am un cal la jos-dreapta
		cand_L = line - 2;
		cand_C = column + 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() != getSide())
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						return true;
					}
			}
		}
		//caz 19 : verific daca am un cal la jos-stanga
		cand_L = line - 2;
		cand_C = column - 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() != getSide())
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						return true;
					}
			}
		}

		//caz 20 : verific daca am un cal la stanga-jos
		cand_L = line - 1;
		cand_C = column - 2;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() != getSide())
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						return true;
					}
			}
		}
		return false;
	}

	// functie care verifica daca 2 regi sunt prea apropiati unul de celalalt, intoarce true daca sunt indepartati
	public boolean awayFromOppositeKing(int king_L, int king_C, int oppKing_L, int oppKing_C) {
		if (king_L == oppKing_L && king_C == oppKing_C + 1) {
			return false;
		}
		if (king_L == oppKing_L && king_C == oppKing_C - 1) {
			return false;
		}
		if (king_L == oppKing_L + 1 && king_C == oppKing_C + 1) {
			return false;
		}
		if (king_L == oppKing_L + 1 && king_C == oppKing_C) {
			return false;
		}
		if (king_L == oppKing_L + 1 && king_C == oppKing_C - 1) {
			return false;
		}
		if (king_L == oppKing_L - 1 && king_C == oppKing_C + 1) {
			return false;
		}
		if (king_L == oppKing_L - 1 && king_C == oppKing_C) {
			return false;
		}
		if (king_L == oppKing_L - 1 && king_C == oppKing_C - 1) {
			return false;
		}
		return true;
	}

	public ArrayList<CandidateMove> get_valid_moves(Board BOARD, int no_use) {

		ArrayList<CandidateMove> result = new ArrayList<>();
		SidePiece[][] board = BOARD.getBoard();
		int line = getLine();
		int column = getColumn();
		if (BOARD.getBoard()[line][column] == null) return null;

		King opposite_king;
		if (getSide() == PlaySide.WHITE) {
			opposite_king = BOARD.getBlackKing();
		} else opposite_king = BOARD.getWhiteKing();

		King copyKing;
		PlaySide side = BOARD.getBoard()[line][column].getSide();

		//Board copyBOARD = new Board();
		//BOARD.copyBOARD(BOARD, BOARD);

		if (getSide() == PlaySide.WHITE) {
			copyKing = BOARD.getWhiteKing();
		} else {
			copyKing = BOARD.getBlackKing();
		}

		SidePiece copyPiece;
		int cand_L, cand_C;


		// 8 cazuri
		// caz 1: sus
		cand_L = line + 1;
		cand_C = column;
		if (correct_coodinates(cand_L, cand_C)) {
			if ((BOARD.getBoard()[cand_L][cand_C] == null || (side != BOARD.getBoard()[cand_L][cand_C].getSide())) && awayFromOppositeKing(cand_L, cand_C, opposite_king.getLine(), opposite_king.getColumn())) {

				copyKing.setLocation(cand_L, cand_C);

				if (copyKing.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing) == 1) {
					result.add(new CandidateMove
							(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
				}
				copyKing.setLocation(line, column);
			}
		}
		// caz 2: sus dreapta
		cand_L = line + 1;
		cand_C = column + 1;
		if (correct_coodinates(cand_L, cand_C)) {
			if ((BOARD.getBoard()[cand_L][cand_C] == null || (side != BOARD.getBoard()[cand_L][cand_C].getSide())) && awayFromOppositeKing(cand_L, cand_C, opposite_king.getLine(), opposite_king.getColumn())) {
				copyKing.setLocation(cand_L, cand_C);

				if (copyKing.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing) == 1) {
					result.add(new CandidateMove
							(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
				}
				copyKing.setLocation(line, column);
			}
		}
		// caz 3: dreapta
		cand_L = line;
		cand_C = column + 1;
		if (correct_coodinates(cand_L, cand_C)) {
			if ((BOARD.getBoard()[cand_L][cand_C] == null || (side != BOARD.getBoard()[cand_L][cand_C].getSide())) && awayFromOppositeKing(cand_L, cand_C, opposite_king.getLine(), opposite_king.getColumn())) {
				copyKing.setLocation(cand_L, cand_C);
				if (copyKing.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing) == 1) {
					result.add(new CandidateMove
							(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
				}
				copyKing.setLocation(line, column);
			}
		}
		// caz 4: jos dreapta
		cand_L = line - 1;
		cand_C = column + 1;
		if (correct_coodinates(cand_L, cand_C)) {
			if ((BOARD.getBoard()[cand_L][cand_C] == null || (side != BOARD.getBoard()[cand_L][cand_C].getSide())) && awayFromOppositeKing(cand_L, cand_C, opposite_king.getLine(), opposite_king.getColumn())) {
				copyKing.setLocation(cand_L, cand_C);
				if (copyKing.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing) == 1) {
					result.add(new CandidateMove
							(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
				}
				copyKing.setLocation(line, column);
			}
		}
		// caz 5: jos
		cand_L = line - 1;
		cand_C = column;
		if (correct_coodinates(cand_L, cand_C)) {
			if ((BOARD.getBoard()[cand_L][cand_C] == null || (side != BOARD.getBoard()[cand_L][cand_C].getSide())) && awayFromOppositeKing(cand_L, cand_C, opposite_king.getLine(), opposite_king.getColumn())) {
				copyKing.setLocation(cand_L, cand_C);
				if (copyKing.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing) == 1) {
					result.add(new CandidateMove
							(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
				}
				copyKing.setLocation(line, column);
			}
		}
		// caz 6: jos stanga
		cand_L = line - 1;
		cand_C = column - 1;
		if (correct_coodinates(cand_L, cand_C)) {
			if ((BOARD.getBoard()[cand_L][cand_C] == null || (side != BOARD.getBoard()[cand_L][cand_C].getSide())) && awayFromOppositeKing(cand_L, cand_C, opposite_king.getLine(), opposite_king.getColumn())) {
				copyKing.setLocation(cand_L, cand_C);
				if (copyKing.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing) == 1) {
					result.add(new CandidateMove
							(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
				}
				copyKing.setLocation(line, column);
			}
		}
		// caz 7: stanga
		cand_L = line;
		cand_C = column - 1;
		if (correct_coodinates(cand_L, cand_C)) {
			if ((BOARD.getBoard()[cand_L][cand_C] == null || (side != BOARD.getBoard()[cand_L][cand_C].getSide())) && awayFromOppositeKing(cand_L, cand_C, opposite_king.getLine(), opposite_king.getColumn())) {
				copyKing.setLocation(cand_L, cand_C);
				if (copyKing.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing) == 1) {
					result.add(new CandidateMove
							(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
				}
				copyKing.setLocation(line, column);
			}
		}
		// caz 8: sus stanga
		cand_L = line + 1;
		cand_C = column - 1;
		if (correct_coodinates(cand_L, cand_C)) {
			if ((BOARD.getBoard()[cand_L][cand_C] == null || (side != BOARD.getBoard()[cand_L][cand_C].getSide())) && awayFromOppositeKing(cand_L, cand_C, opposite_king.getLine(), opposite_king.getColumn())) {
				copyKing.setLocation(cand_L, cand_C);
				if (copyKing.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing) == 1) {
					result.add(new CandidateMove
							(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C)));
				}
				copyKing.setLocation(line, column);
			}
		}
		if (moved == 0) { // 4 cazuri de rocada
			if (getLine() == 1 && getColumn() == 5 && moved == 0 && getSide() == PlaySide.WHITE) {
				if (BOARD.getBoard()[1][8] != null &&
						BOARD.getBoard()[1][8].getPiece() == Piece.ROOK &&
						((Rook) BOARD.getBoard()[1][8]).getMoved() == 0 && BOARD.getBoard()[1][6] == null &&
						BOARD.getBoard()[1][7] == null &&
						awayFromOppositeKing(1, 7, opposite_king.getLine(), opposite_king.getColumn())
						&& is_check(BOARD) == false)                 // rocada mica white
				{

					BOARD.getBoard()[1][6] = BOARD.getBoard()[1][5];
					copyKing.setLocation(1, 6);
					// regele

					if (copyKing.is_check(BOARD) == false) {

						BOARD.getBoard()[1][7] = BOARD.getBoard()[1][6];
						copyKing.setLocation(1, 7);
						if (copyKing.is_check(BOARD) == false) {
							result.add(new CandidateMove
									(this, 1, 7, getPriority(board, line, column, 1, 7)));
						}
						BOARD.getBoard()[1][6] = BOARD.getBoard()[1][7];
						copyKing.setLocation(1, 6);
					}
					BOARD.getBoard()[1][5] = BOARD.getBoard()[1][6];
					BOARD.getBoard()[1][6] = null;
					BOARD.getBoard()[1][7] = null;
					copyKing.setLocation(1, 5);
				}
				if (BOARD.getBoard()[1][1] != null
						&& BOARD.getBoard()[1][1].getPiece() == Piece.ROOK
						&& ((Rook) BOARD.getBoard()[1][1]).getMoved() == 0
						&& BOARD.getBoard()[1][2] == null && BOARD.getBoard()[1][3] == null
						&& BOARD.getBoard()[1][4] == null
						&& awayFromOppositeKing(1, 3, opposite_king.getLine(), opposite_king.getColumn())
						&& is_check(BOARD) == false) // rocada mare white

				{
					BOARD.getBoard()[1][4] = BOARD.getBoard()[1][5];
					copyKing.setLocation(1, 4);
					if (copyKing.is_check(BOARD) == false) {

						BOARD.getBoard()[1][3] = BOARD.getBoard()[1][4];
						copyKing.setLocation(1, 3);

						if (copyKing.is_check(BOARD) == false) {
							result.add(new CandidateMove
									(this, 1, 3, getPriority(board, line, column, 1, 3)));
						}
						BOARD.getBoard()[1][4] = BOARD.getBoard()[1][3];
						copyKing.setLocation(1, 4);
					}
					BOARD.getBoard()[1][5] = BOARD.getBoard()[1][4];
					BOARD.getBoard()[1][4] = null;
					BOARD.getBoard()[1][3] = null;
					copyKing.setLocation(1, 5);
				}
			}

			if (getLine() == 8 && getColumn() == 5 && moved == 0 && getSide() == PlaySide.BLACK) {
				if (BOARD.getBoard()[8][8] != null
						&& BOARD.getBoard()[8][8].getPiece() == Piece.ROOK
						&& ((Rook) BOARD.getBoard()[8][8]).getMoved() == 0
						&& BOARD.getBoard()[8][6] == null && BOARD.getBoard()[8][7] == null
						&& awayFromOppositeKing(8, 7, opposite_king.getLine(), opposite_king.getColumn())
						&& is_check(BOARD) == false) // rocada mica black
				{
					BOARD.getBoard()[8][6] = BOARD.getBoard()[8][5];
					copyKing.setLocation(8, 6);
					if (copyKing.is_check(BOARD) == false) {
						BOARD.getBoard()[8][7] = BOARD.getBoard()[8][6];
						copyKing.setLocation(8, 7);
						if (copyKing.is_check(BOARD) == false) {
							result.add(new CandidateMove
									(this, 8, 7, getPriority(board, line, column, 8, 7)));
						}
						BOARD.getBoard()[8][6] = BOARD.getBoard()[8][7];
						copyKing.setLocation(8, 6);
					}
					BOARD.getBoard()[8][5] = BOARD.getBoard()[8][6];
					BOARD.getBoard()[8][6] = null;
					BOARD.getBoard()[8][7] = null;
					copyKing.setLocation(8, 5);
				}
				if (BOARD.getBoard()[8][1] != null && BOARD.getBoard()[8][1].getPiece() == Piece.ROOK && ((Rook) BOARD.getBoard()[8][1]).getMoved() == 0
						&& BOARD.getBoard()[8][2] == null && BOARD.getBoard()[8][3] == null
						&& BOARD.getBoard()[8][4] == null
						&& awayFromOppositeKing(8, 3, opposite_king.getLine(), opposite_king.getColumn())
						&& is_check(BOARD) == false) // rocada mare black
				{

					BOARD.getBoard()[8][4] = BOARD.getBoard()[8][5];
					copyKing.setLocation(8, 4);
					if (copyKing.is_check(BOARD) == false) {
						BOARD.getBoard()[8][3] = BOARD.getBoard()[8][4];
						copyKing.setLocation(8, 3);
						if (copyKing.is_check(BOARD) == false) {
							result.add(new CandidateMove
									(this, 8, 3, getPriority(board, line, column, 8, 3)));
						}
						BOARD.getBoard()[8][4] = BOARD.getBoard()[8][3];
						copyKing.setLocation(8, 4);
					}
					BOARD.getBoard()[8][5] = BOARD.getBoard()[8][4];
					BOARD.getBoard()[8][4] = null;
					BOARD.getBoard()[8][3] = null;
					copyKing.setLocation(8, 5);
				}
			}
		}
		return result;
	}

	/*
		Functie care modifica tabla temporar
		Nu modifica si ArrayListurile de piese
		Lasa tabla asa cum era initial
		Intoarce 1 daca nu este in sah regele
		Folosita pentru validarea mutarii pentru rege
		Folosire: mut regele sau alta piesa de culoarea regelui si verific daca e in sah
	*/
	public static int movePieceLookForCheck(Board COPY_BOARD, int line, int column, int cand_L, int cand_C, King copyKing) {
		SidePiece copyPiece;
		int res = 0;
		SidePiece[][] BOARD = COPY_BOARD.getBoard();
		copyPiece = BOARD[cand_L][cand_C];
		BOARD[cand_L][cand_C] = BOARD[line][column];
		BOARD[line][column] = null;
		if (copyKing.is_check(COPY_BOARD) == false) {
			res = 1;
		}
		BOARD[line][column] = BOARD[cand_L][cand_C];
		BOARD[cand_L][cand_C] = copyPiece;

		return res;
	}

	public double getPriority(SidePiece[][] board, int line, int column, int cand_L, int cand_C) {
		if (cand_C - column == 2 || column - cand_C == 2)
			return 0.8;
		else return 0.1;
	}

	public int getEval(int line, int column) {
		if (getSide() == PlaySide.WHITE) {
			return whiteEval[line][column];
		} else {
			return blackEval[line][column];
		}
	}

	public int piecesBesides(Board board) {
		// numarul de langa rege, de acceasi culoare
		int res = 0;
		int[] pos = {-2, -1, 0, 1, 2};
		for (int i = 0; i < pos.length; i++) {
			for (int j = 0; j < pos.length; j++) {
				int x = pos[i];
				int y = pos[i];

				if (x == 0 && y == 0)
					continue;
				int cand_L = getLine() + x;
				int cand_C = getLine() + y;

				if (correct_coodinates(cand_L, cand_C) == true
						&& board.getBoard()[cand_L][cand_C] != null && board.getBoard()[cand_L][cand_C].getSide() == getSide()) {
					res++;
				}
			}
		}
		return res;
	}
}