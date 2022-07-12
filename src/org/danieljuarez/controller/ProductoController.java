
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.danieljuarez.bean.Categoria;
import org.danieljuarez.bean.Marca;
import org.danieljuarez.bean.Producto;
import org.danieljuarez.bean.Talla;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.system.Principal;

public class ProductoController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO,ELIMINAR,EDITAR,REPORTE,ACTUALIZAR,CANCELAR,GUARDAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    
    
    private ObservableList<Producto> listaProducto;
    private ObservableList<Categoria> listaCategoria;
    private ObservableList<Marca> listaMarca;
    private ObservableList<Talla> listaTalla;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodProducto;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtExistencia;
    @FXML private TextField txtPrecioUnitario;
    @FXML private TextField txtPrecioDocena;
    @FXML private TextField txtPrecioMayor;
    @FXML private ComboBox cmbCodCategoria;
    @FXML private ComboBox cmbCodMarca;
    @FXML private ComboBox cmbCodTalla;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colCodProducto;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colExistencia;
    @FXML private TableColumn colPrecioUnitario;
    @FXML private TableColumn colPrecioDocena;
    @FXML private TableColumn colPrecioMayor;
    @FXML private TableColumn colCodCategoria;
    @FXML private TableColumn colCodMarca;
    @FXML private TableColumn colCodTalla;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
    }
    
    public void cargarDatos(){
        tblProductos.setItems(getProducto());
        colCodProducto.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("codigoProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto,String>("descripcion"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("existencia"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioUnitario"));
        colPrecioDocena.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioDocena"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precionMayor"));
        colCodCategoria.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("codigoCategoria"));
        colCodMarca.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("codigoTalla"));
        colCodTalla.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("codigoMarca"));
        cmbCodCategoria.setItems(getCategoria());
        cmbCodMarca.setItems(getMarca());
        cmbCodTalla.setItems(getTalla());
        
    }
    
    public ObservableList<Producto> getProducto(){
        ArrayList<Producto> lista=new ArrayList<Producto>();
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Producto(
                                            resultado.getInt("codigoProducto"),
                                            resultado.getString("descripcion"),
                                            resultado.getInt("existencia"),
                                            resultado.getDouble("precioUnitario"),
                                            resultado.getDouble("precioDocena"),
                                            resultado.getDouble("precionMayor"),
                                            resultado.getInt("codigoCategoria"),
                                            resultado.getInt("codigoTalla"),
                                            resultado.getInt("codigoMarca")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return listaProducto = FXCollections.observableArrayList(lista);
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
    
    public ObservableList <Marca> getMarca(){
        ArrayList<Marca> lista = new ArrayList<Marca>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarMarcas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Marca (resultado.getInt("codigoMarca"),resultado.getString("descripcion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
        
        return listaMarca = FXCollections.observableArrayList(lista);
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
    
    public void seleccionarElemento(){
        if(tblProductos.getSelectionModel().getSelectedItem() == null){
            JOptionPane.showMessageDialog(null, "Seleccione algo");
        }else{
            txtCodProducto.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
            txtDescripcion.setText(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getDescripcion());
            txtExistencia.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
            txtPrecioUnitario.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
            txtPrecioDocena.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
            txtPrecioMayor.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getPrecionMayor()));
            cmbCodCategoria.getSelectionModel().select(buscarCategoria(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoCategoria()));
            cmbCodMarca.getSelectionModel().select(buscarMarca(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoMarca()));
            cmbCodTalla.getSelectionModel().select(buscarTalla(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoTalla()));
        }
        
    }
    
    public Categoria buscarCategoria(int codigoCategoria){
        Categoria resultado = null;
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCategoria(?)}");
            procedimiento.setInt(1, codigoCategoria);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado=new Categoria(registro.getInt("codigoCategoria"),
                                             registro.getString("descripcion"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return resultado;
    
    }
    
    public Marca buscarMarca(int codigoMarca){
        Marca resultado = null;
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarMarca(?)}");
            procedimiento.setInt(1, codigoMarca);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado=new Marca(registro.getInt("codigoMarca"),
                                             registro.getString("descripcion"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return resultado;
    
    }
    
    public Talla buscarTalla(int codigoCategoria){
        Talla resultado = null;
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTalla(?)}");
            procedimiento.setInt(1, codigoCategoria);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado=new Talla(registro.getInt("codigoTalla"),
                                             registro.getString("descripcion"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return resultado;
    
    }
    
    public void desactivarControles(){
        txtCodProducto.setEditable(false);
        txtDescripcion.setEditable(false);
        txtExistencia.setEditable(false);
        txtPrecioUnitario.setEditable(false);
        txtPrecioDocena.setEditable(false);
        txtPrecioMayor.setEditable(false);
        cmbCodCategoria.setEditable(true);
        cmbCodMarca.setEditable(true);
        cmbCodTalla.setEditable(true);
        
    }
    public void activarControles(){
        txtCodProducto.setEditable(false);
        txtDescripcion.setEditable(true);
        txtExistencia.setEditable(true);
        txtPrecioUnitario.setEditable(true);
        txtPrecioDocena.setEditable(true);
        txtPrecioMayor.setEditable(true);
        cmbCodCategoria.setEditable(false);
        cmbCodMarca.setEditable(false);
        cmbCodTalla.setEditable(false);
    }
    public void limpiarControles(){
        txtCodProducto.clear();
        txtDescripcion.clear();
        txtExistencia.clear();
        txtPrecioUnitario.clear();
        txtPrecioDocena.clear();
        txtPrecioMayor.clear();
        cmbCodCategoria.setValue(null);
        cmbCodMarca.setValue(null);
        cmbCodTalla.setValue(null);
    }
    
    public void desactivarCMB(){
        cmbCodCategoria.setDisable(true);
        cmbCodMarca.setDisable(true);
        cmbCodTalla.setDisable(true);
    }
    
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                limpiarControles();
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
        Producto registro = new Producto();
        registro.setDescripcion(txtDescripcion.getText());
        registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
        registro.setPrecioDocena(Double.parseDouble(txtPrecioUnitario.getText()));
        registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
        registro.setPrecionMayor(Double.parseDouble(txtPrecioMayor.getText()));
        registro.setCodigoCategoria(((Categoria)cmbCodCategoria.getSelectionModel().getSelectedItem()).getCodigoCategoria());
        registro.setCodigoMarca(((Marca)cmbCodMarca.getSelectionModel().getSelectedItem()).getCodigoMarca());
        registro.setCodigoTalla(((Talla)cmbCodTalla.getSelectionModel().getSelectedItem()).getCodigoTalla());
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProcucto(?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1,registro.getDescripcion());
            procedimiento.setInt(2, registro.getExistencia());
            procedimiento.setDouble(3,registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecionMayor());
            procedimiento.setInt(6, registro.getCodigoCategoria());
            procedimiento.setInt(7, registro.getCodigoTalla());
            procedimiento.setInt(8, registro.getCodigoMarca());
            procedimiento.execute();
            listaProducto.add(registro);          
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
                if(tblProductos.getSelectionModel().getSelectedItem() != null){
                   int respuesta;
                    respuesta=JOptionPane.showConfirmDialog(null, "Seguro de eliminar?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProducto(?)}");
                            procedimiento.setInt(1, ((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            procedimiento.execute();
                            listaProducto.remove(tblProductos.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                
                }else{
                    JOptionPane.showMessageDialog(null, "Seleccione algo");
                }
        }
    
    }
    
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                if(tblProductos.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    desactivarCMB();
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
            
            
            }
        
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProducto(?,?,?,?,?,?)}");
            Producto registro = (Producto)tblProductos.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
            registro.setPrecioDocena(Double.parseDouble(txtPrecioUnitario.getText()));
            registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
            registro.setPrecionMayor(Double.parseDouble(txtPrecioMayor.getText()));
            procedimiento.setInt(1,registro.getCodigoProducto());
            procedimiento.setString(2,registro.getDescripcion());
            procedimiento.setInt(3, registro.getExistencia());
            procedimiento.setDouble(4,registro.getPrecioUnitario());
            procedimiento.setDouble(5, registro.getPrecioDocena());
            procedimiento.setDouble(6, registro.getPrecionMayor());
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
                if(tblProductos.getSelectionModel().getSelectedItem() != null){
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
