package blanks;

import java.util.*;

public class Card {
  private final List<String> solution;
  private final List<String> puzzle;
  private final List<String> colors;

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String WHITE_BOLD = "\033[1;37m";

  public boolean checkGuess(String guess, String actual) {
    actual = actual
            .replaceAll("[\\W]", "")
            .toLowerCase(Locale.ROOT)
            .trim();
    guess = guess
            .replaceAll("[\\W]", "")
            .toLowerCase(Locale.ROOT)
            .trim();

    return actual.equals(guess);
  }

  public Card(String text) {
    solution = Arrays.asList(text.split(" "));
    puzzle = new ArrayList<>();
    colors = new ArrayList<>();

    Random rand = new Random();

    for(String s : solution) {
      if(rand.nextInt(300) < 150) {
        puzzle.add(s);
      } else {
        puzzle.add("-");
      }
      colors.add(WHITE_BOLD);
    }
  }

  public List<String> getSolution() {
    return solution;
  }

  public List<String> getPuzzle() {
    return puzzle;
  }

  public void displayCurrent() {
    int p = 0;

    for(int i=0; i<solution.size(); i++) {
      if (p == 20) {
        System.out.println(colors.get(i) + puzzle.get(i) + ANSI_RESET + " ");
        p = 0;
      } else System.out.print(colors.get(i) + puzzle.get(i) + ANSI_RESET + " ");
      p++;
    }
    System.out.println("");
  }

  public Boolean isComplete() {
    return puzzle.equals(solution);
  }

  public void submitGuess(String guess) {
    String[] guesses = guess.split(" ");
    int correct = 0;

    for(int i=0; i < guesses.length; i++) {
      int guessIndex = puzzle.indexOf("-");
      String actual = solution.get(puzzle.indexOf("-"));
      if(checkGuess(actual, guesses[i])) {
        //System.out.println(ANSI_GREEN + "Correct!" + ANSI_RESET);
        puzzle.set(guessIndex, actual);
        colors.set(guessIndex, ANSI_GREEN);
      } else {
        //System.out.println(ANSI_RED + "Guess Again!" + ANSI_RESET);
        puzzle.set(guessIndex, actual);
        colors.set(guessIndex, ANSI_RED);
      }
    }
    //System.out.println(String.valueOf(correct) + "/" + String.valueOf(guesses.length));
  }
}
