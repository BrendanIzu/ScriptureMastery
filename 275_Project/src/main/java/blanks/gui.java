package blanks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

class gui{
  private static Card card;

  public static void main(String[] args){
    ArrayList<JTextField> textFields = new ArrayList<>();

    try (Scanner file_scanner = new Scanner(
            new InputStreamReader(
                    new FileInputStream(args[0]), StandardCharsets.UTF_8))) {
      StringBuilder text = new StringBuilder();

      while(file_scanner.hasNextLine()) {
        String s = file_scanner.nextLine();
        text.append(s);
      }

      System.out.println("generating new puzzle card...");
      card = new Card(text.toString());
      card.displayCurrent();
//      setGUI();

//      Scanner input_scanner = new Scanner(System.in);
//      while(!card.isComplete()) {
//        card.displayCurrent();
//        System.out.println("Enter in next blank: ");
//        String input = input_scanner.nextLine();
//        card.submitGuess(input);
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

//    Scanner scanner = new Scanner(System.in);  // Create a Scanner object
//    String userName = scanner.nextLine();
//    System.out.println(userName);
  }

  public static void setGUI() {
    JFrame frame = new JFrame("Scripture Mastery");

    int x = 10;
    int y = 20;
    int col = 1;
    int height = 30;

    for(String ss: card.getPuzzle()) {
      int width = ss.length() * 10;

      if(x > 400) {
        x = 10;
        col ++;
      }

      JLabel label = new JLabel(ss);
      label.setBounds(x, col*y, width, height);
      frame.add(label);
      x += width;
    }

    JTextField tf = new JTextField("");
    tf.setBounds(100,col*y + 70, 80, 30);
    tf.addActionListener(e -> {
      card.submitGuess(tf.getText());
      setGUI();
    });
    frame.add(tf);

//    JButton b = new JButton("check");
//    b.setBounds(20, col*y + 70, 80, 30);
//    b.addActionListener(e -> {
//      card.submitGuess(tf.getText());
//      setGUI();
//    });
//    frame.add(b);

    frame.setSize(550,400);
    frame.setLayout(null);
    frame.setVisible(true);
  }
}