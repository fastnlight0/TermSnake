import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class TermSnake extends JFrame implements KeyListener, ActionListener {
    static int nextDirection = 1;
    static int currDirection = 1;

    boolean isRunning = true;
    boolean endGame = false;
    boolean rerun = false;
    int score = 0;
    ArrayList<Integer> apples = new ArrayList<>();
    JMenu scoreDisplay = new JMenu("Score: 0");
    static int currentLocation = 0;
    ArrayList<Integer> nums = new ArrayList<>();

    // Settings for game (modify to your liking)

    int startingLength = 5; // Starting length of snake. Default is 5.

    int startingPosition = 44; // Starting position of snake on board. Put number of desired location. Default
                               // is middle of board (44). Board looks like this:
    /*
     * 0 1 2 3 4 5 6 7 8 9
     * 10 11 12 13 14 15 16 17 18 19
     * 20 21 22 23 24 25 26 27 28 29
     * 30 31 32 33 34 35 36 37 38 39
     * 40 41 42 43 44 45 46 47 48 49
     * 50 51 52 53 54 55 56 57 58 59
     * 60 61 62 63 64 65 66 67 68 69
     * 70 71 72 73 74 75 76 77 78 79
     * 80 81 82 83 84 85 86 87 88 89
     * 90 91 92 93 94 95 96 97 98 99
     */

    int startingDirection = 3; // Starting direction for snake. Put number of desired direction. Default is
                               // right (3). Directions:
    /*
     * 0: Up
     * 1: Down
     * 2: Left
     * 3: Right
     */

    int numOfApples = 3; // Number of apples on screen at a time. Default is 3.

    public static void print(Object t) {
        System.out.println(t);
    }

    public static void main(String[] args) throws InterruptedException {
        new TermSnake();
    }

    public TermSnake() throws InterruptedException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setTitle("TermSnake");
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        JMenuItem menuItemNewGame = new JMenuItem("End Game");
        menuItemNewGame.addActionListener(this);
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuItemExit.addActionListener(this);
        menuGame.add(menuItemNewGame);
        menuGame.add(menuItemExit);
        menuBar.add(menuGame);
        menuBar.add(scoreDisplay);
        setJMenuBar(menuBar);
        setLayout(new GridLayout(10, 10));
        for (int i = 0; i < 100; i++) {
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new GridLayout(60, 60, 0, 0));
            add(jPanel, i);
            nums.add(0);
        }
        for (int i = 0; i < numOfApples; i++){
            apples.add(-1);
        }
        SwingUtilities.updateComponentTreeUI(this);
        startAnimation();
        runGame();
    }

    public void startAnimation() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponents()[i].setBackground(Color.RED);
            TimeUnit.MILLISECONDS.sleep(5);
        }
        for (int i = 0; i < 100; i++) {
            ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponents()[i].setBackground(Color.BLACK);
            TimeUnit.MILLISECONDS.sleep(5);
        }
        for (int i = 0; i < 100; i++) {
            ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponents()[i].setBackground(Color.GREEN);
            TimeUnit.MILLISECONDS.sleep(5);
        }
        for (int i = 0; i < 100; i++) {
            ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponents()[i].setBackground(null);
            TimeUnit.MILLISECONDS.sleep(5);
        }
        makeT(Color.RED);
        TimeUnit.MILLISECONDS.sleep(300);
        makeT(Color.GREEN);
        TimeUnit.MILLISECONDS.sleep(300);
        makeT(Color.BLACK);

        JDialog jd = new JDialog(TermSnake.getFrames()[0]);
        jd.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        jd.setSize(300, 200);
        jd.setLocationRelativeTo(TermSnake.getFrames()[0]);
        jd.setLayout(new GridLayout(0, 1));
        JLabel jl = new JLabel("Welcome to TermSnake");
        jl.setFont(new Font("", Font.PLAIN, 20));
        jl.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        jd.add(jl);
        JButton play = new JButton("Play");
        jd.getRootPane().setDefaultButton(play);
        UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        play.setFont(new Font("", Font.BOLD, 60));
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd.dispose();
                for (int i = 0; i < 100; i++) {
                    ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponents()[i].setBackground(null);
                }
                rerun = true;
            }
        });
        jd.add(play);
        jd.setVisible(true);
    }

    public void makeT(Color color) {
        for (int i = 0; i < 100; i++) {
            if (i < 20 || i % 10 == 4 || i % 10 == 5) {
                ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponents()[i].setBackground(color);
            }
        }
    }

    void runGame() throws InterruptedException {
        while (true) {
            if (rerun) {
                rerun = false;
                nextDirection = startingDirection;
                currentLocation = startingPosition;
                score = startingLength;
                isRunning = true;
                endGame = false;
                for (int i = 1; i < apples.size(); i++){
                    apples.set(i, -1);
                }
                SwingUtilities.updateComponentTreeUI(((JFrame) TermSnake.getFrames()[0]));
                ((JFrame) TermSnake.getFrames()[0]).requestFocus();
                ((JFrame) TermSnake.getFrames()[0]).setFocusable(true);
                ((JFrame) TermSnake.getFrames()[0]).setFocusTraversalKeysEnabled(false);
                while (isRunning) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    switch (nextDirection) {
                        case 0:
                            isOutOfBounds(-10);
                            break;
                        case 1:
                            isOutOfBounds(10);
                            break;
                        case 2:
                            isOutOfBounds(-1);
                            break;
                        case 3:
                            isOutOfBounds(1);
                            break;
                    }
                    while (apples.size() > 100-score) {
                        apples.remove(0);
                    }
                    
                    if (apples.contains(currentLocation)) {
                        score++;
                        apples.set(apples.indexOf(currentLocation), -1);
                    }
                    if (score >= 99) {
                        endGame = true;
                    }
                    if (nums.get(currentLocation) != 0) {
                        endGame = true;
                    }
                    nums.set(currentLocation, score);
                    for (int i = 0; i < apples.size(); i++) {
                        while (apples.get(i) == -1) {
                            int chosen = new Random().nextInt(100);
                            if (nums.get(chosen) == 0) {
                                apples.set(i, chosen);
                            }
                        }
                    }
                    for (int i = 0; i < 100; i++) {
                        if (nums.get(i) == 0) {
                            ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponent(i).setBackground(null);
                        } else {
                            ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponent(i)
                                    .setBackground(Color.BLACK);
                            nums.set(i, nums.get(i) - 1);
                        }
                    }
                    ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponent(currentLocation)
                            .setBackground(Color.GREEN);
                    for (int i = 0; i < apples.size(); i++){
                        ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponent(apples.get(i)).setBackground(Color.RED);    
                    }
                    scoreDisplay.setText(String.format("Score: %d", score));
                    currDirection = nextDirection;
                    if (endGame) {
                        gameOver();
                        break;
                    }
                }
            }
            System.out.print(""); // I don't know why, but if I remove this line, the restart game function won't
                                  // work
        }

    }

    void gameOver() throws InterruptedException {
        isRunning = false;
        if (score < 99) {
            for (int i = 0; i < 100; i++) {
                ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponent(i).setBackground(Color.RED);
                nums.set(i, 0);
                TimeUnit.MILLISECONDS.sleep(5);
            }
        } else {
            for (int i = 0; i < 100; i++) {
                ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponent(i).setBackground(Color.GREEN);
                nums.set(i, 0);
                TimeUnit.MILLISECONDS.sleep(5);
            }
        }
        JDialog jd = new JDialog(TermSnake.getFrames()[0]);
        jd.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        jd.setSize(300, 200);
        jd.setLocationRelativeTo((JFrame) TermSnake.getFrames()[0]);
        jd.setLayout(new GridLayout(0, 1));
        JLabel jl = new JLabel("Game over!");
        if (score >= 99) {
            jl.setText("You Win!");
        }
        JLabel jl2 = new JLabel(String.format("Score: %d", score));
        jl.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        jl2.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        JButton yes = new JButton("New Game");
        jd.getRootPane().setDefaultButton(yes);
        UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        JButton no = new JButton("Exit");
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd.dispose();
                rerun = true;
                for (int i = 0; i < 100; i++) {
                    ((JFrame) TermSnake.getFrames()[0]).getContentPane().getComponent(i).setBackground(null);
                }
            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TermSnake.getFrames()[0]
                        .dispatchEvent(new WindowEvent(TermSnake.getFrames()[0], WindowEvent.WINDOW_CLOSING));
            }
        });
        jp.add(yes);
        jp.add(no);
        jd.add(jl);
        jd.add(jl2);
        jd.add(jp);
        jd.setVisible(true);
    }

    void isOutOfBounds(int movement) {
        int nextStep = currentLocation + movement;
        if (nextStep < 0 || nextStep > 99 || (currentLocation % 10 == 0 && movement == -1)
                || (currentLocation % 10 == 9 && movement == 1)) {
            endGame = true;
        } else {
            currentLocation += movement;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (currDirection != 3) {
                    nextDirection = 2;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (currDirection != 2) {
                    nextDirection = 3;
                }
                break;
            case KeyEvent.VK_UP:
                if (currDirection != 1) {
                    nextDirection = 0;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (currDirection != 0) {
                    nextDirection = 1;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                endGame = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Exit":
                TermSnake.getFrames()[0]
                        .dispatchEvent(new WindowEvent(TermSnake.getFrames()[0], WindowEvent.WINDOW_CLOSING));
                break;
            case "End Game":
                endGame = true;
                break;
        }
    }
}
