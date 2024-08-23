package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MainScreen extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int NUM_MINES = 10;
    private static final int GRID_SIZE = 10;

    private JButton[][] buttons;
    private boolean[][] mines;
    private int numFlags;

    public MainScreen() {
        super("MainScreen");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JPanel panel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        add(panel, BorderLayout.CENTER);

        buttons = new JButton[GRID_SIZE][GRID_SIZE];
        mines = new boolean[GRID_SIZE][GRID_SIZE];

        // Initialize mines
        Random random = new Random();
        for (int i = 0; i < NUM_MINES; i++) {
            int x, y;
            do {
                x = random.nextInt(GRID_SIZE);
                y = random.nextInt(GRID_SIZE);
            } while (mines[x][y]);
            mines[x][y] = true;
        }

        // Create buttons
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(30, 30));
                button.addActionListener(new ButtonListener(i, j));
                panel.add(button);
                buttons[i][j] = button;
            }
        }

        pack();
        setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        private int x, y;

        public ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (mines[x][y]) {
                // Mine clicked!
                JOptionPane.showMessageDialog(MainScreen.this, "Game Over!");
                System.exit(0);
            } else {
                // Reveal adjacent cells
                reveal(x, y);
            }
        }
    }

    private void reveal(int x, int y) {
        if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) {
            return;
        }

        if (buttons[x][y].getText() != null) {
            return;
        }

        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int nx = x + i;
                int ny = y + j;
                if (nx >= 0 && nx < GRID_SIZE && ny >= 0 && ny < GRID_SIZE && mines[nx][ny]) {
                    count++;
                }
            }
        }

        if (count > 0) {
            buttons[x][y].setText(String.valueOf(count));
        } else {
            buttons[x][y].setText("");
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    reveal(x + i, y + j);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainScreen();
            }
        });
    }
}