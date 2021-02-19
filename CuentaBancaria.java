package ed.tarea04;

/**
 * Clase CuentaBancaria, que permite el uso básico de una cuenta bancaria
 *
 * @author Marcelo Valenzuela Rabela
 * @version 11.20
 */

public class CuentaBancaria {
    // atributos de instancia
    private String titular;
    private double saldo;
    private String entidad;
    private String oficina;
    private String numCuenta;

    // atributos de clase
    private static final int TAM_MAX = 100;
    private static final int TAM_MIN = 10;

    /**
     * Constructor CuentaBancaria, que crea una cuenta bancaria con los parámetros de entrada suministrados
     *
     * @param titular Titular de la cuenta, que debe contener entre 10 y 100 caracteres
     * @param entidad Entidad bancaria, formada por 4 dígitos
     * @param oficina Oficina bancaria, formada por 4 dígitos
     * @param DC Dígito de control, formado por 2 dígitos (calculados a partir de la entidad, oficina y número de cuenta)
     * @param numCuenta Número de cuenta, formada por 10 dígitos
     */
    public CuentaBancaria (String titular, String entidad, String oficina, String DC, String numCuenta) {
        if (titular.length()>=TAM_MAX || titular.length()<=TAM_MIN)
            throw new IllegalArgumentException("El nombre del titular no es válido. Debe contener entre 10 y 100 caracteres. ");

        if (comprobarCCC(entidad+oficina+DC+numCuenta) == false)
            throw new IllegalArgumentException("El número de cuenta no es válido.");

        // llegados a este punto, todos los parámetros de entrada son correctos, por lo que procedemos a incluirlos en el objeto
        this.setTitular(titular);
        this.setEntidad(entidad);
        this.setOficina(oficina);
        this.setNumCuenta(numCuenta);
        setSaldo(0);
    }

    /**
     * Constructor CuentaBancaria, que crea una cuenta bancaria con los parámetros de entrada suministrados
     *
     * @param titular Titular de la cuenta, que debe contener entre 10 y 100 caracteres
     * @param CCC Código Cuenta Cliente, formado por 20 dígitos (4 dígitos de la entidad, 4 dígitos de la oficina, 2 dígitos de control y 10 dígitos del número de cuenta)
     */
    public CuentaBancaria (String titular, String CCC) {
        this (titular, CCC.substring(0,4), CCC.substring(4,8), CCC.substring(8,10), CCC.substring(10,20));
    }

    /**
     * Método ingresar, que aumenta el saldo de la cuenta bancaria con la cantidad pasada como parámetro de entrada
     *
     * @param cantidad importe decimal a ingresar en la cuenta bancaria
     */
    public void ingresar (double cantidad) {
        if (cantidad>=0)
            setSaldo(getSaldo() + cantidad);
        else
            throw new IllegalArgumentException("No es posible realizar la operación. La cantidad a ingresar debe ser un número positivo. ");
    }

    /**
     * Método retirar, que disminuye el saldo de la cuenta bancaria con la cantidad pasada como parámetro de entrada
     *
     * @param cantidad importe decimal a retirar en la cuenta bancaria
     */
    public void retirar (double cantidad) {
        if (cantidad>=0)
            if (cantidad <= getSaldo())
                setSaldo(getSaldo() - cantidad);
            else
                throw new IllegalArgumentException("No es posible realizar la operación. El saldo es inferior a la cantidad a retirar. ");
        else
            throw new IllegalArgumentException("No es posible realizar la operación. La cantidad a retirar debe ser un número positivo. ");
    }

    /**
     * Método comprobarCCC, que comprueba si el formato del Código de Cuenta Cliente pasado como parámetro de entrada es el adecuado
     *
     * @param CCC Código Cuenta Cliente, formado por 20 dígitos (4 dígitos de la entidad, 4 dígitos de la oficina, 2 dígitos de control y 10 dígitos del número de cuenta)
     * @return devuelve verdadero si el formato del CCC es el correcto, y devuelve falso en caso contrario
     */
    public static boolean comprobarCCC (String CCC) {
        String DC;

        DC = obtenerDigitosControl(CCC.substring(0,4), CCC.substring(4,8), CCC.substring(10,20));

        if (DC.equals(CCC.substring(8,10)))
            return true;
        else
            return false;
    }

    /**
     * Método obtenerDigitosControl, que devuelve el Dígito de Control a partir de los datos pasados como parámetros de entrada
     *
     * @param entidad Entidad bancaria, formada por 4 dígitos
     * @param oficina Oficina bancaria, formada por 4 dígitos
     * @param numCuenta Número de cuenta, formada por 10 dígitos
     * @return devuelve el Dígito de Control, formado por 2 dígitos (calculados a partir de la entidad, oficina y número de cuenta)
     */
    public static String obtenerDigitosControl (String entidad, String oficina, String numCuenta) {
        int [] peso={1,2,4,8,5,10,9,7,3,6};
        String entidadOficina="00"+entidad+oficina;
        String dc="";
        int digito1dc=0;
        int digito2dc=0;

        for(int contador=0;contador<=peso.length-1;contador++){
            digito1dc+=peso[contador]*(entidadOficina.charAt(contador)-48);
            digito2dc+=peso[contador]*(numCuenta.charAt(contador)-48);
        }

        digito1dc=11-(digito1dc%11);
        digito2dc=11-(digito2dc%11);

        digito1dc=digito1dc==11?0:digito1dc==10?1:digito1dc;
        digito2dc=digito2dc==11?0:digito2dc==10?1:digito2dc;

        dc=String.valueOf(digito1dc)+String.valueOf(digito2dc);
        return dc;
    }

    /**
     * Método toString, que muestra la información completa sobre la situación de la cuenta bancaria
     *
     * @return devuelve el nombre del titular, el CCC y el saldo de la cuenta bancaria de dicho titular
     */
    @Override
    public String toString() {
        String DC;

        DC = obtenerDigitosControl(this.getEntidad(), this.getOficina(), this.getNumCuenta());

        return  "Titular: " + this.getTitular() + " \n" +
                "Entidad: " + this.getEntidad() + " \n" +
                "Oficina: " + this.getOficina() + " \n" +
                "DC: " + DC + " \n" +
                "Número de cuenta: " + this.getNumCuenta() + " \n" +
                "Saldo: "+ this.getSaldo();
    }

    /**
     * Getter del atributo Titular
     * @return
     */
    public String getTitular() {
        return titular;
    }

    /**
     * Setter del atributo Titular como parámetro
     * @param titular
     */
    public void setTitular(String titular) {
        this.titular = titular;
    }

    /**
     * Getter del atributo Saldo
     * @return
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Setter del atributo saldo como parámetro
     * @param saldo
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Getter del atributo entidad
     * @return
     */
    public String getEntidad() {
        return entidad;
    }

    /**
     * Setter del atributo Entidad como parámetro
     * @param entidad
     */
    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    /**
     * Getter del atributo Oficina
     * @return
     */
    public String getOficina() {
        return oficina;
    }

    /**
     * Setter del atributo Oficina como parámetro
     * @param oficina
     */
    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    /**
     * Getter del atributo NumCuenta
     * @return
     */
    public String getNumCuenta() {
        return numCuenta;
    }

    /**
     * Setter del atributo NumCuenta como parámetro
     * @param numCuenta
     */
    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }
}
