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
 * A singly-linked node for our linked queue, which contains a Song object
 */
public class SongNode {
    private SongNode next;// The next SongNode in this queue
    private Song song;// The Song object in this node

    /**
     * Constructs a single SongNode containing the given data, not linked to any other SongNodes
     *
     * @param data the Song for this node
     * @throws IllegalArgumentException if data is null
     */
    public SongNode(Song data) throws IllegalArgumentException {
        if (data == null)// checking if song given is null
        {
            throw new IllegalArgumentException("song given is invalid");
        }
        song = data;
        next = null;// setting next node as null
    }

    /**
     * Constructs a single SongNode containing the given data, linked to the specified SongNode
     *
     * @param data the Song for this node
     * @param next the next node in the queue
     * @throws IllegalArgumentException if data is null
     */
    public SongNode(Song data, SongNode next) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("song given is invalid");
        }
        this.song = data;
        this.next = next;// setting the next node
    }

    /**
     * Accessor method for this node's data
     *
     * @return the Song in this node
     */
    public Song getSong() {
        return song;// returning song
    }

    /**
     * Accessor method for the next node in the queue
     *
     * @return the SongNode following this one, if any
     */
    public SongNode getNext() {
        return next;// returning the next node
    }

    /**
     * Changes the value of this SongNode's next data field to the given value
     *
     * @param next the SongNode to follow this one; may be null
     */
    public void setNext(SongNode next) {
        this.next = next;// setting the new next node
    }
}
