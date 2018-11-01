/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experts;

import experts.Database.FCDatabase;
import experts.Engine.Manager;
import experts.Entities.Answer;
import experts.Entities.Premise;
import experts.Entities.QueueTable;
import experts.Entities.Rule;
import experts.Entities.WorkingMemory;
import experts.Modified.swing.RadioButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

/**
 *
 * @author owner
 */
public class main extends javax.swing.JFrame {

    /**
     * Creates new form main
     */
    
    public Manager manager;
    public Premise active_premise = new Premise();
    
    public ArrayList <RadioButton> radio_buttons = new ArrayList <RadioButton> ();
    
    public ButtonGroup button_group = new ButtonGroup();
    
    public main() {
        initComponents();
        
        // MANAGER LOAD EXPERTS WITH ID integer `X`
        manager = new Manager(2);
        manager.showKnowledgeBase();
        
        active_premise = manager.getNextPremise();
        active_premise.showPremiseOnConsole();
        QuestionLabel.setText("Question: " + active_premise.getQuestion());
        
        setButtons();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        panel1 = new javax.swing.JPanel();
        QuestionLabel = new javax.swing.JLabel();
        submitButton = new javax.swing.JButton();
        conclusionLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel1.setBackground(new java.awt.Color(204, 204, 255));

        QuestionLabel.setText("Premise");

        submitButton.setText("submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        conclusionLabel.setText("Conclusion");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(conclusionLabel)
                    .addComponent(submitButton)
                    .addComponent(QuestionLabel))
                .addContainerGap(245, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(QuestionLabel)
                .addGap(27, 27, 27)
                .addComponent(submitButton)
                .addGap(18, 18, 18)
                .addComponent(conclusionLabel)
                .addContainerGap(129, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        // TODO add your handling code here:
        
        if (active_premise == null || manager.getUnknownConclusion()) 
            return;
        
        if (getSelectedAnswerId() <= 0){
            JOptionPane.showMessageDialog(null, "Pilih 1 jawaban!", "alert", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        manager.setAnswer(active_premise, getSelectedAnswerId());
        active_premise = manager.getNextPremise();
        
        if (manager.getUnknownConclusion()){
            // RULE HABIS ato TIDAK ADA YANG PAS
            QuestionLabel.setText("Question: -");
            conclusionLabel.setText("Conclusion: UNKNOWN");
            return;
        }
        
        // PROCESS ACTIVE PREMISE
        if (active_premise == null){
            QuestionLabel.setText("Question: -");
        } else { 
            active_premise.showPremiseOnConsole();
            QuestionLabel.setText("Question: " + active_premise.getQuestion());
        }
        
        // CONCLUSION CHECK
        if (manager.conclusionObtained()){
            conclusionLabel.setText(manager.getQueueTable().current_rule_conclusion);
        }
        
        if (active_premise == null)
            return;
        
        setButtons();
        
    }//GEN-LAST:event_submitButtonActionPerformed
    
    public int getSelectedAnswerId(){
        for (int i = 0; i < radio_buttons.size(); i++){
            RadioButton button = radio_buttons.get(i);
            if (radio_buttons.get(i).getButton().isSelected())
                return ((Answer)button.getValue()).getId();
        }
        return -1;
    }
    
    public void clearButtons(){
        radio_buttons.clear();
        button_group = new ButtonGroup();
    }
    
    public void setButtons(){
        
        for(int i = 0; i < radio_buttons.size(); i++){
            panel1.remove(radio_buttons.get(i).getButton());
        }
        
        radio_buttons.clear();
        
        for (int i = 0; i < active_premise.list_of_answer.size(); i++){
            
            RadioButton button = new RadioButton();
            button.setValue(active_premise.list_of_answer.get(i));
            button.setText(active_premise.list_of_answer.get(i).getAnswer());
            
            radio_buttons.add(button);
            radio_buttons.get(i).getButton().setBounds(20, 20 * i, 50, 20);
            
            if (i == 0){
                radio_buttons.get(i).getButton().setSelected(true);
            }
            
            panel1.add(radio_buttons.get(i).getButton());
            
            radio_buttons.get(i).getButton().addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    Answer ans = (Answer) button.getValue();
                    System.out.println(ans.getId() + ans.getAnswer());
                }
            });
            
            button_group.add(radio_buttons.get(i).getButton());
            
        }
        
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
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel QuestionLabel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel conclusionLabel;
    private javax.swing.JPanel panel1;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables
}
