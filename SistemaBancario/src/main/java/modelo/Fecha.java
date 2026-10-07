/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author juans
 */

import java.time.LocalDate;

public class Fecha {
    private int dia;
    private int mes;
    private int anio;

    public Fecha(int dia, int mes, int anio) {
        if (!esValida(dia, mes, anio)) {
            throw new IllegalArgumentException("Fecha inválida: " + dia + "/" + mes + "/" + anio);
        }
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public static Fecha hoy() {
        LocalDate h = LocalDate.now();
        return new Fecha(h.getDayOfMonth(), h.getMonthValue(), h.getYear());
    }

    private boolean esValida(int d, int m, int a) {
        if (m < 1 || m > 12) return false;
        if (d < 1 || d > 31) return false;
        if (a < 1900 || a > 2100) return false;
        return true;
    }

    public int getDia() { return dia; }
    public int getMes() { return mes; }
    public int getAnio() { return anio; }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%d", dia, mes, anio);
    }
}
