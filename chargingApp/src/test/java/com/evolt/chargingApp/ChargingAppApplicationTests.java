package com.evolt.chargingApp;

import com.evolt.chargingApp.service.impl.CommonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.evolt.chargingApp.dto.Constants;
@SpringBootTest
class ChargingAppApplicationTests {

	@Mock
	CommonServiceImpl commonServiceImpl;

	@Test
	void contextLoads() {
	}

	@Test
	void givenString_whenEncrypt_thenSuccess()
			throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
			BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeySpecException {


		String input = "Alex789!";

		SecretKey key =commonServiceImpl.getKeyFromPassword(input,Constants.ENCRYPTION_SALT_KEY);
		//System.out.println("Key:"+key.getEncoded());

		//SecretKey key = commonServiceImpl.generateKey(Constants.ENCRYPTION_KEY_BYTES);
		IvParameterSpec ivParameterSpec = commonServiceImpl.generateIv();

		String cipherText = commonServiceImpl.encryptPasswordBased(input, key, ivParameterSpec);
		System.out.println("Encrypted value:"+cipherText);
		String cipherText1="AGjtBrU87GmpeuWXp6Qrdw==";
		System.out.println("Encrypted value1:"+cipherText1);
		System.out.println("Encrypted value5:"+Constants.UserTypes.Customer.name());
		String comp="Admin";
		boolean present=false;
		if(Constants.UserTypes.Customer.name().equalsIgnoreCase(comp))
			System.out.println("Equal");
		for (Constants.UserTypes value : Constants.UserTypes.values()) {
			if(!present)
				present=comp.equalsIgnoreCase(value.name());
		}
System.out.println("Valid user type:"+present);
		String plainText = commonServiceImpl.decryptPasswordBased( cipherText, key, ivParameterSpec);
		System.out.println("decrypted value:"+plainText);

		List<String> values = new ArrayList<>();
		values.add("Jai");
		values.add("12345345");
		values.add("1234567890");

		Pattern phoneNumberPattern = Pattern.compile(Constants.MOBILE_NUMBER_PATTERN);
		for (String value : values){
			Matcher matcher = phoneNumberPattern.matcher(value);
			System.out.println(matcher.matches());
		}

		String ph="1234567809";
		Long longVal=Long.parseLong(ph);
		System.out.println("Long:"+longVal);

		Assertions.assertEquals(input, plainText);
	}

}
