/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.calc Absolute.java
 * DESC : Natural language processing computational engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
package com.june.matrix;

/**
 * <pre>
 * 문법적으로 의미있는 최소의 단위 토큰 관리
 * 토큰의 표현
 * 효율적인 구문분석을 할 수 있도록 토큰을 토큰번호(token number)와 토큰 값(token value)의 순서쌍으로 표현(토큰 번호, 토큰 값)
 * </pre>
 */
public class Token {
	private final Lexemes lexeme; //상수
	private final int type; //유형
	private final Object value; //값
	private final int line_number; //줄번호

	/**
	 * Constructors
	 * @param lexeme 
	 * @param type 
	 * @param value 
	 * @param line_number 
	 */
	public Token(Lexemes lexeme, Object value, int line_number)
	{
		this.lexeme = lexeme;
		this.type = lexeme.getType();
		this.value = value;
		this.line_number = line_number;
	}

	/**
	 * get the lexeme
	 * @return the lexeme
	 */
	public Lexemes getLexeme() {
		return lexeme;
	}

	/**
	 * get the value
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * get the line_number
	 * @return the line_number
	 */
	int getLineNumber() {
		return line_number;
	}

	@Override
	public String toString() {
		return "[type:" + getTypeToString() + ", value:" + getValue() + "]";
	}
	
	/**
	 * 유형 문자
	 * @return
	 */
	public String getTypeToString()
	{
		String resultStr = "";
		
		switch (getLexeme().getType()) {
		case 1:
			resultStr = "DELIMITER"; //기호
			break;
		case 2:
			resultStr = "STRING"; //문자열
			break;
		case 3:
			resultStr = "NUMBER"; //숫자형
			break;
		case 4:
			resultStr = "KEYWORD"; //예약어
			break;
		case 5:
			resultStr = "LOGICAL"; //논리연산
			break;
		case 6:
			resultStr = "RELATIONAL"; //관계연산
			break;
		case 7:
			resultStr = "IDENTIFIER"; //식별자
			break;
		case 8:
			resultStr = "VARIABLE"; //변수
			break;
		case 9:
			resultStr = "CHARACTERS"; //문자(변수, 키워드)
			break;
		case 88:
			resultStr = "UNKNOWN"; //無
			break;
		case 99:
			resultStr = "EOL"; //end of line
			break;

		default:
			break;
		}
		
		return resultStr;
	}

	public int getType() {
		return type;
	}
}
