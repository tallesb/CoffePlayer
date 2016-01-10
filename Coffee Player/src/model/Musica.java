package model;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.cmc.music.metadata.MusicMetadata;
import org.cmc.music.myid3.ID3Tag;
import org.cmc.music.myid3.id3v1.MyID3v1;
import org.farng.mp3.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;


// Classe Musica
public class Musica implements Serializable{
	
	public File mp3;
	private String style;
	private String estado;
	private String album;
	private String cantor;
	private String nomeMusica;
	private Player player;
	
	public Musica(File mp3) throws IOException, TagException{
		this.mp3 = mp3;
		
		ID3Tag infos = new MyID3v1().readID3v1(mp3, true);
		MusicMetadata part = (MusicMetadata) infos.values;
		
		this.estado = "Stop";
		this.album = part.getAlbum();
		this.cantor = part.getArtist();
		this.nomeMusica = part.getSongTitle();
		this.style = part.getGenreName();
	}
	
	public String getNomeMusica() {
		return nomeMusica;
	}

	public String getCantor() {
		return cantor;
	}

	public String getAlbum() {
		return album;
	}

	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado){
		this.estado = estado;
	}

	public String getStyle() {
		return style;
	}


/*	public static void main(String[] args) throws IOException, TagException {
		File musica = new File("C:\\Users\\Thyelle\\workspace\\MP3\\bin\\musica2.mp3");
		Music track1 = new Music(musica);
		System.out.println(track1.album + "\n" + track1.cantor + "\n" + track1.nomeMusica + "\n" + track1.style + "\n");
	} */
}
