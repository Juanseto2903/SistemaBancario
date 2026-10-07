/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author juans
 */

public class CuentaCorriente extends Cuenta {
    private double cupoSobregiro;

    public CuentaCorriente(String numero, String nombreDueno, Fecha fechaApertura,
                           double saldoInicial, double cupoSobregiro) {
        super(numero, nombreDueno, fechaApertura, saldoInicial);
        this.cupoSobregiro = cupoSobregiro;
    }

    public CuentaCorriente(String numero, String nombreDueno, double cupoSobregiro) {
        super(numero, nombreDueno);
        this.cupoSobregiro = cupoSobregiro;
    }

    @Override
    public String retirar(double valor) {
        if (valor <= 0) {
            return "Error: el valor a retirar debe ser mayor que cero.";
        }
        double disponible = saldo + cupoSobregiro;
        if (valor > disponible) {
            return "Error: excede el cupo total disponible (" + disponible + ").";
        }
        saldo -= valor;
        String msg = "Retiro exitoso. Nuevo saldo: " + saldo;
        if (saldo < 0) {
            msg += " (usando sobregiro)";
        }
        return msg;
    }

    public double getCupoSobregiro() { return cupoSobregiro; }

    @Override
    public String imprimirDatos() {
        return super.imprimirDatos() + "\nCupo sobregiro: " + cupoSobregiro;
    }

    @Override
    public String toString() {
        return "Cuenta Corriente [" + super.imprimirDatos() + ", cupo=" + cupoSobregiro + "]";
    }
}
