package com.nathalia.rps.ui;

import com.nathalia.rps.model.Move;
import com.nathalia.rps.model.MoveFactory;
import com.nathalia.rps.model.MoveType;
import com.nathalia.rps.strategy.RandomStrategy;
import com.nathalia.rps.strategy.ComputerStrategy;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

/**
 * MainFrame - Harry Potter Theme
 * - Botões menores com ícones do universo Potter
 * - Fundo temático (imagem)
 * - Título com ícones (óculos do Harry + raio)
 * - Placar e resultado estilizados
 * - Highlight (borda + flash) no painel do vencedor
 *
 * Para funcionar corretamente coloque os recursos em:
 * src/main/resources/icons/hp_rock.png
 * src/main/resources/icons/hp_paper.png
 * src/main/resources/icons/hp_scissors.png
 * src/main/resources/icons/hp_glasses.png
 * src/main/resources/icons/hp_bolt.png
 * src/main/resources/images/hp_bg.jpg
 */
public class MainFrame extends JFrame {

    private final JButton pedraButton;
    private final JButton papelButton;
    private final JButton tesouraButton;
    private final JLabel resultLabel;
    private final JLabel scoreLabel;
    private final JPanel buttonsContainer;

    private int userScore = 0;
    private int cpuScore = 0;

    // Strategy para CPU (mantém padrão Strategy)
    private final ComputerStrategy cpuStrategy = new RandomStrategy();

    public MainFrame() {
        super(- "RPS - Hogwarts Edition");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(760, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // --- Background image (se existir) ---
        JLabel background = null;
        ImageIcon bgIcon = loadIcon("/images/hp_bg.jpg", getWidth(), getHeight());
        if (bgIcon != null) {
            background = new JLabel(bgIcon);
            background.setLayout(new BorderLayout());
            setContentPane(background);
        } else {
            // fallback: cor escura
            getContentPane().setBackground(new Color(18, 18, 20));
        }

        // --- Title with glasses icon + lightning bolt icon ---
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 12));
        titlePanel.setOpaque(false);

        ImageIcon glasses = loadIcon("/icons/hp_glasses.png", 36, 20);
        ImageIcon bolt = loadIcon("/icons/hp_bolt.png", 28, 28);

        JLabel glassesLabel = new JLabel(glasses);
        JLabel boltLabel = new JLabel(bolt);

        JLabel titleText = new JLabel("✧ Pedra, Papel e Tesoura — Wizarding Edition ✧");
        titleText.setFont(new Font("Trajan Pro", Font.BOLD, 26)); // se não tiver, usa uma serif
        titleText.setForeground(new Color(218, 165, 32)); // dourado

        if (glasses != null) titlePanel.add(glassesLabel);
        titlePanel.add(titleText);
        if (bolt != null) titlePanel.add(boltLabel);

        add(titlePanel, BorderLayout.NORTH);

        // --- Center: buttons + small spacing for background image to be visible ---
        buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 16));
        buttonsContainer.setOpaque(false);
        buttonsContainer.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        // Botões menores (para caber o fundo)
        pedraButton = createIconButton("Pedra", "/icons/hp_rock.png");
        papelButton = createIconButton("Papel", "/icons/hp_paper.png");
        tesouraButton = createIconButton("Tesoura", "/icons/hp_scissors.png");

        buttonsContainer.add(pedraButton);
        buttonsContainer.add(papelButton);
        buttonsContainer.add(tesouraButton);

        // painel central transparente pra deixar imagem de fundo visível
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(buttonsContainer);
        add(centerWrapper, BorderLayout.CENTER);

        // --- South: result + score ---
        JPanel south = new JPanel(new BorderLayout());
        south.setOpaque(false);
        south.setBorder(BorderFactory.createEmptyBorder(8, 20, 16, 20));

        resultLabel = new JLabel("Escolha sua jogada!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Georgia", Font.BOLD, 20));
        resultLabel.setForeground(new Color(230, 230, 230));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        scoreLabel = new JLabel(buildScoreText(), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        scoreLabel.setForeground(new Color(218, 165, 32));

        south.add(resultLabel, BorderLayout.CENTER);
        south.add(scoreLabel, BorderLayout.SOUTH);

        add(south, BorderLayout.SOUTH);

        // --- Actions: usa MoveFactory / RandomStrategy para manter padrão existente ---
        pedraButton.addActionListener((ActionEvent e) -> makePlay(MoveFactory.create(MoveType.ROCK), pedraButton));
        papelButton.addActionListener((ActionEvent e) -> makePlay(MoveFactory.create(MoveType.PAPER), papelButton));
        tesouraButton.addActionListener((ActionEvent e) -> makePlay(MoveFactory.create(MoveType.SCISSORS), tesouraButton));
    }

    // Cria botão com ícone pequeno + texto embaixo (vertical)
    private JButton createIconButton(String text, String iconPath) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setPreferredSize(new Dimension(120, 110));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(120, 80, 20), 3, true),
                BorderFactory.createEmptyBorder(6,6,6,6)
        ));

        // ícone
        ImageIcon icon = loadIcon(iconPath, 64, 64);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // texto abaixo do ícone (nome pequeno)
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Dialog", Font.BOLD, 13));
        lbl.setForeground(new Color(245, 245, 220));

        btn.add(iconLabel, BorderLayout.CENTER);
        btn.add(lbl, BorderLayout.SOUTH);

        // mouse cursor
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // Lógica do jogo (usa MoveFactory e RandomStrategy)
    private void makePlay(Move userMove, JButton clickedButton) {
        // CPU via Strategy
        Move cpuMove = cpuStrategy.nextMove();

        // Decide resultado
        int result = decide(userMove.type(), cpuMove.type()); // 1=user win,0=draw,-1=cpu win

        if (result > 0) {
            userScore++;
            resultLabel.setText("Você venceu! " + userMove.name() + " ✦ " + cpuMove.name());
            resultLabel.setForeground(new Color(120, 200, 80)); // verde
            highlightButton(clickedButton, new Color(255, 215, 0)); // dourado
        } else if (result < 0) {
            cpuScore++;
            resultLabel.setText("CPU venceu! " + cpuMove.name() + " ✦ " + userMove.name());
            resultLabel.setForeground(new Color(220, 80, 80));
            // encontre botão do CPU (visual not available): flash clickedButton red
            highlightButton(clickedButton, new Color(178, 34, 34));
        } else {
            resultLabel.setText("Empate! Ambos escolheram " + userMove.name());
            resultLabel.setForeground(new Color(200, 200, 200));
            // efeito de empate: flash todos
            flashAllButtons(new Color(120, 120, 200));
        }

        scoreLabel.setText(buildScoreText());
    }

    // Decide vencedor
    private int decide(MoveType a, MoveType b) {
        if (a == b) return 0;
        if (a == MoveType.ROCK && b == MoveType.SCISSORS) return 1;
        if (a == MoveType.SCISSORS && b == MoveType.PAPER) return 1;
        if (a == MoveType.PAPER && b == MoveType.ROCK) return 1;
        return -1;
    }

    // Highlight simples no botão vencedor (borda + flash)
    private void highlightButton(JButton btn, Color highlightColor) {
        Color original = ((LineBorder) ((javax.swing.border.CompoundBorder) btn.getBorder())
                .getOutsideBorder()).getLineColor();
        // mudar borda temporariamente
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(highlightColor, 4, true),
                BorderFactory.createEmptyBorder(6,6,6,6)
        ));

        // flash via Timer (reverte após 700ms)
        Timer t = new Timer(700, e -> {
            btn.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(original, 3, true),
                    BorderFactory.createEmptyBorder(6,6,6,6)
            ));
            ((Timer) e.getSource()).stop();
        });
        t.setRepeats(false);
        t.start();
    }

    // Flash em todos os botões (empate)
    private void flashAllButtons(Color flashColor) {
        JButton[] arr = new JButton[]{pedraButton, papelButton, tesouraButton};
        Timer t = new Timer(120, null);
        final int[] times = {0};
        t.addActionListener(e -> {
            for (JButton b : arr) {
                // alterna fundo do ícone (simples). usamos border para destaque
                b.setBorder((times[0] % 2 == 0)
                        ? BorderFactory.createCompoundBorder(new LineBorder(flashColor, 3, true), BorderFactory.createEmptyBorder(6,6,6,6))
                        : BorderFactory.createCompoundBorder(new LineBorder(new Color(120,80,20), 3, true), BorderFactory.createEmptyBorder(6,6,6,6))
                );
            }
            times[0]++;
            if (times[0] > 6) {
                ((Timer) e.getSource()).stop();
                // restaura bordas
                for (JButton b : arr) {
                    b.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(120,80,20), 3, true), BorderFactory.createEmptyBorder(6,6,6,6)));
                }
            }
        });
        t.start();
    }

    // Utility: monta texto do placar
    private String buildScoreText() {
        return String.format("Placar — Você: %d   |   CPU: %d", userScore, cpuScore);
    }

    // Carrega ícone do classpath (src/main/resources), retorna null se não existir
    private ImageIcon loadIcon(String resourcePath, int w, int h) {
        try {
            URL res = getClass().getResource(resourcePath);
            if (res == null) return null;
            Image img = Toolkit.getDefaultToolkit().getImage(res);
            Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception ex) {
            System.err.println("Falha ao carregar recurso: " + resourcePath + " -> " + ex.getMessage());
            return null;
        }
    }
}
