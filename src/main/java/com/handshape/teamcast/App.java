package com.handshape.teamcast;

import com.handshape.teamcast.ui.TeamCastFrame;
import com.formdev.flatlaf.FlatLightLaf;

/**
 *
 * @author jturner
 */
public class App {

    public static void main(String arghs[]) {
        FlatLightLaf.install();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TeamCastFrame().setVisible(true);
            }
        });
    }
}
