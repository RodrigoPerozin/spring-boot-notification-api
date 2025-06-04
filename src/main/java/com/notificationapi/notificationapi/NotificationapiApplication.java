package com.notificationapi.notificationapi;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.util.Base64;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.notificationapi.notificationapi.model.VapidKeys;


@SpringBootApplication
public class NotificationapiApplication {

	public static void main(String[] args) throws Exception{

		configVapidKeys();
		SpringApplication.run(NotificationapiApplication.class, args);

	}

	private static byte[] ensureLength(byte[] array, int length) {
		if (array.length == length) {
			return array;
		}
		byte[] result = new byte[length];
		System.arraycopy(array, Math.max(0, array.length - length), result, Math.max(0, length - array.length), Math.min(array.length, length));
		return result;
	}

	private static void configVapidKeys() throws Exception {

		Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
        keyGen.initialize(256);
        KeyPair keyPair = keyGen.generateKeyPair();

		ECPublicKey pub = (ECPublicKey) keyPair.getPublic();
        byte[] x = pub.getW().getAffineX().toByteArray();
        byte[] y = pub.getW().getAffineY().toByteArray();
		x = ensureLength(x, 32);
		y = ensureLength(y, 32);

		byte[] rawPublicKey = new byte[65];
        rawPublicKey[0] = 0x04;
        System.arraycopy(x, 0, rawPublicKey, 1, 32);
        System.arraycopy(y, 0, rawPublicKey, 33, 32);

		ECPrivateKey priv = (ECPrivateKey) keyPair.getPrivate();
        byte[] rawPrivateKey = ensureLength(priv.getS().toByteArray(), 32);

        String publicKeyBase64Url = Base64.getUrlEncoder().withoutPadding().encodeToString(rawPublicKey);
        String privateKeyBase64Url = Base64.getUrlEncoder().withoutPadding().encodeToString(rawPrivateKey);

		VapidKeys.setPUBLIC_KEY(publicKeyBase64Url);
		VapidKeys.setPRIVATE_KEY(privateKeyBase64Url);
		
	}

}
