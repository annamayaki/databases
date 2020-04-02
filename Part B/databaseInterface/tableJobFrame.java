
package databaseInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import javax.swing.JOptionPane;


public class tableJobFrame extends javax.swing.JFrame {
    private Connection conn;
            private String user;
    private DefaultTableModel model;
    private Object idObj;
    private Object dateObj;
    private Vector<Object> temp;
    private Vector<String> ids;
    private Vector<Vector<Object>> table;
    private ArrayList<Integer> jobId;
    private Boolean repeat = false;
     DateFormat format;
   
    public tableJobFrame(Connection conn,String user) {
        this.conn= conn;
        this.user=user;
        format = new SimpleDateFormat("yyyy-MM-dd");
        Statement stmt;
        
        try {
            
            stmt = conn.createStatement();
           String query = "select * from job "
                    +"where recruiter='"+user+"';";
            ResultSet res;
            res = stmt.executeQuery(query);
            
            table = new Vector<Vector<Object>>();
            jobId = new ArrayList<Integer>();
            
            while (res.next()) {
                
                temp = new Vector<Object>();
                idObj = res.getObject("id");
                temp.add(idObj);
                temp.add(res.getObject("start_date"));
                temp.add(res.getObject("salary"));
                temp.add(res.getObject("position"));
                temp.add(res.getObject("edra"));
                temp.add(res.getObject("announce_date"));
                
                String subDateStr= res.getString("submission_date");
                temp.add((Object) subDateStr);

                temp.add(res.getObject("applicants"));
                Date subDate1= new Date();
                try {
                    subDate1 = format.parse(subDateStr);
                } catch (ParseException ex) {
                    Logger.getLogger(editJobFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                Date today = new Date();
                if (today.after(subDate1)){
                    temp.add((Object)"Closed");
                }
                else {
                    temp.add((Object)"Open");
                }
                
                
                int job = (Integer) idObj;
                jobId.add(job);
                
                
                
                table.add(temp);
            }
            
            res.close();
            stmt.close();
            ids = new Vector<String>();
            ids.add("Job ID");
            ids.add("Start Date");
            ids.add("Salary");
            ids.add("Position Description");
            ids.add("Location");
            ids.add("Announcement Date");
            ids.add("Submission Date");
            ids.add("#Applicants");
            ids.add("Status");
            
            
            
            initComponents();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Failed to retrieve announced jobs.");
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

        jButton1.setText(" < Back");
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
                .addGap(37, 37, 37)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
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
