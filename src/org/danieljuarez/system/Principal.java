
package org.danieljuarez.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.danieljuarez.controller.CategoriaController;
import org.danieljuarez.controller.MarcaController;
import org.danieljuarez.controller.MenuPrincipalController;
import org.danieljuarez.controller.ProductoController;
import org.danieljuarez.controller.TallaController;

public class Principal extends Application {
    private final String PAQUETE_VISTA="/org/danieljuarez/view/"; //ruta de las vistas.
    private Stage escenarioPrincipal;
    private Scene escena;
    @Override
    public void start(Stage escenarioPrincipal) {
       this.escenarioPrincipal=escenarioPrincipal;
       this.escenarioPrincipal.setTitle("Ventas 2021");
       //escenarioPrincipal.getIcons().add(new Image("/org/danieljuarez/images/portada.png"));
       menuPrincipal();
       escenarioPrincipal.show();
        
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController) cambiarEscena ("MenuPrincipalVentasView.fxml",575,376);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCategoria(){
        try{
            CategoriaController categoria = (CategoriaController) cambiarEscena("CategoriasView.fxml",832,415);
            categoria.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTalla(){
        try{
            TallaController talla = (TallaController) cambiarEscena("TallasView.fxml",832,415);
            talla.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void ventanaMarca(){
        try{
            MarcaController marca = (MarcaController) cambiarEscena("MarcasView.fxml",832,415);
            marca.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProducto(){
        try{
            ProductoController producto = (ProductoController) cambiarEscena ("ProductosView.fxml",1032,487);
            producto.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    public Initializable cambiarEscena(String fxml,int ancho, int alto) throws IOException{
        Initializable resultado=null;
        FXMLLoader cargadorFXML=new FXMLLoader();
        InputStream archivo=Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena=new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado=(Initializable)cargadorFXML.getController();
        return resultado;
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
