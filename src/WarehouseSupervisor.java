import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class WarehouseSupervisor extends javax.swing.JFrame {
    
    private DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Worker ID", "Worker Name"}, 0);
    
    public WarehouseSupervisor() {
        initComponents();
        this.setTitle("Warehouse Supervisor");
        
        fetchOrder();
        fetchWorker();
        
        WarehouseWorkerTable.getSelectionModel().addListSelectionListener(new WarehouseSupervisor.tableListener());
        
        nameSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searchItem = nameSearch.getText();
                TableModel model = WarehouseWorkerTable.getModel();
                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                WarehouseWorkerTable.setRowSorter(sorter);
                if (searchItem.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+ searchItem, 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String searchItem = nameSearch.getText();
                TableModel model = WarehouseWorkerTable.getModel();
                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                WarehouseWorkerTable.setRowSorter(sorter);
                if (searchItem.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+ searchItem, 1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String searchItem = nameSearch.getText();
                TableModel model = WarehouseWorkerTable.getModel();
                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                WarehouseWorkerTable.setRowSorter(sorter);
                if (searchItem.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+ searchItem, 1));
                }
            }
  
        });
        
    }
    
    private void fetchOrder(){
        DefaultListModel model = new DefaultListModel();
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WarehouseManagement", "wms", "wms");
            java.sql.Statement stmt = con.createStatement();
            String sql1 = "SELECT ORDERID FROM ORDERTABLE WHERE STATUS = 'UNASSIGNED'";
            ResultSet rs1 = stmt.executeQuery(sql1); 
            
            while(rs1.next()){
                String OrderID = rs1.getString("OrderID");
                model.addElement(OrderID);
            }
            OrderList.setModel(model);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void fetchWorker(){
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WarehouseManagement", "wms", "wms");
            java.sql.Statement stmt = con.createStatement();
            String sql1 = "SELECT * FROM WAREHOUSEWORKER";
            ResultSet rs = stmt.executeQuery(sql1);
            
            while(rs.next()){
                String workerID = rs.getString("WORKERID");
                String workerName = rs.getString("WORKERNAME");
                tableModel.addRow(new Object[]{workerID, workerName});
            }
            WarehouseWorkerTable.setModel(tableModel);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void fetchTask(){
        int selectedWorker = WarehouseWorkerTable.getSelectedRow();
        if (selectedWorker >= 0){
            String workerID= WarehouseWorkerTable.getModel().getValueAt(selectedWorker, 0).toString();
            DefaultListModel model = new DefaultListModel();
            try{
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WarehouseManagement", "wms", "wms");
                java.sql.Statement stmt = con.createStatement();
                String sql = "SELECT ORDERID FROM ORDERTABLE WHERE WORKERID='"+ workerID +"'";
                ResultSet rs = stmt.executeQuery(sql);

                while(rs.next()){
                    String OrderID = rs.getString("ORDERID");
                    model.addElement(OrderID);
                }

                WorkerTask.setModel(model);

            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        else{
            DefaultListModel listModel = (DefaultListModel)WorkerTask.getModel();
            listModel.removeAllElements();
        }
    }
    
    private class tableListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int selectedWorker = WarehouseWorkerTable.getSelectedRow();
            if (selectedWorker >= 0){
                String workerID= WarehouseWorkerTable.getModel().getValueAt(selectedWorker, 0).toString();
                fetchTask();
            }
            else{
                DefaultListModel listModel = (DefaultListModel)WorkerTask.getModel();
                listModel.removeAllElements();
            }
        }
    }

    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        OrderList = new javax.swing.JList<>();
        nameSearch = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        WorkerTask = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnAssignTask = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        WarehouseWorkerTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        OrderList.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(OrderList);

        nameSearch.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        WorkerTask.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jScrollPane3.setViewportView(WorkerTask);

        jLabel5.setText("Unassigned Order:");

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        jLabel6.setText("Worker's Name : ");

        btnAssignTask.setText("Assign");
        btnAssignTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssignTaskActionPerformed(evt);
            }
        });

        jLabel2.setText("Worker's Task");

        WarehouseWorkerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(WarehouseWorkerTable);

        jButton1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButton1.setText("LOG OUT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefresh))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameSearch))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAssignTask))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(btnRefresh)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(btnAssignTask))
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        fetchOrder();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnAssignTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignTaskActionPerformed
        int selectedOrder = OrderList.getSelectedIndex();
        int selectedWorker = WarehouseWorkerTable.getSelectedRow();
        if (selectedOrder >= 0 ){
            if (selectedWorker >= 0){
                String orderID = OrderList.getSelectedValue().toString();
                String workerID= WarehouseWorkerTable.getModel().getValueAt(selectedWorker, 0).toString();
                int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to assign order " + orderID + " to " + workerID + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (selectedOption == JOptionPane.YES_OPTION){
                    try{
                        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                        java.sql.Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WarehouseManagement", "wms", "wms");
                        java.sql.Statement stmt = con.createStatement();
                        String sql1 = "UPDATE ORDERTABLE SET WORKERID='"+ workerID +"', STATUS='ASSIGNED' WHERE ORDERID='" + orderID + "'";
                        stmt.executeUpdate(sql1);
                        JOptionPane.showMessageDialog(null, "Order assigned to " + workerID);
                        fetchOrder();
                        fetchTask();

                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
            else{
                 JOptionPane.showMessageDialog(null, "No worker selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
             JOptionPane.showMessageDialog(null, "No order selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAssignTaskActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int selectedOption = JOptionPane.showConfirmDialog(null, "Do you want to log out?", "LOG OUT",  JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION){
            this.dispose();
            LoginPage frame = new LoginPage();
            frame.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
    
    
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
            java.util.logging.Logger.getLogger(WarehouseSupervisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WarehouseSupervisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WarehouseSupervisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WarehouseSupervisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WarehouseSupervisor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> OrderList;
    private javax.swing.JTable WarehouseWorkerTable;
    private javax.swing.JList<String> WorkerTask;
    private javax.swing.JButton btnAssignTask;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField nameSearch;
    // End of variables declaration//GEN-END:variables
}
