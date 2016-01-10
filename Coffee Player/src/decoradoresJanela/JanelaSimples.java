package decoradoresJanela;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import control.ControladorLista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
public class JanelaSimples extends ModeloJanela{

	/**
	 * 
	 */
	private static final long serialVersionUID = -696707429971586860L;
	ArrayList<String> lista;
	String titulo;
	JLabel nomeLista;
	JScrollPane scrollPaneListagem;
	JPanel painelConteudo;
	public JButton btnVoltar;
	JLabel lblNomeLista;
	GridBagConstraints gbc_lblNomeLista;
	GridBagConstraints gbc_btnVoltar;
	public JList list;
	DefaultListModel model;
	
	public JanelaSimples(){
		super();
	}
	
	public void setTituloELista(String titulo, ArrayList<String> lista2){
		this.lista = lista2;
		this.titulo = titulo;
		draw();
	}
	
	@Override
	public void draw() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 218, 63, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 218, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblNomeLista = new JLabel(titulo);
		lblNomeLista.setVerticalAlignment(SwingConstants.TOP);
		gbc_lblNomeLista = new GridBagConstraints();
		gbc_lblNomeLista.insets = new Insets(0, 0, 5, 5);
		gbc_lblNomeLista.gridx = 2;
		gbc_lblNomeLista.gridy = 0;
		add(lblNomeLista, gbc_lblNomeLista);
		
		btnVoltar = new JButton("Voltar");
		gbc_btnVoltar = new GridBagConstraints();
		gbc_btnVoltar.insets = new Insets(0, 0, 5, 5);
		gbc_btnVoltar.gridx = 4;
		gbc_btnVoltar.gridy = 0;
		add(btnVoltar, gbc_btnVoltar);
		btnVoltar.addActionListener(this);
		
	    model = new DefaultListModel();
	    list = new JList(model);
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    if(!lista.isEmpty())
		for (int row = 0; row < lista.size(); row++) {
	    	for (int col = 0; col < 2; col++) {
	    		if(col == 0){ 
	    			if(lista.get(row) != null){
	    				model.add(row, lista.get(row));
	    			}
	    			else
	    				model.add(row, "Estilo Desconhecido");
	    		}
	    	}
	    }
		
	    scrollPaneListagem = new JScrollPane(list);

		GridBagConstraints gbc_scrollPaneListagem = new GridBagConstraints();
		gbc_scrollPaneListagem.gridwidth = 3;
		gbc_scrollPaneListagem.gridheight = 3;
		gbc_scrollPaneListagem.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneListagem.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneListagem.gridx = 2;
		gbc_scrollPaneListagem.gridy = 1;
		add(scrollPaneListagem, gbc_scrollPaneListagem);
		
		repaint();
		validate();
		
	}

	@Override
	public void actionPerformed(ActionEvent e){
		JButton btnTest = (JButton) e.getSource();
		
	}
	
	
}
