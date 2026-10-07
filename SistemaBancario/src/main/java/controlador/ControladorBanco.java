/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author juans
 */

import java.util.ArrayList;
import modelo.Cuenta;
import modelo.CuentaAhorros;
import modelo.CuentaCorriente;
import modelo.Fecha;

public class ControladorBanco {
    private final ArrayList<Cuenta> cuentas;

    public ControladorBanco() {
        cuentas = new ArrayList<>();
    }

    public String crearCuentaAhorros(String numero, String nombre, int dia, int mes, int anio, double saldoInicial) {
        if (buscarCuenta(numero) != null) {
            return "Error: ya existe una cuenta con el número " + numero;
        }
        try {
            Fecha f = new Fecha(dia, mes, anio);
            Cuenta c = new CuentaAhorros(numero, nombre, f, saldoInicial);
            cuentas.add(c);
            return "Cuenta de ahorros creada exitosamente:\n" + c.imprimirDatos();
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String crearCuentaCorriente(String numero, String nombre, int dia, int mes, int anio,
                                       double saldoInicial, double cupoSobregiro) {
        if (buscarCuenta(numero) != null) {
            return "Error: ya existe una cuenta con el número " + numero;
        }
        if (cupoSobregiro < 0) {
            return "Error: el cupo de sobregiro no puede ser negativo.";
        }
        try {
            Fecha f = new Fecha(dia, mes, anio);
            Cuenta c = new CuentaCorriente(numero, nombre, f, saldoInicial, cupoSobregiro);
            cuentas.add(c);
            return "Cuenta corriente creada exitosamente:\n" + c.imprimirDatos();
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    public Cuenta buscarCuenta(String numero) {
        for (Cuenta c : cuentas) {
            if (c.getNumero().equals(numero)) {
                return c;
            }
        }
        return null;
    }

    public String consignar(String numero, double valor) {
        Cuenta c = buscarCuenta(numero);
        if (c == null) return "Error: no existe una cuenta con el número " + numero;
        try {
            c.consignar(valor);
            return "Consignación exitosa. Nuevo saldo: " + c.consultarSaldo();
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String retirar(String numero, double valor) {
        Cuenta c = buscarCuenta(numero);
        if (c == null) return "Error: no existe una cuenta con el número " + numero;
        return c.retirar(valor); // ← POLIMORFISMO: se ejecuta según el tipo real
    }

    public String consultarSaldo(String numero) {
        Cuenta c = buscarCuenta(numero);
        if (c == null) return "Error: no existe una cuenta con el número " + numero;
        return c.imprimirDatos(true); // solo saldo
    }

    public String imprimirDatos(String numero) {
        Cuenta c = buscarCuenta(numero);
        if (c == null) return "Error: no existe una cuenta con el número " + numero;
        return c.imprimirDatos();
    }

    public String listarCuentas() {
        if (cuentas.isEmpty()) return "No hay cuentas registradas.";
        StringBuilder sb = new StringBuilder("=== LISTADO DE CUENTAS (" + cuentas.size() + ") ===\n");
        for (Cuenta c : cuentas) {
            sb.append(c.imprimirDatos()).append("\n----------------\n");
        }
        return sb.toString();
    }
}
