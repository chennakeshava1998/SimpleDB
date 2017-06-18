package simpledb;

import java.io.Serializable;
import java.util.*;

import com.sun.glass.ui.CommonDialogs.Type;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable {

    /**
     * A help class to facilitate organizing the information of each field
     * */
    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         * */
        Type fieldType;
        
        /**
         * The name of the field
         * */
        String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }

        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
    }

    public TDItem[] tdi;
    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    public Iterator<TDItem> iterator() 
    {  
    	String type;
    	for(int i=0;i<tdi.length;i++)
    	{
    		if(tdi[i].fieldType==INT_TYPE)
        		type="INT_TYPE";
        	else if(tdi[i].fieldType==STRING_TYPE)
        		type="STRING_TYPE";
    		System.printf("Field " + (i+1) + ": " + tdi[i].fieldName + type );
    	}
        // some code goes here : in progress
        return null;
    }

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr)
    {
    	if(typeAr.length>0)
    	{
        	tdi=new TDItem[typeAr.length];
    		for(int i=0;i<typeAr.length;i++)
    		{
        	       tdi[i].TDItem(typeAr[i],fieldAr[i]);    		
    		}
    	}
    	else
    		printf("Cannot create a tuple schema with 0 fields");
        // some code goes here : in progress
    }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr)
    {
    	if(typeAr.length>0)
    	{
    		tdi = new TDItem[typeAr.length];
    		for(int i=0;i<typeAr.length;i++)
    		{
    			tdi[i].TDItem(typeAr[i],null);
    		}
    	}
    	else
    		printf("Cannot create a tuple schema with 0 fields");
        // some code goes here : in progress
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields()
    {
        return tdi.length;
    }//modified

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     * 
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        try
        {
        	if(i>=0 && i<=tdi.length)
              return tdi[i].fieldName;      	
        }
        catch(NoSuchElementException e)
        {
        	System.printf("No such index exists in the table");
        }
        return null;
        //some code goes here : in progress
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     * 
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException
    {
    	 try
         {
         	if(i>=0 && i<=tdi.length)
               return tdi[i].fieldType;      	
         }
         catch(NoSuchElementException e)
         {
         	System.printf("No such index exists in the table");
         }
        return null;
        // some code goes here : in progress
    }

    /**
     * Find the index of the field with a given name.
     * 
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     */
    public int fieldNameToIndex(String name) throws NoSuchElementException
    { 
    	boolean flag=0;
    	try
    	{
    		for(i=0;i<tdi.length;i++)
    		{
    			if(equals(name,tdi[i].fieldName))
    			{
    				flag=1;
    				return i;
    			}
    		}
    		if(flag==0)
    			flag=2;
    	}
    	catch( NoSuchElementException e)
	{
    	 	 System.printf("No field with matching name found");
        }
		return -1;
    	// some code goes here : in progress
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() 
    {
    	int size=0;
    	for(int i=0;i<tdi.length;i++)
    	{
    		size+= tdi[i].fieldType.getLen();
    	}
    	return size;
        // some code goes here : in progress
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2)
    {
    	int i;
    	Type[] typeAr = new Type[td1.tdi.length +td2.tdi.length];
    	String[] fieldAr = new String[td1.tdi.length +td2.tdi.length];
    	for(i=0;i<td1.tdi.length;i++)
    	{
    		typeAr[i]=td1.tdi[i].fieldType;
    		fieldAr[i]=td1.tdi[i].fieldName;
    	}
    	for(;i<td2.tdi.length;i++)
    	{
    		typeAr[i]=td2.tdi[i].fieldType;
    		fieldAr[i]=td2.tdi[i].fieldName;
    	}
    	TupleDesc td =new TupleDesc(typeAr,fieldAr);
    	return td;
        // some code goes here : in progress
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they are the same size and if the n-th
     * type in this TupleDesc is equal to the n-th type in td.
     * 
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o)
    {
    	if (!(o instanceof TupleDesc)) {
        	return false;
    	}

    	TupleDesc that = (TupleDesc) o;
    	
    	boolean flag = true;	
    	for(int i=0; i<numFields();i++){
    		flag = this.i.equals(that.i);
    	}
        // some code goes here : in progress
        return flag;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * 
     * @return String describing this descriptor.
     */
    public String toString() 
    {
        String descriptor="",type,index;
        for(int i=0;i<tdi.length;i++)
        {
        	index=Integer.toString(i);
        	if(tdi[i].fieldType==INT_TYPE)
        		type="INT_TYPE";
        	else if(tdi[i].fieldType==STRING_TYPE)
        		type="STRING_TYPE";
        	descriptor = descriptor + type + "[" + index + "](" + tdi[i].fieldName + "[" + index  + "]," ;
        }
        return descriptor;
        //some code goes here : in progress
    }
}
