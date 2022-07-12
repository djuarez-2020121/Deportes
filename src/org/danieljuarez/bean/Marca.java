
package org.danieljuarez.bean;

public class Marca {
    private int codigoMarca;
    private String descripcion;

    public Marca() {
    }

    public Marca(int codigoMarca, String descripcion) {
        this.codigoMarca = codigoMarca;
        this.descripcion = descripcion;
    }

    public int getCodigoMarca() {
        return codigoMarca;
    }

    public void setCodigoMarca(int codigoMarca) {
        this.codigoMarca = codigoMarca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    @Override
    public String toString() {
        return getCodigoMarca() +  " / "+getDescripcion();
    }
    
}
