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
import java.util.Hashtable;

/**
 * 상수 정의
 */
interface Constant {
	final Hashtable<String, String> KEYWORDS = new Hashtable<String, String>(); //예약어
	final boolean IS_LOGGING = true; //계산 중에 사용된 식을 출력할 것인지
	final boolean IS_DEBUGGING = false; //파싱 중에 출력할 것인지
	final BigDecimal E 	= BigDecimal.valueOf(Math.E); //자연로그 e
	final BigDecimal PI 	= BigDecimal.valueOf(Math.PI); //pi
	final int SCALE 		= 17; //계산시 사용되는 소수점 자리 크기
	final String EOE 	= "\0";
	
	//문자유형
	final int WHITE_SPACE	= 0; //공백문자
	final int DELIMITER		= 1; //기호
	final int STRING		= 2; //문자열
	final int NUMBER		= 3; //숫자형
	final int KEYWORD		= 4; //예약어
	final int LOGICAL		= 5; //논리연산
	final int RELATIONAL	= 6; //관계연산
	final int IDENTIFIER	= 7; //식별자
	final int VARIABLE		= 8; //변수
	final int CHARACTERS	= 9; //문자(변수, 키워드)
	final int UNKNOWN    	= 88; //無
	final int EOL			= 99; //end of line

	//message
	String M001 = "Syntax Error"; // 구문 오류
	String M002 = "No Expression Present"; // 표현 없음
	String M003 = "Division by Zero"; // 0으로 나누기
	String M004 = "Unbalanced Parentheses"; //불균형 괄호
	String M005 = "Can't have more than 255 arguments.";
	String M006 = "Only 16, 8, and 2 bits are available."; //16, 8, 2비트 만 사용 가능 합니다.
}
