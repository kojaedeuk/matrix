/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.calc Absolute.java
 * DESC : Natural language processing computational engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
package com.june.matrix;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * matrix에서 사용되는 어휘항목을 정의 합니다. 
 */
public enum Lexemes {
	//산술 연산자 : +, -, *, /, \
	ADDITION("+", Constant.DELIMITER)
	, SUBTRACT("-", Constant.DELIMITER)
	, MULTIPLY("*", Constant.DELIMITER)
	, RIGHT_DIVIDE("/", Constant.DELIMITER)
	, LEFT_DIVIDE("\\", Constant.DELIMITER)
	//최우선 연산자 : (, )
	, LEFT_PAREN("(", Constant.DELIMITER)
	, RIGHT_PAREN(")", Constant.DELIMITER)
	, LEFT_BRACE("{", Constant.DELIMITER)
	, RIGHT_BRACE("}", Constant.DELIMITER)
	//지수 연산자 : ^, √
	, POWER("^", Constant.DELIMITER)
	, ROOT("√", Constant.DELIMITER)
	//증가 연산자, 감소 연산자
	, INCREMENT("++", Constant.DELIMITER)
	, DECREMENT("--", Constant.DELIMITER)
	//증가 할당 연산자, 감소 할당 연산자
	, INCREMENT_ASSIGNMENT("+=", Constant.DELIMITER)
	, DECREMENT_ASSIGNMENT("-=", Constant.DELIMITER)
	//관계 연산자 : <, ==, >, <=, >=, !=
	, LESS_THAN("<", Constant.DELIMITER)
	, EQUAL("==", Constant.DELIMITER)
	, GREATER_THAN(">", Constant.DELIMITER)
	, LESS_EQUAL("<=", Constant.DELIMITER)
	, GREATER_EQUAL(">=", Constant.DELIMITER)
	, NOT_EQUAL("!=", Constant.DELIMITER)
	//논리 연산자 : 논리합, 논리곱, 논리부정
	, OR("OR", Constant.DELIMITER)
	, AND("AND", Constant.DELIMITER)
	, NOT("NOT", Constant.DELIMITER)
	//삼항 연산자 : ?, :
	, QUESTION("?", Constant.DELIMITER)
	, COLON(":", Constant.DELIMITER)
	, SEMICOLON(";", Constant.DELIMITER)
	//순차 연산자 : ,
	, COMMA(",", Constant.DELIMITER)
	, DOT(".", Constant.DELIMITER)
	//백분율 : %
	, PERCENT("%", Constant.DELIMITER)
	//Shift 연산 : <<, >>
	, LEFT_SHIFT("<<", Constant.DELIMITER)
	, RIGHT_SHIFT(">>", Constant.DELIMITER)
	//BIT 논리 연산자 : |, &, ~, ^(#)
	, BIT_OR("|", Constant.DELIMITER)
	, BIT_AND("&", Constant.DELIMITER)
	, BIT_NOT("~", Constant.DELIMITER)
	, BIT_XOR("#", Constant.DELIMITER)
	//문자열
	, STRING("\'", Constant.DELIMITER)
	//내부함수
	, ROUND("ROUND", Constant.KEYWORD)
	, ROUND_UP("ROUND_UP", Constant.KEYWORD)
	, ROUND_DOWN("ROUND_DOWN", Constant.KEYWORD)
	, LARGE("LARGE", Constant.KEYWORD)
	, SMALL("SMALL", Constant.KEYWORD)
	, SIN("SIN", Constant.KEYWORD)
	, COS("COS", Constant.KEYWORD)
	, TAN("TAN", Constant.KEYWORD)
	, MOD("MOD", Constant.KEYWORD)
	, ABS("ABS", Constant.KEYWORD)
	, LOG("LOG", Constant.KEYWORD)
	, HEX("HEX", Constant.KEYWORD)
	, OCT("OCT", Constant.KEYWORD)
	, BIN("BIN", Constant.KEYWORD)
	, TOHEX("TOHEX", Constant.KEYWORD)
	, TOOCT("TOOCT", Constant.KEYWORD)
	, TOBIN("TOBIN", Constant.KEYWORD)
	, TERNARY_OPERATION("TERNARY_OPERATION", Constant.KEYWORD)
	//Keywords
	, IF("IF", Constant.KEYWORD)
	, ELSEIF("ELSEIF", Constant.KEYWORD)
	, ELSE("ELSE", Constant.KEYWORD)
	, PI("PI", Constant.KEYWORD)
	, LOG_E("LOG_E", Constant.KEYWORD)
	, TRUE("TRUE", Constant.KEYWORD)
	, FALSE("FALSE", Constant.KEYWORD)
	//어휘 유형
	, EOL("EOL", Constant.EOL)
	, DELIMITER("DELIMITER", Constant.DELIMITER)
	, WHITE_SPACE("WHITE_SPACE", Constant.WHITE_SPACE)
	, NUMBER("NUMBER", Constant.NUMBER)
	, CHARACTERS("CHARACTERS", Constant.CHARACTERS)
	, VARIABLE("VARIABLE", Constant.VARIABLE)
	, UNKNOWN("UNKNOWN", Constant.UNKNOWN)
	;
	
	/**
	 * 상수명 
	 */
	private final String name;
	/**
	 * 유형
	 */
	private final int type;

	/**
	 * 생성자
	 * 
	 * @param name
	 * @param type
	 */
	private Lexemes(String name, int type) {
		this.name = name;
		this.type = type;
	}
	
	/**
	 * get the name
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * get the type
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * enum객체들을 Map객체에 load하여 Caching처리 하기 
	 * 1. Collections.unmodifiableMap : 이용하여 수정불가(read-only)한 map객체 선언
	 * 2. Stream.of : enum객체의 values()메서드로 부터 입력 받은 배열 객체를 이용하여 Stream 생성
	 * 3. Collectors.toMap : Map객체로 변환
	 */
	private final static Map<String, Lexemes> LEXEMES = Collections.unmodifiableMap(
			Stream.of(values()).collect(
					Collectors.toMap(
							Lexemes::getName, Function.identity()))
            );

	/**
	 * comment : Map객체 반환
	 * @Method Name : getMap
	 * @Return : Map<String,Lexemes>
	 */
	public static Map<String, Lexemes> getMap()
	{
		return LEXEMES;
	}
	
	/**
	 * comment : get Lexeme
	 * @Method Name : getLexemes
	 * @Return : Lexemes
	 */
	public static Lexemes getLexemes(String name)
	{
		return Optional.ofNullable(LEXEMES.get(name)).orElse(UNKNOWN);
	}

}
