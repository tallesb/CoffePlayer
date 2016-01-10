package decoradoresJanela;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JList;

import model.Musica;

public class DecoradorEstilos extends DecoradorJanelaSimples{
	ArrayList<ArrayList<Musica>> estilos = new ArrayList<>();
	String titulo;
	
	public DecoradorEstilos() {
		 super();
	}
	
	public void setEstilosETitulo(ArrayList<ArrayList<Musica>> lista, String titulo){
		this.estilos = lista;
		this.titulo = titulo;
		janela.limparPanel();
		janela.setTituloELista("estilos", this.estilosBiblioteca());
	}
	
	public ArrayList<String> estilosBiblioteca(){
		ArrayList<String> nomeEstilos = new ArrayList<>();
		if(!estilos.isEmpty()){
			for(ArrayList<Musica> umEstilo: estilos) {
				nomeEstilos.add(umEstilo.get(0).getStyle());
			}
		} 
		return nomeEstilos;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw() {
		
	}
}
