package simpledb;

import java.util.*;

/**
 * SeqScan is an implementation of a sequential scan access method that reads
 * each tuple of a table in no particular order (e.g., as they are laid out on
 * disk).
 */
public class SeqScan implements DbIterator {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a sequential scan over the specified table as a part of the
     * specified transaction.
     * 
     * @param tid
     *            The transaction this scan is running as a part of.
     * @param tableid
     *            the table to scan.
     * @param tableAlias
     *            the alias of this table (needed by the parser); the returned
     *            tupleDesc should have fields with name tableAlias.fieldName
     *            (note: this class is not responsible for handling a case where
     *            tableAlias or fieldName are null. It shouldn't crash if they
     *            are, but the resulting name can be null.fieldName,
     *            tableAlias.null, or null.null).
     */
    TransactionId tsid;
    int tabid;
    String tabalias;
    DbFile dbf;
    DbFileIterator dbfi;
    public SeqScan(TransactionId tid, int tableid, String tableAlias) {
        // some code goes here
    	this.tsid = tid;
    	this.tabid = tableid;
    	this.tabalias = tableAlias;
    	this.dbf = Database.getCatalog().getDbFile(tabid);
    	this.dbfi = dbf.iterator(tsid);
    }

    /**
     * @return
     *       return the table name of the table the operator scans. This should
     *       be the actual name of the table in the catalog of the database
     * */
    public String getTableName() {
        return Database.getCatalog().getTableName(tabid);
    }
    
    /**
     * @return Return the alias of the table this operator scans. 
     * */
    public String getAlias()
    {
        // some code goes here
        return tabalias;
    }

    /**
     * Reset the tableid, and tableAlias of this operator.
     * @param tableid
     *            the table to scan.
     * @param tableAlias
     *            the alias of this table (needed by the parser); the returned
     *            tupleDesc should have fields with name tableAlias.fieldName
     *            (note: this class is not responsible for handling a case where
     *            tableAlias or fieldName are null. It shouldn't crash if they
     *            are, but the resulting name can be null.fieldName,
     *            tableAlias.null, or null.null).
     */
    public void reset(int tableid, String tableAlias) {
        // some code goes here
    	this.tabid = tableid;
    	this.tabalias = tableAlias;
    }

    public SeqScan(TransactionId tid, int tableid) {
        this(tid, tableid, Database.getCatalog().getTableName(tableid));
    }

    public void open() throws DbException, TransactionAbortedException {
        // some code goes here
    	dbfi.open();
    }

    /**
     * Returns the TupleDesc with field names from the underlying HeapFile,
     * prefixed with the tableAlias string from the constructor. This prefix
     * becomes useful when joining tables containing a field(s) with the same
     * name.
     * 
     * @return the TupleDesc with field names from the underlying HeapFile,
     *         prefixed with the tableAlias string from the constructor.
     */
    public TupleDesc getTupleDesc() {
        // some code goes here
    	TupleDesc fd = dbf.getTupleDesc();
    	int length = fd.numFields();
    	Type[] t = new Type[length];
    	String[] names = new String[length];
    	for(int i = 0; i < length; i++){
    		t[i] = fd.getFieldType(i);
    		names[i] = tabalias + "." + fd.getFieldName(i);
    	}
        return new TupleDesc(t, names);
    }

    public boolean hasNext() throws TransactionAbortedException, DbException {
		// some code goes here
    	if(dbfi==null){
    		return false;
    	}
        return dbfi.hasNext();
    }

    public Tuple next() throws NoSuchElementException,
            TransactionAbortedException, DbException {
        // some code goes here
    	if(dbfi==null){
    		throw new NoSuchElementException("tuple is null");
    	}
    	Tuple tn = dbfi.next();
    	if(tn != null){
    		return tn;
    	}
    	else
    		throw new NoSuchElementException("No next tuple");
    }

    public void close() {
        // some code goes here
    	dbfi.close();
    }

    public void rewind() throws DbException, NoSuchElementException,
            TransactionAbortedException {
        // some code goes here
    	dbfi.rewind();
    }
}
