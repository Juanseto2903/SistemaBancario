/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemabancario;

/**
 *
 * @author juans
 */

import javax.swing.SwingUtilities;
import vista.VistaBanco;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VistaBanco().setVisible(true);
            }
        });
    }
}
