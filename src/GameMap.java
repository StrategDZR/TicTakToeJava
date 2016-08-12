import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

class GameMap extends JPanel {

    private int CELLS_COUNT = 3;
    private int DOTS_TO_WIN = 3;
    private final int SIZE = 500;
    private int CELL_SIZE = SIZE / CELLS_COUNT;

    private int[][] map;
    private Random rand = new Random();
    private int currentPlayer;
    private String gameOver;

    private String opponents = "H-H"; // H-AI H-H

    public void setOpponents(String opponents) {
        this.opponents = opponents;
    }

    public void setConditions(int size, int dots) {
        CELLS_COUNT = size;
        DOTS_TO_WIN = dots;
        CELL_SIZE = SIZE / CELLS_COUNT;
    }

    public void startGame() {
        currentPlayer = 1;
        gameOver = "";
        map = new int[CELLS_COUNT][CELLS_COUNT];
        repaint();
    }

    public GameMap() {
        setBackground(Color.white);
        currentPlayer = 1;
        gameOver = "NOTHING";
        map = new int[CELLS_COUNT][CELLS_COUNT];
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = e.getX() / CELL_SIZE;
                    int y = e.getY() / CELL_SIZE;
                    if (gameOver.isEmpty() && currentPlayer == 1 && map[x][y] == 0) {
                        map[x][y] = 1;
                        currentPlayer = 2;
                        isFieldFull();
                        checkWin(1);
                        repaint();
                        if (opponents.equals("H-H")) return;
                    }
                    if (gameOver.isEmpty() && currentPlayer == 2) {
                        if (opponents.equals("H-AI")) {
                            aiTurn();
                            isFieldFull();
                            checkWin(2);
                            currentPlayer = 1;
                        } else {
                            if (map[x][y] == 0) {
                                map[x][y] = 2;
                                currentPlayer = 1;
                                isFieldFull();
                                checkWin(2);
                                repaint();
                            }
                        }
                    }
                }
            }
        });
    }

    private void checkWin(int ox) {
        for (int i = 0; i < CELLS_COUNT; i++) {
            for (int j = 0; j < CELLS_COUNT; j++) {
                if (checkLine(i, j, 1, 0, DOTS_TO_WIN, ox) || checkLine(i, j, 0, 1, DOTS_TO_WIN, ox) || checkLine(i, j, 1, 1, DOTS_TO_WIN, ox) || checkLine(i, j, 1, -1, DOTS_TO_WIN, ox)) {
                    if (ox == 1) gameOver = "Игрок 1 WIN";
                    if (ox == 2) gameOver = "Игрок 2 WIN";
                    return;
                }
            }
        }
    }

    private boolean checkLine(int cx, int cy, int vx, int vy, int l, int ox) {
        if (cx + l * vx > CELLS_COUNT || cy + l * vy > CELLS_COUNT || cy + l * vy < -1) return false;
        for (int i = 0; i < l; i++) {
            if (map[cx + i * vx][cy + i * vy] != ox) return false;
        }
        return true;
    }

    private void isFieldFull() {
        for (int i = 0; i < CELLS_COUNT; i++) {
            for (int j = 0; j < CELLS_COUNT; j++) {
                if (map[i][j] == 0) return;
            }
        }
        gameOver = "НИЧЬЯ!";
    }

    private boolean isCellEmpty(int x, int y) {
        return map[x][y] == 0;
    }

    private void aiTurn() {
        int x, y;
        do {
            x = rand.nextInt(CELLS_COUNT);
            y = rand.nextInt(CELLS_COUNT);
        } while (!isCellEmpty(x, y));
        map[x][y] = 2;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameOver.equals("NOTHING")) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 500, 500);
            return;
        }

        g.setColor(Color.black);
        for (int i = 0; i < CELLS_COUNT; i++) {
            g.drawLine(0, i * CELL_SIZE, SIZE, i * CELL_SIZE);
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, SIZE);
        }
        g.drawRect(0, 0, SIZE - 1, SIZE - 1);

        for (int i = 0; i < CELLS_COUNT; i++) {
            for (int j = 0; j < CELLS_COUNT; j++) {
                if (map[i][j] > 0) {
                    if (map[i][j] == 1) g.setColor(Color.red);
                    if (map[i][j] == 2) g.setColor(Color.blue);
                    g.fillOval(i * CELL_SIZE + 2, j * CELL_SIZE + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                    /*Graphics2D g2d = (Graphics2D) g;
                    g2d.setColor(Color.green);
                    g2d.setStroke(new BasicStroke(5));
                    g2d.drawLine(i * CELL_SIZE + 12, j * CELL_SIZE + 12, (i + 1) * CELL_SIZE - 12, (j + 1) * CELL_SIZE - 12);
                    g2d.drawLine(i * CELL_SIZE, (j + 1) * CELL_SIZE, (i + 1) * CELL_SIZE, j * CELL_SIZE);*/
                }
            }
        }

        if (!gameOver.isEmpty()) {
            g.setFont(new Font("Helvetica", Font.BOLD, 72));
            g.setColor(Color.black);
            g.drawString(gameOver, 14, 274);
            g.setColor(Color.orange);
            g.drawString(gameOver, 10, 270);
        }

    }

}
