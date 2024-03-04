import java.util.ArrayList;

public class Board {
	private SidePiece[][] board = new SidePiece[9][9];
	private ArrayList<SidePiece> onBoardWhitePieces = new ArrayList<>();
	private ArrayList<SidePiece> onBoardBlackPieces = new ArrayList<>();
	private ArrayList<SidePiece> outBoardWhitePieces = new ArrayList<>();
	private ArrayList<SidePiece> outBoardBlackPieces = new ArrayList<>();
	private GameMove whiteLastMove;
	private GameMove blackLastMove;
	private int en_passant;
	private SidePiece whiteKing;
	private SidePiece blackKing;
	static int[] center = new int[8];
	static int[] dropCenter = new int[8];
	static SidePiece DEBUGPIECE;
	double outPiecesWhiteEval;
	double outPiecesBlackEval;

	public Board() {
		center[0] = 4;
		center[1] = 5;
		dropCenter[0] = 3;
		dropCenter[1] = 4;
		dropCenter[2] = 5;
		dropCenter[3] = 6;

		en_passant = 0;
		outPiecesWhiteEval = 0.0;
		outPiecesBlackEval = 0.0;
	}

	public SidePiece[][] getBoard() {
		return board;
	}

	public GameMove getWhiteLastMove() {
		return whiteLastMove;
	}

	public GameMove getBlackLastMove() {
		return blackLastMove;
	}

	public int getEn_passant() {
		return en_passant;
	}

	public void setEn_passant(int en_passant) {
		this.en_passant = en_passant;
	}

	public King getWhiteKing() {
		return (King) whiteKing;
	}

	public King getBlackKing() {
		return (King) blackKing;
	}

	public void setWhiteLastMove(GameMove move) {
		whiteLastMove = move;
	}

	public void setBlackLastMove(GameMove move) {
		blackLastMove = move;
	}

	public void addToOutWhiteBoardPieces(SidePiece piece) {
		outBoardWhitePieces.add(piece);
	}

	public void addToOutBlackBoardPieces(SidePiece piece) {
		outBoardBlackPieces.add(piece);
	}

	public void copyBoard(Board SRC_BOARD, Board DEST_BOARD) {

		DEST_BOARD.onBoardWhitePieces.removeAll(DEST_BOARD.onBoardWhitePieces);
		DEST_BOARD.onBoardBlackPieces.removeAll(DEST_BOARD.onBoardBlackPieces);

		DEST_BOARD.outBoardWhitePieces.removeAll(DEST_BOARD.outBoardWhitePieces);
		DEST_BOARD.outBoardBlackPieces.removeAll(DEST_BOARD.outBoardBlackPieces);

		ArrayList<SidePiece> srcOUTWhitePieces = SRC_BOARD.getOutBoardEnginePieces(PlaySide.WHITE);
		ArrayList<SidePiece> srcOUTBlackPieces = SRC_BOARD.getOutBoardEnginePieces(PlaySide.BLACK);

		ArrayList<SidePiece> srcONWhitePieces = SRC_BOARD.getOnBoardEnginePieces(PlaySide.WHITE);
		ArrayList<SidePiece> srcONBlackPieces = SRC_BOARD.getOnBoardEnginePieces(PlaySide.BLACK);

		for (int i = 1; i <= 8; i++)
			for (int j = 1; j <= 8; j++)
				DEST_BOARD.getBoard()[i][j] = null;

		for (int i = 0; i < srcONWhitePieces.size(); i++) {
			SidePiece piece = srcONWhitePieces.get(i);
			DEST_BOARD.addToBoard(piece.getPiece(), piece.getSide(), piece.getLine(), piece.getColumn(), 0);
			if (piece.getPiece() == Piece.PAWN)
				((Pawn) DEST_BOARD.getBoard()[piece.getLine()][piece.getColumn()]).promoted_in = ((Pawn) piece).promoted_in;
		}

		for (int i = 0; i < srcONBlackPieces.size(); i++) {
			SidePiece piece = srcONBlackPieces.get(i);
			DEST_BOARD.addToBoard(piece.getPiece(), piece.getSide(), piece.getLine(), piece.getColumn(), 0);
			if (piece.getPiece() == Piece.PAWN)
				((Pawn) DEST_BOARD.getBoard()[piece.getLine()][piece.getColumn()]).promoted_in = ((Pawn) piece).promoted_in;
		}
		DEST_BOARD.whiteKing = DEST_BOARD.getBoard()[SRC_BOARD.whiteKing.getLine()][SRC_BOARD.whiteKing.getColumn()];
		DEST_BOARD.blackKing = DEST_BOARD.getBoard()[SRC_BOARD.blackKing.getLine()][SRC_BOARD.blackKing.getColumn()];
		if (SRC_BOARD.whiteLastMove == null)
			DEST_BOARD.whiteLastMove = null;
		else
			DEST_BOARD.whiteLastMove = new GameMove(SidePiece.copy(SRC_BOARD.whiteLastMove.piece), SRC_BOARD.whiteLastMove.srcLine,
					SRC_BOARD.whiteLastMove.srcCol, SRC_BOARD.whiteLastMove.destLine,
					SRC_BOARD.whiteLastMove.destCol, SRC_BOARD.whiteLastMove.replacement);
		if (SRC_BOARD.blackLastMove == null)
			DEST_BOARD.blackLastMove = null;
		else
			DEST_BOARD.blackLastMove = new GameMove(SidePiece.copy(SRC_BOARD.blackLastMove.piece), SRC_BOARD.blackLastMove.srcLine,
					SRC_BOARD.blackLastMove.srcCol, SRC_BOARD.blackLastMove.destLine,
					SRC_BOARD.blackLastMove.destCol, SRC_BOARD.blackLastMove.replacement);

		DEST_BOARD.en_passant = SRC_BOARD.en_passant;
		DEST_BOARD.outPiecesWhiteEval = SRC_BOARD.outPiecesWhiteEval;
		DEST_BOARD.outPiecesBlackEval = SRC_BOARD.outPiecesBlackEval;

		for (int i = 0; i < srcOUTWhitePieces.size(); i++) {
			SidePiece piece = srcOUTWhitePieces.get(i);
			DEST_BOARD.addToOutWhiteBoardPieces(PieceFactory.getPiece(piece.getPiece(), piece.getSide(), piece.getLine(), piece.getColumn()));
		}

		for (int i = 0; i < srcOUTBlackPieces.size(); i++) {
			SidePiece piece = srcOUTBlackPieces.get(i);
			DEST_BOARD.addToOutBlackBoardPieces(PieceFactory.getPiece(piece.getPiece(), piece.getSide(), piece.getLine(), piece.getColumn()));
		}
	}

	public GameState addToBoard(Piece piece, PlaySide side, int line, int column, int initialized) {
		// variabila initialized imi spune daca am replacement de piesa
		if (initialized == 1) { // piesa deja a fost initializata
			GameState gameState = new GameState();
			gameState.en_passant = en_passant;
			gameState.whiteLastMove = whiteLastMove;
			gameState.blackLastMove = blackLastMove;
			gameState.outPiecesWhiteEval = outPiecesWhiteEval;
			gameState.outPiecesBlackEval = outPiecesBlackEval;

			SidePiece foundPiece = null;

			int previous_line = 0;
			int previous_column = 0;

			if (side == PlaySide.WHITE) {
				// piesa e pentru alb => caut in piesele de pe margine pentru alb si o scot, pentru a o pune pe tabla
				for (int i = 0; i < outBoardWhitePieces.size(); i++) {
					if (outBoardWhitePieces.get(i).getPiece() == piece) {
						foundPiece = outBoardWhitePieces.get(i);
						if (foundPiece == DEBUGPIECE) {
						}
						previous_line = foundPiece.getLine();
						previous_column = foundPiece.getColumn();
						outBoardWhitePieces.remove(foundPiece);
						outPiecesWhiteEval -= Evaluation.whiteEval.get(foundPiece.getPiece());
						onBoardWhitePieces.add(foundPiece);
						foundPiece.setLocation(line, column);
						foundPiece.setSide(side);
						board[line][column] = foundPiece;
						break;
					}
				}

			} else {
				// piesa e pentru negru => caut in piesele de pe margine pentru negru si o scot, pentru a o pune pe tabla
				for (int i = 0; i < outBoardBlackPieces.size(); i++) {
					if (outBoardBlackPieces.get(i).getPiece() == piece) {
						foundPiece = outBoardBlackPieces.get(i);
						if (foundPiece == DEBUGPIECE) {
						}
						previous_line = foundPiece.getLine();
						previous_column = foundPiece.getColumn();
						outBoardBlackPieces.remove(foundPiece);

						outPiecesBlackEval -= Evaluation.blackEval.get(foundPiece.getPiece());

						onBoardBlackPieces.add(foundPiece);
						foundPiece.setLocation(line, column);
						foundPiece.setSide(side);
						board[line][column] = foundPiece;
						break;
					}
				}
			}

			if (foundPiece.getSide() == PlaySide.WHITE) {
				whiteLastMove = new GameMove(-1, foundPiece, previous_line, previous_column, line, column);
			} else {
				blackLastMove = new GameMove(-1, foundPiece, previous_line, previous_column, line, column);
			}
			en_passant = 0;

			gameState.addDroppedPiece(new GameMove(foundPiece, previous_line, previous_column, line, column));
			if (foundPiece == null)
				System.out.println("Nu s-a gasit piesa");
			return gameState;
		}

		// piesa nu a fost initializata, o pun pentru prima data pe tabla
		if (piece == Piece.QUEEN) board[line][column] = PieceFactory.getPiece(Piece.QUEEN, side, line, column);
		else if (piece == Piece.KING) board[line][column] = PieceFactory.getPiece(Piece.KING, side, line, column);
		else if (piece == Piece.ROOK) board[line][column] = PieceFactory.getPiece(Piece.ROOK, side, line, column);
		else if (piece == Piece.KNIGHT) board[line][column] = PieceFactory.getPiece(Piece.KNIGHT, side, line, column);
		else if (piece == Piece.BISHOP) board[line][column] = PieceFactory.getPiece(Piece.BISHOP, side, line, column);
		else if (piece == Piece.PAWN) board[line][column] = PieceFactory.getPiece(Piece.PAWN, side, line, column);

		if (side == PlaySide.WHITE) onBoardWhitePieces.add(board[line][column]);
		else onBoardBlackPieces.add(board[line][column]);
		return null;
	}

	public void initializeBoard() {
		addToBoard(Piece.ROOK, PlaySide.WHITE, 1, 1, 0); // a1
		addToBoard(Piece.KNIGHT, PlaySide.WHITE, 1, 2, 0); // b1
		addToBoard(Piece.BISHOP, PlaySide.WHITE, 1, 3, 0); // c1
		addToBoard(Piece.QUEEN, PlaySide.WHITE, 1, 4, 0); // d1
		addToBoard(Piece.KING, PlaySide.WHITE, 1, 5, 0); // e1
		addToBoard(Piece.BISHOP, PlaySide.WHITE, 1, 6, 0); // f1
		addToBoard(Piece.KNIGHT, PlaySide.WHITE, 1, 7, 0); // g1
		addToBoard(Piece.ROOK, PlaySide.WHITE, 1, 8, 0); // h1


		addToBoard(Piece.ROOK, PlaySide.BLACK, 8, 1, 0); // a8
		addToBoard(Piece.KNIGHT, PlaySide.BLACK, 8, 2, 0); // b8
		addToBoard(Piece.BISHOP, PlaySide.BLACK, 8, 3, 0); // c8
		addToBoard(Piece.QUEEN, PlaySide.BLACK, 8, 4, 0); // d8
		addToBoard(Piece.KING, PlaySide.BLACK, 8, 5, 0); // e8
		addToBoard(Piece.BISHOP, PlaySide.BLACK, 8, 6, 0); // f8
		addToBoard(Piece.KNIGHT, PlaySide.BLACK, 8, 7, 0); // g8
		addToBoard(Piece.ROOK, PlaySide.BLACK, 8, 8, 0); // h8


		for (int i = 1; i <= 8; i++) {
			addToBoard(Piece.PAWN, PlaySide.WHITE, 2, i, 0); // linia 2
			addToBoard(Piece.PAWN, PlaySide.BLACK, 7, i, 0); // linia 7
		}
		whiteKing = board[1][5];
		blackKing = board[8][5];

		DEBUGPIECE = board[2][5];
	}

	public void displayBoard(SidePiece[][] board) {
		for (int i = 8; i >= 1; i--) {
			for (int j = 1; j <= 8; j++) {
				if (board[i][j] == null) System.out.print("--------- ");
				else if (board[i][j].getPiece() == Piece.PAWN) {
					System.out.print("P(" + board[i][j].getLine() + "," + board[i][j].getColumn() + ")");
					if (board[i][j].getSide() == PlaySide.WHITE) System.out.print("(W)");
					else System.out.print("(B)");
					System.out.print(((Pawn) board[i][j]).promoted_in);
				} else if (board[i][j].getPiece() == Piece.KNIGHT) {
					System.out.print("k(" + board[i][j].getLine() + "," + board[i][j].getColumn() + ")");
					if (board[i][j].getSide() == PlaySide.WHITE) System.out.print("(W) ");
					else System.out.print("(B) ");
				} else if (board[i][j].getPiece() == Piece.BISHOP) {
					System.out.print("B(" + board[i][j].getLine() + "," + board[i][j].getColumn() + ")");
					if (board[i][j].getSide() == PlaySide.WHITE) System.out.print("(W) ");
					else System.out.print("(B) ");
				} else if (board[i][j].getPiece() == Piece.ROOK) {
					System.out.print("R(" + board[i][j].getLine() + "," + board[i][j].getColumn() + ")");
					if (board[i][j].getSide() == PlaySide.WHITE) System.out.print("(W) ");
					else System.out.print("(B) ");
				} else if (board[i][j].getPiece() == Piece.QUEEN) {
					System.out.print("Q(" + board[i][j].getLine() + "," + board[i][j].getColumn() + ")");
					if (board[i][j].getSide() == PlaySide.WHITE) System.out.print("(W) ");
					else System.out.print("(B) ");
				} else if (board[i][j].getPiece() == Piece.KING) {
					System.out.print("K(" + board[i][j].getLine() + "," + board[i][j].getColumn() + ")");
					if (board[i][j].getSide() == PlaySide.WHITE) System.out.print("(W) ");
					else System.out.print("(B) ");
				}
			}
			System.out.println();
		}
	}

	public void displayWhitePieces() {
		System.out.print("W pieces: on board-> ");
		for (int i = 0; i < onBoardWhitePieces.size(); i++)
			if (onBoardWhitePieces.get(i).getPiece() == Piece.PAWN) {
				System.out.print("P ");
			} else if (onBoardWhitePieces.get(i).getPiece() == Piece.KNIGHT) {
				System.out.print("k ");
			} else if (onBoardWhitePieces.get(i).getPiece() == Piece.BISHOP) {
				System.out.print("B ");
			} else if (onBoardWhitePieces.get(i).getPiece() == Piece.ROOK) {
				System.out.print("R ");
			} else if (onBoardWhitePieces.get(i).getPiece() == Piece.QUEEN) {
				System.out.print("Q ");
			} else if (onBoardWhitePieces.get(i).getPiece() == Piece.KING) {
				System.out.print("K ");
			}
		System.out.print("out board-> ");
		for (int i = 0; i < outBoardWhitePieces.size(); i++)
			if (outBoardWhitePieces.get(i).getPiece() == Piece.PAWN) {
				System.out.print("P ");
			} else if (outBoardWhitePieces.get(i).getPiece() == Piece.KNIGHT) {
				System.out.print("k ");
			} else if (outBoardWhitePieces.get(i).getPiece() == Piece.BISHOP) {
				System.out.print("B ");
			} else if (outBoardWhitePieces.get(i).getPiece() == Piece.ROOK) {
				System.out.print("R ");
			} else if (outBoardWhitePieces.get(i).getPiece() == Piece.QUEEN) {
				System.out.print("Q ");
			} else if (outBoardWhitePieces.get(i).getPiece() == Piece.KING) {
				System.out.print("K ");
			}
		System.out.println();
	}

	public void displayBlackPieces() {
		System.out.print("B pieces: on board-> ");
		for (int i = 0; i < onBoardBlackPieces.size(); i++)
			if (onBoardBlackPieces.get(i).getPiece() == Piece.PAWN) {
				System.out.print("P ");
			} else if (onBoardBlackPieces.get(i).getPiece() == Piece.KNIGHT) {
				System.out.print("k ");
			} else if (onBoardBlackPieces.get(i).getPiece() == Piece.BISHOP) {
				System.out.print("B ");
			} else if (onBoardBlackPieces.get(i).getPiece() == Piece.ROOK) {
				System.out.print("R ");
			} else if (onBoardBlackPieces.get(i).getPiece() == Piece.QUEEN) {
				System.out.print("Q ");
			} else if (onBoardBlackPieces.get(i).getPiece() == Piece.KING) {
				System.out.print("K ");
			}

		System.out.print("out board-> ");
		for (int i = 0; i < outBoardBlackPieces.size(); i++)
			if (outBoardBlackPieces.get(i).getPiece() == Piece.PAWN) {
				System.out.print("P ");
			} else if (outBoardBlackPieces.get(i).getPiece() == Piece.KNIGHT) {
				System.out.print("k ");
			} else if (outBoardBlackPieces.get(i).getPiece() == Piece.BISHOP) {
				System.out.print("B ");
			} else if (outBoardBlackPieces.get(i).getPiece() == Piece.ROOK) {
				System.out.print("R ");
			} else if (outBoardBlackPieces.get(i).getPiece() == Piece.QUEEN) {
				System.out.print("Q ");
			} else if (outBoardBlackPieces.get(i).getPiece() == Piece.KING) {
				System.out.print("K ");
			}
		System.out.println();
	}

	public ArrayList<SidePiece> getOnBoardEnginePieces(PlaySide side) {
		if (side == PlaySide.WHITE) return onBoardWhitePieces;
		else return onBoardBlackPieces;
	}

	public ArrayList<SidePiece> getOutBoardEnginePieces(PlaySide side) {
		if (side == PlaySide.WHITE) return outBoardWhitePieces;
		else return outBoardBlackPieces;
	}

	public void reverseChangeBoard(GameState gameState) {
		/*if (gameState == null)
			return*/
		;

		if (gameState.movedPieces.size() != 0) {
			setEn_passant(gameState.en_passant);

			outPiecesWhiteEval = gameState.outPiecesWhiteEval;
			outPiecesBlackEval = gameState.outPiecesBlackEval;

			GameMove gameMove = gameState.movedPieces.get(0);
			SidePiece movedPiece = gameMove.piece;
			getBoard()[gameMove.srcLine][gameMove.srcCol] = movedPiece;
			movedPiece.setLocation(gameMove.srcLine, gameMove.srcCol);
			getBoard()[gameMove.destLine][gameMove.destCol] = null;
			if (movedPiece.getSide() == PlaySide.WHITE) {
				setWhiteLastMove(gameState.whiteLastMove);
			} else {
				setBlackLastMove(gameState.blackLastMove);
			}
			if (gameMove.replacement == true && movedPiece.getPiece() == Piece.PAWN) {
				((Pawn) movedPiece).promoted_in = 'Z';
			}
			if (gameState.movedPieces.size() == 2) { // in caz de rocada, sunt 2 mutate
				GameMove gameMove2 = gameState.movedPieces.get(1);
				SidePiece movedPiece2 = gameMove2.piece;
				getBoard()[gameMove2.srcLine][gameMove2.srcCol] = movedPiece2;
				getBoard()[gameMove2.destLine][gameMove2.destCol] = null;
				movedPiece2.setLocation(gameMove2.srcLine, gameMove2.srcCol);
				((King) movedPiece).setMoved(0);
				((Rook) movedPiece2).setMoved(0);
			}

			if (gameState.capturedPieces.size() == 1) {
				GameMove capturedMove = gameState.capturedPieces.get(0);
				SidePiece capturedPiece = capturedMove.piece;
				getBoard()[capturedMove.destLine][capturedMove.destCol] = capturedPiece;
				capturedPiece.setLocation(capturedMove.destLine, capturedMove.destCol);
				if (capturedPiece.getSide() == PlaySide.BLACK) {
					capturedPiece.setSide(PlaySide.WHITE);
					getOutBoardEnginePieces(PlaySide.BLACK).remove(capturedPiece);
					getOnBoardEnginePieces(PlaySide.WHITE).add(capturedPiece);
				} else {
					capturedPiece.setSide(PlaySide.BLACK);
					getOutBoardEnginePieces(PlaySide.WHITE).remove(capturedPiece);
					getOnBoardEnginePieces(PlaySide.BLACK).add(capturedPiece);
				}
				if (capturedMove.replacement == true && movedPiece.getPiece() == Piece.PAWN) {
					//System.out.println("se transforma in " + capturedMove.r);
					((Pawn) movedPiece).promoted_in = capturedMove.r;
				}
			}
		}
	}

	public void reverseAddToBoard(GameState gameState) {
		en_passant = gameState.en_passant;

		outPiecesWhiteEval = gameState.outPiecesWhiteEval;
		outPiecesBlackEval = gameState.outPiecesBlackEval;

		GameMove dropMove = gameState.droppedPieces.get(0);
		SidePiece droppedPiece = dropMove.piece;
		getBoard()[dropMove.destLine][dropMove.destCol] = null;
		droppedPiece.setLocation(dropMove.srcLine, dropMove.srcCol);
		if (droppedPiece.getSide() == PlaySide.WHITE) {
			getOutBoardEnginePieces(PlaySide.WHITE).add(droppedPiece);
			getOnBoardEnginePieces(PlaySide.WHITE).remove(droppedPiece);
		} else {
			getOutBoardEnginePieces(PlaySide.BLACK).add(droppedPiece);
			getOnBoardEnginePieces(PlaySide.BLACK).remove(droppedPiece);
		}
		if (droppedPiece.getSide() == PlaySide.WHITE) {
			setWhiteLastMove(gameState.whiteLastMove);
		} else {
			setBlackLastMove(gameState.blackLastMove);
		}
	}

	/*
	 Modifica tabla
	*/
	public GameState changeBoard(int srcLine, int srcCol, int destLine, int destCol, char replacement) {
		GameState gameState = new GameState();
		SidePiece movingPiece = board[srcLine][srcCol];
		gameState.en_passant = en_passant;
		gameState.whiteLastMove = whiteLastMove;
		gameState.blackLastMove = blackLastMove;
		gameState.outPiecesWhiteEval = outPiecesWhiteEval;
		gameState.outPiecesBlackEval = outPiecesBlackEval;

		if (movingPiece.getSide() == PlaySide.WHITE) {
			whiteLastMove = new GameMove(movingPiece, srcLine, srcCol, destLine, destCol);
		} else {
			blackLastMove = new GameMove(movingPiece, srcLine, srcCol, destLine, destCol);
		}
		int ok = 0;
		GameMove move = new GameMove();
		if (movingPiece.getPiece() == Piece.KING) // verific daca e rocada
		{
			if (movingPiece.getSide() == PlaySide.WHITE && srcLine == 1 && srcCol == 5) {
				if (destCol - srcCol == 2) // rocada mica
				{
					// pentru rege
					board[1][5].setLocation(1, 7);
					board[1][7] = board[1][5];
					board[1][5] = null;

					gameState.addMove(new GameMove(board[1][7], 1, 5, 1, 7, 1));
					// pentru tura
					board[1][8].setLocation(1, 6);
					board[1][6] = board[1][8];
					board[1][8] = null;
					((Rook) board[1][6]).setMoved(1);
					ok = 1;
					gameState.addMove(new GameMove(board[1][6], 1, 8, 1, 6, 1));
				} else if (srcCol - destCol == 2) // rocada mare
				{
					// pentru rege
					board[1][5].setLocation(1, 3);
					board[1][3] = board[1][5];
					board[1][5] = null;

					gameState.addMove(new GameMove(board[1][3], 1, 5, 1, 3, 1));
					// pentru tura
					board[1][1].setLocation(1, 4);
					board[1][4] = board[1][1];
					board[1][1] = null;
					((Rook) board[1][4]).setMoved(1);

					ok = 1;
					gameState.addMove(new GameMove(board[1][4], 1, 1, 1, 4, 1));
				}

			} else if (movingPiece.getSide() == PlaySide.BLACK) {
				if (destCol - srcCol == 2) // rocada mica
				{
					// pentru rege
					board[8][5].setLocation(8, 7);
					board[8][7] = board[8][5];
					board[8][5] = null;
					gameState.addMove(new GameMove(board[8][7], 8, 5, 8, 7, 1));

					// pentru tura
					board[8][8].setLocation(8, 6);
					board[8][6] = board[8][8];
					board[8][8] = null;
					((Rook) board[8][6]).setMoved(1);

					ok = 1;
					gameState.addMove(new GameMove(board[8][6], 8, 8, 8, 6, 1));
				} else if (srcCol - destCol == 2) // rocada mare
				{
					// pentru rege
					board[8][5].setLocation(8, 3);
					board[8][3] = board[8][5];
					board[8][5] = null;
					gameState.addMove(new GameMove(board[8][3], 8, 5, 8, 3, 1));

					// pentru tura
					board[8][1].setLocation(8, 4);
					board[8][4] = board[8][1];
					board[8][1] = null;
					((Rook) board[8][4]).setMoved(1);
					ok = 1;
					gameState.addMove(new GameMove(board[8][4], 8, 1, 8, 4, 1));
				}
			}

			((King) movingPiece).setMoved(1);
			if (ok == 1) return gameState;
		}
		if (movingPiece.getPiece() == Piece.ROOK) {
			((Rook) movingPiece).setMoved(1);
			ok = 1;
		}

		if (movingPiece.getPiece() == Piece.PAWN && ((Pawn) movingPiece).promoted_in == 'Z') // este pion normal
		// verfic regulile speciale
		{
			if (movingPiece.getSide() == PlaySide.WHITE) {
				if (destLine == 8) {
					gameState.addMove(new GameMove(board[srcLine][srcCol], srcLine, srcCol,
							8, destCol, true));
					((Pawn) movingPiece).promoted_in = replacement;
				}

			} else if (movingPiece.getSide() == PlaySide.BLACK) {
				if (destLine == 1) {
					gameState.addMove(new GameMove(board[srcLine][srcCol], srcLine, srcCol,
							1, destCol, true));
					((Pawn) movingPiece).promoted_in = replacement;
				}
			}
			if (en_passant == 1 && board[destLine][destCol] == null) // in caz ca e un pion drop in
			{
				if (movingPiece.getSide() == PlaySide.WHITE) {

					// verific ca pionul alb, cel bun, ia prin en_passant
					if (destCol == srcCol - 1 && srcLine == 5 && board[5][destCol] != null && board[5][destCol].getPiece() == Piece.PAWN
					) {
						// se scoate de pe tabla piesa neagra si o adaug pe marginea albului

						outBoardWhitePieces.add(board[5][destCol]);

						outPiecesWhiteEval += Evaluation.whiteEval.get(board[5][destCol].getPiece());

						gameState.addCapturedPieces(new GameMove(board[5][destCol],
								5, destCol));
						board[5][destCol].setSide(PlaySide.WHITE);
						onBoardBlackPieces.remove(board[5][destCol]);
						board[5][srcCol - 1] = null; // piesa e capturata
					}
					if (destCol == srcCol + 1 && srcLine == 5 && destCol <= 8 && board[5][destCol] != null && board[5][destCol].getPiece() == Piece.PAWN
					) {
						// se scoate de pe tabla piesa neagra si o adaug pe marginea albului

						outBoardWhitePieces.add(board[5][destCol]);
						outPiecesWhiteEval += Evaluation.whiteEval.get(board[5][destCol].getPiece());

						gameState.addCapturedPieces(new GameMove(board[5][destCol],
								5, destCol));
						board[5][destCol].setSide(PlaySide.WHITE);
						onBoardBlackPieces.remove(board[5][destCol]);
						board[5][destCol] = null; // e capturat pionul prin en_passant
					}
				}
				if (movingPiece.getSide() == PlaySide.BLACK) {
					// verific ca pionul negru, cel bun ia prin en_passant
					if (destCol == srcCol - 1 && srcLine == 4 && destCol >= 1 && board[4][destCol] != null && board[4][destCol].getPiece() == Piece.PAWN
					) {
						// se scoate de pe tabla piesa alba si o adaug pe marginea negrului

						onBoardWhitePieces.remove(board[4][destCol]);
						gameState.addCapturedPieces(new GameMove(board[4][destCol],
								4, destCol));
						board[4][destCol].setSide(PlaySide.BLACK);
						outBoardBlackPieces.add(board[4][destCol]);
						outPiecesBlackEval += Evaluation.blackEval.get(board[4][destCol].getPiece());
						board[4][destCol] = null; // piesa e capturata
					}
					if (destCol == srcCol + 1 && srcLine == 4 && destCol <= 8 && board[4][destCol] != null && board[4][destCol].getPiece() == Piece.PAWN
					) {
						// se scoate de pe tabla piesa alba si o adaug pe marginea negrului
						onBoardWhitePieces.remove(board[4][destCol]);
						gameState.addCapturedPieces(new GameMove(board[4][destCol],
								4, destCol));
						board[4][destCol].setSide(PlaySide.BLACK);
						outBoardBlackPieces.add(board[4][destCol]);
						outPiecesBlackEval += Evaluation.blackEval.get(board[4][destCol].getPiece());

						board[4][destCol] = null; // e capturat pionul prin en_passant
					}
				}
			}
			if ((destLine - srcLine == 2 || srcLine - destLine == 2) && ((Pawn) movingPiece).promoted_in == 'Z') // s-au mutat doua patratele si nu promovat
			{
				en_passant = 1;
			} else {
				en_passant = 0;
			}
		} else {
			en_passant = 0;
		}

		if (board[destLine][destCol] != null) {
			if (board[destLine][destCol].getSide() == PlaySide.WHITE) {
				// piesa era a albului si devine al negrului
				// se scoate de pe tabla piesa alba si o adaug pe marginea negrului
				onBoardWhitePieces.remove(board[destLine][destCol]);
				board[destLine][destCol].setSide(PlaySide.BLACK);
				outBoardBlackPieces.add(board[destLine][destCol]);
				outPiecesBlackEval += Evaluation.blackEval.get(board[destLine][destCol].getPiece());

			} else {
				// piesa era a negrului si devine a albului
				outBoardWhitePieces.add(board[destLine][destCol]);
				outPiecesWhiteEval += Evaluation.whiteEval.get(board[destLine][destCol].getPiece());

				board[destLine][destCol].setSide(PlaySide.WHITE);
				onBoardBlackPieces.remove(board[destLine][destCol]);
			}
			if (board[destLine][destCol].getPiece() == Piece.PAWN) {
				gameState.addCapturedPieces(new GameMove(board[destLine][destCol], srcLine, srcCol, destLine, destCol,
						true, ((Pawn) board[destLine][destCol]).promoted_in));
			} else
				gameState.addCapturedPieces(new GameMove(board[destLine][destCol], srcLine, srcCol, destLine, destCol));
		}


		if (board[destLine][destCol] != null && board[destLine][destCol].getPiece() == Piece.PAWN) {
			((Pawn) board[destLine][destCol]).promoted_in = 'Z'; // pionul transformat si capturat devine pion
		}

		if (gameState.movedPieces.size() == 0)
			gameState.movedPieces.add(new GameMove(board[srcLine][srcCol], srcLine, srcCol, destLine, destCol));

		board[destLine][destCol] = movingPiece;
		movingPiece.setLocation(destLine, destCol);

		board[srcLine][srcCol] = null;
		return gameState;
	}
}
