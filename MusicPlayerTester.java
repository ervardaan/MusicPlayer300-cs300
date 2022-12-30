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

import java.util.Scanner;

/**
 * tester class which checks for correct implementation of all classes and their methods
 *
 * @author vardaan
 *
 */
public class MusicPlayerTester {
    /**
     * Tests the constructor with an invalid file, like one that doesn’t exist or one of the provided
     * .txt files (this should throw an IllegalArgumentException) Tests a valid file with toString()
     * and getTitle() and getArtist() accessor methods.
     *
     * @return true if correct implementation is found
     */
    public static boolean testSongConstructor() {
        // scenario 1:giving valid title
        {

            try {
                Song s1 = new Song(null, "Klaus Badelt", "audio/1.mid");// should not throw an exception
                if (s1.getTitle() != "" || !s1.getArtist().equals("Klaus Badelt")) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }



        }

        // scenario 2:giving wrong artist
        {
            try {
                Song s2 = new Song("He's A Pirate", null, "audio/pirates.mid");// should not throw an
                // exception
                if (!s2.getTitle().equals("He's A Pirate") || !s2.getArtist().equals("")) {
                    return false;
                }
            }

            catch (Exception e) {
                return false;
            }
        }

        // scenario 3:giving wrong filepath which is not able to load file but starts with "Audio"
        {
            try {
                // africa changed afri
                Song s1 = new Song("He's A Pirate", "Klaus Badelt", "audio/toto-afri.mid");// should throw
                // an
                // exception
                return false;

            }

            catch (IllegalArgumentException e) {
                // if we catch exception,then we are good
            } catch (Exception e) {
                return false;
            }
        }

        // scenario 4:correct case
        {
            try {
                Song s1 = new Song("He's A Pirate", "Klaus Badelt", "audio/toto-africa.mid");// should not
                // throw an
                // exception
                if (!s1.getTitle().equals("He's A Pirate") || !s1.getArtist().equals("Klaus Badelt")) {
                    return false;
                }
            }

            catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    /**
     * checking the implementation of playback methods of song class
     *
     * @return true if we get correct implementation
     */
    public static boolean testSongPlayback() {

        // scenario 1:when song is playing
        {

            // scenario 1a:we check in middle of playing song
            Song s1 = new Song("He's A Pirate", "Klaus Badelt", "audio/pirates.mid");
            if (s1.isPlaying())// if song is playing after some time before we even play it,return false
            {
                return false;
            }
            s1.play();
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                System.out.println("something has interrupted the thread");
            }
            if (!s1.isPlaying()) {
                return false;
            } else {
                s1.stop();
                if (s1.isPlaying()) {
                    return false;
                }
            }


        }

        // scenario 2:we rerun a stopped song
        {
            Song s2 = new Song("He's A Pirate", "Klaus Badelt", "audio/pirates.mid");
            if (s2.isPlaying()) {
                return false;
            }
            s2.play();
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e)// catching interrupted exception for running sleep method
            {
                System.out.println("something has interrupted the thread");
            }
            if (!s2.isPlaying()) {
                return false;
            } else {
                s2.stop();
                if (s2.isPlaying()) {
                    return false;
                }
            }
            try {
                Thread.sleep(1100);
            } catch (InterruptedException e) {
                System.out.println("something has interrupted the thread");
            }
            // rerunning the song
            s2.play();// playing the song after it has already run
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("something has interrupted the thread");
            }
            if (!s2.isPlaying()) {

                return false;
            } else {
                s2.stop();
                if (s2.isPlaying()) {
                    return false;
                }
            }
        }


        return true;
    }

    /**
     * checking correct implementation of songnode class
     *
     * @return
     */
    public static boolean testSongNode() {
        // scenario 1:we get null data song in non parameterized constructor
        {
            try {
                SongNode sn1 = new SongNode(null);// giving null data
                return false;
            } catch (IllegalArgumentException e) {

            }
        }
        // scenario 2:we get null data song in parameterized constructor
        {
            try {// we should get exception
                SongNode sn1 =
                        new SongNode(null, new SongNode(new Song("Africa", "Toto", "audio/toto-africa.mid")));
                return false;
            } catch (IllegalArgumentException e) {

            }
        }
        // checking the setting of next node
        {
            try {
                SongNode sn1 = new SongNode(new Song("He's A Pirate", "Klaus Badelt", "audio/pirates.mid"),
                        new SongNode(new Song("Africa", "Toto", "audio/toto-africa.mid")));
                SongNode collect1 = sn1.getNext();
                sn1.setNext(new SongNode(new Song("Waterloo", "ABBA", "audio/waterloo.mid")));
                SongNode collect2 = sn1.getNext();
                if (collect1.getSong().getArtist().equals(collect2.getSong().getArtist()))// checking two
                // different nodes
                // by their
                // artists
                {
                    return false;
                }
            } catch (IllegalArgumentException | NullPointerException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * testing the implementation of enqueue method
     *
     * @return true if there is a correct implementation
     */
    public static boolean testEnqueue() {
        // scenario 1:adding to no element linked list
        {
            Playlist p1 = new Playlist();
            if (p1.size() == 0) {
                p1.enqueue(new Song("He's A Pirate", "Klaus Badelt", "audio/pirates.mid"));
                // adding first element
                if (p1.size() != 1) {
                    return false;
                }
            } else {
                return false;
            }
        }

        // scenario 2:adding to 1 element linked list
        {
            Playlist p1 = new Playlist();
            if (p1.size() == 0) {
                Song s2 = new Song("Africa", "Toto", "audio/toto-africa.mid");
                p1.enqueue(new Song("He's A Pirate", "Klaus Badelt", "audio/pirates.mid"));
                p1.enqueue(s2);
                if (p1.size() != 2 || p1.dequeue().toString().equals(s2.toString()))// can't check the 2nd
                // element added
                {
                    return false;
                }
            } else {
                return false;
            }
        }
        // scenario 3:adding to many elements linked list
        {
            Playlist p1 = new Playlist();
            if (p1.size() == 0)// checking if at default,the size is 0
            {
                Song s3 = new Song("Waterloo", "ABBA", "audio/waterloo.mid");
                p1.enqueue(new Song("He's A Pirate", "Klaus Badelt", "audio/pirates.mid"));
                p1.enqueue(new Song("Africa", "Toto", "audio/toto-africa.mid"));
                p1.enqueue(s3);
                if (p1.size() != 3 || p1.dequeue().toString().equals(s3.toString()))// can't check the 2nd
                // element added
                {
                    return false;
                }
            } else {
                return false;
            }
        }


        return true;
    }

    /**
     * testing the implementation of dequeue
     *
     * @return true if there is a correct implementation
     */
    public static boolean testDequeue() {
        // scenario 1:dequeuing from empty linked list
        {
            Playlist p1 = new Playlist();// no element exists to be removed
            if (p1.dequeue() != null || p1.size() != 0 || p1.peek() != null) {
                return false;
            }
        }

        // scenario 2:dequeueing from 1 element linked list
        {
            Playlist p1 = new Playlist();
            p1.enqueue(new Song("Africa", "Toto", "audio/toto-africa.mid"));
            // only 1 node exists
            if (!p1.dequeue().toString()
                    .equals((new Song("Africa", "Toto", "audio/toto-africa.mid").toString())) || !p1.isEmpty()
                    || p1.peek() != null) {
                return false;
            }
        }

        // scenario 3:dequeuing from many element linked list
        {
            Playlist p1 = new Playlist();
            p1.enqueue(new Song("Africa", "Toto", "audio/toto-africa.mid"));
            p1.enqueue(new Song("Waterloo", "ABBA", "audio/waterloo.mid"));
            p1.enqueue(new Song("He's A Pirate", "Klaus Badelt", "audio/pirates.mid"));
            if (!p1.dequeue().toString()
                    .equals((new Song("Africa", "Toto", "audio/toto-africa.mid").toString())) || p1.isEmpty()
                    || p1.size() != 2 || !p1.peek().toString()
                    .equals(new Song("Waterloo", "ABBA", "audio/waterloo.mid").toString()))
            // we check at each stage if peek and dequeue give the correct song
            {
                return false;
            }
        }


        return true;
    }

    private static void testRunMusicPlayer300() {
        MusicPlayer300 mp1 = new MusicPlayer300();
        mp1.runMusicPlayer300(new Scanner(System.in));
    }

    /**
     * main method to run all the methods
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        
         System.out.println(testSongConstructor());
         System.out.println(testSongPlayback());
         System.out.println(testSongNode());
         System.out.println(testEnqueue());
         System.out.println(testDequeue());
        testRunMusicPlayer300();
    }

}
