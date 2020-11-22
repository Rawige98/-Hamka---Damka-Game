package Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *This class represents a logger for capture the application activity.
 */
public final class Logger {
	//-------------------------------------------------------------------
	//-----------------------------fields--------------------------------
	//-------------------------------------------------------------------
	
	/** logging output file */
	static private File outputLogFile;
	static private long latestMessage;
	
	/** logging writer buffer */
	static private PrintStream writer; 
	
	//-------------------------------------------------------------------
	//-------------------------initialize-----------------------------
	//-------------------------------------------------------------------
	/**
	 * Initialize the output log file and the writer for logging.
	 */
	public static void initializeMyFileWriter(){
	
		outputLogFile = new File("output.txt");
		try {
			writer = new PrintStream(outputLogFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	//-------------------------------------------------------------------
	//-------------------------functionality-----------------------------
	//-------------------------------------------------------------------
	
	/**
	 * write the message on the output log file.
	 */
	public static void log(String message) {
		// TODO Auto-generated method stub
		Logger.log(message, false);
	}
	
	/**
	 * Writes given text message to the log file.
	 * @param message The text to be written in the log
	 * @param isSeparatorNeeded True if separator is needed
	 */
	protected static void log(String message, boolean isSeparatorNeeded){
		Date dateNow = new Date();
		SimpleDateFormat dt = new SimpleDateFormat("hh:mm:ss"); 
		String dateprefix = dt.format(dateNow);
		

		if(latestMessage > 0 && System.currentTimeMillis() - latestMessage > 5000)
			writeAsteriskSeparatorToLogFile();
		
		latestMessage = System.currentTimeMillis();
		writer.print(dateprefix+"\t"+message+"\n");
		System.out.println(dateprefix+"\t"+message+"\n");

		writer.flush();

	}
	
	/**
	 * Writes separator to log file.
	 */
	protected static void writeHyphenSeparatorToLogFile(){
		writer.print("\n-----------------------------------------------------------------------------");
	}
	
	/**
	 * Writes separator to log file.
	 */
	protected static void writeAsteriskSeparatorToLogFile(){
		writer.print("****************************************************************************\n");
	}
	
	/**
	 * Saves the log file (by closing the file writer).
	 */
	protected static void saveLogFile(){
		writer.close();
	}

}
