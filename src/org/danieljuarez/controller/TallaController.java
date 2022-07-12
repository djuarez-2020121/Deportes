
package org.danieljuarez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.danieljuarez.bean.Talla;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.system.Principal;

public class TallaController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    private ObservableList <Talla> listaTalla;
    
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TableView tblTalla;
    @FXML private TableColumn colCodTalla;
    @FXML private TableColumn colDescripcion;
    @FXML private TextField txtCodTalla;
    @FXML private TextField txtDescripcion;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
    }
    
   
    
    public void cargarDatos(){
        tblTalla.setItems(getTalla());
        colCodTalla.setCellValueFactory(new PropertyValueFactory<Talla,Integer>("codigoTalla"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Talla,String>("descripcion"));
    }
    
    public ObservableList <Talla> getTalla(){
        ArrayList<Talla> lista = new ArrayList<Talla>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTallas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Talla (resultado.getInt("codigoTalla"),resultado.getString("descripcion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
        
        return listaTalla = FXCollections.observableArrayList(lista);
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Calcelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion=operaciones.GUARDAR;
            }break;
            case GUARDAR:{
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion=operaciones.NINGUNO;
                cargarDatos();
            }break;
        }
    }
    
    
    public void guardar(){
        Talla registro = new Talla();
        registro.setDescripcion(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTalla(?)}");
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.execute();
            listaTalla.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void eliminar(){
           switch(tipoDeOperacion){
               case GUARDAR:{
                   desactivarControles();
                   limpiarControles();
                   btnNuevo.setText("Nuevo");
                   btnEliminar.setText("Eliminar");
                   btnEditar.setDisable(false);
                   btnReporte.setDisable(false);
                   tipoDeOperacion=operaciones.NINGUNO;
                   
               }break;
               default: 
                   if(tblTalla.getSelectionModel().getSelectedItem() != null){
                       int respuesta = JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                       if(respuesta == JOptionPane.YES_NO_OPTION){
                           try{
                               PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTalla(?)}");
                               procedimiento.setInt(1,((Talla)tblTalla.getSelectionModel().getSelectedItem()).getCodigoTalla());
                               procedimiento.execute();
                               listaTalla.remove(tblTalla.getSelectionModel().getSelectedIndex());
                               limpiarControles();
                           }catch(Exception e){
                               e.printStackTrace();
                           }
                       }
                    }else{
                       JOptionPane.showMessageDialog(null,"Seleccione algo");
                    }
           }
    }
    
    
    public void seleccionarElemento(){
        if(tblTalla.getSelectionModel().getSelectedItem() == null){
           JOptionPane.showMessageDialog(null,"Seleccione algo");
        }else{
           txtCodTalla.setText(String.valueOf(((Talla)tblTalla.getSelectionModel().getSelectedItem()).getCodigoTalla()));
           txtDescripcion.setText(String.valueOf(((Talla)tblTalla.getSelectionModel().getSelectedItem()).getDescripcion()));
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
              if(tblTalla.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion=operaciones.ACTUALIZAR;
                }else{
                   JOptionPane.showMessageDialog(null,"Seleccione algo");
              }
                   
            }break;
            case ACTUALIZAR:{
                actualizar();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                limpiarControles();
                desactivarControles();
                cargarDatos();
                tipoDeOperacion=operaciones.NINGUNO;
            }break;
           
           
        }
    
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTalla(?,?)}");
            Talla registro = (Talla)tblTalla.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            procedimiento.setInt(1,registro.getCodigoTalla());
            procedimiento.setString(2,registro.getDescripcion());
            procedimiento.execute();
            cargarDatos();
            tipoDeOperacion = operaciones.NINGUNO;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void cancelar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                if(tblTalla.getSelectionModel().getSelectedItem() != null){
                    tipoDeOperacion=operaciones.ACTUALIZAR;
                }
            }break;
            case ACTUALIZAR:{
                actualizar();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                limpiarControles();
                desactivarControles();

                
            }
        }
    }
    

    
    
    public void desactivarControles(){
        txtCodTalla.setEditable(false);
        txtDescripcion.setEditable(false);
    }
    
    public void activarControles(){
        txtCodTalla.setEditable(false);
        txtDescripcion.setEditable(true);
        
    }
    public void limpiarControles(){
        txtCodTalla.clear();
        txtDescripcion.clear();
 
    }
   
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
}
