package com.appdev.presentation.components.forms;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

public class LostItemForm extends JScrollPane {
    private JPanel panel;
    
    public LostItemForm() {
        panel = new JPanel(new MigLayout("fillx,wrap,insets 5 30 5 30, width 500", "[fill]", ""));
        setViewportView(panel);  
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        init();        
    }

    private void init() {
    }
}
