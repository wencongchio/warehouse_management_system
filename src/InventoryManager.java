import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class InventoryManager extends javax.swing.JFrame {
    
    private String userID;
    private DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Stock ID", "Stock Name", "Stock Category", "Description", "Stock Location", "Stock Quantity", "Supplier ID", "Manager ID"}, 0);

    public InventoryManager(String userID) {
        
        initComponents();
        this.setTitle("Inventory Manager");
        this.userID = userID;
        fetch();
        
        TitledBorder border = new TitledBorder("Item Details");
        jPanel1.setBorder(border); 
        
        txtItemName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searchItem = txtItemName.getText();
                TableModel model = itemTable.getModel();
                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                itemTable.setRowSorter(sorter);
                if (searchItem.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+ searchItem, 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String searchItem = txtItemName.getText();
                TableModel model = itemTable.getModel();
                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                itemTable.setRowSorter(sorter);
                if (searchItem.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+ searchItem, 1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String searchItem = txtItemName.getText();
                TableModel model = itemTable.getModel();
                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                itemTable.setRowSorter(sorter);
                if (searchItem.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+ searchItem, 1));
                }
            }
  
        });
        
        itemTable.getSelectionModel().addListSelectionListener(new tableListener());
    }
    
    public InventoryManager() {
        
        initComponents();
        this.setTitle("Inventory Manager");
        fetch();
        
        TitledBorder border = new TitledBorder("Item Details");
        jPanel1.setBorder(border); 
        
        txtItemName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searchItem = txtItemName.getText();
                TableModel model = itemTable.getModel();
                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                itemTable.setRowSorter(sorter);
                if (searchItem.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+ searchItem, 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String searchItem = txtItemName.getText();
                TableModel model = itemTable.getModel();
                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                itemTable.setRowSorter(sorter);
                if (searchItem.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+ searchItem, 1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String searchItem = txtItemName.getText();
                TableModel model = itemTable.getModel();
                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                itemTable.setRowSorter(sorter);
                if (searchItem.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+ searchItem, 1));
                }
            }
  
        });
        
        itemTable.getSelectionModel().addListSelectionListener(new tableListener());
    }
    
    private void fetch(){
        tableModel.setRowCount(0);
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WarehouseManagement", "wms", "wms");
            java.sql.Statement stmt = con.createStatement();
            String sql1 = "SELECT * FROM STOCK";
            ResultSet rs = stmt.executeQuery(sql1);
            while(rs.next()){
                String id = rs.getString("STOCKID");
                String name = rs.getString("STOCKNAME");
                String category = rs.getString("STOCKCATEGORY");
                String description = rs.getString("DESCRIPTION");
                String location = rs.getString("STOCKLOCATION");
                String quantity = rs.getString("STOCKQUANTITY");
                String supplier = rs.getString("SUPPLIERID");
                String manager = rs.getString("MANAGERID");
                tableModel.addRow(new Object[]{id, name, category, description, location, quantity, supplier, manager});
            }
            itemTable.setModel(tableModel);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private class tableListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int selectedRow =itemTable.getSelectedRow();
            if (selectedRow >= 0){
                String stockID = itemTable.getModel().getValueAt(selectedRow, 0).toString();
                String supplierID = itemTable.getModel().getValueAt(selectedRow, 6).toString();
                try{
                    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                    java.sql.Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WarehouseManagement", "wms", "wms");
                    java.sql.Statement stmt = con.createStatement();
                    String sql = "SELECT STOCKID, STOCKNAME,STOCKCATEGORY,DESCRIPTION,STOCKLOCATION,STOCKQUANTITY FROM STOCK WHERE STOCKID='"+ stockID +"'";
                    ResultSet rs = stmt.executeQuery(sql);
                    if(rs.next()){
                        String StockID=rs.getString("STOCKID");
                        String StockName= rs.getString("STOCKNAME");
                        String StockCategory= rs.getString("STOCKCATEGORY");
                        String Description=rs.getString("DESCRIPTION");
                        String StockLocation= rs.getString("STOCKLOCATION");
                        String StockQuantity = rs.getString("STOCKQUANTITY");

                        txtCode.setText(StockID);
                        txtName.setText(StockName);
                        txtCategory.setText(StockCategory);
                        txtDescription.setText(Description);
                        txtLocation.setText(StockLocation);
                        txtQuantity.setText(StockQuantity);
                    }

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, e);
                }
                try{
                    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                    java.sql.Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WarehouseManagement", "wms", "wms");
                    java.sql.Statement stmt = con.createStatement(); 
                    String sql1 = "SELECT * FROM SUPPLIER WHERE SUPPLIERID='"+ supplierID +"'";
                    ResultSet rs1 = stmt.executeQuery(sql1);
                    if(rs1.next()){
                        String SupplierID=rs1.getString("SUPPLIERID");
                        String SupplierName= rs1.getString("SUPPLIERNAME");
                        String SupplierAddress= rs1.getString("SUPPLIERADDRESS");
                        String SupplierPhoneNo=rs1.getString("SUPPLIERPHONENO");

                        txtSupplierID.setText(SupplierID);
                        txtSupplierName.setText(SupplierName);
                        txtSupplierAddress.setText(SupplierAddress);
                        txtTelephoneNo.setText(SupplierPhoneNo);

                    }

                } catch(Exception ex){
                    JOptionPane.showMessageDialog(null, e);
                }
            }
            else{
                txtCode.setText(null);
                txtName.setText(null);
                txtCategory.setText(null);
                txtDescription.setText(null);
                txtLocation.setText(null);
                txtQuantity.setText(null);
                txtSupplierID.setText(null);
                txtSupplierName.setText(null);
                txtSupplierAddress.setText(null);
                txtTelephoneNo.setText(null);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtItemName = new javax.swing.JTextField();
        btnAddItem = new javax.swing.JButton();
        btnRemoveItem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnEditQuantity = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTelephoneNo = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtSupplierAddress = new javax.swing.JTextArea();
        txtSupplierID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtSupplierName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        txtCategory = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtCode = new javax.swing.JTextField();
        txtLocation = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        btnEditItem = new javax.swing.JButton();
        btnEditSupplier = new javax.swing.JButton();
        btnAddSupplier = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Item:");

        jLabel2.setText("Item name:");

        btnAddItem.setText("Add item");
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });

        btnRemoveItem.setText("Remove");
        btnRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveItemActionPerformed(evt);
            }
        });

        itemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        itemTable.setToolTipText("");
        jScrollPane1.setViewportView(itemTable);

        btnEditQuantity.setText("Edit quantity");
        btnEditQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditQuantityActionPerformed(evt);
            }
        });

        jLabel10.setText("Supplier ID :");

        jLabel11.setText("Supplier Address :");

        jLabel12.setText("Telephone No.:");

        txtTelephoneNo.setEditable(false);

        txtSupplierAddress.setEditable(false);
        txtSupplierAddress.setColumns(20);
        txtSupplierAddress.setRows(4);
        jScrollPane3.setViewportView(txtSupplierAddress);

        txtSupplierID.setEditable(false);

        jLabel5.setText("Name:");

        jLabel6.setText("Category:");

        jLabel3.setText("Supplier Name :");

        jLabel7.setText("Description:");

        txtSupplierName.setEditable(false);

        jLabel8.setText("Location:");

        jLabel9.setText("Quantity:");

        txtDescription.setEditable(false);

        txtCategory.setEditable(false);

        txtName.setEditable(false);

        txtCode.setEditable(false);

        txtLocation.setEditable(false);

        jLabel4.setText("Stock ID:");

        txtQuantity.setEditable(false);

        btnEditItem.setText("Edit item");
        btnEditItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditItemActionPerformed(evt);
            }
        });

        btnEditSupplier.setText("Edit supplier");
        btnEditSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSupplierActionPerformed(evt);
            }
        });

        btnAddSupplier.setText("Add supplier");
        btnAddSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSupplierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btnEditItem)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditQuantity))
                        .addComponent(jLabel9)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                            .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel5))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtName)
                                .addComponent(txtCategory)
                                .addComponent(txtDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel3))
                                .addGap(34, 34, 34))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtSupplierName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(txtTelephoneNo)
                            .addComponent(txtSupplierID)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAddSupplier)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditSupplier)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtSupplierID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTelephoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12))
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditQuantity)
                            .addComponent(btnEditItem)
                            .addComponent(btnAddSupplier)
                            .addComponent(btnEditSupplier))))
                .addContainerGap())
        );

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveItem, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtItemName))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)))
                        .addContainerGap(44, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddItem)
                    .addComponent(btnRemoveItem)
                    .addComponent(btnRefresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
       AddItem frame = new AddItem(new InventoryManager(), true, userID);
       frame.setTitle("Add Item");
       frame.setVisible(true);
       frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                fetch();
            }
        });
    }//GEN-LAST:event_btnAddItemActionPerformed

    private void btnRemoveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveItemActionPerformed
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow >= 0){
            int selectedOption = JOptionPane.showConfirmDialog(null, "Do you really want to remove this item from list?", "Remove Item", JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION){
                try{
                    String stockID = itemTable.getValueAt(selectedRow, 0) + "";
                    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                    java.sql.Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WarehouseManagement", "wms", "wms");
                    java.sql.Statement stmt = con.createStatement(); 
                    String sql = "DELETE FROM STOCK WHERE STOCKID = '"+ stockID +"'";
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "Item is removed from stock.");
                    fetch();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "No item selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRemoveItemActionPerformed

    private void btnEditItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditItemActionPerformed
        int selectedRow =itemTable.getSelectedRow();
        if (selectedRow >= 0){
            String stockID = itemTable.getModel().getValueAt(selectedRow, 0).toString();
            EditItem frame = new EditItem(new InventoryManager(), true, stockID);
            frame.setTitle("Edit Item");
            frame.setVisible(true);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    fetch();
                }
            });
        }
        else{
            JOptionPane.showMessageDialog(null, "No item selected", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }//GEN-LAST:event_btnEditItemActionPerformed

    private void btnEditQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditQuantityActionPerformed
        int selectedRow =itemTable.getSelectedRow();
        if (selectedRow >= 0){
            String stockID = itemTable.getModel().getValueAt(selectedRow, 0).toString();
            EditQuantity frame = new EditQuantity(new InventoryManager(), true, stockID);
            frame.setTitle("Edit Quantity");
            frame.setVisible(true);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    fetch();
                }
            });
        }
        else{
            JOptionPane.showMessageDialog(null, "No item selected", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }//GEN-LAST:event_btnEditQuantityActionPerformed

    private void btnEditSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSupplierActionPerformed
        int selectedRow =itemTable.getSelectedRow();
        if (selectedRow >= 0){
            String supplierID = itemTable.getModel().getValueAt(selectedRow, 6).toString();
            EditSupplier frame = new EditSupplier(new InventoryManager(), true, supplierID);
            frame.setTitle("Edit Supplier");
            frame.setVisible(true); 
        }
        else{
            JOptionPane.showMessageDialog(null, "No item selected", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }//GEN-LAST:event_btnEditSupplierActionPerformed

    private void btnAddSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSupplierActionPerformed
       AddSupplier frame = new AddSupplier(new InventoryManager(), true);
       frame.setTitle("Add Supplier");
       frame.setVisible(true);
    }//GEN-LAST:event_btnAddSupplierActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        fetch();
    }//GEN-LAST:event_btnRefreshActionPerformed

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
            java.util.logging.Logger.getLogger(InventoryManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InventoryManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InventoryManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InventoryManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InventoryManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnAddSupplier;
    private javax.swing.JButton btnEditItem;
    private javax.swing.JButton btnEditQuantity;
    private javax.swing.JButton btnEditSupplier;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRemoveItem;
    private javax.swing.JTable itemTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtItemName;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextArea txtSupplierAddress;
    private javax.swing.JTextField txtSupplierID;
    private javax.swing.JTextField txtSupplierName;
    private javax.swing.JTextField txtTelephoneNo;
    // End of variables declaration//GEN-END:variables
}
