package com.zuoke.common.util;

import java.util.Random;

public class RandomUtil {
	public static void main(String[] args) {
		Random random=new Random();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				int caipiao=0;
				do {
					caipiao=random.nextInt(33);
					
				} while (caipiao<12);
				System.out.print(caipiao+",");
			}
			System.out.println("");
		}
	}

}
