import java.util.ArrayList;

public class Minimax {
	Board current; //Represents the current board
	long startTime; //Start time of the function
	Integer v; //value
	int currentDepth; 
	long stopTime = 250;
	int c = 0;
	int aggressiveness = 7;
	public Minimax(Board current){
		this.current = current;
		current.getNextStates();
		current.calcNextStates();
	}
	
	public Board abSearch(){
		
		currentDepth = 0;
		startTime = System.currentTimeMillis();
		int bestVal = Integer.MIN_VALUE;
		Board bestBoard = null;
		Board prevBestBoard = current.getRandBoard();
		ArrayList<Board> children = current.getChildren();
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		
		
		for (int cutOffDepth = 1; cutOffDepth < Integer.MAX_VALUE; cutOffDepth++){

			for (Board child: children){
				child.getNextStates();
				child.calcNextStates();
				
				v = minVal(child, alpha, beta, cutOffDepth);
				
				if (v == null){
					return prevBestBoard;
				}
				alpha = Math.max(alpha, v);
				if (v > bestVal){
					bestVal = v;
					bestBoard = child;
				}
				
			}
			prevBestBoard = bestBoard;
			
		}
		
		return prevBestBoard;
	}
	
	public Integer maxVal(Board b, int alpha, int beta, int cutOffDepth){
		currentDepth++;
	
		if (currentDepth >= cutOffDepth){
			if (b.getIsPlayerMove()){
				int numCompMoves = b.getNumMoves(false);
				int numPlayerMoves = b.getChildren().size();
				return numCompMoves - (aggressiveness*numPlayerMoves);
				
			}
			else{
				int numPlayerMoves = b.getNumMoves(true);
				int numCompMoves = b.getChildren().size();
				return numCompMoves - (aggressiveness*numPlayerMoves);
			}
			//return heuristic
		}
	
		//Calculate the elapsed time and return a random board if time runs out (Time limit is x seconds)
		long elapsed = System.currentTimeMillis() - startTime;
		if (elapsed >= stopTime){
			return null; 
		}
		ArrayList<Board> children = b.getChildren();
		
		//If the game is over, return the number of possible moves left
		if (b.gameOver()){
			if (b.getIsPlayerMove()){
				return Integer.MAX_VALUE;
			}
			else{
				return Integer.MIN_VALUE;
			}
		}
		v = Integer.MIN_VALUE;
		
		for (int i = 0; i < children.size(); i++){
			Board next = children.get(i);
			next.getNextStates();
			next.calcNextStates();
			
			//Calculate v by finding the maximum value between v and the minVal of the next board
			v = Math.max(v, minVal(next,alpha,beta,currentDepth));
			
			//Prune
			if (v >= beta){
				return v;
			}
			
			//Alpha becomes the max of alpha and v
			alpha = Math.max(alpha, v);
			
		}
		return v;
		
	}
	
	public Integer minVal(Board b, int alpha, int beta, int cutOffDepth){
		currentDepth++;
		
		long elapsedTime = System.currentTimeMillis() - startTime;
		if (elapsedTime > stopTime){
			return null;
		}
		
		//STOPS AT THIS CODE
		if (currentDepth >= cutOffDepth){
			if (b.getIsPlayerMove()){
				int numCompMoves = b.getNumMoves(false);
				int numPlayerMoves = b.getChildren().size();
				return numCompMoves - (aggressiveness*numPlayerMoves);
				
			}
			else{
				int numPlayerMoves = b.getNumMoves(true);
				int numCompMoves = b.getChildren().size();
				return numCompMoves - (aggressiveness*numPlayerMoves);
			}
		}
		
		//return utility; +Infinity if you won, -Infinity if opponent won
		if (b.gameOver()){
			if (b.getIsPlayerMove()){
				return Integer.MAX_VALUE;
			}
			else{
				return Integer.MIN_VALUE;
			}
			
		}
		
		ArrayList<Board> children = b.getChildren();
		
		v = Integer.MAX_VALUE;
		for (int i = 0; i < children.size(); i++){
			Board next = children.get(i);
			next.getNextStates();
			next.calcNextStates();
			
			
			v = Math.min(v, maxVal(next,alpha,beta,currentDepth));
			
			if (v <= alpha){
				return v;
			}
			beta = Math.min(beta,v);
		}
		
		return v;
	}
}
	