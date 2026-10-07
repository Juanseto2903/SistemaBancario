/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemabancario;

/**
 *
 * @author juans
 */

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import vista.VistaBanco;

public class Main {
    public static void main(String[] args) {
        // Esto hace que los botones respeten los colores
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Si falla, se queda con el que esta por defecto
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VistaBanco().setVisible(true);
            }
        });
    }
}
