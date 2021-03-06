import java.util.*;
import java.io.*;

  public class Maze{

    private char[][] maze;
    private boolean animate;//false by default

    /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)
      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!

      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:
         throw a FileNotFoundException or IllegalStateException
    */
    public Maze(String filename){
      try{
      animate = false;
        File text = new File(filename);
        Scanner inf = new Scanner(text);
        String finals = "";
        String line = "";
        int counter = 0;
        while(inf.hasNextLine()){
          line = inf.nextLine();
          counter++;
          finals+=line;
        }
        int countS = 0;
        int countE = 0;
        for (int i = 0; i <finals.length(); i++){
          if (finals.charAt(i) == 'E'){
            countE++;
          }
          if (finals.charAt(i)== 'S'){
            countS++;
          }
        }
        if (countS != 1 || countE != 1){
          throw new IllegalStateException();
        }
      int x = 0;
	maze = new char[counter][line.length()*counter];
        for (int r =0; r < counter; r++){
          for (int c =0; c < line.length(); c++){
            maze[r][c] = finals.charAt(x);//SOMETHING;
            x++;
          }
        }
      }
      catch (FileNotFoundException e){};
    }

      public String toString(){
        String str ="";
        for (int r = 0; r < maze.length; r++){
          for (int c = 0; c < maze[0].length; c++){
            str+= maze[r][c];
          }
          str+="\n";
        }
        return str;
      }

    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }

    public void setAnimate(boolean b){
        animate = b;
    }

    public void clearTerminal(){
        //erase terminal, go to top left of screen.
        System.out.println("\033[2J\033[1;1H");
    }


    /*Wrapper Solve Function returns the helper function
      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.
    */
    public int solve(){
      int row = 0;
      int col = 0;
      for (int r = 0; r < maze.length; r++){
        for (int c = 0; c < maze[0].length; c++){
          if (maze[r][c]=='S'){
            row = r;
            col= c;
          }
        }
      }
            //find the location of the S.
            maze[row][col] = '@';
            //erase the S
return solve(row, col);
            //and start solving at the location of the s.
            //return solve(???,???);

    }

    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.

      Postcondition:
        The S is replaced with '@' but the 'E' is not.
        All visited spots that were not part of the solution are changed to '.'
        All visited spots that are part of the solution are changed to '@'
    */
    private int solve(int row, int col){ //you can add more parameters since this is private
int[] rowMove = new int[] {1, -1, 0, 0};
int[] colMove = new int[] {0, 0, 1, -1};
int moves = 0;
        //automatic animation! You are welcome.
        if(animate){
            clearTerminal();
            System.out.println(this);
            wait(100);
        }
        if (maze[row][col]=='E'){
          for (int r = 0; r < maze.length; r++){
            for (int c = 0; c < maze[0].length; c++){
              if (maze[r][c] == '@'){
                moves++;
              }
            }
          }
        }
        for (int i = 0; i < rowMove.length; i++){
          char next = maze[row+rowMove[i]][col+colMove[i]];
          if (next == ' '){
            maze[row+rowMove[i]][col+colMove[i]] = '@';
            moves++;
            return solve(row+rowMove[i], col+colMove[i]);
          }
          if (next == 'E'){
            for (int r = 0; r < maze.length; r++){
              for (int c = 0; c < maze[0].length; c++){
                if (maze[r][c] == '@'){
                  moves++;
                }
              }
            }
            return moves;
          }
          else{
            if (i >= 3){
              for (int x = 0; x < rowMove.length; x++){
                char prev = maze[row+rowMove[x]][col+colMove[x]];
                if (prev == '@'){
                  maze[row][col]='.';
                  moves--;
                  return solve(row+rowMove[x], col+colMove[x]);
                }
                if (prev == 'E'){
                  for (int r = 0; r < maze.length; r++){
                    for (int c = 0; c < maze[0].length; c++){
                      if (maze[r][c] == '@'){
                        moves++;
                      }
                    }
                  }
                  return moves;
                }
          }
        }
        }
      }
        return -1;
      }


public static void main(String[] args) {
  Maze test = new Maze("Maze1.txt");
  System.out.println(test.solve());


  Maze test2 = new Maze("data1.dat");
  test2.setAnimate(true);
  System.out.println(test2.solve());

  Maze test3 = new Maze("data2.dat");
  test3.setAnimate(true);
  System.out.println(test3.solve());

  Maze test4 = new Maze("data3.dat");
  test4.setAnimate(true);
  System.out.println(test4.solve());
}
}
