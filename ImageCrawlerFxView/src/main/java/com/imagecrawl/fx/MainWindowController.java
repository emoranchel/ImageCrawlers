package com.imagecrawl.fx;

/**
 * Sample Skeleton for "FXML.fxml" Controller Class You can copy and paste this
 * code into your favorite IDE
 *
 */
import com.imagecrawl.api.API;
import com.imagecrawl.api.AnalizeAction;
import com.imagecrawl.engine.EngineStart;
import com.imagecrawl.model.GalleryImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.asmatron.messengine.ViewEngine;
import org.asmatron.messengine.annotations.EventMethod;

public class MainWindowController
        implements Initializable {
    
    @FXML //  fx:id="btnLeech"
    private Button btnLeech; // Value injected by FXMLLoader
    @FXML //  fx:id="table"
    private TableView<?> table; // Value injected by FXMLLoader
    @FXML //  fx:id="txtFrom"
    private TextField txtFrom; // Value injected by FXMLLoader
    @FXML //  fx:id="txtSaveTo"
    private TextField txtSaveTo; // Value injected by FXMLLoader
    @FXML //  fx:id="txtTo"
    private TextField txtTo; // Value injected by FXMLLoader
    @FXML //  fx:id="txtUrl"
    private TextField txtUrl; // Value injected by FXMLLoader
    private final ViewEngine viewEngine;
    
    public MainWindowController(ViewEngine engine) {
        this.viewEngine = engine;
    }
    
    public void onLeechClick() {
        btnLeech.setDisable(true);
        AnalizeAction action = viewEngine.get(API.Model.FACTORY).newAction();
        action.setSavePath(txtSaveTo.getText());
        action.setAnalizeUrl(txtUrl.getText());
        action.setStartPage(Integer.parseInt(txtFrom.getText().trim()));
        action.setEndPage(Integer.parseInt(txtTo.getText().trim()));
        viewEngine.send(API.Actions.ANALIZE, action);
    }
    
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert btnLeech != null : "fx:id=\"btnLeech\" was not injected: check your FXML file 'FXML.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'FXML.fxml'.";
        assert txtFrom != null : "fx:id=\"txtFrom\" was not injected: check your FXML file 'FXML.fxml'.";
        assert txtSaveTo != null : "fx:id=\"txtSaveTo\" was not injected: check your FXML file 'FXML.fxml'.";
        assert txtTo != null : "fx:id=\"txtTo\" was not injected: check your FXML file 'FXML.fxml'.";
        assert txtUrl != null : "fx:id=\"txtUrl\" was not injected: check your FXML file 'FXML.fxml'.";
        AnalizeAction action = viewEngine.get(API.Model.FACTORY).newAction();
        txtFrom.setText("1");
        txtTo.setText("10");
        txtSaveTo.setText(action.getSavePath());
        txtUrl.setText(action.getAnalizeUrl());
    }
    
    @EventMethod(API.Events.IMAGE_FOUND_ID)
    public synchronized void onImageFound(GalleryImage image) {
//        ((DefaultTableModel) jTable1.getModel()).addRow(
//                new Object[]{
//                    image.getPage() + ":" + jTable1.getRowCount(),
//                    image,
//                    image.getStatus(),
//                    image.getProgressAsStr(),
//                    image.getRating(),
//                    image.getTagsAsStr(),
//                    image.getMessage()});
//        image.setViewObject(jTable1.getModel().getRowCount() - 1);
    }
    
    @EventMethod(API.Events.IMAGE_UPDATED_ID)
    public void onImageUpdate(GalleryImage image) {
//        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
//        Integer row = (Integer) image.getViewObject();
//        tableModel.setValueAt(image.getStatus(), row, 2);
//        tableModel.setValueAt(image.getProgressAsStr(), row, 3);
//        tableModel.setValueAt(image.getRating(), row, 4);
//        tableModel.setValueAt(image.getTagsAsStr(), row, 5);
//        tableModel.setValueAt(image.getMessage(), row, 6);
    }
    
    @EventMethod(API.Events.PROCESS_COMPLETE_ID)
    public void onProcessComplete() {
        btnLeech.setDisable(false);
        JOptionPane.showMessageDialog(null, "Process complete!");
    }
}
