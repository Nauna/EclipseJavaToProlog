/*2017
 * Author: Annahita Doulatshahi
 * 
 * Class Description: 
 * Use this class to utilize tkEclipse prolog engine from Java program
 * Must have tkEclipse installed on current local mashine to use
 * 
 * Usage Description:
 * 1) In PrologCall constructor, set path to prolog program
 * 2)Link prolog engine lib to main of this project. 
 * In Elipse: <rightMouseClick Project>,BuildPath,AddExternalArchives,<find Path to tkEclipeEngine on local machine>
 * 3)need to add cmd line option-->(-Declipse.directory="C:\\Program Files\\ECLiPSe 6.1")
 * In Eclipse: <rightMouseClick Main.class>,Properties,Run/Debug Settings,<pick the class>,edit,arguments (tab)
 * you can on this screen add cmdLine arguments(program arguments) or supply a cmdLine option (VM arguments). 
 * The latter is what is needed. 
 * 
 * */
import com.parctechnologies.eclipse.*;
import java.io.*;

public class PrologCall {
	//CLASS VARIABLES	
	EclipseEngineOptions eclipseEngineOptions; 
    EclipseEngine eclipse;	// Object representing the Eclipse process
    File eclipseProgram;// Path of the Eclipse program
    FromEclipseQueue eclipse_to_java;// Data coming in from eclipse
    java.util.Scanner stringScanner;
    int globalCall=0;
    BufferedWriter outputFile;
    
    public PrologCall(BufferedWriter outputFil) {
    	outputFile = outputFil;
    		//path = local path to prolog program
		String path = "C:\\Users\\annad\\Documents\\EclipseToJava\\grade.pl";
    	  // Create some default Eclipse options
	    eclipseEngineOptions = new EclipseEngineOptions();
	    	// Connect the Eclipse's standard streams to the JVM's
	    eclipseEngineOptions.setUseQueues(true);
	    
	    // Initialize Eclipse
	    try {
			eclipse = EmbeddedEclipse.getInstance(eclipseEngineOptions);
				// Set up the path of the Eclipse program to be used.
		    eclipseProgram = new File(path);
		    	// Compile the eclipse program.
		    eclipse.compile(eclipseProgram);
		    	// Create the stream
		    eclipse_to_java = eclipse.getFromEclipseQueue("eclipse_to_javaTest");
		    eclipse_to_java = eclipse.getEclipseStdout();
		    /*stringScanner = new java.util.Scanner(eclipse_to_java).useDelimiter("\\A");
		    String output  = stringScanner.hasNext() ? stringScanner.next() : "";
		    stringScanner.close();*/
		    
		    
		} catch (EclipseException e) {
			e.printStackTrace();
			System.out.println("***Error in Creating prolog engine. EclipseException");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("***Error in Creating prolog engine. IOExceprion");
		}	   
	  }

	@SuppressWarnings("resource") //resources closed remotely, when objected no longer needed
	public String runQuery(String query){
		/*results from runQuery could come in two forms, depending on used prolog program.
		 * 
		 * output(String) will store result from stream
		 * result(String) contains the general result
		 * 
		 * This mini program has the result as result
		 * 
		 * */
		
		String output = "";
		CompoundTerm result;

		try {
			 result= eclipse.rpc(query);
			 output  = "";
			 /*use this to get entire stream into output*/
			 stringScanner = new java.util.Scanner(eclipse_to_java).useDelimiter("\\A");
			 while(stringScanner.hasNext()){
				 output  = stringScanner.next(); 
			 }
			  
			//GENERAL PRINT- Best to see what is happening and use String modifications to get result of interest
			print_function("***Prolog CompoundTerm result Return ***",3);
			
			print_function("*result(String) = ",3);
			print_function(result.toString(),3);
			
			print_function("*arg(1) = ",3);
			print_function(result.arg(1).toString(),3);
			print_function("*arg(2) = ",3);
			print_function(result.arg(2).toString(),3);
			
			print_function("*functor(String) = ",3);
			print_function(result.functor(),3);
			
			print_function("*arity = ",3);
			print_function(""+result.arity(),3);
			
			print_function("***From Stream result Return: output ***",3);
			print_function(output,3);
			 
			//In a different program, we maybe interested in returning output
			return result.toString();

		} catch (EclipseException e) {
			System.out.println("\n Error1: EclipseException : no querie result \n");
			return "";
		} catch (IOException e) {
			System.out.println("\n Error2: IOException in prologCall.runQuery \n");
			return "";
		}
		
	}
	
	public void print_function(String stm, int type){
		/*
		 * type 1 = to std
		 * type 2 = to set bufferWriter(prefined txt file)
		 * type 3 = to both std and txt file
		*/
		
		if(type == 1 ||type == 3){
			System.out.println("\n"+stm);
		}
		if(type == 2 ||type == 3){
			try {
				outputFile.newLine();
				outputFile.write(stm);
			} catch (IOException e) {
				System.out.println("\n Error3: IOException in prologCall.printFunction() \n");
				e.printStackTrace();
			}	
		}
	}
	
	public void closeResources(){ 
		// Destroy the Eclipse process
	    try {
		    ((EmbeddedEclipse) eclipse).destroy();
		    if(stringScanner != null) stringScanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


