package decoradoresJanela;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import model.ListaMusicas;
import model.Musica;


public class DecoradorFavoritos extends DecoradorJanelaSimples{

	JButton btnOpc;
	String titulo;
	ListaMusicas lista;
	
	public DecoradorFavoritos() {
		 super();
		 ArrayList<String> lista = new ArrayList<>();
		 lista.add("<Default>");
		 janela.setTituloELista("<Default>", lista);
	}
	
	public void setTituloELista(ListaMusicas lista, String titulo) {
		this.titulo = titulo;
		this.lista = lista;
		janela.limparPanel();
		janela.setTituloELista(titulo, this.getNomeMusicasFavoritas());
		janela.list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		            int index = list.locationToIndex(evt.getPoint());
		            System.out.println(list.getSelectedValue() + "no decorador playlist");
		        }
		    }
		});
	}

	public ArrayList getNomeMusicasFavoritas(){
		ArrayList<String> nomeMusicasFavoritas = new ArrayList<>();
		for(Musica musica : lista.listaDeMusicas){
			nomeMusicasFavoritas.add(musica.getNomeMusica());
		}
		return nomeMusicasFavoritas;
	}
	
	@Override
	public void draw() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

}
