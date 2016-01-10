package control;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.ListaMusicas;

public interface InterfacePlayer {
	public void play() throws IOException, LineUnavailableException;
	public void stop();
	public void pause();
	public void pular() throws UnsupportedAudioFileException, IOException, LineUnavailableException, Exception;
	public void voltar() throws UnsupportedAudioFileException, IOException, LineUnavailableException, Exception;
	public void resume() throws UnsupportedAudioFileException, Exception;
}
