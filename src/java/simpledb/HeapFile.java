package simpledb;

import java.io.*;
import java.util.*;

/**
 * HeapFile is an implementation of a DbFile that stores a collection of tuples
 * in no particular order. Tuples are stored on pages, each of which is a fixed
 * size, and the file is simply a collection of those pages. HeapFile works
 * closely with HeapPage. The format of HeapPages is described in the HeapPage
 * constructor.
 * 
 * @see simpledb.HeapPage#HeapPage
 * @author Sam Madden
 */
public class HeapFile implements DbFile 
{

    /**
     * Constructs a heap file backed by the specified file.
     * 
     * @param f
     *            the file that stores the on-disk backing store for this heap
     *            file.
     */
	private File file;
	private int Id = 0;
	private TupleDesc tup_d;
    
	public HeapFile(File f, TupleDesc td) 
	{
	    this.file = f;
            this.Id = f.getAbsoluteFile().hashCode();
            this.tup_d = td;
	}

    /**
     * Returns the File backing this HeapFile on disk.
     * 
     * @return the File backing this HeapFile on disk.
     */
  	public File getFile() 
	{       	
	   return this.file;
        }

    /**
     * Returns an ID uniquely identifying this HeapFile. Implementation note:
     * you will need to generate this tableid somewhere ensure that each
     * HeapFile has a "unique id," and that you always return the same value for
     * a particular HeapFile. We suggest hashing the absolute file name of the
     * file underlying the heapfile, i.e. f.getAbsoluteFile().hashCode().
     * 
     * @return an ID uniquely identifying this HeapFile.
     */
    public int getId() {
	if(Id!=0)
	return this.Id;
	else
        throw new UnsupportedOperationException("Error in getting Id.");
    }

    /**
     * Returns the TupleDesc of the table stored in this DbFile.
     * 
     * @return TupleDesc of this DbFile.
     */
    public TupleDesc getTupleDesc() {
        if(tup_d!=null)
	return tup_d;
	else
        throw new UnsupportedOperationException("Error in getting Tuple Descriptor.");
    }

    // see DbFile.java for javadocs
    public Page readPage(PageId pid) {
        // some code goes here
      try {
            RandomAccessFile f = new RandomAccessFile(this.file,"r");
            int pos = BufferPool.PAGE_SIZE * pid.pageNumber();
            byte[] Data = new byte[BufferPool.PAGE_SIZE];
            if ((BufferPool.PAGE_SIZE + pos )> f.length()) {
                System.err.println("Error: The Page index exceeds max size");
                System.exit(1);
            }
            f.seek(pos);
            f.readFully(Data);
            f.close();
            return new HeapPage((HeapPageId) pid, Data);
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
            throw new IllegalArgumentException();
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            throw new IllegalArgumentException();
	}
        
    }

    // see DbFile.java for javadocs
    public void writePage(Page page) throws IOException {
        // some code goes here
        // not necessary for proj1
    }

    /**
     * Returns the number of pages in this HeapFile.
     */
    public int numPages() {
        // some code goes here
	int ans = (int) (file.length() / BufferPool.getPageSize());
       return ans;
    }

    // see DbFile.java for javadocs
    public ArrayList<Page> insertTuple(TransactionId tid, Tuple t)
            throws DbException, IOException, TransactionAbortedException {
        // some code goes here
        return null;
        // not necessary for proj1
    }

    // see DbFile.java for javadocs
    public Page deleteTuple(TransactionId tid, Tuple t) throws DbException,
            TransactionAbortedException {
        // some code goes here
        return null;
        // not necessary for proj1
    }

    // see DbFile.java for javadocs
   public DbFileIterator iterator(TransactionId tid) {
        // some code goes here
        //NOT SURE HOW TO IMPLEMENT THIS
    }
}
