import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Frame extends JFrame {
    public static BackPanel backpanel;
    public static MainPanel mainpanel;
    public static JButton[][] buttons;
    public static int[][] board;
    public static JLabel instructions, time;
    public static final int N = 9;
    public static int seconds = 0, minutes = 0;
    public Timer timer;

    Frame() {
        super("Sudoku");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setMainPanel();
        setBackPanel();
        setBoard();
        setButtons();

        this.add(backpanel);
        this.addKeyListener(mainpanel);
        timer = new Timer(1000, new TimeCounter());
        timer.start();
        this.pack();
        this.setVisible(true);
    }

    private void setBackPanel() {
        backpanel = new BackPanel();
        backpanel.setPreferredSize(new Dimension(630, 750));
        backpanel.setLayout(new BorderLayout(20, 20));
        backpanel.add(mainpanel, BorderLayout.NORTH);
        setInstructions();
        setTime();
        backpanel.add(instructions, BorderLayout.WEST);
        backpanel.add(time, BorderLayout.EAST);
    }

    private void setMainPanel() {
        mainpanel = new MainPanel();
        mainpanel.setPreferredSize(new Dimension(630, 630));
        mainpanel.setBackground(Color.YELLOW);
        mainpanel.setLayout(new GridLayout(9, 9));
    }

    private void setButtons() {
        buttons = new JButton[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setOpaque(true);
                buttons[i][j].setFocusable(false);
                buttons[i][j].setVisible(true);
                buttons[i][j].setContentAreaFilled(false);
                 buttons[i][j].addActionListener(mainpanel);
                buttons[i][j].setFont(new Font(Font.SERIF, Font.ITALIC, 20));
                if(board[i][j] != 0) buttons[i][j].setText(board[i][j] + "");
                mainpanel.add(buttons[i][j]);
            }
        }
    }

    public static void setBoard() {
        PuzzleGenerator generator = new PuzzleGenerator();
        Random r = new Random();
        int next = r.nextInt(5);
        board = generator.getBoardByIndex(next);
    }

    private void setInstructions() {
        instructions = new JLabel();
        instructions.setFont(new Font(Font.SERIF, Font.ITALIC, 20));
        instructions.setText("Press Space to solve and R for a new game");
    }

    private void setTime() {
        time = new JLabel();
        time.setFont(new Font(Font.SERIF, Font.ITALIC, 20));
        String sec = "", min = "";
        if(seconds < 10) sec = "0" + seconds;
        else sec = seconds + "";
        if(minutes < 10) min = "0" + minutes;
        else min = minutes + "";
        time.setText("Time: " + min + ":" + sec);
    }
}
