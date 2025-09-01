package com.nathalia.rps;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.nathalia.rps.ui.MainFrame;

import javax.swing.*;

/**
 * App (Entry point)
 * Padrões usados:
 *  - Strategy (estratégias do computador)
 *  - Factory Method (criação de jogadas)
 *  - Singleton (gerenciador/placar global)
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Tema bonito
            FlatLaf.registerCustomDefaultsSource("themes");
            FlatMacLightLaf.setup();
            // Suavizar fontes
            System.setProperty("awt.useSystemAAFontSettings","on");
            System.setProperty("swing.aatext", "true");
            new MainFrame().setVisible(true);
        });
    }
}
