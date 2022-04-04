package blanks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class Main {
  public static void main(final String[] args) {
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_YELLOW = "\u001B[33m";

    // 1. enter difficulty level TODO
//    System.out.println("Enter difficulty");
//    Scanner scanner = new Scanner(System.in);
//    String diff = scanner.nextLine().toLowerCase(Locale.ROOT);

    boolean game = true;
    while(game) {
      try (Scanner file_scanner = new Scanner(
              new InputStreamReader(
                      new FileInputStream(args[0]), StandardCharsets.UTF_8))) {




        System.out.println(ANSI_YELLOW + "Enter a verse in format (1 Nephi 3:7)" + ANSI_RESET);
        Scanner verse_scanner = new Scanner(System.in);
        String verse_input = verse_scanner.nextLine().toLowerCase(Locale.ROOT);

        if(verse_input.toLowerCase(Locale.ROOT).equals("quit")) {
          break;
        }

        StringBuilder text = new StringBuilder();
        boolean found = false;

        while(file_scanner.hasNextLine() && !found) {
          String check = file_scanner.nextLine().toLowerCase(Locale.ROOT);
          if(check.equals(verse_input)) {
            found = true;
            while (file_scanner.hasNextLine()) {
              check = file_scanner.nextLine();
              if (Objects.equals(check, "")) {
                break;
              } else {
                text.append(check).append(" ");
              }
            }
          }
        } if (!found) {
          System.out.println(ANSI_RED + "ERROR: invalid reference" + ANSI_RESET);
          continue;
        }

        // remove verse number and all spaces at beginning and end of verse
        String verse = text.toString()
                .replaceAll("\\d","")
                .trim();

        System.out.println("generating new puzzle card");
        Card card = new Card(verse);

        while(!card.isComplete()) {
          card.displayCurrent();
          System.out.println("Enter in next guess: ");
          Scanner input_scanner = new Scanner(System.in);
          String input = input_scanner.nextLine();

          if (input.toLowerCase(Locale.ROOT).equals("quit")) {
            game = false;
            break;
          }
          else if (input.toLowerCase(Locale.ROOT).equals("new verse")) {
            break;
          }
          else {
            card.submitGuess(input);
          }
        }

      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}
