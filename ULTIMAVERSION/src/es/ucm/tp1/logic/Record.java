package es.ucm.tp1.logic;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import es.ucm.tp1.control.excepciones.InputOutputRecordException;




public class Record {
	private String level;
	private static final String ERROR_RECORD = "[ERROR]: Error trying to open the file";
    private final long[] records = new long[MODO_LEVEL.length];
    private static final String[] MODO_LEVEL = new String[] { "TEST", "EASY","HARD", "ADVANCED"};

    public Record(String lvl) throws InputOutputRecordException{
        for(int i=0; i < records.length; i++) {
           records[i]=Long.MAX_VALUE;
        }
        level=lvl;
 	    String rd;
        try {
           BufferedReader record=null;
           record=new BufferedReader(new FileReader("record.txt"));
           rd=record.readLine();
           while(rd!=null) {
        	   String[] parts = rd.split(":");
        	   String part1 = parts[0]; 
        	   String part2 = parts[1];
        	   for(int i=0; i < MODO_LEVEL.length; i++) {
        		  if(part1.equalsIgnoreCase(MODO_LEVEL[i])) {
        			  records[i]=Long.parseLong(part2);
        		  }
        	   }
			   rd=record.readLine();
           }
           record.close();
        }
        catch(IOException i){
         throw new InputOutputRecordException(ERROR_RECORD);
       }
    }
    public boolean  checkRecord(float time) {
    	float record;
    	
    	for(int i=0; i < MODO_LEVEL.length; i++) {
            if(level.equalsIgnoreCase(MODO_LEVEL[i])) {
            	record=records[i]/1000f;
            	if(time<record) {
            		records[i]=(long)(time*1000);
            		writeRecord();
            		return true;
            	}
            }
         }
		return false;
    }
    public float showRecord(String l) {
    	for(int i=0; i < MODO_LEVEL.length; i++) {
            if(l.equalsIgnoreCase(MODO_LEVEL[i])) {
            	return records[i]/1000f;
            }
         }
		return 0;
    }
    
   public void writeRecord() {
	   BufferedWriter newrecord=null;
		try {
			newrecord=new BufferedWriter(new FileWriter("record.txt"));
			for(int i=0;i<MODO_LEVEL.length; i++) {
				newrecord.write(MODO_LEVEL[i]+":"+ records[i]+"\n");
			}
			newrecord.close();
		}
		catch(IOException i) {
			System.out.println("Failed to write the new record");
		}
   }

  
}