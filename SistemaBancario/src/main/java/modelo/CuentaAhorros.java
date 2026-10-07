/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author juans
 */

public class CuentaAhorros extends Cuenta {

    public CuentaAhorros(String numero, String nombreDueno, Fecha fechaApertura, double saldoInicial) {
        super(numero, nombreDueno, fechaApertura, saldoInicial);
    }

    public CuentaAhorros(String numero, String nombreDueno) {
        super(numero, nombreDueno);
    }

    @Override
    public String retirar(double valor) {
        if (valor <= 0) {
            return "Error: el valor a retirar debe ser mayor que cero.";
        }
        if (valor > saldo) {
            return "Error: saldo insuficiente. Saldo actual: " + saldo;
        }
        saldo -= valor;
        return "Retiro exitoso. Nuevo saldo: " + saldo;
    }

    @Override
    public String toString() {
        return "Cuenta de Ahorros [" + super.imprimirDatos() + "]";
    }
}
