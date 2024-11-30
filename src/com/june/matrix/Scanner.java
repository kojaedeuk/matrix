/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.calc Absolute.java
 * DESC : Natural language processing computational engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
package com.june.matrix;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 * Description : 어휘 분석(lexical analysis)이란 소스 프로그램을 읽어
 * 토큰(token)이라는 문법적으로 의미있는 최소의 단위로 분리하는 것으로 토큰
 * 스트림(stream)을 출력 하는 것이다.
 * 
 * 어휘 분석하는 도구를 어휘 분석기(lexical analyzer) 또는 스캐너(scanner)라 한다.
 * 
 * 1. 토큰을 인식
 * 2. 전처리 과정(preprocessing) : 코멘트, white space, tab, new line, macro 등을 처리
 * 3. 기호표(symbol table) 구성
 * 4. 정규표현식에 의한 변수선언 체크 및 미정의 구현에 대한 에러 처리
 * </pre>
 */
public class Scanner
{
	private final List<Token> tokens = new ArrayList<>(); //symbol table
	private char[] chars; //문자배열
	private int lineNumber = 1;  //줄번호
	private int length = 0; //문자열길이
	private int index = 0; //문자위치
	
	/**
	 * Constructors
	 */
	public Scanner()
	{
		super();
	}
	

	/**
	 * comment : 어휘 분석
	 * @Method Name : scan
	 * @Return : List<Token>
	 */
	public List<Token> scan(String formula)
	{
		if ( null == formula || formula.length() == 0 )
			return tokens;
		
		StringToChars(formula); 
		
		String sString = "";
		int type = 0;
		BigDecimal number = BigDecimal.ZERO;
		Lexemes delimiter = Lexemes.EOL;
		while(true)
		{
			if(getIndex() >= getLength())
			{
				tokens.add(new Token(Lexemes.EOL, "", getLineNumber()));
				break;
			}

			type = getCharType();
			
			switch (type) {
			case Constant.WHITE_SPACE:
				//전처리 과정(preprocessing) : white space(‘ ’), tab(\t), new line(\n), macro 등은 프로그램과 상관없는 코드들이기에 skip 처리 합니다.
				if(getChar() == '\n' || getChar() == '\r') //줄바꿈(unix)
				{
					setLineNumber(getLineNumber() + 1); //줄번호 증가
				}
				break;
				
			case Constant.NUMBER:
				number = getNumber();
				tokens.add(new Token(Lexemes.NUMBER, number, getLineNumber()));
				break;
				
			case Constant.DELIMITER:
				delimiter = getDelimiter();
				tokens.add(new Token(delimiter, delimiter.getName(), getLineNumber()));
				break;

			case Constant.CHARACTERS:
				//대문자로 변환
				sString = getString().toUpperCase();
				if(   "IF".equals(sString)
						|| "ELSEIF".equals(sString) || "ELSE".equals(sString) || "PI".equals(sString) || "LOG_E".equals(sString)
						|| "TRUE".equals(sString) || "FALSE".equals(sString) || "NULL".equals(sString) || "ROUND".equals(sString)
						|| "ROUND_UP".equals(sString) || "ROUND_DOWN".equals(sString) || "LARGE".equals(sString)
						|| "SMALL".equals(sString) || "SIN".equals(sString) || "COS".equals(sString) || "TAN".equals(sString)
						|| "MOD".equals(sString) || "ABS".equals(sString) || "LOG".equals(sString) || "HEX".equals(sString)
						|| "OCT".equals(sString) || "BIN".equals(sString) || "TOHEX".equals(sString) || "TOOCT".equals(sString)
						|| "TOBIN".equals(sString) || "AND".equals(sString) || "OR".equals(sString) || "NOT".equals(sString)
				) {
					//내부함수
					tokens.add(new Token(Lexemes.getLexemes(sString), sString, getLineNumber()));
				}
				else
				{
					//변수(runtime 시 받아오는 값)
					tokens.add(new Token(Lexemes.VARIABLE, sString, getLineNumber()));
				}
				break;
				
			case Constant.STRING:
				sString = getString();
				sString = sString.substring(1, sString.length() - 1);
				//문자열
				tokens.add(new Token(Lexemes.STRING, sString, getLineNumber()));
				break;

			default:
				throw new ParseException(Constant.M002);
			}
			addIndex();
			
		}//end while

		//토큰을 분리하여 담는 것을 말합니다. List를 사용하여 토큰들을 담습니다.
		return tokens;
	}
	
	/**
	 * comment : 입력된 문자열을 문자배열로 변환 한다.
	 * @Method Name : StringToChars
	 * @Return : void
	 */
	void StringToChars(String formula)
	{
		setIndex(0); //Column number setting
		setLength(formula.length()); //string length setting
		setChars(new char[getLength()]); //character array declaration
		formula.getChars(0, getLength(), getChars(), 0); //Set string to character array
	}
	
	/**
	 * 문자열 추출
	 * @param length
	 * @return
	 */
	String getString()
	{
		StringBuffer tmpStr = new StringBuffer();
		tmpStr.append(getChar());
		while (getIndex() <= getLength())
		{
			addIndex();

			if(Constant.DELIMITER == getCharType() || Constant.WHITE_SPACE == getCharType())
			{
				setIndex(getIndex() - 1); 
				break;
			}
			if (getIndex() > getLength()) // 문자열 길이 초과
				break;
			
			tmpStr.append(getChar());
		}//end while
		
		return tmpStr.toString();
	}
	
	/**
	 * comment : 구분자에 맞는 상수 추출
	 * @Method Name : getDelimiter
	 * @Return : Lexemes
	 */
	Lexemes getDelimiter()
	{
		Lexemes lexemes = Lexemes.EOL;
		
		switch (getChar()) {

		case '+':
			if('+' == nextChar())
			{
				addIndex();
				lexemes = Lexemes.INCREMENT; //++
				
			}
			else if('=' == nextChar())
			{
				addIndex();
				lexemes = Lexemes.INCREMENT_ASSIGNMENT; //+=
			}
			else
			{
				lexemes = Lexemes.ADDITION; //+
			}
			break;
			
		case '-':
			if('-' == nextChar())
			{
				addIndex();
				lexemes = Lexemes.DECREMENT; //--
			}
			else if('=' == nextChar())
			{
				addIndex();
				lexemes = Lexemes.DECREMENT_ASSIGNMENT; //-=
			}
			else
			{
				lexemes = Lexemes.SUBTRACT; //-
			}
			break;

		case '>':
			if('>' == nextChar())
			{
				addIndex();
				lexemes = Lexemes.RIGHT_SHIFT; //>>
			}
			else if('=' == nextChar())
			{
				addIndex();
				lexemes = Lexemes.GREATER_EQUAL; //>=
			}
			else
			{
				lexemes = Lexemes.GREATER_THAN; //>
			}
			break;

		case '<':
			if('<' == nextChar())
			{
				addIndex();
				lexemes = Lexemes.LEFT_SHIFT; //<<
			}
			else if('=' == nextChar())
			{
				addIndex();
				lexemes = Lexemes.LESS_EQUAL; //<=
			}
			else
			{
				lexemes = Lexemes.LESS_THAN; //<
			}
			break;

		case '!':
			if('=' == nextChar())
			{
				addIndex();
				lexemes = Lexemes.NOT_EQUAL; //!=
			}
			else
			{
				lexemes = Lexemes.NOT; //!
			}
			break;

		case '&':
			lexemes = Lexemes.BIT_AND; //&
			break;

		case '|':
			lexemes = Lexemes.BIT_OR; //|
			break;

		case '/':
			lexemes = Lexemes.RIGHT_DIVIDE; //나누기
			break;

		case '=':
			if('=' == nextChar())
			{
				addIndex();
				lexemes = Lexemes.EQUAL; //==
			}
			break;

		default:
			lexemes = Lexemes.getLexemes(String.valueOf(getChar()));
			break;
		}
		
		return lexemes;
	}
	
	/**
	 * 숫자 추출
	 * @param length
	 * @return
	 */
	BigDecimal getNumber()
	{
		StringBuffer tmpStr = new StringBuffer();
		while (Constant.NUMBER == getCharType())
		{
			tmpStr.append(getChar());
			addIndex();
			if('.' == getChar())
			{
				tmpStr.append(getChar());
				addIndex();
			}

			if (getIndex() >= getLength() )
			{
				break;
			}
		}//end while
		
		setIndex(getIndex() - 1); 
		
		return new BigDecimal(tmpStr.toString());
	}

	/**
	 * 문자 유형
	 * @param c
	 * @return
	 */
	int getCharType()
	{
		char c = getChar();
		Lexemes result = Lexemes.EOL;

		if (' ' == c) //공백문자
		{
			result = Lexemes.WHITE_SPACE; 
		}
		else if('\n' == c)  //줄바꿈 /n:unix, /r:mac, /r/n:windows
		{
			result = Lexemes.WHITE_SPACE; 
		}
		else if('\r' == c) //줄바꿈
		{
			result = Lexemes.WHITE_SPACE; 
		}
		else if('\t' == c) //tab
		{
			result = Lexemes.WHITE_SPACE; 
		}
		else if ('+' == c || '*' == c || '/' == c || '\\' == c || '%' == c || '-' == c || '(' == c || ')' == c
			  || '^' == c || '√' == c || '<' == c || '=' == c || '>' == c || '!' == c || ':' == c || ',' == c
			  || '{' == c || '}' == c || ';' == c || '?' == c || '|' == c  || '&' == c
		) {
			result = Lexemes.DELIMITER; //계산기호
		}
		else if ('\'' == c)
		{
			result = Lexemes.STRING; //문자형
		}
		else if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || c == '_')
		{
			result = Lexemes.CHARACTERS; //문자(변수, 키워드)
		}
		else if ('0' <= c && c <= '9')
		{
			result = Lexemes.NUMBER; //숫자형
		}
		else
		{
			new ParseException(Constant.M001);
		}
		
		return result.getType();
	}
	
	/**
	 * comment : 문자 배열 세팅
	 * @Method Name : setChars
	 * @Return : void
	 */
	void setChars(char[] chars)
	{
		this.chars = chars;
	}

	/**
	 * comment : 문자 배열 반환
	 * @Method Name : getChars
	 * @Return : char[]
	 */
	char[] getChars() {
		return chars;
	}

	/**
	 * comment : 문자 반환
	 * @Method Name : getChar
	 * @Return : char
	 */
	char getChar()
	{
		return getChar(getIndex());
	}

	/**
	 * comment : 해당 열에 위치한 문자 반환
	 * @Method Name : getChar
	 * @Return : char
	 */
	char getChar(int index)
	{
		if(getLength() <= index)
			return chars[index-1];
			
		return chars[index];
	}
	
	/**
	 * comment : 다음 문자 반환
	 * @Method Name : nextChar
	 * @Return : char
	 */
	char nextChar()
	{
		return getChar(getIndex() + 1);
	}

	/**
	 * comment : 현재 열번호
	 * @Method Name : getIndex
	 * @Return : int
	 */
	int getIndex() {
		return index;
	}
	
	/**
	 * comment : 열번호 세팅
	 * @Method Name : setIndex
	 * @Return : void
	 */
	void setIndex(int index) {
		this.index = index;
	}

	/**
	 * comment : 열번호 증가
	 * @Method Name : addIndex
	 * @Return : int
	 */
	int addIndex()
	{
		setIndex(getIndex() + 1);
		return getIndex();
	}

	/**
	 * comment : get the length
	 * @Method Name : getLength
	 * @Return : int
	 */
	int getLength() {
		return length;
	}

	/**
	 * comment : set the length
	 * @Method Name : setLength
	 * @Return : void
	 */
	void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		Iterator<Token> iterator = tokens.iterator();
		while(iterator.hasNext())
		{
			buffer.append(iterator.next().toString());
		}	
		return buffer.toString();
	}

	/**
	 * comment : get the lineNumber
	 * @Method Name : getLineNumber
	 * @Return : int
	 */
	int getLineNumber() {
		return lineNumber;
	}

	/**
	 * comment : set the lineNumber
	 * @Method Name : setLineNumber
	 * @Return : void
	 */
	void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
}
