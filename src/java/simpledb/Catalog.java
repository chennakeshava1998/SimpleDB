package simpledb;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The Catalog keeps track of all available tables in the database and their
 * associated schemas.
 * For now, this is a stub catalog that must be populated with tables by a
 * user program before it can be used -- eventually, this should be converted
 * to a catalog that reads a catalog table from disk.
 */




public class Catalog {

class Table
{
	public String name,pKeyField,DbFile;
	public int tableID;
	public TupleDesc td;
  public DbFile db;
  public Table(int tableID,String name,String pkeyField,DbFile file)
  {
    this.tableID = tableID;
    this.name = name;
    this.pkeyField = pkeyField;
    this.td = file.getTupleDesc();
  }
}


	/**
     * Constructor.
     * Creates a new, empty catalog.
     */
	public ArrayList<Table> cat;    
	public Catalog() {
	cat = new ArrayList<Table>();        
	// a new list cat is created, it's of the type Table and contains these fields.
	// I felt dealing with arrayList would be easier than with sql tables for storing this info.

/* I chose ArrayList because it offers a constant time complexity for retreival of element, and the cases of adding new elements might be lesser than retrieval. Also, TreeSet could have been used but it does not provide random access */ 
    }

    /**
     * Add a new table to the catalog.
     * This table's contents are stored in the specified DbFile.
     * @param file the contents of the table to add;  file.getId() is the identifier of
     *    this file/tupledesc param for the calls getTupleDesc and getFile
     * @param name the name of the table -- may be an empty string.  May not be null.  If a name
     * @param pkeyField the name of the primary key field
     * conflict exists, use the last table to be added as the table for a given name.
     */
    public void addTable(DbFile file, String name, String pkeyField) {
        // tableId is obtained by hashing the name of the table using the string.hashCode() method
	    /* the order of details would be - tableID,table name,pKeyField,TupleDesc
       * */
      int tableID = name.hashCode();
      Table t = new Table(tableID,name,pkeyField,file);
      cat.add(t);
    }

    public void addTable(DbFile file, String name) {
        addTable(file, name, "");
    }

    /**
     * Add a new table to the catalog.
     * This table has tuples formatted using the specified TupleDesc and its
     * contents are stored in the specified DbFile.
     * @param file the contents of the table to add;  file.getId() is the identifier of
     *    this file/tupledesc param for the calls getTupleDesc and getFile
     */
    public void addTable(DbFile file) {
        addTable(file, (UUID.randomUUID()).toString());
    }

    /**
     * Return the id of the table with a specified name,
     * @throws NoSuchElementException if the table doesn't exist
     */
    public int getTableId(String name) throws NoSuchElementException {
        
      int flag = 0; //variable to break out of this for loop
      for(int i=0;i<cat.size();i++)
        {
          if(cat.get(i).name==name)
          {
            System.out.println("TableID of " + name + " is " + cat.get(i).tableID);
            flag=1;
          }

          if(flag) break;
        }
        
      if(flag==0) System.out.println("No such file name exists");
        return 0;
    }

    /**
     * Returns the tuple descriptor (schema) of the specified table
     * @param tableid The id of the table, as specified by the DbFile.getId()
     *     function passed to addTable
     * @throws NoSuchElementException if the table doesn't exist
     */
    public TupleDesc getTupleDesc(int tableid) throws NoSuchElementException {
	int flag = 0;	
	for(int i = 0;i<cat.size();i++)
	{
		if(cat.get(i).tableID = tableID){
		cat.get(i).td.toString();
		flag=1;
// td is an object of TupleDesc class declared as a member in the Table class.
// also, toString is a function that performs this task in the TupleDesc class.
		}
		if (flag==1) break;
	}
	
	if(flag==0) System.out.println("No such table exists");       
	
        return null;
    }

    /**
     * Returns the DbFile that can be used to read the contents of the
     * specified table.
     * @param tableid The id of the table, as specified by the DbFile.getId()
     *     function passed to addTable
     */
    public DbFile getDbFile(int tableid) throws NoSuchElementException {
        int flag = 0; //variable to break out of this for loop
        for(int i=0;i<cat.size();i++)
        {
          if(cat.get(i).tableID==tableid)
          {
            System.out.println("DbFile of table with TableID " + tableid + " is " + cat.get(i).file);
            flag=1;
          }

          if(flag) break;
        }
        
        if(flag==0) System.out.println("No table with such id exists");
        
        return null;
    }

    public String getPrimaryKey(int tableid) {
        int flag = 0; //variable to break out of this for loop
        for(int i=0;i<cat.size();i++)
        {
          if(cat.get(i).tableID==tableid)
          {
            System.out.println("Primary Field Value of table with TableID " + tableid + " is " + cat.get(i).pkeyField);
            flag=1;
          }

          if(flag) break;
        }
        
        if(flag==0) System.out.println("No table with such id exists");
        return null;
    }

    public Iterator<Integer> tableIdIterator() {
        for(int i=0;i<cat.size();i++)
	{
		for(int j=0;j<4;j++){
		//some processing with the elements can be performed here.
		// individual elements can be accessed as cat.get(i).approprite_member_name
		}
	}
        return null;
    }

    public String getTableName(int id) {
        int flag = 0; //variable to break out of this for loop
        for(int i=0;i<cat.size();i++)
        {
          if(cat.get(i).tableID==tableid)
          {
            System.out.println("Table name of table with TableID " + tableid + " is " + cat.get(i).name);
            flag=1;
          }

          if(flag) break;
        }
        
        if(flag==0) System.out.println("No table with such id exists");
        return null;
    }
    
    /** Delete all tables from the catalog */
    public void clear() {
        cat.clear(); /* clear() is faster than removeAll(). */
    }
    
    /**
     * Reads the schema from a file and creates the appropriate tables in the database.
     * @param catalogFile
     */
    public void loadSchema(String catalogFile) {
        String line = "";
        String baseFolder=new File(catalogFile).getParent();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(catalogFile)));
            
            while ((line = br.readLine()) != null) {
                //assume line is of the format name (field type, field type, ...)
                String name = line.substring(0, line.indexOf("(")).trim();
                //System.out.println("TABLE NAME: " + name);
                String fields = line.substring(line.indexOf("(") + 1, line.indexOf(")")).trim();
                String[] els = fields.split(",");
                ArrayList<String> names = new ArrayList<String>();
                ArrayList<Type> types = new ArrayList<Type>();
                String primaryKey = "";
                for (String e : els) {
                    String[] els2 = e.trim().split(" ");
                    names.add(els2[0].trim());
                    if (els2[1].trim().toLowerCase().equals("int"))
                        types.add(Type.INT_TYPE);
                    else if (els2[1].trim().toLowerCase().equals("string"))
                        types.add(Type.STRING_TYPE);
                    else {
                        System.out.println("Unknown type " + els2[1]);
                        System.exit(0);
                    }
                    if (els2.length == 3) {
                        if (els2[2].trim().equals("pk"))
                            primaryKey = els2[0].trim();
                        else {
                            System.out.println("Unknown annotation " + els2[2]);
                            System.exit(0);
                        }
                    }
                }
                Type[] typeAr = types.toArray(new Type[0]);
                String[] namesAr = names.toArray(new String[0]);
                TupleDesc t = new TupleDesc(typeAr, namesAr);
                HeapFile tabHf = new HeapFile(new File(baseFolder+"/"+name + ".dat"), t);
                addTable(tabHf,name,primaryKey);
                System.out.println("Added table : " + name + " with schema " + t);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println ("Invalid catalog entry : " + line);
            System.exit(0);
        }
    }
}

