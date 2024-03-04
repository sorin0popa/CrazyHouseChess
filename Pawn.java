import java.util.ArrayList;

public class Pawn extends SidePiece {

	char promoted_in;
	static int[][] whiteEval = {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{20, 20, 20, 20, 20, 20, 20, 20},
			{5, 5, 10, 15, 15, 10, 5, 5},
			{2, 2, 5, 17, 17, 5, 2, 2},
			{0, 0, 0, 17, 17, 0, 0, 0},
			{2, -2, -5, 0, 0, -5, -2, 2},
			{2, 5, 5, -10, -10, 5, 5, 2},
			{0, 0, 0, 0, 0, 0, 0, 0}};

	static int[][] blackEval = {{0, 0, 0, 0, 0, 0, 0, 0},
			{-2, -5, -5, +10, +10, -5, -5, -2},
			{-2, +2, +5, 0, 0, +5, +2, -2},
			{0, 0, 0, -17, -17, 0, 0, 0},
			{-2, -2, -5, -17, -17, -5, -2, -2},
			{-5, -5, -10, -15, -15, -10, -5, -5},
			{-20, -20, -20, -20, -20, -20, -20, -20},
			{0, 0, 0, 0, 0, 0, 0, 0}};

	public Pawn(PlaySide side, int line, int column) {
		super(side, line, column);
		setPiece(Piece.PAWN);
		promoted_in = 'Z'; // don't modify
	}

	public ArrayList<CandidateMove> get_valid_moves(Board BOARD, int en_passant) {
		SidePiece[][] board = BOARD.getBoard();
		GameMove lastMove;
		if (Main.getEngineSide() == PlaySide.BLACK) {
			lastMove = BOARD.getWhiteLastMove();
		} else
			lastMove = BOARD.getBlackLastMove();

		int valid;
		King copyKing;
		if (getSide() == PlaySide.WHITE) {
			copyKing = BOARD.getWhiteKing();
		} else {
			copyKing = BOARD.getBlackKing();
		}
		if (promoted_in == 'Q')
			return PieceFactory.getPiece(Piece.QUEEN, getSide(), getLine(), getColumn()).get_valid_moves(BOARD, 0);
		else if (promoted_in == 'K')
			return PieceFactory.getPiece(Piece.KNIGHT, getSide(), getLine(), getColumn()).get_valid_moves(BOARD, 0);
		else if (promoted_in == 'B')
			return PieceFactory.getPiece(Piece.BISHOP, getSide(), getLine(), getColumn()).get_valid_moves(BOARD, 0);
		else if (promoted_in == 'R')
			return PieceFactory.getPiece(Piece.ROOK, getSide(), getLine(), getColumn()).get_valid_moves(BOARD, 0);
		ArrayList<CandidateMove> result = new ArrayList<>();
		int line = getLine();
		int column = getColumn();
		int cand_L;
		int cand_C;

		if (Main.getEngineSide() == PlaySide.WHITE) {

			// captura
			// caz 1: captureaza la stanga

			cand_L = line + 1;
			cand_C = column - 1;
			if (cand_L <= 8 && cand_C >= 1 && board[cand_L][cand_C] != null && board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 0)));
			}

			// caz 2: captura la dreapta
			cand_L = line + 1;
			cand_C = column + 1;
			if (cand_L <= 8 && cand_C <= 8 && board[cand_L][cand_C] != null && board[cand_L][cand_C].getSide() != getSide()) {
				// new Board().copyBoard(BOARD, BOARD);
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 0)));
			}

			// caz 3: muta 2 patratele in fata
			cand_L = line + 2;
			cand_C = column;
			if (cand_L <= 8 && cand_C >= 1 && board[cand_L][cand_C] == null && line == 2 && board[line + 1][cand_C] == null) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 0)));
			}

			// cand e pe ultima linie si se poate transforma
			if (line == 7) { // se afla pe a saptea linie, poate promova
				{
					// caz 4:  captura la dreapta negrului
					cand_L = line + 1;
					cand_C = column - 1;
					if (cand_C >= 1 && board[cand_L][cand_C] != null && board[cand_L][cand_C].getSide() != getSide()) {
						valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
						if (valid == 1) {
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
						}
					}

					// caz 5: captura la stanga negrului
					cand_L = line + 1;
					cand_C = column + 1;
					if (cand_C <= 8 && board[cand_L][cand_C] != null && board[cand_L][cand_C].getSide() != getSide()) {
						valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
						if (valid == 1) {
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
						}
					}


					// caz 6: muta un patratel in fata cu promovare
					cand_L = line + 1;
					cand_C = column;
					if (board[cand_L][cand_C] == null) {
						valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
						if (valid == 1) {
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
						}
					}
				}
			}
			// en_passant + in fata
			// caz 7: muta un patratel in fata
			cand_L = line + 1;
			cand_C = column;
			if (cand_L <= 7) {
				if (board[cand_L][cand_C] == null) {
					valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
					if (valid == 1) {
						result.add(new CandidateMove
								(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 0)));
					}
				}

				if (en_passant == 1 && promoted_in == 'Z') {
					int colLastMove = lastMove.destCol;
					int lineLastMove = lastMove.destLine;

					if (board[lineLastMove][colLastMove].getPiece() != Piece.PAWN) return result;

					if (line != lineLastMove || (column != colLastMove - 1 && column != colLastMove + 1))
						return result; // nu iau pionul cel bun

					// caz 8: en passant (stanga + dreapta)
					if (line == 5 && colLastMove <= 8 && colLastMove >= 1 && board[5][colLastMove] != null && board[5][colLastMove].getPiece() == Piece.PAWN) {
						SidePiece copyPiece = BOARD.getBoard()[5][colLastMove];
						BOARD.getBoard()[5][colLastMove] = null;
						valid = King.movePieceLookForCheck(BOARD, line, 5, colLastMove, cand_C, copyKing);
						if (valid == 1) result.add(new CandidateMove
								(this, cand_L, cand_C, getPriority(board, 6, colLastMove, cand_L, cand_C, 1)));
						BOARD.getBoard()[5][colLastMove] = copyPiece;
					}
				}
			}

		} else if (Main.getEngineSide() == PlaySide.BLACK) {
			// caz 1: captura la dreapta negrului
			cand_L = line - 1;
			cand_C = column - 1;
			if (cand_L >= 2 && cand_C >= 1 && board[cand_L][cand_C] != null && board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 0)));
			}
			// caz 2: captura la stanga negrului
			cand_L = line - 1;
			cand_C = column + 1;
			if (cand_L >= 2 && cand_C <= 8 && board[cand_L][cand_C] != null && board[cand_L][cand_C].getSide() != getSide()) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 0)));
			}
			// caz 3: muta 2 patratele
			cand_L = line - 2;
			cand_C = column;
			if (cand_L >= 1 && board[cand_L][cand_C] == null && line == 7 && board[line - 1][cand_C] == null) {
				valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
				if (valid == 1) result.add(new CandidateMove
						(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 0)));
			}
			// cand e pe ultima linie si se poate transforma
			if (line == 2) { // se afla pe a doua linie, poate promova
				{
					// caz 4: captura la dreapta negrului
					cand_L = line - 1;
					cand_C = column - 1;
					if (cand_C >= 1 && board[cand_L][cand_C] != null && board[cand_L][cand_C].getSide() != getSide()) {
						valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
						if (valid == 1) {
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
						}
					}
					// caz 5: captura la stanga negrului
					cand_L = line - 1;
					cand_C = column + 1;
					if (cand_C <= 8 && board[cand_L][cand_C] != null && board[cand_L][cand_C].getSide() != getSide()) {
						valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
						if (valid == 1) {
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
							result.add(new CandidateMove
									(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
						}
					}
				}
				// caz 6: muta un patratel in fata cu promovare
				cand_L = line - 1;
				cand_C = column;
				if (board[cand_L][cand_C] == null) {
					valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
					if (valid == 1) {
						result.add(new CandidateMove
								(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
						result.add(new CandidateMove
								(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
						result.add(new CandidateMove
								(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
						result.add(new CandidateMove
								(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 2)));
					}
				}
			}
			// en_passant + in fata
			// caz 7: muta un patratel in fata

			cand_L = line - 1;
			cand_C = column;
			if (cand_L >= 2) {
				if (board[cand_L][cand_C] == null) {
					valid = King.movePieceLookForCheck(BOARD, line, column, cand_L, cand_C, copyKing);
					if (valid == 1) {
						result.add(new CandidateMove
								(this, cand_L, cand_C, getPriority(board, line, column, cand_L, cand_C, 0)));
					}
				}

				if (en_passant == 1 && promoted_in == 'Z') {
					int colLastMove = lastMove.destCol;
					int lineLastMove = lastMove.destLine;

					if (board[lineLastMove][colLastMove].getPiece() != Piece.PAWN) return result;

					if (line != lineLastMove || (column != colLastMove - 1 && column != colLastMove + 1))
						return result; // nu iau pionul cel bun

					// caz 8: en passant (stanga sau dreapta, in functie de ultima mutare a albului)
					if (line == 4 && colLastMove <= 8 && colLastMove >= 1 && board[4][colLastMove] != null && board[4][colLastMove].getPiece() == Piece.PAWN) {

						SidePiece copyPiece = BOARD.getBoard()[4][colLastMove];
						BOARD.getBoard()[4][colLastMove] = null;
						valid = King.movePieceLookForCheck(BOARD, 4, colLastMove, 3, colLastMove, copyKing);
						if (valid == 1) result.add(new CandidateMove
								(this, cand_L, cand_C, getPriority(board, 3, colLastMove, cand_L, cand_C, 1)));
						BOARD.getBoard()[4][colLastMove] = copyPiece;
					}
				}
			}
		}
		return result;
	}

	public double getPriority(SidePiece[][] board, int line, int column, int cand_L, int cand_C, int special) {
		// special = 0, mutare normala
		// special = 1, en_passant
		// special = 2, mutare normala
		if (promoted_in != 'Z') {
			return 0.80 * 2;
		}
		if (special == 2) {
			if (board[cand_L][cand_C] != null) {
				return 0.99;
			} else return 0.98;
		}

		if (board[cand_L][cand_C] != null) { // captura
			return 0.95;
		}
		if (special == 1) { // en_passant
			return 0.9;
		}
		int center_L = 0;
		int center_C = 0;
		for (int i = 0; i < Board.center.length; i++) {
			if (cand_L == Board.center[i]) {
				center_L = 1;
			}
			if (cand_C == Board.center[i]) {
				center_C = 1;
			}
		}
		if (center_L == 1 && center_C == 1) {
			//System.out.println("[PAWN] is in center!");
			return 0.5;
		} else if (center_L == 1 || center_C == 1) {
			return 0.48;
		}

		if (getSide() == PlaySide.BLACK) {
			if (line - cand_L == 2) // 2 patratele pentru negru
			{
				return 0.47;
			} else return 0.4; // un patratel
		} else {
			if (cand_L - 2 == 2) // doua patratele pentru alb
			{
				return 0.47;
			} else return 0.4; // un patratel
		}
	}

	public static SidePiece copy(SidePiece src) {
		Pawn res = (Pawn) PieceFactory.getPiece(src.getPiece(), src.getSide(), src.getLine(), src.getColumn());
		res.promoted_in = res.promoted_in;
		return res;
	}

	public int getEval(int line, int column) {
		if (getSide() == PlaySide.WHITE) {
			return whiteEval[line][column];
		} else {
			return blackEval[line][column];
		}
	}
}

