package decoradoresJanela;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PainelScroll extends JPanel{

	JButton[] btnLista = new JButton[40];
	JLabel nomeLista;
	
	public PainelScroll(ArrayList<String> lista) {
		super();
	    setLayout(new GridLayout(16, 5, 10, 0));
	    setBackground(new Color(255,255,255,120));
	    

	    
	    for (int row = 0; row < lista.size(); row++) {
	    	ButtonGroup bg = new ButtonGroup();
	    	for (int col = 0; col < 2; col++) {
	    		if(col == 0) {
	    			bg.add(btnLista[row] = new JButton( row + "" ));
	    			add(btnLista[row]);
	    		} else 
					add(nomeLista = new JLabel(lista.get(row)));
	    	}
	    }
	    repaint();
	    validate();
	}


}
