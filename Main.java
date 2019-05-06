import java.util.Scanner;
public class Main {
	public static void main(String[] args){
		Scanner reader = new Scanner(System.in);
		boolean validInput = false;
		Board b = new Board(true);
		//Ask for computer first or player first
		while(!validInput){
			System.out.println("1) For player first ");
			System.out.println("2) For computer first ");
			int input = reader.nextInt();
			if (input == 1){
				b = new Board(true);
				validInput = true;
			}
			else if (input ==2){
				b = new Board(false);
				validInput = true;
			}
			else{
				System.out.println("Invalid input!!");
			}
		}
		
		
		b.getNextStates();
		b.calcNextStates();
		GameEngine engine = new GameEngine(b);
		engine.run();
		
		
		reader.close();
		
		
		//For game simulator to test AI win percentage
		
//		int count = 0;
//		for (int i = 0; i < 25; i++){
//			Board b = new Board(true);
//			b.getNextStates();
//			b.calcNextStates();
//			GameSimulator g = new GameSimulator(b);
//			count += g.run();
//		}
//		System.out.println(count + "/25");
		
		//reader.close();
	}
}

