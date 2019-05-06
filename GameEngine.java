import java.util.Scanner;
import java.util.ArrayList;
public class GameEngine {
	Board current;
	ArrayList<String> playerMoves;
	ArrayList<String> compMoves;
	int movesCount;
	
	public GameEngine(){
		Board initial = new Board(true);
		current = initial;
		playerMoves = new ArrayList<>();
		compMoves = new ArrayList<>();
		movesCount = 0;
	}
	
	public GameEngine(Board b){
		current = b;
		playerMoves = new ArrayList<>();
		compMoves = new ArrayList<>();
		movesCount = 0;
	}
	
	public void run(){
		Scanner reader = new Scanner(System.in);

		print(current);

		int count = 0;
		while (!current.gameOver()){
			
			
			
			if (!current.getIsPlayerMove()){
				Board b;
				Minimax m = new Minimax(current);
				b = m.abSearch();
				Point p = b.getCompPos();
				compMoves.add(convertToString(p));
				current = b;
				
			}
			
			
			else{
				System.out.println("Enter player move: ");
				String input = reader.nextLine();
				input = input.toUpperCase();
				
				
				while(!isValid(input)){
					System.out.println("Invalid input");
					System.out.println("Enter player move");
					input = reader.nextLine();
					input = input.toUpperCase();
				}
				while (!current.canMoveThere(input)){
					System.out.println("You cannot move there");
					System.out.println("Enter player move");
					input = reader.nextLine();
					input = input.toUpperCase();
				}
				
				current.playerMove(input);
				String[][] b2 = current.copyBoard();
				Board b = new Board(b2,false);
				Point p = b.getPlayerPos();
				playerMoves.add(convertToString(p));
				
				
				//Testing purposes
//				Board b = current.getRandBoard();
//				Point p = b.getPlayerPos();
//				playerMoves.add(convertToString(p));
				
				
				current = b;
				movesCount++;
		
			}
			current.getNextStates();
			current.calcNextStates();
			
			if (count%2 != 0){
				print(current);
			}
			
			count++;
			
		}
		
		if (current.getIsPlayerMove()){
			System.out.println("Player lost");
		}
		else{
			System.out.println("Computer lost");
		}
		reader.close();
	}
	
	public boolean isValid(String s){
		
		if (s.length() != 2){
			return false;
		}
		int first = s.charAt(0);
		int second = s.charAt(1);
		
		if (65 <= first && first <= 72 && 49 <= second && second <= 56){
			return true;
		}
		
		return false;
	}
	
	public void print(Board b){
		String[][] board = b.getBoard();
		
		System.out.println("  1 2 3 4 5 6 7 8 \t Computer vs. Opponent");
		for (int i = 0; i < board.length; i++){
			System.out.print((char)(i+65) + " ");
			for (int j = 0; j < board.length; j++){
				System.out.print(board[i][j] + " ");
			}
			
			
			
			if (i < movesCount){
				System.out.print("\t" + (int)(i+1) + "." + compMoves.get(i) + "  " + playerMoves.get(i));
			}
			if (8 < movesCount && (int)(i+8) < movesCount){
				if (i==0){
					System.out.print("\t" + " " + (int)(i+9) + "." + compMoves.get(i+8) + " " + playerMoves.get(i+8));
				}
				else
					System.out.print("\t" + (int)(i+9) + "." + compMoves.get(i+8) + " " + playerMoves.get(i+8));
			}
			if (16 < movesCount && (int)(i+8+8) < movesCount){
				System.out.print("\t" + (int)(i+17) + "."+ compMoves.get(i+8+8) + " " + playerMoves.get(i+8+8));
			}
			if (24 < movesCount && (int)(i+8+8+8) < movesCount){
				System.out.print("\t" + (int)(i+25) + "."+ compMoves.get(i+8+8+8) + " " + playerMoves.get(i+8+8+8));
			}
			
			
			
			System.out.println();
		}
		
		System.out.println();
	}
	
	public String convertToString(Point p){
		int x = p.getX();
		int y = p.getY();
		
		char s = (char)(x+65);
		String s2 = Integer.toString(y+1);
		
		return s + s2;
	}
	
}
