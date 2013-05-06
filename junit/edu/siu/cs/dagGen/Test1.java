package edu.siu.cs.dagGen;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Assert;
import org.junit.Test;

public class Test1 {

	@Test
	public void rawImport() {
		try {
			boolean[][] correct ={
					// 0     1     2	 3     4	 5		6	 7
					{false,false,false,false,false,false,false,false},//0
					{ true,false,false,false,false,false,false,false},//1
					{ true,false,false,false,false,false,false,false},//2
					{false, true, true,false,false,false,false,false},//3
					{false,false,false, true,false,false,false, true},//4
					{false,false,false,false,false,false,false,false},//5
					{false,false,false,false,false,false,false,false},//6
					{false,false,false,false,false, true, true,false},//7
					};
			FileReader file = new FileReader("junit/files/test1.dam");
			DAGFileParser parse = new DAGFileParser(file);
			parse.startParse();
			boolean[][] result = parse.getResult();
			Assert.assertArrayEquals(this.GenerateError(correct, result),correct, result);
			DAGFunctions.integrityCheck(result);
		} catch (FileNotFoundException e) {
			fail("File 'test1.dam' Not Found!");
		} catch (Exception e) {
			fail("Unknown IOException");
		}
	}
	
	@Test
	public void dependancyResolution() {
		try{
			boolean[][] correct ={
					// 0     1     2	 3     4	 5		6	 7
					{false,false,false,false,false,false,false,false},//0
					{ true,false,false,false,false,false,false,false},//1
					{ true,false,false,false,false,false,false,false},//2
					{ true, true, true,false,false,false,false,false},//3
					{ true, true, true, true,false, true, true, true},//4
					{false,false,false,false,false,false,false,false},//5
					{false,false,false,false,false,false,false,false},//6
					{false,false,false,false,false, true, true,false},//7
					};
			
			FileReader file = new FileReader("junit/files/test1.dam");
			DAGFileParser parse = new DAGFileParser(file);
			parse.startParse();
			boolean[][] result = DAGFunctions.resolveDependancies(parse.getResult());
			Assert.assertArrayEquals(this.GenerateError(correct, result),correct, result);
			DAGFunctions.integrityCheck(result);
		} catch(FileNotFoundException e) {
			fail("File 'test1.dam' Not Found!");
		} catch(Exception e) {
			fail("Unkown Exception");
		}
	}
	
	private String GenerateError(boolean[][] expected, boolean[][] generated) {
		String result = "";
		result+="Expected Array:\n";
		for(int i = 0; i < expected.length; i++) {
			result+="|";
			for(int j = 0; j<expected[0].length;j++) {
				char c;
				if(expected[i][j])
					c = '1';
				else
					c = '0';
				result+=(c+"|");
			}
			result+="\n";
		}
		result+="\nGenerated:\n";
		for(int i = 0; i < generated.length; i++) {
			result+="|";
			for(int j = 0; j<generated[0].length;j++) {
				char c;
				if(generated[i][j])
					c = '1';
				else
					c = '0';
				result+=(c+"|");
			}
			result+="\n";
		}
		
		return result;
	}

}
