package com.pluralsight.security.hacking;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.codec.Hex;

public class DigestAuthenticationGenerator {

	public static void main(String[] args) {

		/**
		 * Digest realm="admin-digest-realm", qop="auth", nonce="MTUzMjgxODMwNjU1MzpjNGYwODEyYjE1YjYxZGIzZTM3ZGQ1MTEwZDI4NzlmNQ=="
		 */
		String uri = "/support/admin";
		String httpMethod = "GET";
		String nonce = "MTUzMjgxODMwNjU1MzpjNGYwODEyYjE1YjYxZGIzZTM3ZGQ1MTEwZDI4NzlmNQ==";
		String username = "admin";
		String realm = "admin-digest-realm";
		String ha1 = "18d94adb9db016d4aed2502f88ca6e56";
		
		// HA2=MD5(method:digestURI)
		String ha2 = generateHa2(httpMethod, uri);
		// response=MD5(HA1:nonce:HA2)
		String digest = generateDigest(ha1, nonce, ha2);
		System.out.println("Digest " + digest);
	}

	private static String generateDigest(String ha1, String nonce, String ha2) {
		System.out.println(ha1 + ":" + nonce + ":" + ha2);
		return md5Hex(ha1 + ":" + nonce + ":" + ha2);
	}

	private static String generateHa2(String httpMethod, String uri) {
		return md5Hex(httpMethod + ":" + uri);
	}



	private static String md5Hex(String data) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new String(Hex.encode(digest.digest(data.getBytes())));
	}

}
