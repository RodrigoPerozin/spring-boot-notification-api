package com.notificationapi.notificationapi;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationapiApplication {

	public static String PublicKey;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException{

		Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
        keyGen.initialize(256);
        KeyPair keyPair = keyGen.generateKeyPair();

        PublicKey = Base64.getUrlEncoder().withoutPadding().encodeToString(keyPair.getPublic().getEncoded());
        final String privateKey = Base64.getUrlEncoder().withoutPadding().encodeToString(keyPair.getPrivate().getEncoded());

		SpringApplication.run(NotificationapiApplication.class, args);

	}

}
