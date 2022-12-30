
//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P08 MUSIC PLAYER
// Course: CS 300 Fall 2022
//
// Author: VARDAAN KAPOOR
// Email: VKAPOOR5@WISC.EDU
// Lecturer: (Mouna Kacem, Hobbes LeGault, or Jeff Nyhoff)
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////
import java.io.IOException;

/**
 * A representation of a single Song. Interfaces with the provided AudioUtility class, which uses
 * the javax.sound.sampled package to play audio to your computer's audio output device
 *
 * @author vardaan
 *
 */
public class Song {
    private AudioUtility audioClip;// This song's AudioUtility interface to javax.sound.sampled
    private String artist;// The artist of this song
    private int duration;// The duration of this song in number of seconds
    private String title;// The title of this song

    /**
     * Initializes all instance data fields according to the provided values
     *
     * @param title    the title of the song, set to empty string if null
     * @param artist   the artist of this song, set to empty string if null
     * @param filepath the full relative path to the song file,begins with the "audio" directory for
     *                 P08
     * @throws IllegalArgumentException if the song file cannot be read
     */
    public Song(String title, String artist, String filepath) throws IllegalArgumentException {


        try {
            audioClip = new AudioUtility(filepath);// making an audio file object
            this.duration = audioClip.getClipLength();
        } catch (IOException e) {
            throw new IllegalArgumentException("the song cannot be read");// catching io exception
        }
        if (title == null) {
            this.title = "";
        } else {
            this.title = title;
        }
        if (artist == null) {
            this.artist = "";
        } else {
            this.artist = artist;
        }



    }

    /**
     * Accessor method for the song's title
     *
     * @return the title of this song
     */
    public String getTitle() {
        return title;// giving title back
    }

    /**
     * Accessor method for the song's artist
     *
     * @return the artist of this song
     */
    public String getArtist() {
        return artist;// giving artist back
    }

    @Override
    /**
     * Creates and returns a string representation of this Song
     *
     * @return a string representation
     */
    public String toString() {
        int min = duration / 60;
        int sec = duration % 60;// getting remainder seconds
        String s = "\"" + title + "\" " + "(" + min + ":" + sec + ")" + " " + "by " + artist;// "\""
        // stands
        // for "
        // character
        return s;
    }

    /**
     * Tests whether this song is currently playing using the AudioUtility
     *
     * @return true if song is running
     */
    public boolean isPlaying() {
        return audioClip.isRunning();// calling isRunning() of audioutility
    }

    /**
     * Uses the AudioUtility to start playback of this song, reopening the clip for playback if
     * necessary
     */
    public void play() {
        if (audioClip.isReadyToPlay()) {
            audioClip.startClip();// if file is ready, then call start clip method
        } else {
            audioClip.reopenClip();// only reopens the file
            audioClip.startClip();// we have to play the file again
        }
        System.out.println("playing" + this.toString());
    }

    /**
     * Uses the AudioUtility to stop playback of this song
     */

    public void stop() {
        audioClip.stopClip();// stop song by calling audioutility method
    }
}
