package main;

import javax.swing.JFrame;
import java.util.Scanner;

/**
 * Main class
 */
public class Main {
    
    /** 
     * @param args param for main
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter player name!");
        String name = scan.nextLine();

        JFrame window1 = new JFrame();
        window1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window1.setResizable(false);
        window1.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel(name);
        window1.add(gamePanel);

        window1.pack();

        window1.setLocationRelativeTo(null);
        window1.setVisible(true);

        gamePanel.gameBuild();

        gamePanel.startGameThread();
        gamePanel.run();
    }
}