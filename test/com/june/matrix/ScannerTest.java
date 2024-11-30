package com.june.matrix;

import java.util.List;

import org.junit.jupiter.api.Test;

class ScannerTest {

	@Test
	void test() {
		Scanner lexicalAnalyzer = new Scanner();
		String formula = "LARGE(12,15)-sin(15)>21?12:11";
		formula = "15>21 AND -1>0 ?12:11";
		formula = "\n 1 | 2 & 3";
		System.out.println("formula:"+formula);
		List<Token> tokens = lexicalAnalyzer.scan(formula);
		for (Token token : tokens) {
			System.out.println("token:"+ token);
		}
		
		equals(true);
	}

}
