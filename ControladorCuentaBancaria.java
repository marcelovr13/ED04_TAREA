package ed.tarea04;

/**
 * @author Marcelo Valenzuela Rabela
 * @version 11.20
 */

public class ControladorCuentaBancaria {

    public static void main(String[] args) {

        // crea
        CuentaBancaria Micuenta = new CuentaBancaria("Jorge Izquierdo", "00491500051234567892");

        MetodosCuentaBancaria(Micuenta);

        System.out.println(Micuenta.toString());
    }

    public static void MetodosCuentaBancaria(CuentaBancaria micuenta) {
        try {
            micuenta.ingresar(10000);
        } catch (Exception e) {
            System.err.print(e.getMessage());
            System.out.println("Error en el método ingresar. ");
        }

        try {
            micuenta.retirar(1500);
        } catch (Exception e) {
            System.err.print(e.getMessage());
            System.out.println("Error en el método retirar. ");
        }
    }

}
