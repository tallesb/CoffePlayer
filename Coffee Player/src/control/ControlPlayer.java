package control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import view.Tocador;
import control.ControladorLista;
import model.ListaMusicas;
import model.Musica;

import org.tritonus.share.sampled.file.TAudioFileFormat;

@SuppressWarnings("unchecked")
public class ControlPlayer implements InterfacePlayer {
	
	private static File musicFile;
	private static SourceDataLine sDLine;
	private static AudioInputStream aIOStream;
	private static Thread aux;
	private boolean flag;
	private static int posMusica;
	private static ListaMusicas lista;
	
	public void choose(ListaMusicas listaMusicas, int index) throws UnsupportedAudioFileException, IOException,
	LineUnavailableException {
		posMusica = index;
		//lista = listaMusicas.getListaDeMusicas();
		setLista(listaMusicas);
		musicFile = getLista().listaDeMusicas.get(posMusica).mp3;
		create();
		aux = new Thread(new Runnable() {
			public void run() {
			}
		});
	}

	/**
	 * Get the Inputs from the music file name
	 */
	private void create() throws UnsupportedAudioFileException, IOException,
			LineUnavailableException {
				AudioInputStream in = AudioSystem.getAudioInputStream(musicFile);
				AudioInputStream din = null;
				if (in != null) {
					AudioFormat baseFormat = in.getFormat();
					AudioFormat decodedFormat = new AudioFormat(
							AudioFormat.Encoding.PCM_SIGNED,
							baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
							baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
							false);
					din = AudioSystem.getAudioInputStream(decodedFormat, in);
					setsDLine(getLine(decodedFormat));
					aIOStream = din;
				}
	}

	/**
	 * Start or continue the music
	 * 
	 * @param t
	 *            A auxiliary thread when play the music keep running the
	 *            program
	 * @return 
	 */
	public void play() throws IOException, LineUnavailableException {
			start();
			aux.interrupt();
			aux = new Thread(new Runnable() {
				public void run() {
					rawplay();
				}
			});
			aux.start();
	}

	/**
	 * Play same part of the music
	 * @param e 
	 * @throws IOException 
	 */
	private void play(byte[] data) throws IOException {
		try {
			// Read a stream
			int length = aIOStream.read(data, 0, data.length);
			// Play the stream
			try {
				getsDLine().write(data, 0, length);
			} catch (Exception e) {
				try {
					pular();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Start the SourceDataLine for play
	 */
	private void start() {
		getsDLine().start();
		flag = true;
	}

	/**
	 * The engine of the player
	 */
	private void rawplay() {
		byte[] data = new byte[4096];
		try {
			while (flag)
				play(data);
		} catch (IOException e) {
			System.out.println("Music end");
		}
	}

	/**
	 * Close all I/O
	 */
	public void stop() {
		aux.interrupt();
		getsDLine().drain();
		getsDLine().stop();
		getsDLine().close();
		setsDLine(null);
		try {
			aIOStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stop the SourceDataLine, if started again only can continue.
	 */
	public void pause() {
		aux.interrupt();
		getsDLine().stop();
		getsDLine().drain();
		flag = false;
	}

	/**
	 * If the SourceDataLine is already in stop continue playing.
	 * @throws Exception 
	 * @throws UnsupportedAudioFileException 
	 */
	
	
	public void resume() throws UnsupportedAudioFileException, Exception {
		getsDLine().start();
		flag = true;
		try {
			play();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void pular() throws UnsupportedAudioFileException, IOException, LineUnavailableException, Exception {
		if(temMusicaTocando()) {
			pause();
			stop();
		}
		if(posMusica + 1 < getLista().listaDeMusicas.size()) {
			choose(getLista(), posMusica + 1);
			play();
		} else {
			choose(getLista(), 0);
			play();
		}
	}
	
	public void voltar() throws UnsupportedAudioFileException, IOException, LineUnavailableException, Exception {
		if(temMusicaTocando()) {
			pause();
			stop();
		}
		if(posMusica - 1 >= 0) {
			choose(getLista(), posMusica - 1);
			play();
		} else {
			choose(getLista(), getLista().listaDeMusicas.size() - 1);
			play();
		}
	}
	
	public boolean temMusicaTocando() {
		if(getsDLine() != null){
			return true;
		} else return false;
	}
	
	public int getPosMusica() {
		return posMusica;
	}

	/**
	 * Get the SourceDataLine for Read and Write the music
	 * 
	 * @param audioFormat
	 *            The music as Stream
	 */
	private static SourceDataLine getLine(AudioFormat audioFormat)
			throws LineUnavailableException {
		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		res = (SourceDataLine) AudioSystem.getLine(info);
		res.open(audioFormat);
		return res;
	}

	public static SourceDataLine getsDLine() {
		return sDLine;
	}

	public static void setsDLine(SourceDataLine sDLine) {
		ControlPlayer.sDLine = sDLine;
	}

	public static ListaMusicas getLista() {
		return lista;
	}

	public static void setLista(ListaMusicas lista) {
		ControlPlayer.lista = lista;
	}
}