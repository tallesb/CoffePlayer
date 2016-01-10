package control;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;

import model.ListaMusicas;
import model.Musica;

public class ControladorLista{

	public static ListaMusicas getUmEstilo(int indexEstilo, String nomeLista) {
		ListaMusicas lista = new ListaMusicas(nomeLista);
		lista.listaDeMusicas = estilos.get(indexEstilo);
		return lista;
	}


	static ArrayList<ArrayList<Musica>> estilos;
	// Pega o nome da lista e a musica. Depois disso, adicionamos a musica � lista. Primeiro desserializamos a lista, posteriormente
	// adicionamos a musica a sua arraylist. Ap�s isso, 
	
	public static void adicionarMusicaALista(Musica musica, String nomeLista) throws Exception{
		ListaMusicas lista = ControladorLista.desserializar(nomeLista);
		lista.addInLista(musica);
		lista.serializar();
	}
	
	public static void inicializarArquivosDefault() throws Exception{
		System.out.println("Entrei no inicializar");
		String dir = System.getProperty("user.home") + File.separator + "Music" + File.separator;
		File diretorio = new File(dir + "Controles");

		if(!diretorio.isDirectory())
			diretorio.mkdir();


		File fileBiblioteca = new File(dir + "Controles" + File.separator + "AllMusics.tmp"); 
		File fileFavoritos = new File(dir + "Controles" + File.separator + "Favoritos.tmp");
		if(!fileFavoritos.exists()) {
			ListaMusicas favoritos = new ListaMusicas("Favoritos");
			favoritos.serializar();
		}
		
		if(!fileBiblioteca.exists()){
			ListaMusicas biblioteca = new ListaMusicas("AllMusics");
			biblioteca.serializar();
		}
	}
	
	public static ArrayList listarTodasAsListas() throws IOException, ClassNotFoundException{
		
		FileFilter filefilter = new FileFilter() {
	        public boolean accept(File file) {
	            if (file.getName().endsWith(".tmp")) {
	                return true;
	            }
	            return false;
	        }
	    };
	    
		File dir = new File(System.getProperty("user.home") + File.separator + "Music" + File.separator + "Controles");
		ListaMusicas lista;
		ArrayList <ListaMusicas> nomes = new ArrayList<>();
		
		for(File listas : dir.listFiles(filefilter)){
			if(!listas.getName().equals("AllMusics.tmp") && !listas.getName().equals("Favoritos.tmp")){
				FileInputStream in = new FileInputStream(listas);
				ObjectInputStream obj = new ObjectInputStream(in);
				lista = (ListaMusicas)obj.readObject();
				nomes.add(lista);
			}
		}
		return nomes;
	}
	
	public static String [] getNomeListas(ArrayList<ListaMusicas> listas){
		String[] nomeListas = new String[listas.size()];
		for(int i =0; i < nomeListas.length; i++)
			nomeListas[i] = listas.get(i).getNomeLista();
		return nomeListas;
	}
	
	public static ListaMusicas getListaMusicas(String nomeLista) throws Exception{
		return desserializar(nomeLista);
	}
	
	public static ArrayList listarMusicasLista(ListaMusicas lista){
		
		ArrayList<String> nomeMusicas = new ArrayList<>();
		
		for(int cont = 0; cont < lista.listaDeMusicas.size(); cont++){
			nomeMusicas.add(lista.listaDeMusicas.get(cont).getNomeMusica());
		}
		
		return nomeMusicas;
	}
	
	public static ListaMusicas desserializar(String nomeLista) throws Exception{
		FileInputStream in = new FileInputStream(System.getProperty("user.home") + File.separator + "Music" + File.separator + "Controles" + File.separator + nomeLista + ".tmp");
		ObjectInputStream obj = new ObjectInputStream(in);
		return (ListaMusicas)obj.readObject();
	}
	
	public static void capturarTodasAsMusicas() throws Exception{

		ListaMusicas lista = new ListaMusicas("AllMusics");
		File dir = new File(System.getProperty("user.home") + File.separator + "Music");
		FileFilter filefilter = new FileFilter() {		 
	        public boolean accept(File file) {
	        	if (file.getName().endsWith(".mp3")) {
	                return true;
	            }
	            return false;
	        }
	    };
		
		for(File file : dir.listFiles(filefilter)){
			Musica musica = new Musica(file);
			lista.addInLista(musica);
		}
	
	    lista.serializar();
	} 
	
	public static void excluirLista(String nomeLista) throws IOException{
		File f = new File(System.getProperty("user.home") + File.separator + "Music" + File.separator + "Controles" + File.separator + nomeLista + ".tmp");
		if(f.exists())
			f.delete();
	}
		
	public static void criarLista(String nomeLista) throws Exception{
		ListaMusicas lista = new ListaMusicas(nomeLista);
		lista.serializar();
	}
	
	// M�todo que captura todos os estilos da pasta Player
	
	public static ArrayList capturarEstilos() throws Exception {
		
		ListaMusicas lista = ControladorLista.desserializar("AllMusics");
		estilos = new ArrayList<>();
		
		for(Musica musica: lista.listaDeMusicas){
			// verifico se o estilo de 'musica' j� est� em uma array... 
				// se encontrar, adiciono 'musica' a arraylist que encontrei
				// caso n�o encontre nenhuma lista, crio um array e adiciono a musica

			boolean existeEstilo = false;
			
			String nameEstiloMusica = musica.getStyle();
			
			if(musica.getStyle() == null)
				nameEstiloMusica = "Estilo Desconhecido";

			for(ArrayList<Musica> subEstilo: estilos){
					String nameEstilo = subEstilo.get(0).getStyle();
					if(nameEstilo == null)
						nameEstilo = "Estilo Desconhecido";
					if(nameEstilo.equals(nameEstiloMusica)){
							existeEstilo = true;
							subEstilo.add(musica);
					}
			}

			if (!existeEstilo){
				ArrayList<Musica> listaDeEstilos = new ArrayList<>();	
				listaDeEstilos.add(musica);
				estilos.add(listaDeEstilos);
			}
			
		}
		return estilos;
	}

}
