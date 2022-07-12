package org.danieljuarez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.danieljuarez.system.Principal;

public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaCategoria(){
        escenarioPrincipal.ventanaCategoria();
    }
    
    public void ventanaTalla(){
        escenarioPrincipal.ventanaTalla();
        
    }
    
    public void ventanaMarca(){
        escenarioPrincipal.ventanaMarca();
    }
    
    public void ventanaProducto(){
        escenarioPrincipal.ventanaProducto();
    }
    
}
