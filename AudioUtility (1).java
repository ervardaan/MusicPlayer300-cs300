import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * An interface to <code>javax.sound.sampled</code> for MusicPlayer300.
 */
public class AudioUtility {
  
  private Clip clip;
  private AudioInputStream audioInputStream;
  private String filepath;
  
  /**
   * Creates a new AudioUtility object for the given song
   * 
   * @param filepath the full relative path to the song file, which begins with the "audio" directory
   * for the purposes of P08
   * @throws IOException if the audio clip cannot be loaded
   */
  public AudioUtility (String filepath) throws IOException {
    this.filepath = filepath;
    try {
      audioInputStream = AudioSystem.getAudioInputStream(new File(filepath));
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
    } catch (LineUnavailableException | UnsupportedAudioFileException e) {
      throw new IOException("Unable to load file "+filepath);
    }
  }
  
  /**
   * Reopens the clip for playing if it has finished or been stopped
   */
  public void reopenClip() {
    if (!this.clip.isOpen()) {
      try {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filepath));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
      } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
        // hopefully won't happen if we get this far
      }
    }
  }

  /**
   * Accessor method for the length of this clip in seconds
   * @return the length of the audio clip in seconds
   */
  public int getClipLength() {
    return (int) (this.clip.getMicrosecondLength()/1000000L);
  }

  /**
   * Accessor for the playback status of this audio clip
   * @return true if the clip is currently playing, false otherwise
   */
  public boolean isRunning() {
    return this.clip.isRunning();
  }
  
  /**
   * Checks whether this clip is ready to play. If it is not, it must be reopened with reopenClip().
   * 
   * @return true if the clip is open and ready to play, false otherwise
   */
  public boolean isReadyToPlay() {
    return this.clip.isOpen();
  }

  /**
   * Starts playback of the audio clip. Only works ONCE per clip; to play a song again after it has
   * finished, you will need to manually reopen the clip
   */
  public void startClip() {
    this.clip.start();
  }

  /**
   * Stops playback of the audio clip
   */
  public void stopClip() {
    this.clip.stop();
    this.clip.close();
  }

}
