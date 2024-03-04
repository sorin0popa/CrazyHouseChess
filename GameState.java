import java.util.ArrayList;

public class GameState {
	int en_passant;
	GameMove whiteLastMove;
	GameMove blackLastMove;
	ArrayList<GameMove> movedPieces = new ArrayList<>();
	ArrayList<GameMove> capturedPieces = new ArrayList<>();
	ArrayList<GameMove> droppedPieces = new ArrayList<>();
	double outPiecesWhiteEval;
	double outPiecesBlackEval;

	public void addMove(GameMove gameMove) {
		movedPieces.add(gameMove);
	}
	public void addCapturedPieces(GameMove gameMove) {
		capturedPieces.add(gameMove);
	}

	public void addDroppedPiece(GameMove gameMove) {
		droppedPieces.add(gameMove);
	}
}
