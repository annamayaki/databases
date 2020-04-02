
package databaseInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class jobAssessmentFrame extends javax.swing.JFrame {

    private Connection conn;
    private String user;
    private int job;
    private Vector<Object> temp;
    private Vector<String> ids1;
    private Vector<Vector<Object>> table1;
    private Vector<String> ids2;
    private Vector<Vector<Object>> table2;
    private String tempStr;
    private Object o;
    private ArrayList<Integer> jobId;
    
    public jobAssessmentFrame(Connection conn, String user) {
        
        this.conn=conn;
        this.user=user;
        table1 = new Vector<Vector<Object>>();
        ids1 = new Vector<String>();
        table2 = new Vector<Vector<Object>>();
        ids2 = new Vector<String>();
        ids1.add("Candidate");
        ids1.add("Education");
        ids1.add("Experience");
        ids1.add("Personality");
        ids1.add("Total Score");
        ids1.add("Interviews");
        ids2.add("Candidate");
        ids2.add("Reason(s)");
        jobId = new ArrayList<Integer>();
        try {
            Statement stmt = conn.createStatement();
            String query = "select id from job where recruiter='"+user+"';";
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                jobId.add(res.getInt("id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(jobAssessmentFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Assessment Results for Job ID:");

        jTextField1.setText("");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });

        jButton1.setText("View");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            table1,ids1
        ));
        jTable1.setEnabled(false);
        jTable1.setFocusable(false);
        jTable1.setRequestFocusEnabled(false);
        jTable1.setRowSelectionAllowed(false);
        jTable1.setVerifyInputWhenFocusTarget(false);
        jTable1.setVisible(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setText("Candidate Ranking");

        jLabel3.setText("Rejected Candidates");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            table2,ids2
        ));
        jTable3.setEnabled(false);
        jTable3.setFocusable(false);
        jTable3.setRequestFocusEnabled(false);
        jTable3.setRowSelectionAllowed(false);
        jTable3.setVerifyInputWhenFocusTarget(false);
        jTable3.setVisible(false);
        jScrollPane3.setViewportView(jTable3);

        jButton2.setText("< Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(40, 40, 40)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(35, 35, 35)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        tempStr = jTextField1.getText();
        job = Integer.parseInt(tempStr);
    }//GEN-LAST:event_jTextField1FocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jobId.contains(job)){
            try {
                CallableStatement stmt=conn.prepareCall("call job_assessment(?)");
                stmt.setInt(1,job);
                ResultSet res;
                String status;
                boolean cont = stmt.execute();
                if (cont){
                    res = stmt.getResultSet();
                    res.next();
                    status = res.getString(1);
                    if (status.equals("assessment over")){
                        cont = stmt.getMoreResults();
                        while(cont){
                            res = stmt.getResultSet();
                            res.next();
                            o = res.getObject(1);
                            String str = (String) o;
                            if (!str.equals("Rejected Candidates")) {
                                temp = new Vector<Object>();
                                temp.add(o);
                                temp.add(res.getObject(2));
                                temp.add(res.getObject(3));
                                temp.add(res.getObject(4));
                                temp.add(res.getObject(5));
                                temp.add(res.getObject(6));
                                table1.add(temp);
                                cont = stmt.getMoreResults();
                            }
                            else {
                                cont = false;
                            }
                        }
                        cont = stmt.getMoreResults();
                        while(cont){
                            res = stmt.getResultSet();
                            res.next();
                            temp = new Vector<Object>();
                            temp.add(res.getObject(1));
                            temp.add(res.getObject(2));
                            table2.add(temp);
                            cont = stmt.getMoreResults();
                        }
                        res.close();
                        jTable1.setVisible(true);
                        jTable3.setVisible(true);
                        SwingUtilities.updateComponentTreeUI(this);
                    }
                    else {
                        JOptionPane.showMessageDialog(rootPane, ("Job "+job+" "+status+"."));
                        jTable1.setVisible(false);
                        jTable3.setVisible(false);
                        SwingUtilities.updateComponentTreeUI(this);
                    }

                }    
            stmt.close();   
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, ("Query failed. Please try again."));
                Logger.getLogger(jobAssessmentFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
           JOptionPane.showMessageDialog(rootPane, ("Please select a valid job ID.")); 
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
