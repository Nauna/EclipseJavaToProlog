/*2017
 * Author: Annahita Doulatshahi
 * 
 * Class Description: 
 * Using prolog program grade.pl (which is a very basic prolog program to calculate a predefined course final grade)
 * 
 * Hard Coded:
 * filePath = Folder path, to grade.pl, as well as location for outputExecution.txt
 * query = query into compiled prolog program
 * 
 * Program execution will compile prolog program from given filePath, then run query into program. 
 * The results of our query will be sent to stdout and written into a txt file filePath\outputExecution.txt
 * 
 * Extra functionality set into this. 
 * 1)numOfQueries can be used to set a loop of queries
 * 2)object prolog is not killed until end of loop
 * 
 * Main.process(String result) unique to this program. String format will be different for different queries and programs.
 * 
 * When running a different program and or query, consider commenting out Main.process(), and just running this program
 * stdout and outputExecution.txt will contain the query results in an unformatted version. Can view results and alter
 * Main.process()
 * 
 * */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	private static PrologCall prolog;
	private static BufferedWriter outputFile; 	// various outputs will be included in this file, not limited to error msgs
	private static int numOfQueries = 1;
	
	public static void main(String[] args) {
		//
		String query = "grade(G),letter(L,G).";//"letter(L,G).";//"grade(G).";
		String result;
		String filePath = "C:\\Users\\annad\\Documents\\EclipseToJava";
		

		try{
			outputFile   = new BufferedWriter( new FileWriter( filePath + "\\outputExecution.txt" ) ) ;
			prolog = new PrologCall(outputFile); //contains the prolog translator object
			
			prolog.print_function("******FILE RESULTS FROM PROLOG QUERY RUN FROM JAVA PROGRAM******", 2);
			
			for(int i = 0;i<numOfQueries;i++){
				result = prolog.runQuery(query);
				process(result);
			}
			
			if(prolog != null) prolog.closeResources();
			if(outputFile != null) outputFile.close();
			
		}catch (IOException e) { e.printStackTrace(); }  
	}
	
	private static void process(String result){
		/*Will vary by prolog program-here is a basic example
		 * STRING PROCESSING: SPECIFIC TO THIS QUERY AND PROGRAM(grade.pl)
		 */
		
			//Variables
		String temp;
		int location;
			//header
		prolog.print_function("***SAMPLE STRING FORMAT FOR RESULTS***",3);
			//grade
		result = result.substring(result.indexOf("functor=grade"));
		location = result.indexOf("arg(1)=")+ ("arg(1)=").length();
		temp = result.substring(location, result.indexOf(']') );
		prolog.print_function("grade(G) = "+temp,3);
			//letter
		location = result.indexOf("functor=letter")+ ("functor=letter").length();
		result = result.substring(location);
		location = result.indexOf("functor=")+ ("functor=").length();
		temp = result.substring( location, result.indexOf(']') );
		prolog.print_function("letter(L,G) = "+temp,3);	
	}
}
