package com.daneitian.example;

import java.time.ZonedDateTime;

public class Main {

	public static void main(String[] args) {
		
		// 現在の日時
		ZonedDateTime now = ZonedDateTime.now();
		System.out.println(now);
		
		// ひとつ前の15分足の日時
		System.out.println(now.minus(1, TimeframeUnit.M15));
		// 現在の15分足の日時
		System.out.println(now.minus(0, TimeframeUnit.M15));
		// 現在の15分足の日時		
		System.out.println(now.plus(0, TimeframeUnit.M15));
		// ひとつ後の15分足の日時
		System.out.println(now.plus(1, TimeframeUnit.M15));
	}
}
