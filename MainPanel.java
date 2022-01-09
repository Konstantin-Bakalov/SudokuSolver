import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPanel extends JPanel implements ActionListener, KeyListener {
    private int x = -1, y = -1;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawLines(g2d);
        for(int i = 0; i < Frame.N; i++) {
            for(int j = 0; j < Frame.N; j++) {
                if(Frame.board[i][j] != 0)
                    Frame.buttons[i][j].setText(Frame.board[i][j] + "");
                else Frame.buttons[i][j].setText("");
            }
        }
    }

    public boolean solveUtil(int i, int j) {
        if(j >= 9) {
            i++;
            j -= 9;
        }
        if(i >= 9) return true;
        if(Frame.board[i][j] != 0) return solveUtil(i, j + 1);
        if(Frame.board[i][j] == 0) {
            for (int t = 1; t <= 9; t++) {
                if(MainPanel.isSafe(t, i, j)) {
                    Frame.board[i][j] = t;
                    if(solveUtil(i, j + 1)) return true;
                    Frame.board[i][j] = 0;
                }
            }
        }
        return false;
    }

    public void solve() {
        solveUtil(0, 0);
        repaint();
    }

    private void drawLines(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(210, 0, 210, 630);
        g2d.drawLine(420, 0, 420, 630);
        g2d.drawLine(0, 210, 630, 210);
        g2d.drawLine(0, 420, 630, 420);
        g2d.drawLine(0, 630, 630, 630);

        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(2));
        for(int i = 1; i <= 8; i++) {
            if(i != 6 && i != 3) {
                g2d.drawLine(70 * i, 0, 70 * i, 630);
                g2d.drawLine(0, i * 70, 630, i * 70);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < Frame.N; i++) {
            for(int j = 0; j < Frame.N; j++) {
                if(e.getSource() == Frame.buttons[i][j]) {
                    x = j;
                    y = i;
                    break;
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if(x != -1 && y != -1 && c >= '1' && c <= '9') {
            if(isSafe(c - '0', y, x))
                Frame.instructions.setText("  That is possible");
            else Frame.instructions.setText("  Maybe try something else");
            Frame.buttons[y][x].setText(c + "");
            Frame.board[y][x] = c - '0';

        }
        x = -1;
        y = -1;
        if(c == ' ') solve();
        else if(c == 'r') {
            Frame.setBoard();
            Frame.instructions.setText("Press Space to solve and R for a new game");
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static boolean isSafe(int number, int i, int j) {
        for(int x = 0; x < Frame.N; x++) {
            if(Frame.board[i][x] == number) return false;
        }
        for(int y = 0; y < Frame.N; y++) {
            if(Frame.board[y][j] == number) return false;
        }
        i -= i % 3;
        j -= j % 3;
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                if(Frame.board[i + y][j + x] == number) return false;
            }
        }
        return true;
    }
}