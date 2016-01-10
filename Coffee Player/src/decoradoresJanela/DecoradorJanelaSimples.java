package decoradoresJanela;


public abstract class DecoradorJanelaSimples extends ModeloJanela{
	public JanelaSimples janela;
	public DecoradorJanelaSimples() {
		this.janela = new JanelaSimples();
	}
}
