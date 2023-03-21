//package com.cgs.loyalty.util;
//
//public class GenerateRandom {
//
//	public String generateRandeomAccountNo() {
//
//		int max = 9999;
//		int min = 1000;
//		int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
//		String bankCode = "123";
//		String branchCode = "0";
//		String number = String.valueOf(random_int);
//		String accountNo = bankCode + branchCode + number;
//
//		// Printing the generated random account numbers
//		System.out.println(accountNo);
//
//		return accountNo;
//	}
//	
//	public static void main(String[] args) {
//		GenerateRandom g = new GenerateRandom();
//		g.generateRandeomAccountNo();
//		g.generateRandeomAccountNo();
//		g.generateRandeomAccountNo();
//		g.generateRandeomAccountNo();
//	}
//
//}
