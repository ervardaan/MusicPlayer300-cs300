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
 * A FIFO linked queue of SongNodes, conforming to our QueueADT interface.
 */
public class Playlist implements QueueADT<Song> {
    private SongNode first;//the first node
    private SongNode last;//the last node
    private int numSongs;//the number of songs

    /**
     * Constructs a new, empty playlist queue
     */
    public Playlist()
    {
        first=null;last=null;numSongs=0;//setting to default values
    }

    /**
     * Adds a new song to the end of the queue
     * @param element the song to add to the Playlist
     */
    @Override
    public void enqueue(Song element) {
        SongNode collect=new SongNode(element);
        if(size()==0)//if initial size is 0
        {
            this.first=this.last=collect;

        }
        else{
            this.last.setNext(collect);
            this.last=collect;

        }
        this.last.setNext(null);//setting the next node of added element to null
        numSongs++;
    }

    /**
     * Removes the song from the beginning of the queue
     * @return the song that was removed from the queue, or null if the queue is empty
     */
    @Override
    public Song dequeue() {
        if(size()==1)//when only one element is there and is to be removed
        {
            Song collect=first.getSong();
            this.first=this.last=null;
            numSongs--;//decreasing the number of elements
            return collect;

        }
        else if(size()>1)
        {
            Song collect=first.getSong();
            this.first=this.first.getNext();
            numSongs--;
            return collect;

        }
        else{
            return null;
        }
    }

    /**
     * Returns the song at the front of the queue without removing it
     * @return the song that is at the front of the queue, or null if the queue is empty
     */
    @Override
    public Song peek() {
        if(size()==1)
        {
            Song collect=first.getSong();//getting the first song at top
            return collect;
        }
        else if(size()>1)
        {
            Song collect=first.getSong();
            return collect;
        }
        else{
            return null;
        }
    }

    /**
     * Returns true if and only if there are no songs in this queue
     * @return true if this queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size()==0;//checking if number of elements is 0
    }

    /**
     * Returns the number of songs in this queue
     * @return the number of songs in this queue
     */
    @Override
    public int size() {
        return numSongs;//give the size
    }
    private Playlist deepCopy(Playlist orig)
    {
        Playlist copy=new Playlist();
        while(!orig.isEmpty())
        {
            copy.enqueue(orig.dequeue());
        }
        return copy;
    }
    /**
     * Creates and returns a formatted string representation of this playlist, with the string version of each song
     * in the list on a separate line
     * @return the string representation of this playlist
     */
    @Override
    public String toString()
    {
        String s="";
//        Playlist copy=deepCopy(this);
//        while(!copy.isEmpty())
//        {
//            s+=copy.dequeue().toString();
//            s+="\n";
//        }
        SongNode curNode=this.first;
        while(curNode!=null)
        {
            Song collect=curNode.getSong();
            s+=collect.toString();
            curNode=curNode.getNext();
            s+="\n";
        }
        return s;
    }
}
