import java.util.ArrayList;
public class Board {
	String[][] board = new String[8][8];
	int playerX;
	int playerY;
	
	int compX;
	int compY;
	
	boolean playerMove;
	
	int heuristic;
	
	ArrayList<Board> nextStates = new ArrayList<>();
	
	ArrayList<Point> pMoves = new ArrayList<>();
	
	public Board(boolean playerMove){
		this.playerMove = playerMove;
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[0].length; j++){
				board[i][j] = "-";
			}
		}
		board[0][0] = "X";
		compX = 0;
		compY = 0;
		
		board[7][7] = "O";
		playerX = 7;
		playerY = 7;
		
		
		heuristic = pMoves.size();
		
	}
	
	public Board(String[][] b, boolean playerMove){
		this.playerMove = playerMove;
		for (int i = 0; i < b.length; i++){
			for (int j = 0; j < b[0].length; j++){
				if (b[i][j].equals("O")){
					playerX = i;
					playerY = j;
				}
				else if (b[i][j].equals("X")){
					compX = i;
					compY = j;
				}
				board[i][j] = b[i][j];
			}
		}
		
	}
	
	public void print(){
		System.out.println("  1 2 3 4 5 6 7 8");
		for (int i = 0; i < board.length; i++){
			System.out.print((char)(i+65) + " ");
			for (int j = 0; j < board.length; j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void compMove(String move){
		int x = (int)(move.charAt(0)-65);	
		int y = Character.getNumericValue(move.charAt(1)) - 1;
		
		board[compX][compY] = "#";
		board[x][y] = "X";
		
		compX = x;
		compY = y;
		
	}
	
	
	
	public void playerMove(String move){
		int x = (int)(move.charAt(0)-65);	
		int y = Character.getNumericValue(move.charAt(1)) - 1;
		
		board[playerX][playerY] = "#";
		board[x][y] = "O";
			
		playerX = x;
		playerY = y;
	
		
	}
	
	public void calcNextStates(){
		for (int i = 0; i < pMoves.size(); i++){
			int x = pMoves.get(i).getX();
			int y = pMoves.get(i).getY();
			Board b;
			String[][] b2 = copyBoard();
			if (playerMove){
				b2[playerX][playerY] = "#";
				b2[x][y] = "O";
				b = new Board(b2,false);
			}
			else{
				b2[compX][compY] = "#";
				b2[x][y] = "X";
				b = new Board(b2,true);
			}
			
			nextStates.add(b);
		}
	}
	

	
	public String[][] getBoard(){
		return board;
	}
	
	public boolean gameOver(){
		if (pMoves.isEmpty()){
			return true;
		}
		return false;
	}
	
	public void getNextStates(){
		int x;
		int y;
		if (playerMove){
			x = playerX;
			y = playerY;
			
		}
		else{
			x = compX;
			y = compY;
		}
		
		
		
		//left moves
		for (int i = y-1; i >= 0; i--){
			if (!isValidMove(x,i)){
				break;
			}
			pMoves.add(new Point(x,i));
		}

		
		//right moves
		for (int i = y+1; i <= 7; i++){
			if (!isValidMove(x,i)){
				break;
			}
			pMoves.add(new Point(x,i));
		}

		
		//up moves
		for (int i = x-1; i >= 0; i--){
			if (!isValidMove(i,y)){
				break;
			}
			pMoves.add(new Point(i,y));
		}

		
		//down moves
		for (int i = x+1; i <= 7; i++){
			if (!isValidMove(i,y)){
				break;
			}
			pMoves.add(new Point(i,y));
		}

		
		//diagonal up-right
		int x2 = x-1;
		int y2 = y+1;
		while (x2 >= 0 && y2 <= 7){
			if (!isValidMove(x2,y2)){
				break;
			}
			pMoves.add(new Point(x2,y2));
			x2--;
			y2++;
		}
	
		//diagonal up-left
		x2 = x-1;
		y2 = y-1;
		while (x2 >= 0 && y2 >=0){
			if (!isValidMove(x2,y2)){
				break;
			}
			pMoves.add(new Point(x2,y2));
			x2--;
			y2--;
		}

		
		//diagonal down-left
		x2 = x+1;
		y2 = y-1;
		while (x2 <= 7 && y2 >= 0){
			if (!isValidMove(x2,y2)){
				break;
			}
			pMoves.add(new Point(x2,y2));
			x2++;
			y2--;
		}
		
		
		//diagonal down-right
		x2 = x+1;
		y2 = y+1;
		while (x2 <= 7 && y2 <= 7){
			if (!isValidMove(x2,y2)){
				break;
			}
			pMoves.add(new Point(x2,y2));
			x2++;
			y2++;
		}
		
		heuristic = pMoves.size();
	
	}
	public int getNumMoves(boolean isPlayerMove){
		int count = 0;
		int x;
		int y;
		if (isPlayerMove){
			x = playerX;
			y = playerY;
			
		}
		else{
			x = compX;
			y = compY;
		}
		
		
		
		//left moves
		for (int i = y-1; i >= 0; i--){
			if (!isValidMove(x,i)){
				break;
			}
			count++;
		}

		
		//right moves
		for (int i = y+1; i <= 7; i++){
			if (!isValidMove(x,i)){
				break;
			}
			count++;
		}

		
		//up moves
		for (int i = x-1; i >= 0; i--){
			if (!isValidMove(i,y)){
				break;
			}
			count++;
		}

		
		//down moves
		for (int i = x+1; i <= 7; i++){
			if (!isValidMove(i,y)){
				break;
			}
			count++;
		}

		
		//diagonal up-right
		int x2 = x-1;
		int y2 = y+1;
		while (x2 >= 0 && y2 <= 7){
			if (!isValidMove(x2,y2)){
				break;
			}
			count++;
			x2--;
			y2++;
		}
	
		//diagonal up-left
		x2 = x-1;
		y2 = y-1;
		while (x2 >= 0 && y2 >=0){
			if (!isValidMove(x2,y2)){
				break;
			}
			count++;
			x2--;
			y2--;
		}

		
		//diagonal down-left
		x2 = x+1;
		y2 = y-1;
		while (x2 <= 7 && y2 >= 0){
			if (!isValidMove(x2,y2)){
				break;
			}
			count++;
			x2++;
			y2--;
		}
		
		
		//diagonal down-right
		x2 = x+1;
		y2 = y+1;
		while (x2 <= 7 && y2 <= 7){
			if (!isValidMove(x2,y2)){
				break;
			}
			count++;
			x2++;
			y2++;
		}
		
		return count;
	
	}
	
	public void getPossibleMoves(){
		for (int i = 0; i < pMoves.size(); i++){
			Point p = pMoves.get(i);
			System.out.println("(" + p.getX() + "," + p.getY() + ")");
		}
	}
	
	
	public boolean isValidMove(int i, int j){
		String place = board[i][j];
		if (place == "#" || place == "O" || place == "X"){
			return false;
		}
		
		return true;
	}
	
	
	public String[][] copyBoard(){
		String[][] copy = new String[8][8];
		for (int i = 0; i < 8; i ++){
			for (int j = 0; j < 8; j++){
				copy[i][j] = board[i][j];
			}
		}
		
		return copy;
	}
	
	public Board getRandBoard(){
		int randPos = (int)(Math.random() * nextStates.size());
		return nextStates.get(randPos);
	}
	
	
	public boolean getIsPlayerMove(){
		return playerMove;
	}
	
	public boolean canMoveThere(String move){
		int x = (int)(move.charAt(0)-65);	
		int y = Character.getNumericValue(move.charAt(1)) - 1;
	
		for (int i = 0; i < pMoves.size(); i++){
			Point p = pMoves.get(i);
			if (p.getX() == x && p.getY() == y){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Board> getChildren(){
		return nextStates;
	}
	
	public Point getPlayerPos(){
		return new Point(playerX,playerY);
	}
	
	public Point getCompPos(){
		return new Point(compX,compY);
	}
	
}
