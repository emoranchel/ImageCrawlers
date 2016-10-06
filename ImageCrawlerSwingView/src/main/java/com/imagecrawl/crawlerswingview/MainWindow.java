package com.imagecrawl.crawlerswingview;

import com.imagecrawl.api.API;
import com.imagecrawl.api.AnalizeAction;
import com.imagecrawl.model.GalleryImage;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.asmatron.messengine.ViewEngine;
import org.asmatron.messengine.annotations.EngineStarted;
import org.asmatron.messengine.annotations.EventMethod;

public class MainWindow extends javax.swing.JFrame {

  private ViewEngine viewEngine;

  public MainWindow(ViewEngine viewEngine) {
    this();
    this.viewEngine = viewEngine;
    jSpinner1.setValue(1);
    jSpinner2.setValue(10);
  }

  public MainWindow() {
    initComponents();
    TableCellRenderer imageCellRenderer = new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        GalleryImage image = (GalleryImage) table.getValueAt(row, 1);
        switch (image.getStatus().getType()) {
          case end:
            setForeground(Color.BLUE);
            break;
          case error:
            setForeground(Color.RED);
            break;
          case inProgress:
            if (image.getProgress() > 0) {
              setForeground(Color.GREEN);
            } else {
              setForeground(Color.BLACK);
            }
            break;
        }
        return this;
      }
    };
    jTable1.setDefaultRenderer(GalleryImage.class, imageCellRenderer);
    jTable1.setDefaultRenderer(String.class, imageCellRenderer);
  }

  @EngineStarted
  public void init() {
    AnalizeAction action = viewEngine.get(API.Model.FACTORY).newAction();
      String path = System.getProperty("user.home") +"/pictures/"+ action.getSavePath();
      path=path.replaceAll("\\\\", "/");
    jTextField1.setText(path);
    jTextField2.setText(action.getAnalizeUrl());
    setTitle(viewEngine.get(API.Model.TITLE));
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jButton1 = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();
    jTextField1 = new javax.swing.JTextField();
    jSpinner1 = new javax.swing.JSpinner();
    jSpinner2 = new javax.swing.JSpinner();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jTextField2 = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    jButton1.setText("LEECH!");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });

    jTable1.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {

      },
      new String [] {
        "#", "FileId", "Status", "Progress", "Rating", "Tags", "Message"
      }
    ) {
      Class[] types = new Class [] {
        java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }
    });
    jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    jScrollPane1.setViewportView(jTable1);

    jTextField1.setText("D:/images");

    jLabel1.setText("Save to:");

    jLabel2.setText("Image Query:");

    jLabel3.setText("From page:");

    jLabel4.setText("To page:");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel2)
              .addComponent(jLabel1))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
              .addComponent(jTextField2))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(3, 3, 3)
                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(123, 123, 123))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jScrollPane1)
            .addContainerGap())))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButton1)
          .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel1))
        .addGap(2, 2, 2)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel2)
          .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel3)
          .addComponent(jLabel4))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    jButton1.setEnabled(false);
    AnalizeAction action = viewEngine.get(API.Model.FACTORY).newAction();
    action.setSavePath(jTextField1.getText());
    action.setAnalizeUrl(jTextField2.getText());
    action.setStartPage((Integer) jSpinner1.getValue());
    action.setEndPage((Integer) jSpinner2.getValue());
    viewEngine.send(API.Actions.ANALIZE, action);
  }//GEN-LAST:event_jButton1ActionPerformed

  @EventMethod(API.Events.IMAGE_FOUND_ID)
  public synchronized void onImageFound(GalleryImage image) {
    ((DefaultTableModel) jTable1.getModel()).addRow(
            new Object[]{
              image.getPage() + ":" + jTable1.getRowCount(),
              image,
              image.getStatus(),
              image.getProgressAsStr(),
              image.getRating(),
              image.getTagsAsStr(),
              image.getMessage()});
    image.setViewObject(jTable1.getModel().getRowCount() - 1);
  }

  @EventMethod(API.Events.IMAGE_UPDATED_ID)
  public void onImageUpdate(GalleryImage image) {
    DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
    Integer row = (Integer) image.getViewObject();
    tableModel.setValueAt(image.getStatus(), row, 2);
    tableModel.setValueAt(image.getProgressAsStr(), row, 3);
    tableModel.setValueAt(image.getRating(), row, 4);
    tableModel.setValueAt(image.getTagsAsStr(), row, 5);
    tableModel.setValueAt(image.getMessage(), row, 6);
  }

  @EventMethod(API.Events.PROCESS_COMPLETE_ID)
  public void onProcessComplete() {
    jButton1.setEnabled(true);
    JOptionPane.showMessageDialog(this, "Process complete!");
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
        //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new MainWindow().setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton1;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSpinner jSpinner1;
  private javax.swing.JSpinner jSpinner2;
  private javax.swing.JTable jTable1;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField jTextField2;
  // End of variables declaration//GEN-END:variables
}
