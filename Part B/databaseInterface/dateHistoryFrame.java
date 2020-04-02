
package databaseInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class dateHistoryFrame extends javax.swing.JFrame {
     private Connection conn;
     private String name;
     private Vector<Object> temp;
     private Vector<String> ids;
     private Vector<Vector<Object>> table;
     private String from;
     private String to;
    
    public dateHistoryFrame(Connection conn,String name,String from,String to) {
        this.conn=conn;
        this.name=name;
        this.from=from;
        this.to=to;
           
        
        Statement stmt;
        try {
            System.out.println(from);
            System.out.println(to);
            System.out.println(name);
            stmt = conn.createStatement();
            String query = "select * from action where "
                    +"act_time< '"+to+"' and act_time>'"+from+"' and act_username='"+name+"';";
            ResultSet res;
            res = stmt.executeQuery(query);
             
            table = new Vector<Vector<Object>>();
             
            while (res.next()) {
                temp = new Vector<Object>();
                temp.add(res.getObject(1));
                temp.add(res.getObject(2));
                temp.add(res.getObject(3));
                temp.add(res.getObject(4));
                temp.add(res.getObject(5));
                table.add(temp);    
            }
             
            res.close();
            stmt.close();
             
            ids = new Vector<String>();
            ids.add("Tablename");
            ids.add("Action type");
            ids.add("Action Time");
            ids.add("Success");
            ids.add("Username");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Failed to retrieve actions. Please try again.");
            Logger.getLogger(historyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents(); 
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(table,ids
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("< Back");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

// select * from action where act_time<'2019-08-31' and act_time>'2019-07-01' and act_username='klelia';