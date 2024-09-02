package com.orchasp.cis.ContactInformationSyatem;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@SuppressWarnings("deprecation")
public class TokenMakerTest {
	@Test
	public void generateSecretKey() {
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		System.out.println(key);
		String encodedKey = DatatypeConverter.printHexBinary(key.getEncoded());
		System.out.printf("\nKey = [%s]\n", encodedKey);
	}

	}