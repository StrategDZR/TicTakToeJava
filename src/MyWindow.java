import javax.swing.*;
import java.awt.*;


class MyWindow extends JFrame {

    public MyWindow() {
        setBounds(400, 100, 506, 568);
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel bottomPanel = new JPanel(new GridLayout());
        GameMap gamePanel = new GameMap();

        JButton jbNewGame = new JButton("Начать новую игру");
        JButton jbExitGame = new JButton("Выйти из игры");
        jbExitGame.addActionListener(e -> System.exit(0));

        add(bottomPanel, BorderLayout.SOUTH);

        JPanel jpStartOrExit = new JPanel(new GridLayout());
        jpStartOrExit.add(jbNewGame);
        jpStartOrExit.add(jbExitGame);
        JPanel jpOpponents = new JPanel(new GridLayout());
        JPanel jpGameConditions = new JPanel(new GridLayout());
        JButton jbHumanVsHuman = new JButton("Человек-Человек");
        JButton jbHumanVsAI = new JButton("Человек-ИИ");
        jpOpponents.add(jbHumanVsHuman);
        jpOpponents.add(jbHumanVsAI);

        JButton jbCond333 = new JButton("S: 3X3 - W: 3");
        JButton jbCond554 = new JButton("S: 5X5 - W: 4");
        JButton jbCond10105 = new JButton("S: 10X10 - W: 5");
        jpGameConditions.add(jbCond333);
        jpGameConditions.add(jbCond554);
        jpGameConditions.add(jbCond10105);

        jbCond333.addActionListener(e -> {
            gamePanel.setConditions(3, 3);
            gamePanel.startGame();
            ((CardLayout) bottomPanel.getLayout()).show(bottomPanel, "jpStartOrExit");
        });

        jbCond554.addActionListener(e -> {
            gamePanel.setConditions(5, 4);
            gamePanel.startGame();
            ((CardLayout) bottomPanel.getLayout()).show(bottomPanel, "jpStartOrExit");
        });

        jbCond10105.addActionListener(e -> {
            gamePanel.setConditions(10, 5);
            gamePanel.startGame();
            ((CardLayout) bottomPanel.getLayout()).show(bottomPanel, "jpStartOrExit");
        });

        jbHumanVsHuman.addActionListener(e -> {
            gamePanel.setOpponents("H-H");
            ((CardLayout) bottomPanel.getLayout()).show(bottomPanel, "jpConditions");
        });

        jbHumanVsAI.addActionListener(e -> {
            gamePanel.setOpponents("H-AI");
            ((CardLayout) bottomPanel.getLayout()).show(bottomPanel, "jpConditions");
        });

        bottomPanel.setLayout(new CardLayout());
        bottomPanel.add(jpStartOrExit, "jpStartOrExit");
        bottomPanel.add(jpOpponents, "jpOpponents");
        bottomPanel.add(jpGameConditions, "jpConditions");

        bottomPanel.setPreferredSize(new Dimension(1, 40));

        ((CardLayout) bottomPanel.getLayout()).show(bottomPanel, "jpStartOrExit");

        jbNewGame.addActionListener(e -> ((CardLayout) bottomPanel.getLayout()).show(bottomPanel, "jpOpponents"));

        add(gamePanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
