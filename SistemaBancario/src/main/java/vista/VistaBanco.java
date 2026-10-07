/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author juans
 */

import controlador.ControladorBanco;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VistaBanco extends JFrame implements ActionListener {

    private final ControladorBanco controlador;

    // --- Pestaña Crear Cuenta ---
    private JTextField txtNumero;
    private JTextField txtNombre;
    private JTextField txtDia;
    private JTextField txtMes;
    private JTextField txtAnio;
    private JTextField txtSaldoInicial;
    private JTextField txtCupoSobregiro;
    private JComboBox<String> cbTipoCuenta;
    private JButton btnCrear;

    // --- Pestaña Operaciones ---
    private JTextField txtNumeroOp;
    private JTextField txtValorOp;
    private JButton btnConsignar;
    private JButton btnRetirar;
    private JButton btnConsultarSaldo;
    private JButton btnImprimirDatos;
    private JButton btnListar;

    // --- Salida ---
    private JTextArea areaSalida;
    private JButton btnLimpiar;
    private JButton btnSalir;

    public VistaBanco() {
        controlador = new ControladorBanco();
        initComponents();
        setTitle("Sistema Bancario - MVC");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        areaSalida.append("Bienvenido al Sistema Bancario.\n");
        areaSalida.append("1) Cree una cuenta en la pestaña 'Crear Cuenta'.\n");
        areaSalida.append("2) Realice operaciones en la pestaña 'Operaciones'.\n\n");
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Crear Cuenta", crearPanelCrearCuenta());
        tabs.addTab("Operaciones", crearPanelOperaciones());
        add(tabs, BorderLayout.NORTH);

        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        add(new JScrollPane(areaSalida), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnLimpiar = new JButton("Limpiar");
        btnSalir = new JButton("Salir");
        btnLimpiar.addActionListener(this);
        btnSalir.addActionListener(this);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelCrearCuenta() {
        JPanel panel = new JPanel(new GridLayout(0, 4, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        LocalDate hoy = LocalDate.now();

        panel.add(new JLabel("Número de cuenta:"));
        txtNumero = new JTextField();
        panel.add(txtNumero);
        panel.add(new JLabel("Nombre del dueño:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Día (1-31):"));
        txtDia = new JTextField(String.valueOf(hoy.getDayOfMonth()));
        panel.add(txtDia);
        panel.add(new JLabel("Mes (1-12):"));
        txtMes = new JTextField(String.valueOf(hoy.getMonthValue()));
        panel.add(txtMes);

        panel.add(new JLabel("Año:"));
        txtAnio = new JTextField(String.valueOf(hoy.getYear()));
        panel.add(txtAnio);
        panel.add(new JLabel("Saldo inicial:"));
        txtSaldoInicial = new JTextField("0");
        panel.add(txtSaldoInicial);

        panel.add(new JLabel("Tipo de cuenta:"));
        cbTipoCuenta = new JComboBox<>(new String[]{"Ahorros", "Corriente"});
        panel.add(cbTipoCuenta);
        panel.add(new JLabel("Cupo sobregiro:"));
        txtCupoSobregiro = new JTextField("0");
        panel.add(txtCupoSobregiro);

        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        btnCrear = new JButton("Crear Cuenta");
        btnCrear.addActionListener(this);
        panel.add(btnCrear);

        return panel;
    }

    private JPanel crearPanelOperaciones() {
        JPanel panel = new JPanel(new GridLayout(0, 4, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Número de cuenta:"));
        txtNumeroOp = new JTextField();
        panel.add(txtNumeroOp);
        panel.add(new JLabel("Valor:"));
        txtValorOp = new JTextField();
        panel.add(txtValorOp);

        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        btnConsignar = new JButton("Consignar");
        btnRetirar = new JButton("Retirar");
        btnConsultarSaldo = new JButton("Consultar saldo");
        btnImprimirDatos = new JButton("Imprimir datos");
        btnListar = new JButton("Listar cuentas");

        btnConsignar.addActionListener(this);
        btnRetirar.addActionListener(this);
        btnConsultarSaldo.addActionListener(this);
        btnImprimirDatos.addActionListener(this);
        btnListar.addActionListener(this);

        panel.add(btnConsignar);
        panel.add(btnRetirar);
        panel.add(btnConsultarSaldo);
        panel.add(btnImprimirDatos);

        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(btnListar);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnCrear) {
                crearCuenta();
            } else if (e.getSource() == btnConsignar) {
                String num = txtNumeroOp.getText().trim();
                double val = leerDouble(txtValorOp, "Valor");
                mostrar(controlador.consignar(num, val));
            } else if (e.getSource() == btnRetirar) {
                String num = txtNumeroOp.getText().trim();
                double val = leerDouble(txtValorOp, "Valor");
                mostrar(controlador.retirar(num, val));
            } else if (e.getSource() == btnConsultarSaldo) {
                String num = txtNumeroOp.getText().trim();
                mostrar(controlador.consultarSaldo(num));
            } else if (e.getSource() == btnImprimirDatos) {
                String num = txtNumeroOp.getText().trim();
                mostrar(controlador.imprimirDatos(num));
            } else if (e.getSource() == btnListar) {
                mostrar(controlador.listarCuentas());
            } else if (e.getSource() == btnLimpiar) {
                limpiar();
            } else if (e.getSource() == btnSalir) {
                System.exit(0);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dato inválido", JOptionPane.ERROR_MESSAGE);
            mostrar("Error: " + ex.getMessage());
        }
    }

    private void crearCuenta() {
        String numero = txtNumero.getText().trim();
        String nombre = txtNombre.getText().trim();
        if (numero.isEmpty() || nombre.isEmpty()) {
            throw new IllegalArgumentException("Número y nombre no pueden estar vacíos.");
        }
        int dia = (int) leerDouble(txtDia, "Día");
        int mes = (int) leerDouble(txtMes, "Mes");
        int anio = (int) leerDouble(txtAnio, "Año");
        double saldoInicial = leerDouble(txtSaldoInicial, "Saldo inicial");
        String tipo = (String) cbTipoCuenta.getSelectedItem();

        String resultado;
        if ("Ahorros".equals(tipo)) {
            resultado = controlador.crearCuentaAhorros(numero, nombre, dia, mes, anio, saldoInicial);
        } else {
            double cupo = leerDouble(txtCupoSobregiro, "Cupo sobregiro");
            resultado = controlador.crearCuentaCorriente(numero, nombre, dia, mes, anio, saldoInicial, cupo);
        }
        mostrar(resultado);
    }

    private double leerDouble(JTextField campo, String nombre) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombre + " no puede estar vacío.");
        }
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo " + nombre + " debe ser un número válido.");
        }
    }

    private void mostrar(String mensaje) {
        areaSalida.append(mensaje + "\n\n");
        areaSalida.setCaretPosition(areaSalida.getDocument().getLength());
    }

    private void limpiar() {
        txtNumero.setText("");
        txtNombre.setText("");
        txtSaldoInicial.setText("0");
        txtCupoSobregiro.setText("0");
        txtNumeroOp.setText("");
        txtValorOp.setText("");
        areaSalida.setText("");
    }
}
