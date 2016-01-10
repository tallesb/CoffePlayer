package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import org.farng.mp3.TagException;

import model.ListaMusicas;
import model.Musica;
import control.ControlPlayer;
import control.ControladorLista;
import decoradoresJanela.DecoradorEstilos;
import decoradoresJanela.DecoradorFavoritos;
import decoradoresJanela.DecoradorPlaylists;
import decoradoresJanela.JanelaSimples;
import decoradoresJanela.ModeloJanela;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.ResourceBundle.Control;

public class SwingPlayer extends JFrame implements ActionListener, MouseListener{

	// Pilha que armazenar� os passos 
	Stack pilha = new Stack<JPanel>();
	
	// Janelas utilzadas no aplicativo
	DecoradorPlaylists playlist = null;
	DecoradorFavoritos favoritos = null;
	DecoradorEstilos estilos = null;
	Tocador guiPlayer = null;
	
	// Paineis de nossa aplica��o
	private JPanel home;

	// Botoes da p�gina Home
	JButton btnFavoritos = new JButton("");
	JButton btnPlaylists = new JButton("");
	JButton btnEstilos = new JButton("");
	JButton btnBiblioteca = new JButton("");
	JButton btnVoltar = new JButton("Voltar");
	JButton btnEmRep = new JButton("Em reprodução");
	private final JButton btnAbrirArquivo = new JButton("");
	
	// Botoes da p�gina painelTeste
	private ImageIcon iconFavoritos = new ImageIcon(getClass().getResource(
			"/icons/iconFavoritos.png"));
	private ImageIcon iconPlaylists = new ImageIcon(getClass().getResource(
			"/icons/iconPlaylist.png"));
	private ImageIcon iconBiblioteca = new ImageIcon(getClass().getResource(
			"/icons/iconBiblioteca.png"));
	private ImageIcon iconEstilos = new ImageIcon(getClass().getResource(
			"/icons/iconEstilos.png"));
	private ImageIcon iconVoltar = new ImageIcon(getClass().getResource(
			"/icons/iconVoltar.png"));
	private ImageIcon iconFundoHome = new ImageIcon(getClass().getResource(
			"/icons/FundoHome.PNG"));
	
	
	public SwingPlayer() throws Exception {
		
		// Caracteristicas da Frame
		super("Coffee Player");
		ControladorLista.inicializarArquivosDefault();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 419);

		// Painel p�gina Home
		home = new JPanel();
		home.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// Painel p�gina Minhas Listas
		// minhasListas = new JPanel();
		
		pilha.push(home);
		this.getContentPane().add((JPanel)pilha.peek());
		
		// Modificando as caracter�sticas dos bot�es da p�gina Home
		
		btnPlaylists.setIcon(iconPlaylists);
		btnPlaylists.setBounds(475, 194, 71, 59);
		btnEstilos.setIcon(iconEstilos);
		btnEstilos.setBounds(475, 124, 71, 59);
		btnBiblioteca.setIcon(iconBiblioteca);
		btnBiblioteca.setBounds(475, 54, 71, 59);				
		btnFavoritos.setIcon(iconFavoritos);
		btnFavoritos.setBounds(475, 264, 71, 59);
		btnBiblioteca.addActionListener(this);
		btnEstilos.addActionListener(this);
		btnPlaylists.addActionListener(this);
		btnFavoritos.addActionListener(this);
		
		// Adicionando os bot�es e labels na Frame...
		// SetLayout: Absolute (por isso que o argumento do m�todo setLayout � 'null')
		home.setLayout(null);
		home.add(btnPlaylists);
		home.add(btnEstilos);
		home.add(btnBiblioteca);
		home.add(btnFavoritos);
		
		btnAbrirArquivo.setBounds(10, 279, 89, 23);
		btnAbrirArquivo.addActionListener(this);
		btnAbrirArquivo.setIcon(iconBiblioteca);
		
		home.add(btnAbrirArquivo);
		
		
		// Label onde est� conida a imagem da p�gina Home
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 576, 399);
		label.setIcon(iconFundoHome);
		home.add(label);
		
		// Janelas que serão utilizadas ao clicar em um botão...
		
		estilos = new DecoradorEstilos();
		playlist = new DecoradorPlaylists();
		
	}
	

	@Override
	// M�todo sobrescrito da interface ActionListener. Aqui ser� tratada 
	// as a��es dos bot�es seleccionados.
	
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();
		
		if (source instanceof JButton) {
			JButton button = (JButton) source;
			if (button == btnBiblioteca){
				if(guiPlayer.stop || !guiPlayer.play)
					guiPlayer = null;
				
				if(guiPlayer == null){
					guiPlayer = new Tocador();
					try {
						ControladorLista.capturarTodasAsMusicas();
						guiPlayer.setListaMusicas(ControladorLista.getListaMusicas("AllMusics"), "Biblioteca");
						guiPlayer.btnVoltar.addActionListener(this);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				this.getContentPane().remove((JPanel)pilha.peek());
				pilha.push(guiPlayer);
				this.getContentPane().add((JPanel)pilha.peek());
				this.setVisible(true);
				repaint();
				validate();
			} 
			
			if (button == btnEstilos) {
				try {
					estilos.setEstilosETitulo(ControladorLista.capturarEstilos(), "Estilos");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				estilos.janela.btnVoltar.addActionListener(this);
				estilos.janela.list.addMouseListener(this);;
				
				this.getContentPane().remove((JPanel)pilha.peek());
				pilha.push(estilos.janela);
				this.getContentPane().add((JPanel)pilha.peek());
				this.setVisible(true);
				repaint();
			} 
			
			else if (button == btnPlaylists){
				
				try {
					playlist.setTituloEListas(ControladorLista.listarTodasAsListas(), "Playlists");
				} catch (ClassNotFoundException | IOException e1) {
					System.out.println("caiu aqui");
				}

				playlist.janela.list.addMouseListener(this);
				playlist.janela.btnVoltar.addActionListener(this);
				
				this.getContentPane().remove((JPanel)pilha.peek());
				pilha.push(playlist.janela);
				this.getContentPane().add((JPanel)pilha.peek());
				this.setVisible(true);
				repaint();
			} 
			
			else if (button == btnFavoritos) {
				if(guiPlayer.stop){
					guiPlayer = null;
				}
				if(guiPlayer == null){
					guiPlayer = new Tocador();
					try {
						ControladorLista.capturarTodasAsMusicas();
						guiPlayer.setListaMusicas(ControladorLista.getListaMusicas("Favoritos"), "Favoritos");
						guiPlayer.btnVoltar.addActionListener(this);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				this.getContentPane().remove((JPanel)pilha.peek());
				pilha.push(guiPlayer);
				this.getContentPane().add((JPanel)pilha.peek());
				this.setVisible(true);
				repaint();
				validate();
			}
			
			else if(button.getText() == "Voltar"){
				if(guiPlayer != null)
					guiPlayer.btnVoltar.removeActionListener(this);
				this.getContentPane().remove((JPanel)pilha.peek());
				pilha.pop();
				this.getContentPane().add((JPanel)pilha.peek());
				setVisible(true);
				repaint();
				validate();
				button.addActionListener(this);
			}
			
			else if(button == btnAbrirArquivo){
				try {
					if(biblioteca()){
						this.getContentPane().remove((JPanel)pilha.peek());
						pilha.push(guiPlayer);
						this.getContentPane().add((JPanel)pilha.peek());
						this.setVisible(true);
						repaint();
						validate();
					}		
				} catch (IOException | TagException e1) {
					e1.printStackTrace();
				}
			}
		
		}
	}
	
		public void mouseClicked(MouseEvent e){
			JList list = (JList)e.getSource();
	    	if (e.getClickCount() == 2) {
	    		if(list.equals(playlist.janela.list)){
	    			guiPlayer = null;
	    			guiPlayer = new Tocador();
	    			
	    			try {
	    				System.out.println(ControladorLista.getListaMusicas((String)list.getSelectedValue()).listaDeMusicas.get(0).getNomeMusica());
	    				guiPlayer.setListaMusicas(ControladorLista.getListaMusicas((String)list.getSelectedValue()), (String)list.getSelectedValue());
					} catch (Exception e1) {
						System.out.println();
					}
		    		getContentPane().remove((JPanel)pilha.peek());
					pilha.push(guiPlayer);
					guiPlayer.btnVoltar.addActionListener(this);
					getContentPane().add((JPanel)pilha.peek());
					setVisible(true);
					repaint();
	    		}
	    		else if(list.equals(estilos.janela.list)){
	    			guiPlayer = null;
	    			guiPlayer = new Tocador();
	    			
	    			try {	
	    				guiPlayer.setListaMusicas(ControladorLista.getUmEstilo((int)list.getSelectedIndex(), (String)list.getSelectedValue() ), (String)list.getSelectedValue());
					} catch (Exception e1) {
						System.out.println("erro");
					}
		    		getContentPane().remove((JPanel)pilha.peek());
					pilha.push(guiPlayer);
					guiPlayer.btnVoltar.addActionListener(this);
					getContentPane().add((JPanel)pilha.peek());
					setVisible(true);
					repaint();
	    		}
	    	} 
		}
	
	private boolean biblioteca() throws IOException, TagException{
		
		JFileChooser fileChooser = new JFileChooser();
		
		FileFilter mp3Only = new FileFilter(){
			public String getDescription() {
				return "Sound file (*.MP3)";
			}

			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				} else {
					return file.getName().toLowerCase().endsWith(".mp3");
				}
			}
		};
		
		fileChooser.setFileFilter(mp3Only);
		fileChooser.setDialogTitle("Coffee Player");
		fileChooser.showOpenDialog(btnBiblioteca);
		
		File arquivo = fileChooser.getSelectedFile();
		if(arquivo != null){
			Musica musica = new Musica(arquivo);
			ListaMusicas lista = new ListaMusicas("Musicas do PC");
			lista.addInLista(musica);
			guiPlayer = null;
			guiPlayer = new Tocador();
			guiPlayer.setListaMusicas(lista, lista.getNomeLista());
			guiPlayer.btnVoltar.addActionListener(this);
		}
		return (arquivo!=null);
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingPlayer frame = new SwingPlayer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}