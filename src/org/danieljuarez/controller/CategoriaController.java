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
import org.danieljuarez.bean.Categoria;

import org.danieljuarez.db.Conexion;
import org.danieljuarez.system.Principal;

public class CategoriaController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    private ObservableList <Categoria> listaCategoria;
    
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TableView tblCategoria;
    @FXML private TableColumn colCodigoCategoria;
    @FXML private TableColumn colDescripcion;
    @FXML private TextField txtCodCategoria;
    @FXML private TextField txtDescripcion;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
    }
    
   
    
    public void cargarDatos(){
        tblCategoria.setItems(getCategoria());
        colCodigoCategoria.setCellValueFactory(new PropertyValueFactory<Categoria,Integer>("codigoCategoria"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Categoria,String>("descripcion"));
    }
    
    public ObservableList <Categoria> getCategoria(){
        ArrayList<Categoria> lista = new ArrayList<Categoria>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCartegorias()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Categoria (resultado.getInt("codigoCategoria"),resultado.getString("descripcion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
        
        return listaCategoria = FXCollections.observableArrayList(lista);
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
        Categoria registro = new Categoria();
        registro.setDescripcion(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCategoria(?)}");
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.execute();
            listaCategoria.add(registro);
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
                   if(tblCategoria.getSelectionModel().getSelectedItem() != null){
                       int respuesta = JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                       if(respuesta == JOptionPane.YES_NO_OPTION){
                           try{
                               PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCategoria(?)}");
                               procedimiento.setInt(1,((Categoria)tblCategoria.getSelectionModel().getSelectedItem()).getCodigoCategoria());
                               procedimiento.execute();
                               listaCategoria.remove(tblCategoria.getSelectionModel().getSelectedIndex());
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
        if(tblCategoria.getSelectionModel().getSelectedItem() == null){
           JOptionPane.showMessageDialog(null,"Seleccione algo");
        }else{
           txtCodCategoria.setText(String.valueOf(((Categoria)tblCategoria.getSelectionModel().getSelectedItem()).getCodigoCategoria()));
           txtDescripcion.setText(String.valueOf(((Categoria)tblCategoria.getSelectionModel().getSelectedItem()).getDescripcion()));
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
              if(tblCategoria.getSelectionModel().getSelectedItem() != null){
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCategoria(?,?)}");
            Categoria registro=(Categoria)tblCategoria.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            procedimiento.setInt(1,registro.getCodigoCategoria());
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
                if(tblCategoria.getSelectionModel().getSelectedItem() != null){
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
        txtCodCategoria.setEditable(false);
        txtDescripcion.setEditable(false);
    }
    
    public void activarControles(){
        txtCodCategoria.setEditable(false);
        txtDescripcion.setEditable(true);
        
    }
    public void limpiarControles(){
        txtCodCategoria.clear();
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
