package decoradoresJanela;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import view.SwingPlayer;
import view.Tocador;
import model.ListaMusicas;
import model.Musica;
import control.ControladorLista;


public class DecoradorPlaylists extends DecoradorJanelaSimples{
	
	JButton btnOpc;
	String titulo;
	ArrayList<ListaMusicas> listaDeListas;
	String[] nomeListas;
	
	public DecoradorPlaylists() {
		 super();
		 ArrayList<String> lista = new ArrayList<>();
		 janela.setTituloELista("Playlists", lista);
	}
	
	public void setTituloEListas(ArrayList<ListaMusicas> listaDeListas, String titulo ){
		
		this.titulo = titulo;
		this.listaDeListas = listaDeListas;
		this.nomeListas = new String[listaDeListas.size()];
		nomeListas = new String[listaDeListas.size()];
		nomeListas = ControladorLista.getNomeListas(listaDeListas);
		janela.limparPanel();
		janela.setTituloELista(titulo, this.getNomeListas());
		janela.btnVoltar.addActionListener(this);
		 draw();
	}
	
	public ArrayList getNomeListas(){
		ArrayList<String> listaNomes = new ArrayList<>();
		for (ListaMusicas lista : listaDeListas){
			listaNomes.add(lista.getNomeLista());
		}
		return listaNomes;
	}
	@Override
	public void draw() {
		GridBagConstraints c = new GridBagConstraints();
		btnOpc = new JButton("Opcoes");
		GridBagConstraints gbc_btnOpc = new GridBagConstraints();
		gbc_btnOpc.insets = new Insets(0, 0, 5, 5);
		gbc_btnOpc.gridx = 3;
		gbc_btnOpc.gridy = 0;
		this.janela.add(btnOpc, gbc_btnOpc);
		btnOpc.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton button = (JButton)e.getSource();
		
		if(button.getText().equals("Opcoes")){
			String[] opcoes = {"Criar", "Excluir"};
			String opcao = (String) JOptionPane.showInputDialog(null, "Selecione a ação:", "Playlists", JOptionPane.QUESTION_MESSAGE, null, opcoes, null);
			if(opcao.equals("Criar")){
				String nomeLista = (String) JOptionPane.showInputDialog("Informe o nome da Lista");
				if(!nomeLista.equals(""))
					try {
						ControladorLista.criarLista(nomeLista);
						JOptionPane.showMessageDialog(null, "Lista criada com sucesso");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				else{
					JOptionPane.showMessageDialog(null, "Nome da lista inválida!");
				}
			} else {
				opcao = (String) JOptionPane.showInputDialog(null, "Selecione a lista:", "Playlists", JOptionPane.QUESTION_MESSAGE, null, nomeListas, null);
				int num = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir " + opcao + "?");
				if(num==0){
					try {
						ControladorLista.excluirLista(opcao);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		} else if(button.getText().equals("Voltar")){
			System.out.println("oi");
		} else {
			
		}
	}

}
