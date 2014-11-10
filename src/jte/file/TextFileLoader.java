/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public class TextFileLoader {
    
    /**
    * This method loads the complete contents of the textFile argument into
    * a String and returns it.
    * 
    * @param textFile The name of the text file to load. Note that the path
    * will be added by this method.
    * 
    * @return All the contents of the text file in a single String.
    * 
    * @throws IOException This exception is thrown when textFile is an invalid
    * file or there is some problem in accessing the file.
    * 
    * @author Paul Fodor
    */
   public static String loadTextFile(String textFile) throws IOException
   {
       
       // WE'LL ADD ALL THE CONTENTS OF THE TEXT FILE TO THIS STRING
       String textToReturn = "";
      
       // OPEN A STREAM TO READ THE TEXT FILE
       FileReader fr = new FileReader(textFile);
       BufferedReader reader = new BufferedReader(fr);
           
       // READ THE FILE, ONE LINE OF TEXT AT A TIME
       String inputLine = reader.readLine();
       while (inputLine != null)
       {
           // APPEND EACH LINE TO THE STRING
           textToReturn += inputLine + "\n";
           
           // READ THE NEXT LINE
           inputLine = reader.readLine();        
       }
       
       // RETURN THE TEXT
       return textToReturn;
   }    

    
}
