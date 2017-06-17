package simpledb;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import simpledb.TupleDesc.TDItem;

/**
 * Tuple maintains information about the contents of a tuple. Tuples have a
 * specified schema specified by a TupleDesc object and contain Field objects
 * with the data for each field.
 */
public class Tuple implements Serializable {

    private static final long serialVersionUID = 1L;

    //create a new private variable
    private TupleDesc tupDesc;
    private Field[] Obj;    
    private RecordId record_Id;
    
    /**
     * Create a new tuple with the specified schema (type).
     * 
     * @param td
     *            the schema of this tuple. It must be a valid TupleDesc
     *            instance with at least one field.
     */
    public Tuple(TupleDesc td) {
        // some code goes here
    	  	if(tupDesc.numFields() < 1)
    		throw new IllegalArgumentException(td.toString());
    	
    	tupDesc = td;
    	Obj = new Field[td.numFields()];
    	record_Id = null;
    }

    /**
     * @return The TupleDesc representing the schema of this tuple.
     */
    public TupleDesc getTupleDesc() {
        // some code goes here
    	
        return tupDesc;
    }

    /**
     * @return The RecordId representing the location of this tuple on disk. May
     *         be null.
     */
    public RecordId getRecordId() {
        return record_Id;
    }

    /**
     * Set the RecordId information for this tuple.
     * 
     * @param rid
     *            the new RecordId for this tuple.
     */
    public void setRecordId(RecordId rid) {
    	record_Id = rid;
    }

    /**
     * Change the value of the ith field of this tuple.
     * 
     * @param i
     *            index of the field to change. It must be a valid index.
     * @param f
     *            new value for the field.
     */
    public void setField(int i, Field f) {
        // some code goes here
    	
    	if(i<0 || i>=Obj.length)
    	throw new IllegalArgumentException("Invalid input, either out of bound or negative");
    	if(f.getType().equals(Obj[i].getType()))
    		Obj[i] = f;
    	else
    	throw new IllegalArgumentException("Input Type Mismatch");
    }

    /**
     * @return the value of the ith field, or null if it has not been set.
     * 
     * @param i
     *            field index to return. Must be a valid index.
     */
    public Field getField(int i) {
        // some code goes here
    	if(i<0 || i >= Obj.length)
    		throw new IllegalArgumentException("Invalid input, either out of bound or negative.");
    	 else
        return Obj[i];
    }

    /**
     * Returns the contents of this Tuple as a string. Note that to pass the
     * system tests, the format needs to be as follows:
     * 
     * column1\tcolumn2\tcolumn3\t...\tcolumnN\n
     * 
     * where \t is any whitespace, except newline, and \n is a newline
     */
    public String toString() {
    	
    	String result = "";
    	for(int i=0; i<Obj.length; i++)
    	{
    		String temp = "";
    		if(i == Obj.length-1)
    			temp = Obj[i] + "\n";
    		else
    			temp = Obj[i] + "\t";
    		
    		result+=temp;
    	}
    	
    	return result;
    	
         }
    
    /**
     * @return
     *        An iterator which iterates over all the fields of this tuple
     * */
    public Iterator<Field> fields()
    {   	
    	List<Field> fileList = Arrays.asList(Obj);//converting to list to make it easier to use iterator
    	Iterator<Field> itr = fileList.iterator();
        return itr;
    }
    
    /**
     * reset the TupleDesc of this tuple
     * */
    public void resetTupleDesc(TupleDesc td)
    {
        // some code goes here
    	tupDesc = td;
    }
}
