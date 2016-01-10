package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.Scrollable;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.ScrollPane;
import java.awt.SystemColor;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle.Control;

import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;

import control.ControlPlayer;
import control.ControladorLista;
import model.ListaMusicas;
import model.Musica;
import decoradoresJanela.PainelScroll;


public class Tocador extends JPanel implements ActionListener, MouseListener{

	/**
	 * 
	 */
	
	static boolean play, flag = false;
	private static boolean pause;
	private static boolean resume;
	static boolean stop;
	final static ControlPlayer player = new ControlPlayer();
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("");
	JButton btnAddLista;
	ListaMusicas listaMusicas = null;
	Musica atualMusica;
	JScrollPane jsp;
	DefaultListModel model;
	JList list;
	JButton btnVoltar, btnAnterior, btnPlay, btnPular, btnStop, btnPause;
	JLabel lblAlbum;
    JLabel lblCantor;
    JLabel lblNomeLista;
    JLabel lblNomeMusica;
    String [] nomeMusicas;
    String tituloLista;
    JButton btnFavorito;

	/**
	 * Create the JPanel.
	 */
	public Tocador() {
		
		setBounds(100, 100, 582, 419);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JCheckBox Repetir = new JCheckBox("Repetir");
		Repetir.setFont(new Font("Vijaya", Font.PLAIN, 18));
		Repetir.setBackground(new Color(160, 82, 45));
		Repetir.setBorderPainted(false);
		Repetir.setBounds(16, 78, 89, 29);
		add(Repetir);
		
		JLabel panel_foto = new JLabel();
		panel_foto.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_foto.setBackground(new Color(255, 228, 196));
		panel_foto.setIcon(new ImageIcon(Tocador.class.getResource("/icons/disco.gif")));
		panel_foto.setBounds(111, 46, 107, 137);
		add(panel_foto);
		
		JLabel label = new JLabel("");
		panel_foto.add(label);
		
		btnFavorito = new JButton();
		btnFavorito.addActionListener(this);
		btnFavorito.setIcon(new ImageIcon(Tocador.class.getResource("/icons/faixa.png")));
		btnFavorito.setBounds(16, 116, 89, 67);
		btnFavorito.setBorderPainted(false);
		add(btnFavorito);
		
		JCheckBox Aleatorio = new JCheckBox("Aleatorio");
		Aleatorio.setFont(new Font("Vijaya", Font.PLAIN, 18));
		Aleatorio.setBackground(new Color(160, 82, 45));
		Aleatorio.setBorderPainted(false);
		Aleatorio.setBounds(16, 46, 89, 29);
		add(Aleatorio);
		
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/icons/cooffe02.png")));
		lblNewLabel.setBounds(-11, 244, 165, 108);
		add(lblNewLabel);
		
		btnAddLista = new JButton();
		btnAddLista.addActionListener(this);
		btnAddLista.setIcon(new ImageIcon(getClass().getResource("/icons/add_list02.png")));
		
		btnAddLista.setContentAreaFilled(false);
		btnAddLista.setBorderPainted(false);
		btnAddLista.setBounds(373, 46, 36, 37);
		add(btnAddLista);
		
		btnPlay = new JButton();
		btnPlay.setIcon(new ImageIcon(getClass().getResource("/icons/playIconMarron.png")));
		btnPlay.setBounds(228, 305, 54, 53);
		btnPlay.setBorderPainted(false);
		btnPlay.setContentAreaFilled(false);
		add(btnPlay);
		btnPlay.addActionListener(this);
		
		btnPause = new JButton();
		btnPause.setIcon(new ImageIcon(getClass().getResource("/icons/pinkpause01.png")));
		btnPause.setBounds(228, 305, 54, 53);
		btnPause.setBorderPainted(false);
		btnPause.setContentAreaFilled(false);
		add(btnPause);
		btnPause.addActionListener(this);
		
		if(player.temMusicaTocando()) {
			btnPlay.setVisible(false);
			btnPause.setVisible(true);
		} else {
			btnPlay.setVisible(true);
			btnPause.setVisible(false);
		}
		
		btnPular = new JButton();
		btnPular.setIcon(new ImageIcon(getClass().getResource("/icons/proximaIconMarron.png")));
		btnPular.setBounds(355, 305, 54, 53);
		btnPular.setContentAreaFilled(false);
		btnPular.setBorderPainted(false);
		add(btnPular);
		btnPular.addActionListener(this);
		
		btnAnterior = new JButton();
		btnAnterior.setIcon(new ImageIcon(getClass().getResource("/icons/anteriorMarron.png")));
		btnAnterior.setBounds(164, 305, 54, 53);
		btnAnterior.setContentAreaFilled(false);
		btnAnterior.setBorderPainted(false);
		add(btnAnterior);
		btnAnterior.addActionListener(this);
		
		btnStop = new JButton();
		btnStop.setIcon(new ImageIcon(getClass().getResource("/icons/stopMarron.png")));
		btnStop.setBounds(292, 305, 54, 53);
		btnStop.setContentAreaFilled(false);
		btnStop.setBorderPainted(false);
		add(btnStop);
		btnStop.addActionListener(this);
		
		model = new DefaultListModel();
		list = new JList(model);
		
		list.addMouseListener(this);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jsp = new JScrollPane(list);
		jsp.setBounds(413, 46, 143, 324);
	    jsp.setBackground(new Color(255, 228, 196));
	    add(jsp);
		
	    
	    
		lblAlbum = new JLabel("Album");
		lblAlbum.setFont(new Font("Vijaya", Font.PLAIN, 18));
		lblAlbum.setBounds(228, 163, 181, 14);
		add(lblAlbum);
		
		lblCantor = new JLabel("Cantor");
		lblCantor.setFont(new Font("Vijaya", Font.PLAIN, 18));
		lblCantor.setBounds(228, 138, 181, 14);
		add(lblCantor);
		
		lblNomeMusica = new JLabel("NomeMusica");
		lblNomeMusica.setFont(new Font("Vijaya", Font.PLAIN, 30));
		lblNomeMusica.setBounds(226, 95, 183, 32);
		add(lblNomeMusica);
		
		JLabel lblImagemGraos = new JLabel("");
		lblImagemGraos.setIcon(new ImageIcon(getClass().getResource("/icons/graos200.png")));
		lblImagemGraos.setBounds(-1, 328, 165, 53);
		add(lblImagemGraos);
		
		btnVoltar = new JButton("Voltar");
		btnVoltar.setFont(new Font("Vijaya", Font.PLAIN, 0));
		btnVoltar.setContentAreaFilled(false);
		btnVoltar.setBounds(330,43,40 , 40);
		btnVoltar.setBorderPainted(false);
		btnVoltar.setIcon(new ImageIcon(getClass().getResource("/icons/voltar02.png")));
		add(btnVoltar);
		
		lblNomeLista = new JLabel("");
		lblNomeLista.setBounds(16, 11, 202, 28);
		lblNomeLista.setFont(new Font("Vijaya", Font.PLAIN, 30));
		add(lblNomeLista);
		
		JLabel lblFundo = new JLabel();
		lblFundo.setBackground(new Color(0,0,0,0));
		lblFundo.setIcon(new ImageIcon(getClass().getResource("/icons/fundoEnvelhecido.jpg")));
		lblFundo.setBounds(-1, 0, 566, 381);
		add(lblFundo);
	}
	
	public void setListaMusicas(ListaMusicas lista, String nomeLista){
		this.listaMusicas = lista;
		this.tituloLista = nomeLista;
		if(!listaMusicas.listaDeMusicas.isEmpty()){
			if(player.getLista() == null) {
				this.atualMusica = listaMusicas.listaDeMusicas.get(0);
				this.nomeMusicas = new String[listaMusicas.listaDeMusicas.size()];
				for (int i = 0; i < nomeMusicas.length; i++)
					nomeMusicas[i] = listaMusicas.listaDeMusicas.get(i).getNomeMusica();
				draw();
				modificarLabels();
				stop = true;
				try {
					player.choose(listaMusicas, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if(!player.getLista().getNomeLista().equals(lista.getNomeLista())) {
				this.atualMusica = listaMusicas.listaDeMusicas.get(0);
				this.nomeMusicas = new String[listaMusicas.listaDeMusicas.size()];
				for (int i = 0; i < nomeMusicas.length; i++)
					nomeMusicas[i] = listaMusicas.listaDeMusicas.get(i).getNomeMusica();
				draw();
				modificarLabels();
				btnPlay.setVisible(true);
				btnPause.setVisible(false);
				stop = true;
				if(player.temMusicaTocando()) {
					player.pause();
					player.stop();
				}
				try {
					player.choose(listaMusicas, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				this.atualMusica = listaMusicas.listaDeMusicas.get(player.getPosMusica());
				this.nomeMusicas = new String[listaMusicas.listaDeMusicas.size()];
				for (int i = 0; i < nomeMusicas.length; i++)
					nomeMusicas[i] = listaMusicas.listaDeMusicas.get(i).getNomeMusica();
				draw();
				modificarLabels();
				stop = true;
			}
		}
		
	}
	
	void modificarLabels(){
		lblNomeMusica.setText(atualMusica.getNomeMusica());
		lblAlbum.setText(atualMusica.getAlbum());
		lblCantor.setText(atualMusica.getCantor());
		lblNomeLista.setText(tituloLista);
	}
	
	void draw(){
		for (int row = 0; row < listaMusicas.listaDeMusicas.size(); row++) {
		   	for (int col = 0; col < 2; col++) {
		   		if(col == 0) 
		   			model.add(row, listaMusicas.listaDeMusicas.get(row).getNomeMusica());
		   	}
		}
		
		validate();
		repaint();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
			
		if (source instanceof JButton) {
			
			JButton button = (JButton) source;
			
			if(button == btnAddLista) {
				String nomeLista = null;
				try {
					nomeLista = (String) JOptionPane.showInputDialog(null,"Selecione a Lista:",
							"Adicionar",
							JOptionPane.PLAIN_MESSAGE,
							null,
							ControladorLista.getNomeListas(ControladorLista.listarTodasAsListas()),
							"no");
				} catch (HeadlessException | ClassNotFoundException | IOException e2) {
					e2.printStackTrace();
				}
				try {
					ControladorLista.adicionarMusicaALista(atualMusica, nomeLista);
					JOptionPane.showMessageDialog(null, atualMusica.getNomeMusica() + " foi adicionada à " + nomeLista);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			if(button == btnPlay) {
				if(play == false) {
					System.out.println("1");
					play = true;
					pause = true;
					stop = true;
					try {
						player.play();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					atualMusica = listaMusicas.listaDeMusicas.get(player.getPosMusica());
					modificarLabels();
					btnPlay.setVisible(false);
					btnPause.setVisible(true);
				} else if(play == true && player.temMusicaTocando()) {
					pause = true;
					stop = true;
					try {
						player.resume();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					btnPlay.setVisible(false);
					btnPause.setVisible(true);
				} else if(play == true && !player.temMusicaTocando()) {
					pause = true;
					stop = true;
					try {
						player.choose(player.getLista(), player.getPosMusica());
						player.play();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					btnPlay.setVisible(false);
					btnPause.setVisible(true);
				}
			}
			if(button == btnPause) {
					play = true;
					pause = false;
					player.pause();
					btnPlay.setVisible(true);
					btnPause.setVisible(false);
			}
			if(button == btnStop) {
				if (stop) {
					player.pause();
					player.stop();
					play = true;
					pause = false;
					stop = false;
					btnPlay.setVisible(true);
					btnPause.setVisible(false);
				}
			}
			if(button == btnPular) {
				stop = true;
				
				try {
					player.pular();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				btnPlay.setVisible(false);
				btnPause.setVisible(true);
				atualMusica = listaMusicas.listaDeMusicas.get(player.getPosMusica());
				modificarLabels();

			}
			if(button == btnAnterior) {
				stop = true;
				
				try {
					player.voltar();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				btnPlay.setVisible(false);
				btnPause.setVisible(true);
				atualMusica = listaMusicas.listaDeMusicas.get(player.getPosMusica());
				modificarLabels();
			}
			if(button == btnFavorito){
				try {
					ControladorLista.adicionarMusicaALista(atualMusica, "Favoritos");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JList list = (JList) e.getSource();
		if(e.getClickCount() == 2){
			atualMusica = listaMusicas.listaDeMusicas.get(list.getSelectedIndex());
			modificarLabels();
			
			play = true;
			stop = true;
			pause = true;
            int index = list.locationToIndex(e.getPoint());
            try {
            	if(player.temMusicaTocando()) {
            		player.pause();
            		player.stop();
            	}
            	player.choose(listaMusicas, index);
            	player.play();
            	btnPlay.setVisible(false);
				btnPause.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean existeListaParaTocar() {
		if(this.listaMusicas==null)
			return false;
		return true;
	}
}

