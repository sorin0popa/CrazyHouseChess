import java.util.ArrayList;

public abstract class SidePiece {
	private Piece piece;
	private PlaySide side;
	private int line, column;

	int captured;

	abstract public ArrayList<CandidateMove> get_valid_moves(Board BOARD, int options);

	public SidePiece(PlaySide side, int line, int column) {
		this.side = side;
		this.line = line;
		this.column = column;
		this.captured = 0;
	}

	public boolean correct_coodinates(int candidate_line, int candidate_column) {
		if (candidate_line < 1 || candidate_line > 8 || candidate_column < 1 || candidate_column > 8) return false;
		return true;
	}

	public void setLocation(int line, int column) {
		this.line = line;
		this.column = column;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public void setSide(PlaySide side) {
		this.side = side;
	}


	public PlaySide getSide() {
		return side;
	}

	public Piece getPiece() {
		return piece;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public char checkIfPromoted() {
		if (this.piece == Piece.PAWN) {
			return ((Pawn) this).promoted_in;
		}
		return 'X'; // daca nu e pion
	}
	public String toString()  {
	return piece + " " + line + " " + column;
	}
	public static SidePiece copy (SidePiece src) {
		return PieceFactory.getPiece(src.getPiece(), src.getSide(), src.getLine(), src.getColumn());

	}

	public abstract int getEval(int line, int column);

	public boolean nr_def_greater_nr_attack(Board Board) {
		SidePiece board[][] = Board.getBoard();

		int line = getLine();
		int column = getColumn();
		int cand_L;
		int cand_C;
		int def = 0;
		int attack = 0;


		//caz 1 : ma duc in sus
		cand_L = line + 1;
		cand_C = column;

		while (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] == null) {
				cand_L++;
				continue;
			} else if (board[cand_L][cand_C].getSide() == getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.ROOK || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'R') {
					def++;
			}
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.ROOK || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'R') {
					attack++;
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
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.ROOK || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'R') {
					def++;
				}
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.ROOK || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'R') {
					attack++;
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
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.ROOK || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'R') {
					def++;
				}
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.ROOK || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'R') {
					attack++;
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
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.ROOK || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'R') {
					def++;
				}
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.ROOK || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'R') {
					attack++;
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
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.BISHOP || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'B') {
					def++;
				}
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.BISHOP || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'B') {
					attack++;
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
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.BISHOP || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'B') {
					def++;
				}
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.BISHOP || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'B') {
					attack++;
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
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.BISHOP || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'B') {
					def++;
				}
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.BISHOP || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'B') {
					attack++;
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
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.BISHOP || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'B') {
					def++;
				}
				break;
			} else if (board[cand_L][cand_C].getSide() != getSide()) {
				if (board[cand_L][cand_C].getPiece() == Piece.QUEEN || board[cand_L][cand_C].getPiece() == Piece.BISHOP || board[cand_L][cand_C].checkIfPromoted() == 'Q' || board[cand_L][cand_C].checkIfPromoted() == 'B') {
					attack++;
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
				if (board[cand_L][cand_C].getPiece() == Piece.PAWN && board[cand_L][cand_C].checkIfPromoted() != 'R' && board[cand_L][cand_C].checkIfPromoted() != 'K') {
					{
						if (board[cand_L][cand_C].getSide() == PlaySide.BLACK && getSide() == PlaySide.WHITE)
							attack++;
						if (board[cand_L][cand_C].getSide() == PlaySide.BLACK && getSide() == PlaySide.BLACK)
							def++;
					}
				}
			}
		}

		//caz 10 : verific daca am sus-stanga un pion
		cand_L = line + 1;
		cand_C = column - 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getPiece() == Piece.PAWN
						&& board[cand_L][cand_C].checkIfPromoted() != 'R' && board[cand_L][cand_C].checkIfPromoted() != 'K') {
					{
						if (board[cand_L][cand_C].getSide() == PlaySide.BLACK && getSide() == PlaySide.WHITE)
							attack++;
						if (board[cand_L][cand_C].getSide() == PlaySide.BLACK && getSide() == PlaySide.BLACK)
							def++;
					}
				}
			}
		}

		//caz 11 : verific daca am jos-dreapta un pion
		cand_L = line - 1;
		cand_C = column + 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getPiece() == Piece.PAWN
						&& board[cand_L][cand_C].checkIfPromoted() != 'R' && board[cand_L][cand_C].checkIfPromoted() != 'K') {
					{
						if (board[cand_L][cand_C].getSide() == PlaySide.WHITE && getSide() == PlaySide.BLACK)
							attack++;
						if (board[cand_L][cand_C].getSide() == PlaySide.WHITE && getSide() == PlaySide.WHITE)
							def++;
					}
				}
			}
		}

		//caz 12 : verific daca am jos-stanga un pion
		cand_L = line - 1;
		cand_C = column - 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getPiece() == Piece.PAWN && board[cand_L][cand_C].checkIfPromoted() != 'R'
						&& board[cand_L][cand_C].checkIfPromoted() != 'K') {
					{
						if (board[cand_L][cand_C].getSide() == PlaySide.WHITE && getSide() == PlaySide.BLACK)
						{
							//System.out.println("attack");
							attack++;
						}
						if (board[cand_L][cand_C].getSide() == PlaySide.WHITE && getSide() == PlaySide.WHITE)
						{
							//System.out.println("def");
							def++;
						}
					}
				}
			}
		}

		//caz 13 : verific daca am un cal la stanga-sus
		cand_L = line + 1;
		cand_C = column - 2;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() == getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						def++;
					}
				}
				if (board[cand_L][cand_C].getSide() != getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						attack++;
					}
				}
			}
		}

		//caz 14 : verific daca am un cal la sus-stanga
		cand_L = line + 2;
		cand_C = column - 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() == getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						def++;
					}
				}
				if (board[cand_L][cand_C].getSide() != getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						attack++;
					}
				}
			}
		}

		//caz 15 : verific daca am un cal la sus-dreapta
		cand_L = line + 2;
		cand_C = column + 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
					if (board[cand_L][cand_C].getSide() == getSide()) {
						if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
							def++;
						}
					}
				if (board[cand_L][cand_C].getSide() != getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						attack++;
					}
				}
			}
		}

		//caz 16 : verific daca am un cal la dreapta-sus
		cand_L = line + 1;
		cand_C = column + 2;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
					if (board[cand_L][cand_C].getSide() == getSide()) {
						if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
							def++;
						}
					}
				if (board[cand_L][cand_C].getSide() != getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						attack++;
					}
				}
			}
		}

		//caz 17 : verific daca am un cal la dreapta-jos
		cand_L = line - 1;
		cand_C = column + 2;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() == getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						def++;
					}
				}
				if (board[cand_L][cand_C].getSide() != getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						attack++;
					}
				}
			}
		}

		//caz 18 : verific daca am un cal la jos-dreapta
		cand_L = line - 2;
		cand_C = column + 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() == getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						def++;
					}
				}
				if (board[cand_L][cand_C].getSide() != getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						attack++;
					}
				}
			}
		}

		//caz 19 : verific daca am un cal la jos-stanga
		cand_L = line - 2;
		cand_C = column - 1;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() == getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						def++;
					}
				}
				if (board[cand_L][cand_C].getSide() != getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						attack++;
					}
				}
			}
		}

		//caz 20 : verific daca am un cal la stanga-jos
		cand_L = line - 1;
		cand_C = column - 2;

		if (correct_coodinates(cand_L, cand_C)) {
			if (board[cand_L][cand_C] != null) {
				if (board[cand_L][cand_C].getSide() == getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						def++;
					}
				}
				if (board[cand_L][cand_C].getSide() != getSide()) {
					if (board[cand_L][cand_C].getPiece() == Piece.KNIGHT || board[cand_L][cand_C].checkIfPromoted() == 'K') {
						attack++;
					}
				}
			}
		}
		// verific daca este regele in preajma
		int [] dir = {-1, 0, 1};

		for (int i = 0; i < dir.length; i++) {
			for (int j = 0; j < dir.length; j++)
			{
				int x = dir[i];
				int y = dir[j];

				if (x == 0 && y == 0) {
					continue;
				}
				cand_L = line + x;
				cand_C = column + y;
				if (correct_coodinates(cand_L, cand_C) && board[cand_L][cand_C] != null) {
					if (board[cand_L][cand_C].getPiece() == Piece.KING
							&& board[cand_L][cand_C].getSide() != getSide())
					{
						attack++;
					}
				else if (correct_coodinates(cand_L, cand_C) && board[cand_L][cand_C].getPiece() == Piece.KING
							&& board[cand_L][cand_C].getSide() == getSide()) {
						def++;
					}
				}
			}
		}
		if (def > attack || attack == 0)
			return true;
		else
			return false;
	}
}