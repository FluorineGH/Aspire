/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import javax.swing.JFrame;

public class Aspire extends JFrame {

    public Aspire() {
        add(new ABoard());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 800);
        setLocationRelativeTo(null);
        setTitle("Aspire");
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Aspire();
    }
    
}
