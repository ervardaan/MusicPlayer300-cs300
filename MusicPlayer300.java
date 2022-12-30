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
/**
 * A linked-queue based music player which plays Actual Music Files based on keyboard input in an
 * interactive console method.This music player can load playlists of music or add individual song
 * files to the queue.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A linked-queue based music player which plays Actual Music Files based on keyboard input in an
 * interactive console method.This music player can load playlists of music or add individual song
 * files to the queue.
 */
public class MusicPlayer300 {
    private String filterArtist;// The artist to play if filterPlay is true; should be null otherwise
    private boolean filterPlay;// Whether the current playback mode should be filtered by artist;
    // false by default
    private Playlist playlist;// The current playlist of Songs

    /**
     * Creates a new MusicPlayer300 with an empty playlist
     */
    public MusicPlayer300() {
        this.playlist = new Playlist();// Making new Playlist object
        filterPlay = false;
        filterArtist = null;
    }

    /**
     * Creates and returns the menu of options for the interactive console program.
     *
     * @return the formatted menu String
     */
    public String getMenu() {
        return "Enter one of the following options:\n"
                + "[A <filename>] to enqueue a new song file to the end of this playlist\n"
                + "[F <filename>] to load a new playlist from the given file\n"
                + "[L] to list all songs in the current playlist\n"
                + "[P] to start playing ALL songs in the playlist from the beginning\n"
                + "[P -t <Title>] to play all songs in the playlist starting from <Title>\n"
                + "[P -a <Artist>] to start playing only the songs in the playlist by Artist\n"
                + "[N] to play the next song\n" + "[Q] to stop playing music and quit the program";

    }

    /**
     * Loads a playlist from a provided file, skipping any individual songs which cannot be loaded
     *
     * @param file the File object to load
     * @throws FileNotFoundException if the playlist file cannot be loaded
     */
    public void loadPlaylist(File file) throws FileNotFoundException {

        try {
            Scanner sc = new Scanner(file);// making a scanner object
            while (sc.hasNext()) {
                String s_orig = sc.nextLine();// getting next line
                ;
                String s = s_orig;// getting copy
                int last_comma = s.lastIndexOf(",");
                String path = s.substring(last_comma + 1);// getting only the deformed path
                path = "audio/" + path;// adding the audio to get the relative path(correct)

                // getting the title
                int first_comma = s.indexOf(",");
                String title = s.substring(0, first_comma);
                s = s.substring(first_comma + 1);
                // getting the artist
                int second_comma = s.indexOf(",");
                String artist = s.substring(0, second_comma);
                s = s.substring(second_comma + 1);
                try {
                    Song s1 = new Song(title, artist, path);
                    System.out.println("Loading" + " " + "\"" + s1.getTitle() + "\"");// prints only when no
                    // exception is thrown
                    this.playlist.enqueue(s1);// only enqueue if file is found and song is correctly loaded
                } catch (IllegalArgumentException e) {
                    System.out.println("x");// prints when exception is thrown
                }


            }
        } catch (Exception e) {
            throw new FileNotFoundException("file cannot be loaded");
        }
    }

    /**
     * Loads a single song to the end of the playlist given the title, artist, and filepath
     *
     * @param title    the title of the song
     * @param artist   the artist of this song
     * @param filepath the full relative path to the song file, begins with the "audio" directory for
     *                 P08
     */
    public void loadOneSong(String title, String artist, String filepath)
    // ask:can we change the signature of the method to write "throws IllegalArgumentException
    {
        try {
            Song s1 = new Song(title, artist, filepath);// making song object
            this.playlist.enqueue(s1);

        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Provides a string representation of all songs in the current playlist
     *
     * @return a string representation of all songs in the current playlist
     */
    public String printPlaylist() {

        // Playlist copy=deepCopy(this.playlist);
        // return copy.toString();//toString of playlist used
        return this.playlist.toString();

    }

    /**
     * makes a deep copy of the playlist
     *
     * @param orig the original playlist object
     * @return the copied playlist
     */
    private Playlist deepCopy(Playlist orig) {
        Playlist copy = new Playlist();
        Playlist satisfy = new Playlist();
        while (!orig.isEmpty()) {
            Song s1 = orig.dequeue();
            copy.enqueue(s1);// enqueing what we deque
            satisfy.enqueue(s1);
        }
        while (!satisfy.isEmpty()) {
            // making sure that dequeued playlist is again back with us
            this.playlist.enqueue(satisfy.dequeue());
        }
        return copy;
    }

    /**
     * Stops playback of the current song (if one is playing) and advances to the next song in the
     * playlist.
     *
     * @throws IllegalStateException if the playlist is null or empty, or becomes empty at any time
     *                               during this method
     */
    public void playNextSong() throws IllegalStateException {
        if (this.playlist == null || this.playlist.isEmpty()) {
            throw new IllegalStateException("the song playlist is empty");
        }
        Song present = this.playlist.peek();
        if (present.isPlaying()) {
            present.stop();
        }

        if (filterPlay) {
            this.playlist.dequeue();// dequeing the last stopped song
            while (!this.playlist.isEmpty() && !this.playlist.peek().getArtist().equals(filterArtist)) {
                this.playlist.dequeue();
            }
            if (this.playlist.isEmpty()) {
                throw new IllegalStateException("playlist became empty at this point ");
            }
            this.playlist.peek().play();
        }
        //IF AN ARTIST SONG IS PLAYED, THEN DON'T AUTOMATICALLY PLAY THE NEXT SONG-ALWAYS CHECK BEFORE
        else {

            // this.playlist.peek().play();//ADDED
            this.playlist.dequeue();

            if (this.playlist.isEmpty()) {
                throw new IllegalStateException("playlist became empty at this point ");

            }
            this.playlist.peek().play();//PLAYING AFTER DEQUEING AND CHECKING ONLY WHEN ARTIST SONG IS NOT THERE
        }
    }



    // public void playNextSong() throws IllegalStateException {
    //// playlist can only be empty when there are less than 2 elements left
    // //we can access and play only the first song in the queue
    // //to play the next song,we have to dequeue the song before it
    // if (filterArtist == null) {
    // if (this.playlist.isEmpty()) {
    // throw new IllegalStateException("the song playlist is empty");
    // } else {
    // //Playlist playlist1 = deepCopy(playlist);
    // Song present = this.playlist.peek();
    // if (present.isPlaying()) {
    // present.stop();
    // //TODO:
    // this.playlist.dequeue();//EITHER THIS OR THE DOWN PART
    // if (!this.playlist.isEmpty()) {
    // present = this.playlist.peek();
    // String s = present.toString();
    // int index1 = s.indexOf("(");
    // int index2 = s.indexOf(":");
    // String min = s.substring(index1 + 1, index2);
    // int index3 = s.indexOf(")");
    // String sec = s.substring(index2 + 1, index3);
    // int min1 = Integer.parseInt(min);
    // int sec1 = Integer.parseInt(sec);
    // int total = min1 * 60 + sec1;
    // present.play();
    // //sleeping till the full song runs
    // try {
    // Thread.sleep(total * 1000);
    // } catch (InterruptedException e) {
    //
    // }
    // //should we even dequeue this newly played song
    // //TODO:
    // //this.playlist.dequeue();//ADDED EXTRA
    // } else {
    // throw new IllegalStateException("after stopping the current playing song,the playlist ended");
    // }
    // } else {
    // //present.play();
    // this.playlist.dequeue();//extra added
    // }
    // }
    // }
    //// //TODO:do it at end
    //// if (this.playlist.isEmpty()) {
    //// throw new IllegalStateException("the song playlist is empty");
    //// }
    //// else{
    //// Song present2=this.playlist.peek();
    //// if(present2.isPlaying()) {
    //// present2.stop();
    //// this.playlist.dequeue();
    //// }
    ////
    //// }
    //
    // }

    /**
     * Stops any song that is playing and clears out the playlist
     */
    public void clear() {
        if (playlist.size() != 0) {
            // only get the first element when size>0
            if (playlist.peek().isPlaying()) {
                playlist.peek().stop();
            }
            while (playlist.peek() != null) {
                playlist.dequeue();
            }
        }
    }

    // private Song getfirsttitle(String title)
    // {
    // Playlist copy=deepCopy();
    // while(copy.peek()!=null)
    // {
    // if(copy.peek().getTitle().equals(title))
    // {
    // return copy.peek();
    // }
    // }
    // return null;
    // }

    /**
     * Interactive method to display the MusicPlayer300 menu and get keyboard input from the user.
     *
     * @param in scanner object
     */
    public void runMusicPlayer300(Scanner in) {
        System.out.println(getMenu());
        System.out.println("> ");
        String line = in.nextLine();// getting the line which user inputs
        // System.out.println(line);//check
        char choice = line.charAt(0);
        // System.out.println(choice);//check
        switch (choice) {
            case 'A':
                String filepath = line.substring(2);
                // System.out.println(filepath);//check
                System.out.println("Title:");
                String title = in.nextLine();
                // System.out.println(title);//check
                System.out.println("Artist:");
                String artist = in.nextLine();
                // System.out.println(artist);//check
                try {

                    loadOneSong(title, artist, "audio/" + filepath);
                } catch (IllegalArgumentException e) {
                    System.out.println("Can't add the song as it is not found");
                }
                // System.out.println(getMenu());
                runMusicPlayer300(in);
                break;
            case 'F':
                String filesource = line.substring(2);// making file object
                try {
                    File f = new File(filesource);
                    loadPlaylist(f);
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                // System.out.println(getMenu());
                runMusicPlayer300(in);
                break;
            case 'L':
                if(this.playlist.isEmpty())
                {
                    System.out.println("no songs left at this point");
                }
                else{
                System.out.println(printPlaylist());}
                // System.out.println(getMenu());
                runMusicPlayer300(in);
                break;
            case 'P':
                try {
                    char modifier = line.charAt(3);

                    switch (modifier) {


                        case 'a':
                            String artist_name = line.substring(5);
                            this.filterPlay = true;
                            this.filterArtist = artist_name;

                            while (!playlist.isEmpty()) {
                                if (playlist.peek().getArtist().equals(artist_name)) {
                                    Song s1 = playlist.peek();
                                    String s = s1.toString();
                                    int index1 = s.indexOf("(");
                                    int index2 = s.indexOf(":");
                                    String min = s.substring(index1 + 1, index2);
                                    int index3 = s.indexOf(")");
                                    String sec = s.substring(index2 + 1, index3);
                                    int min1 = Integer.parseInt(min);
                                    int sec1 = Integer.parseInt(sec);
                                    int total = min1 * 60 + sec1;
                                    playlist.peek().play();
                                    try {
                                        Thread.sleep(total * 1000);
                                    } catch (InterruptedException e) {

                                    }


                                }
                                playlist.dequeue();
                            }
                            break;//so we don't go to t case

                            // while (true) {
                            // try {
                            // this.playNextSong();
                            // } catch (IllegalArgumentException e) {
                            // System.out.println(e.getMessage());
                            // break;
                            // }
                            // }
                        case 't':
                            String title_name = line.substring(5);
                            // Playlist copy=deepCopy(this.playlist);
                            // while(copy.peek()!=null)
                            // {
                            // if(copy.peek().getTitle().equals(title_name))
                            // {
                            // this.playNextSong();
                            // }
                            // playlist.dequeue();
                            // }
                            while (!this.playlist.isEmpty()) {
                                if (!this.playlist.peek().getTitle().equals(title_name)) {
                                    this.playlist.dequeue();
                                } else {
                                    break;
                                }
                            }
                            if (!this.playlist.isEmpty()) {
                                this.playlist.peek().play();
                            }
                            while (true) {

                                try {
                                    playNextSong();
                                } catch (IllegalStateException e) {
                                    System.out.println(e.getMessage());
                                    // System.out.println("hi");//extra or checking
                                    break;
                                }
                            }
                    }
                } catch (IndexOutOfBoundsException e) {
                    if (!this.playlist.isEmpty()) {
                        this.playlist.peek().play();// extra
                    }
                    while (true) {
                        try {

                            playNextSong();
                        } catch (IllegalStateException e1) {
                            System.out.println(e1.getMessage());
                            break;
                        }
                    }
                }
                // System.out.println(getMenu());
                runMusicPlayer300(in);
                break;
            case 'N':
//                if(!this.playlist.isEmpty())
//                {
//                    this.playlist.dequeue().play();
//                }
//                else{
//                    System.out.println("the playlist is currently empty");
//                }
//                runMusicPlayer300(in);
//               break;
               if(!this.playlist.isEmpty())
            {
                if(this.playlist.peek().isPlaying())
                {
                    this.playlist.dequeue();
                    this.playlist.peek().play();
                }
                else{
                    this.playlist.peek().play();
                    this.playlist.dequeue();
                }
            }
               else{
                   System.out.println("the playlist is currently empty");
               }
                runMusicPlayer300(in);break;
//                try {
//                    playNextSong();
//                } catch (IllegalStateException e) {
//                    System.out.println(e.getMessage());
//                }
//                // System.out.println(getMenu());
//                runMusicPlayer300(in);
//                break;
            case 'Q':
                clear();
                System.out.println("Goodbye");
                break;// ending the method
            default:
                System.out.println("I don't know how to do that");
                runMusicPlayer300(in);

        }
    }
}
