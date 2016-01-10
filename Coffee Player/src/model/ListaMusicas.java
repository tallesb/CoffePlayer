package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class ListaMusicas implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1121512294193392493L;
	public ArrayList<Musica> listaDeMusicas = new ArrayList<Musica>();
	String nomeLista;
	
	public ListaMusicas (String nome){
		this.nomeLista = nome;
	}
	
	public ArrayList<Musica> getListaDeMusicas() {
		return listaDeMusicas;
	}

	public String getNomeLista() {
		return nomeLista;
	}

	public void addInLista(Musica musica){ 
		listaDeMusicas.add(musica);
	}

	public void serializar() throws Exception{
		FileOutputStream out = new FileOutputStream(System.getProperty("user.home") + File.separator + "Music" + File.separator + "Controles" + File.separator + nomeLista + ".tmp");
		ObjectOutputStream obj = new ObjectOutputStream(out);
		obj.writeObject(this);
		obj.close();
		out.close();
	}
}
