package com.consultalog;

import com.consultalog.gui.LogSearchGUI;
import javax.swing.SwingUtilities;

/**
 * Classe principal da aplicação.
 * Contém o método main para iniciar a interface gráfica.
 */
public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater garante que a criação e atualização da interface gráfica
        // ocorra na Thread de Despacho de Eventos (EDT - Event Dispatch Thread).
        // Isso é crucial para a estabilidade e responsividade de aplicações Swing.
        // A sintaxe "() -> { ... }" é uma expressão lambda que substitui a classe anônima de Runnable.
        SwingUtilities.invokeLater(() -> {
            LogSearchGUI gui = new LogSearchGUI(); // Cria uma nova instância da janela da GUI
            gui.setVisible(true); // Torna a janela visível para o usuário
        });
    }
}