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
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import modelo.Cuenta;
import modelo.CuentaAhorros;
import modelo.CuentaCorriente;

public class VistaBanco extends JFrame implements ActionListener {

    // ============ PALETA DE COLORES ============
    private static final Color C_FONDO      = new Color(0xF1F5F9);
    private static final Color C_CARD       = Color.WHITE;
    private static final Color C_PRIMARIO   = new Color(0x2563EB);
    private static final Color C_EXITO      = new Color(0x16A34A);
    private static final Color C_PELIGRO    = new Color(0xDC2626);
    private static final Color C_NEUTRO     = new Color(0x64748B);
    private static final Color C_TEXTO      = new Color(0x1E293B);
    private static final Color C_TEXTO_MUT  = new Color(0x64748B);
    private static final Color C_BORDE      = new Color(0xCBD5E1);
    private static final Color C_HEADER     = new Color(0x1E293B);

    // ============ FUENTES ============
    private static final Font F_TITULO   = new Font("SansSerif", Font.BOLD, 22);
    private static final Font F_SUBT     = new Font("SansSerif", Font.BOLD, 13);
    private static final Font F_NORMAL   = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font F_BOTON    = new Font("SansSerif", Font.BOLD, 12);

    private ControladorBanco controlador;

    // Pestaña Crear Cuenta
    private JTextField txtNumero, txtNombre, txtDia, txtMes, txtAnio, txtSaldoInicial, txtCupoSobregiro;
    private JComboBox<String> cbTipoCuenta;
    private JButton btnCrear;

    // Pestaña Operaciones
    private JTextField txtNumeroOp, txtValorOp;
    private JButton btnConsignar, btnRetirar, btnConsultarSaldo, btnImprimirDatos, btnListar;

    // Salida y botones inferiores
    private JTextPane areaSalida;
    private JButton btnLimpiar, btnSalir;

    public VistaBanco() {
        controlador = new ControladorBanco();
        initComponents();
        setTitle("Sistema Bancario");
        setSize(980, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mostrarInfo("Sistema iniciado",
                "Bienvenido al Sistema Bancario.<br>"
              + "1. Cree una cuenta en la pestaña <b>Crear Cuenta</b>.<br>"
              + "2. Realice operaciones en la pestaña <b>Operaciones</b>.",
                "info");
    }

    private void initComponents() {
        getContentPane().setBackground(C_FONDO);
        setLayout(new BorderLayout());

        // ============ HEADER ============
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(C_HEADER);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel lblTitulo = new JLabel("🏦  Sistema Bancario");
        lblTitulo.setFont(F_TITULO);
        lblTitulo.setForeground(Color.WHITE);
        header.add(lblTitulo, BorderLayout.WEST);

        JLabel lblSub = new JLabel("Cuentas de Ahorro y Corriente");
        lblSub.setFont(F_NORMAL);
        lblSub.setForeground(new Color(0xCBD5E1));
        header.add(lblSub, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(F_SUBT);
        tabs.setBackground(C_FONDO);
        tabs.setBorder(new EmptyBorder(12, 12, 6, 12));
        tabs.addTab("  Crear Cuenta  ", crearPanelCrearCuenta());
        tabs.addTab("  Operaciones  ", crearPanelOperaciones());

        areaSalida = new JTextPane();
        areaSalida.setContentType("text/html");
        areaSalida.setEditable(false);
        areaSalida.setBackground(C_CARD);
        areaSalida.setBorder(new EmptyBorder(10, 12, 10, 12));

        JScrollPane scroll = new JScrollPane(areaSalida);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(0, 12, 6, 12),
                BorderFactory.createLineBorder(C_BORDE)));

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(C_FONDO);
        panelCentral.add(tabs, BorderLayout.NORTH);
        panelCentral.add(scroll, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelInferior.setBackground(C_FONDO);
        panelInferior.setBorder(new EmptyBorder(0, 12, 12, 12));

        btnLimpiar = crearBoton("Limpiar salida", C_NEUTRO);
        btnSalir   = crearBoton("Salir", C_PELIGRO);
        panelInferior.add(btnLimpiar);
        panelInferior.add(btnSalir);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel crearPanelCrearCuenta() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(C_FONDO);
        wrapper.setBorder(new EmptyBorder(8, 0, 8, 0));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(C_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(C_BORDE),
                new EmptyBorder(16, 20, 16, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        LocalDate hoy = LocalDate.now();

        txtNumero      = new JTextField(12);
        txtNombre      = new JTextField(16);
        txtDia         = new JTextField(String.valueOf(hoy.getDayOfMonth()), 4);
        txtMes         = new JTextField(String.valueOf(hoy.getMonthValue()), 4);
        txtAnio        = new JTextField(String.valueOf(hoy.getYear()), 6);
        txtSaldoInicial= new JTextField("0", 10);
        txtCupoSobregiro = new JTextField("0", 10);
        cbTipoCuenta   = new JComboBox<>(new String[]{"Ahorros", "Corriente"});

        // Fila 0
        gbc.gridx=0; gbc.gridy=0; card.add(etiqueta("Número de cuenta:"), gbc);
        gbc.gridx=1; card.add(txtNumero, gbc);
        gbc.gridx=2; card.add(etiqueta("Nombre del dueño:"), gbc);
        gbc.gridx=3; card.add(txtNombre, gbc);

        // Fila 1
        gbc.gridx=0; gbc.gridy=1; card.add(etiqueta("Día:"), gbc);
        gbc.gridx=1; card.add(txtDia, gbc);
        gbc.gridx=2; card.add(etiqueta("Mes:"), gbc);
        gbc.gridx=3; card.add(txtMes, gbc);

        // Fila 2
        gbc.gridx=0; gbc.gridy=2; card.add(etiqueta("Año:"), gbc);
        gbc.gridx=1; card.add(txtAnio, gbc);
        gbc.gridx=2; card.add(etiqueta("Saldo inicial:"), gbc);
        gbc.gridx=3; card.add(txtSaldoInicial, gbc);

        // Fila 3
        gbc.gridx=0; gbc.gridy=3; card.add(etiqueta("Tipo de cuenta:"), gbc);
        gbc.gridx=1; card.add(cbTipoCuenta, gbc);
        gbc.gridx=2; card.add(etiqueta("Cupo sobregiro:"), gbc);
        gbc.gridx=3; card.add(txtCupoSobregiro, gbc);

        // Fila 4 (botón)
        gbc.gridx=3; gbc.gridy=4;
        gbc.anchor = GridBagConstraints.EAST;
        btnCrear = crearBoton("Crear Cuenta", C_PRIMARIO);
        card.add(btnCrear, gbc);

        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel crearPanelOperaciones() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(C_FONDO);
        wrapper.setBorder(new EmptyBorder(8, 0, 8, 0));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(C_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(C_BORDE),
                new EmptyBorder(16, 20, 16, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNumeroOp = new JTextField(12);
        txtValorOp  = new JTextField(10);

        gbc.gridx=0; gbc.gridy=0; card.add(etiqueta("Número de cuenta:"), gbc);
        gbc.gridx=1; card.add(txtNumeroOp, gbc);
        gbc.gridx=2; card.add(etiqueta("Valor:"), gbc);
        gbc.gridx=3; card.add(txtValorOp, gbc);

        btnConsignar      = crearBoton("Consignar", C_EXITO);
        btnRetirar        = crearBoton("Retirar", C_PELIGRO);
        btnConsultarSaldo = crearBoton("Consultar saldo", C_PRIMARIO);
        btnImprimirDatos  = crearBoton("Imprimir datos", C_PRIMARIO);
        btnListar         = crearBoton("Listar cuentas", C_NEUTRO);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx=0; gbc.gridy=1; card.add(btnConsignar, gbc);
        gbc.gridx=1; card.add(btnRetirar, gbc);
        gbc.gridx=2; card.add(btnConsultarSaldo, gbc);
        gbc.gridx=3; card.add(btnImprimirDatos, gbc);

        gbc.gridx=3; gbc.gridy=2; gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        card.add(btnListar, gbc);

        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    private JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(F_NORMAL);
        l.setForeground(C_TEXTO);
        return l;
    }

    private JButton crearBoton(String texto, Color fondo) {
        JButton btn = new JButton(texto);
        btn.setFont(F_BOTON);
        btn.setForeground(Color.WHITE);
        btn.setBackground(fondo);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnCrear) {
                crearCuenta();
            } else if (e.getSource() == btnConsignar) {
                String num = txtNumeroOp.getText().trim();
                double val = leerDouble(txtValorOp, "Valor");
                String r = controlador.consignar(num, val);
                mostrarInfo("Consignación - Cuenta " + num, escapar(r), detectarTipo(r));
            } else if (e.getSource() == btnRetirar) {
                String num = txtNumeroOp.getText().trim();
                double val = leerDouble(txtValorOp, "Valor");
                String r = controlador.retirar(num, val);
                mostrarInfo("Retiro - Cuenta " + num, escapar(r), detectarTipo(r));
            } else if (e.getSource() == btnConsultarSaldo) {
                String num = txtNumeroOp.getText().trim();
                String r = controlador.consultarSaldo(num);
                mostrarInfo("Consulta de saldo", escapar(r), detectarTipo(r));
            } else if (e.getSource() == btnImprimirDatos) {
                String num = txtNumeroOp.getText().trim();
                String r = controlador.imprimirDatos(num);
                mostrarInfo("Datos de la cuenta", escapar(r).replace("\n", "<br>"), detectarTipo(r));
            } else if (e.getSource() == btnListar) {
                listarCuentas();
            } else if (e.getSource() == btnLimpiar) {
                limpiarSalida();
            } else if (e.getSource() == btnSalir) {
                System.exit(0);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dato inválido", JOptionPane.ERROR_MESSAGE);
            mostrarInfo("Error de validación", escapar(ex.getMessage()), "error");
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
        mostrarInfo("Creación de cuenta", escapar(resultado).replace("\n", "<br>"), detectarTipo(resultado));
    }

    private void listarCuentas() {
        List<Cuenta> cuentas = controlador.getCuentas();
        if (cuentas.isEmpty()) {
            mostrarInfo("Listado de cuentas", "No hay cuentas registradas.", "info");
            return;
        }
        mostrarInfo("Listado de cuentas",
                "Se encontraron <b>" + cuentas.size() + "</b> cuenta(s) registradas.", "info");

        for (Cuenta c : cuentas) {
            String tipo = (c instanceof CuentaAhorros) ? "Cuenta de Ahorros"
                        : (c instanceof CuentaCorriente) ? "Cuenta Corriente"
                        : "Cuenta";
            String color = (c.consultarSaldo() >= 0) ? "#16A34A" : "#DC2626";
            String extra = "";
            if (c instanceof CuentaCorriente) {
                extra = " &nbsp;|&nbsp; <span style='color:#64748B;'>Cupo sobregiro:</span> "
                      + "<b style='color:#1E293B;'>" + formatoMoneda(((CuentaCorriente) c).getCupoSobregiro()) + "</b>";
            }

            String html = "<div style='margin:8px 0; padding:12px 16px; background:#F8FAFC; "
                        + "border-left:5px solid " + color + ";'>"
                        + "<div style='font-family:SansSerif; font-size:13px; font-weight:bold; color:#1E293B;'>"
                        + "💳 " + tipo + " &nbsp;#" + c.getNumero() + "</div>"
                        + "<div style='font-family:SansSerif; font-size:12px; color:#1E293B; margin-top:4px;'>"
                        + "<span style='color:#64748B;'>Titular:</span> " + c.getNombreDueno()
                        + " &nbsp;|&nbsp; <span style='color:#64748B;'>Apertura:</span> " + c.getFechaApertura()
                        + extra
                        + "</div>"
                        + "<div style='font-family:SansSerif; font-size:14px; margin-top:6px; color:" + color + "; font-weight:bold;'>"
                        + "Saldo: " + formatoMoneda(c.consultarSaldo())
                        + "</div>"
                        + "</div>";

            insertarHTML(html);
        }
    }

    private void mostrarInfo(String titulo, String contenidoHTML, String tipo) {
        String color;
        String icono;
        switch (tipo) {
            case "exito": color = "#16A34A"; icono = "✔"; break;
            case "error": color = "#DC2626"; icono = "✖"; break;
            default:      color = "#2563EB"; icono = "ℹ";
        }

        String html = "<div style='margin:6px 0; padding:12px 16px; background:#FFFFFF; "
                    + "border-left:5px solid " + color + ";'>"
                    + "<div style='font-family:SansSerif; font-size:12px; font-weight:bold; color:" + color + ";'>"
                    + icono + " " + titulo + "</div>"
                    + "<div style='font-family:SansSerif; font-size:12px; color:#1E293B; margin-top:4px;'>"
                    + contenidoHTML + "</div>"
                    + "</div>";

        insertarHTML(html);
    }

    private void insertarHTML(String html) {
        try {
            HTMLEditorKit kit = (HTMLEditorKit) areaSalida.getEditorKit();
            HTMLDocument doc = (HTMLDocument) areaSalida.getDocument();
            kit.insertHTML(doc, doc.getLength(), html, 0, 0, null);
            areaSalida.setCaretPosition(doc.getLength());
        } catch (Exception ex) {
            System.err.println("Error insertando HTML: " + ex.getMessage());
        }
    }

    private String detectarTipo(String respuesta) {
        String lower = respuesta.toLowerCase();
        if (lower.startsWith("error") || lower.contains("no existe") || lower.contains("insuficiente")) {
            return "error";
        }
        if (lower.contains("exitoso") || lower.contains("exitosamente")) {
            return "exito";
        }
        return "info";
    }

    private String escapar(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private String formatoMoneda(double valor) {
        return String.format("$%,.2f", valor);
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

    private void limpiarSalida() {
        areaSalida.setText("");
        areaSalida.setContentType("text/html");
    }
}