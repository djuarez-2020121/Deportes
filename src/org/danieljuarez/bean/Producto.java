
package org.danieljuarez.bean;

public class Producto {
    private int codigoProducto;
    private String descripcion;
    private int existencia;
    private double precioUnitario;
    private double precioDocena;
    private double precionMayor;
    private int codigoCategoria;
    private int codigoTalla;
    private int codigoMarca;

    public Producto() {
    }

    public Producto(int codigoProducto, String descripcion, int existencia, double precioUnitario, double precioDocena, double precionMayor, int codigoCategoria, int codigoTalla, int codigoMarca) {
        this.codigoProducto = codigoProducto;
        this.descripcion = descripcion;
        this.existencia = existencia;
        this.precioUnitario = precioUnitario;
        this.precioDocena = precioDocena;
        this.precionMayor = precionMayor;
        this.codigoCategoria = codigoCategoria;
        this.codigoTalla = codigoTalla;
        this.codigoMarca = codigoMarca;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioDocena() {
        return precioDocena;
    }

    public void setPrecioDocena(double precioDocena) {
        this.precioDocena = precioDocena;
    }

    public double getPrecionMayor() {
        return precionMayor;
    }

    public void setPrecionMayor(double precionMayor) {
        this.precionMayor = precionMayor;
    }

    public int getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(int codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public int getCodigoTalla() {
        return codigoTalla;
    }

    public void setCodigoTalla(int codigoTalla) {
        this.codigoTalla = codigoTalla;
    }

    public int getCodigoMarca() {
        return codigoMarca;
    }

    public void setCodigoMarca(int codigoMarca) {
        this.codigoMarca = codigoMarca;
    }
    
    
}
