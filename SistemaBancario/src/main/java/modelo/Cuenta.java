/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author juans
 */

public abstract class Cuenta {
    private String numero;
    private String nombreDueno;
    private Fecha fechaApertura;
    protected double saldo; // protected para que las subclases lo modifiquen

    // Constructor completo
    public Cuenta(String numero, String nombreDueno, Fecha fechaApertura, double saldoInicial) {
        this.numero = numero;
        this.nombreDueno = nombreDueno;
        this.fechaApertura = fechaApertura;
        this.saldo = saldoInicial;
    }

    // Constructor sobrecargado: sin fecha ni saldo
    public Cuenta(String numero, String nombreDueno) {
        this(numero, nombreDueno, Fecha.hoy(), 0);
    }

    // Método concreto compartido
    public void consignar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor a consignar debe ser mayor que cero.");
        }
        saldo += valor;
    }

    // Método abstracto: cada subclase lo implementa a su manera (polimorfismo)
    public abstract String retirar(double valor);

    public double consultarSaldo() {
        return saldo;
    }

    // Método sobrecargado: imprimir todos los datos
    public String imprimirDatos() {
        return "Número: " + numero
                + "\nDueño: " + nombreDueno
                + "\nFecha apertura: " + fechaApertura
                + "\nSaldo: " + saldo;
    }

    // Método sobrecargado: imprimir solo saldo o todos los datos
    public String imprimirDatos(boolean soloSaldo) {
        if (soloSaldo) {
            return "Saldo de la cuenta " + numero + ": " + saldo;
        }
        return imprimirDatos();
    }

    // Getters
    public String getNumero() { return numero; }
    public String getNombreDueno() { return nombreDueno; }
    public Fecha getFechaApertura() { return fechaApertura; }
}
