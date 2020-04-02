
package databaseInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class applicationTableFrame extends javax.swing.JFrame {
private Object o;
    private Vector<Object> temp;
    private Vector<String> ids;
    private Vector<Vector<Object>> table;
    private ArrayList<Integer> jobId;
    private String query;
     private Connection conn;
     private String user;
    
   
    public applicationTableFrame(Connection conn,String user) {
        this.conn = conn;
         this.user = user;
          Statement stmt;
        
         try {
            
            stmt = conn.createStatement();
            query = "select job.id, job.position, job.submission_date from job "
                    +"inner join applies on job.id = applies.job_id "
                    +"where job.submission_date>current_date() and "
                    +"cand_usrname= '"+user+"';";
            ResultSet res;
            res = stmt.executeQuery(query);
            table = new Vector<Vector<Object>>();
            jobId = new ArrayList<Integer>();
            while (res.next()) {
                temp = new Vector<Object>();
                o = res.getObject("id");
                temp.add(o);
                temp.add(res.getObject("position"));
                temp.add(res.getObject("submission_date"));
                int k = (Integer) o;
                jobId.add(k);
                System.out.println(jobId.get(0));
                table.add(temp);
            }
            stmt.close();
            ids = new Vector<String>();
            ids.add("Job ID");
            ids.add("Position Description");
            ids.add("Submission Date");
            
            
            initComponents();
        } catch (SQLException ex) {
            Logger.getLogger(applyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
           
          
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
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(17, 17, 17))
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
