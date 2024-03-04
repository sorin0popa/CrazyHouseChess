import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Bot {
	/* Edit this, escaped characters (e.g newlines, quotes) are prohibited */
	private static final String BOT_NAME = "MyBot";

	/* Declare custom fields below */
	public static Board board;
	private Random random;
	public static int MINUS_INF = -(int) 1e9;
	public static int INF = (int) 1e9;
	public static Evaluation eval = new Evaluation();

	public Bot() {
		/* Initialize custom fields here */
		board = new Board();
		board.initializeBoard();
		random = new Random(System.currentTimeMillis());
	}
	/* Declare custom fields above */

	/*
		Desparte String move in 2 cifre, ex: e8 => {5, 8} Important: Intoarce coloana, apoi linia
	 */
	public static int[] parseMoveToInts(String move) {
		int[] result = new int[2];
		// se adauga 1 pentru ca tabla porneste de la indicii 1
		// daca de exemplu primesc 'e' pe prima pozitie => update la tabla pe linia 'e' - 'a' + 1 = 4 + 1 = 5
		result[0] = move.charAt(0) - 'a' + 1;
		result[1] = Integer.parseInt(move.charAt(1) + "");
		return result;
	}

	/*
	2 cifre (coloana, linia) => A String move. Ex: 5 8 => e8
	 */
	public static String parseIntsToString(int column, int line) {
		return (char) (column - 1 + 'a') + Integer.toString(line);
	}

	// returneaza formatul cerut
	public Move fromCandidateToMove(CandidateMove move) {
		Move currentMove = null;
		SidePiece piece = move.piece;

		char promoted = 'Z';
		if (piece.getPiece() == Piece.PAWN) promoted = ((Pawn) piece).promoted_in;

		int srcLine = piece.getLine();
		int srcCol = piece.getColumn();

		int x = move.destLine; // dest line
		int y = move.destCol; // dest column
		board.changeBoard(piece.getLine(), piece.getColumn(), x, y, move.replacement);
		String src = parseIntsToString(srcCol, srcLine);
		String dest = parseIntsToString(y, x);

		if (piece.getPiece() == Piece.PAWN && promoted == 'Z' && ((Pawn) piece).promoted_in != 'Z') // promotion
		{
			char new_promotion = ((Pawn) piece).promoted_in;
			if (new_promotion == 'Q') currentMove = Move.promote(src, dest, Piece.QUEEN);
			else if (new_promotion == 'K') currentMove = Move.promote(src, dest, Piece.KNIGHT);
			else if (new_promotion == 'R') currentMove = Move.promote(src, dest, Piece.ROOK);
			else if (new_promotion == 'B') currentMove = Move.promote(src, dest, Piece.BISHOP);
			return currentMove;
		} else currentMove = Move.moveTo(parseIntsToString(srcCol, srcLine), parseIntsToString(y, x));

		return currentMove;
	}


	/**
	 * Record received move (either by enemy in normal play,
	 * or by both sides in force mode) in custom structures
	 *
	 * @param move       received move
	 * @param sideToMove side to move (either PlaySide.BLACK or PlaySide.WHITE)
	 */
	public void recordMove(Move move, PlaySide sideToMove) {
		/* You might find it useful to also separately record last move in another custom field */
		if (move.getSource().toString().compareTo("Optional.empty") != 0) {
			//subtring pentru ca move.getSource().toString() e in format Optional[a1]
			int[] source = parseMoveToInts(move.getSource().toString().substring(9, 11));
			int[] dest = parseMoveToInts(move.getDestination().toString().substring(9, 11));
			int sourceColumn = source[0];
			int sourceLine = source[1];

			int destColumn = dest[0];
			int destLine = dest[1];
			SidePiece movingPiece = board.getBoard()[sourceLine][sourceColumn];
			char r = 'Z';
			if (move.getReplacement().toString().compareTo("Optional.empty") != 0) // un pion se transforma
			{
				String replacement = move.getReplacement().toString().substring(9);
				replacement = replacement.substring(0, replacement.length() - 1); // scot paranteza
				if (replacement.compareTo("QUEEN") == 0 && movingPiece.getPiece() == Piece.PAWN) {
					((Pawn) movingPiece).promoted_in = 'Q';
					r = 'Q';
				} else if (replacement.compareTo("KNIGHT") == 0 && movingPiece.getPiece() == Piece.PAWN) {
					((Pawn) movingPiece).promoted_in = 'K';
					r = 'K';
				} else if (replacement.compareTo("BISHOP") == 0 && movingPiece.getPiece() == Piece.PAWN) {
					((Pawn) movingPiece).promoted_in = 'B';
					r = 'B';
				} else if (replacement.compareTo("ROOK") == 0 && movingPiece.getPiece() == Piece.PAWN) {
					((Pawn) movingPiece).promoted_in = 'R';
					r = 'R';
				}
			}

			board.changeBoard(sourceLine, sourceColumn, destLine, destColumn, r);
		} else // am replacement
		{
			int[] dest = parseMoveToInts(move.getDestination().toString().substring(9, 11));

			int destColumn = dest[0];
			int destLine = dest[1];

			String s = move.getReplacement().toString().substring(9);
			s = s.substring(0, s.length() - 1); // scot paranteza

			Piece piece = Piece.PAWN;
			if (s.compareTo("PAWN") == 0) piece = Piece.PAWN;
			else if (s.compareTo("KNIGHT") == 0) piece = Piece.KNIGHT;
			else if (s.compareTo("BISHOP") == 0) piece = Piece.BISHOP;
			else if (s.compareTo("ROOK") == 0) piece = Piece.ROOK;
			else if (s.compareTo("QUEEN") == 0) piece = Piece.QUEEN;

			board.addToBoard(piece, sideToMove, destLine, destColumn, 1); // 1 pentru ca piesa a fost deja initializata
		}
	}

	/**
	 * Calculate and return the bot's next move
	 *
	 * @return your move
	 */
	public Move calculateNextMove() {
		/* Calculate next move for the side the engine is playing (Hint: Main.getEngineSide())
		 * Make sure to record your move in custom structures before returning.
		 *
		 * Return move that you are willing to submit
		 * Move is to be constructed via one of the factory methods defined in Move.java */

		PlaySide copyEngineSide = Main.getEngineSide();
		Move currentMove;
		CandidateMove move = null;
		ArrayList<CandidateMove> totalCandidateMoves = getAllValidMoves(board);
		if (totalCandidateMoves == null) {
			return null;
		}
		Collections.sort(totalCandidateMoves);

		double maxEval = -(int) 1e9, eval;
		boolean firstEval = false;

		Board new_board = new Board();
		new_board.copyBoard(board, new_board);

		for (int i = 0; i < totalCandidateMoves.size(); i++) {
			Main.setEngineSide(copyEngineSide);
			GameState gameState = new GameState();
			int caz = 0;
			CandidateMove candidateMove = totalCandidateMoves.get(i);
			if (candidateMove.piece != null) {
				gameState = new_board.changeBoard(candidateMove.piece.getLine(), candidateMove.piece.getColumn(),
						candidateMove.destLine, candidateMove.destCol, candidateMove.replacement);
				caz = 1;
			} else if (candidateMove.dropPiece != null) {
				gameState = new_board.addToBoard(candidateMove.dropPiece, Main.getEngineSide(), candidateMove.destLine, candidateMove.destCol, 1);
				caz = 2;
			}
			if (Main.getEngineSide() == PlaySide.BLACK)
				Main.setEngineSide(PlaySide.WHITE);
			else
				Main.setEngineSide(PlaySide.BLACK);

			if (Main.getEngineSide() == PlaySide.WHITE) {
				eval = -negamax(new_board, 4, MINUS_INF, INF, 1);
				if (firstEval == false) {
					firstEval = true;
					maxEval = eval;
					move = candidateMove;
				} else if (maxEval < eval) { // engine-ul muta cu negru, negamax maximizeaza pentru alb, iau cea mai mica valoare
					maxEval = eval;
					move = candidateMove;
				}
			} else {
				eval = -negamax(new_board, 4, MINUS_INF, INF, -1);
				if (firstEval == false) {
					firstEval = true;
					maxEval = eval;
					move = candidateMove;
				} else if (maxEval < eval) {
					maxEval = eval;
					move = candidateMove;
				}
			}
			Main.setEngineSide(copyEngineSide);
			if (caz == 1)
				new_board.reverseChangeBoard(gameState);
			else if (caz == 2)
				new_board.reverseAddToBoard(gameState);
		}

		// iau mutarea random
		// move = totalCandidateMoves.get(random.nextInt(0, totalCandidateMoves.size()));
		Main.setEngineSide(copyEngineSide);
		if (move == null) {
			return null;
		}

		if (move.piece == null) // am un drop_in
		{
			board.addToBoard(move.dropPiece, Main.getEngineSide(), move.destLine, move.destCol, 1);
			currentMove = Move.dropIn(parseIntsToString(move.destCol, move.destLine), move.dropPiece);
			return currentMove;
		}

		currentMove = fromCandidateToMove(move);
		if (currentMove != null) {
			return currentMove;
		}

		return null;
	}

	public ArrayList<CandidateMove> getAllValidMoves(Board board) {

		ArrayList<SidePiece> onBoardPieces = board.getOnBoardEnginePieces(Main.getEngineSide());
		ArrayList<SidePiece> outBoardPieces = board.getOutBoardEnginePieces(Main.getEngineSide());

		ArrayList<CandidateMove> totalCandidateMoves = new ArrayList<>();
		King copyKing = null;
		King oppositeKing = null;

		if (Main.getEngineSide() == PlaySide.WHITE) {
			copyKing = board.getWhiteKing();
			oppositeKing = board.getBlackKing();
		} else {
			copyKing = board.getBlackKing();
			oppositeKing = board.getWhiteKing();
		}
		//  realizez un drop-in
		ArrayList<CandidateMove> dropMoves = new ArrayList<>();
		ArrayList<CandidateMove> discardMoves = new ArrayList<>();

		int min_len = 4; // in caz ca am mai multe bune pe langa rege
		int len = 0;
		int[] visited = new int[5]; // pion, cal, nebun, tura, regina

		if (Main.getEngineSide() == PlaySide.WHITE) {
			for (int i = 0; i < outBoardPieces.size(); i++) {
				// nu iau de 2 acceasi piesa la drop-in
				if (outBoardPieces.get(i).getPiece() == Piece.PAWN && visited[0] == 1) {
					continue;
				} else {
					visited[0] = 1;
				}
				if (outBoardPieces.get(i).getPiece() == Piece.KNIGHT && visited[1] == 1) {
					continue;
				} else {
					visited[1] = 1;
				}
				if (outBoardPieces.get(i).getPiece() == Piece.BISHOP && visited[2] == 1) {
					continue;
				} else {
					visited[2] = 1;
				}
				if (outBoardPieces.get(i).getPiece() == Piece.ROOK && visited[3] == 1) {
					continue;
				} else {
					visited[3] = 1;
				}
				if (outBoardPieces.get(i).getPiece() == Piece.QUEEN && visited[4] == 1) {
					continue;
				} else {
					visited[4] = 1;
				}

				for (int j = 1; j <= 8; j++)
					for (int k = 1; k <= 8; k++)
						if (board.getBoard()[j][k] == null) {
							if (outBoardPieces.get(i).getPiece() == Piece.PAWN && (j == 1 || j == 8)) // nu pot sa pun pe prima si ultima linie
							{
								continue;
							}

							Piece dropPiece = outBoardPieces.get(i).getPiece();
							board.getBoard()[j][k] = outBoardPieces.get(i);
							if (copyKing.is_check(board) == true) {
								board.getBoard()[j][k] = null;
								continue;
							}
							board.getBoard()[j][k] = null;
							boolean more_def = outBoardPieces.get(i).nr_def_greater_nr_attack(board);
							int multiply;
							if (more_def == true)
								multiply = 2;
							else multiply = 1;

							if (oppositeKing.piecesBesides(board) <= 4) {
								board.getBoard()[j][k] = outBoardPieces.get(i);
								if (oppositeKing.is_check(board) == true) {
									board.getBoard()[j][k] = outBoardPieces.get(i);
									int line = outBoardPieces.get(i).getLine();
									int column = outBoardPieces.get(i).getColumn();
									board.getBoard()[j][k].setLocation(j, k);
									if (more_def == true) {
										dropMoves.add(new CandidateMove(j, k, dropPiece,
												1.0));
									} else dropMoves.add(new CandidateMove(j, k, dropPiece,
											1.0  * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 100));
									board.getBoard()[j][k].setLocation(line, column);
									board.getBoard()[j][k] = null;
								}
								else {
									board.getBoard()[j][k] = null;
									if (copyKing.piecesBesides(board) < 4) {
										if (Math.abs(j - copyKing.getLine()) <= 2 &&  Math.abs(k - copyKing.getColumn()) <= 2)
										{
											dropMoves.add(new CandidateMove(j, k, dropPiece,
													1.0  * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 50 * multiply));
										}
										else
											dropMoves.add(new CandidateMove(j, k, dropPiece,
													1.0  * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 100 * multiply));

									}
									else
										dropMoves.add(new CandidateMove(j, k, dropPiece,
												1.0  * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 100 * multiply));
								}
							} else {
							 if (copyKing.piecesBesides(board) < 4) {
								if (Math.abs(j - copyKing.getLine()) <= 2 &&  Math.abs(k - copyKing.getColumn()) <= 2)
								{
									dropMoves.add(new CandidateMove(j, k, dropPiece,
											1.0  * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 50 * multiply));
								}
								else
									dropMoves.add(new CandidateMove(j, k, dropPiece,
											1.0  * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 100 * multiply));

							}
							else
								dropMoves.add(new CandidateMove(j, k, dropPiece,
										1.0  * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 100 * multiply));
						}
						}
			}
		} else if (Main.getEngineSide() == PlaySide.BLACK) {

			for (int i = 0; i < outBoardPieces.size(); i++) {

				if (outBoardPieces.get(i).getPiece() == Piece.PAWN && visited[0] == 1) {
					continue;
				} else {
					visited[0] = 1;
				}
				if (outBoardPieces.get(i).getPiece() == Piece.KNIGHT && visited[1] == 1) {
					continue;
				} else {
					visited[1] = 1;
				}
				if (outBoardPieces.get(i).getPiece() == Piece.BISHOP && visited[2] == 1) {
					continue;
				} else {
					visited[2] = 1;
				}
				if (outBoardPieces.get(i).getPiece() == Piece.ROOK && visited[3] == 1) {
					continue;
				} else {
					visited[3] = 1;
				}
				if (outBoardPieces.get(i).getPiece() == Piece.QUEEN && visited[4] == 1) {
					continue;
				} else {
					visited[4] = 1;
				}
				for (int j = 1; j <= 8; j++)
					for (int k = 1; k <= 8; k++)
						if (board.getBoard()[j][k] == null) {
							if (outBoardPieces.get(i).getPiece() == Piece.PAWN && (j == 1 || j == 8)) // nu pot sa pun pe prima si ultima linie
								continue;
							Piece dropPiece = outBoardPieces.get(i).getPiece();
							board.getBoard()[j][k] = outBoardPieces.get(i);
							if (copyKing.is_check(board) == true) {
								board.getBoard()[j][k] = null;
								continue;
							}
							board.getBoard()[j][k] = null;
							boolean more_def = outBoardPieces.get(i).nr_def_greater_nr_attack(board);
							int multiply;
							if (more_def == true)
								multiply = 2;
							else multiply = 1;


							if (oppositeKing.piecesBesides(board) <= 4) {
								board.getBoard()[j][k] = outBoardPieces.get(i);
								if (oppositeKing.is_check(board) == true) {
									board.getBoard()[j][k] = outBoardPieces.get(i);
									int line = outBoardPieces.get(i).getLine();
									int column = outBoardPieces.get(i).getColumn();
									board.getBoard()[j][k].setLocation(j, k);
									if (more_def == true) {
										dropMoves.add(new CandidateMove(j, k, dropPiece,
												1.0));
									} else dropMoves.add(new CandidateMove(j, k, dropPiece,
											1.0 * (-1) * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 100));
									board.getBoard()[j][k].setLocation(line, column);
									board.getBoard()[j][k] = null;
								}
								else {
									board.getBoard()[j][k] = null;
									if (copyKing.piecesBesides(board) < 4) {
										if (Math.abs(j - copyKing.getLine()) <= 2 &&  Math.abs(k - copyKing.getColumn()) <= 2)
										{
											dropMoves.add(new CandidateMove(j, k, dropPiece,
													1.0  * (-1) * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 50 * multiply));
										}
										else
											dropMoves.add(new CandidateMove(j, k, dropPiece,
													1.0  * (-1) * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 100 * multiply));

									}
									else
										dropMoves.add(new CandidateMove(j, k, dropPiece,
												1.0  * (-1) * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 100 * multiply));
								}
								board.getBoard()[j][k] = null;
							} else {
								if (copyKing.piecesBesides(board) < 4) {
									if (Math.abs(j - copyKing.getLine()) <= 2 &&  Math.abs(k - copyKing.getColumn()) <= 2)
									{
										dropMoves.add(new CandidateMove(j, k, dropPiece,
												1.0  * (-1) * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 50 * multiply));
									}
									else
										dropMoves.add(new CandidateMove(j, k, dropPiece,
												1.0  * (-1) * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 100 * multiply));

								}
								else
									dropMoves.add(new CandidateMove(j, k, dropPiece,
											1.0  * (-1) * (outBoardPieces.get(i)).getEval(8 - j, k - 1) / 100 * multiply));
							}
						}
			}
		}
		len = Math.max(len, min_len);
		Collections.sort(dropMoves);

		for (int i = 0; i < dropMoves.size() && len >= 0; i++) {
			totalCandidateMoves.add(dropMoves.get(i));
			len--;
		}

		// pentru mutarile normale
		for (int i = 0; i < onBoardPieces.size(); i++) {
			SidePiece piece = onBoardPieces.get(i);
			ArrayList<CandidateMove> candidateMoves;
			if (Main.getEngineSide() == PlaySide.WHITE) {
				candidateMoves = piece.get_valid_moves(board, board.getEn_passant());
			} else {
				candidateMoves = piece.get_valid_moves(board, board.getEn_passant());
			}

			if (candidateMoves == null || candidateMoves.size() == 0) continue;

			totalCandidateMoves.addAll(candidateMoves);
		}
		if (totalCandidateMoves.size() == 0) return null;
		return totalCandidateMoves;
	}

	public double negamax(Board new_board, int depth, double alpha, double beta, int player) {
		// se incearca maximizarea pentru white
		if (depth == 0) {
			double evaluation = Evaluation.getBoardEval(new_board);
			return player * evaluation;
		}

		// IMPORTANT: The engine side is used in validating moves
		if (player == -1)
			Main.setEngineSide(PlaySide.BLACK);
		else if (player == 1)
			Main.setEngineSide(PlaySide.WHITE);

		double maxEval = -(int) 1e9;
		// mutari tabla
		ArrayList<CandidateMove> totalCandidateMoves = getAllValidMoves(new_board);
		if (totalCandidateMoves == null) {
			return -(int) 1e9;
		}
		Collections.sort(totalCandidateMoves); // sunt pozitive prioritatile

		PlaySide copyEngineSide = Main.getEngineSide();

		for (int i = 0; i < totalCandidateMoves.size(); i++) {

			Main.setEngineSide(copyEngineSide);
			GameState gameState = null;
			CandidateMove currentMove = totalCandidateMoves.get(i);
			if (currentMove.piece != null) {
				gameState = new_board.changeBoard(currentMove.piece.getLine(), currentMove.piece.getColumn(),
						currentMove.destLine, currentMove.destCol, currentMove.replacement);

				maxEval = Math.max(maxEval, -negamax(new_board, depth - 1, -beta, -alpha, -player));

				// revine la mutarea anterioara
				Main.setEngineSide(copyEngineSide);
				new_board.reverseChangeBoard(gameState);

				alpha = Math.max(alpha, maxEval);
				if (alpha >= beta) {
					break;
				}
			} else if (currentMove.dropPiece != null) {
				gameState = new_board.addToBoard(currentMove.dropPiece, Main.getEngineSide(), currentMove.destLine, currentMove.destCol, 1);
				maxEval = Math.max(maxEval, -negamax(new_board, depth - 1, -beta, -alpha, -player));
				// revine la mutarea anterioara

				Main.setEngineSide(copyEngineSide);
				new_board.reverseAddToBoard(gameState);
				alpha = Math.max(alpha, maxEval);
				if (alpha >= beta) {
					break;
				}
			}
		}
		return maxEval;
	}

	public static String getBotName() {
		return BOT_NAME;
	}
}
