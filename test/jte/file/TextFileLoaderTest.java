/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.file;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Frank
 */
public class TextFileLoaderTest {
    
    /**
     * Test of loadTextFile method, of class TextFileLoader.
     */
    @Test
    public void testLoadTextFile() throws Exception {
        System.out.println("loadTextFile");
        String textFile = "./data/testread";
        String expResult = "Hello\n";
        String result = TextFileLoader.loadTextFile(textFile);
        assertEquals(expResult, result);
    }
    
}
