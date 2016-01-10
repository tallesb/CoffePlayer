package decoradoresJanela;
import java.awt.event.ActionListener;

import javax.swing.JPanel;


public abstract class ModeloJanela extends JPanel implements ActionListener{
	
	public abstract void draw();
	public void limparPanel(){
		this.removeAll();
	}

}
