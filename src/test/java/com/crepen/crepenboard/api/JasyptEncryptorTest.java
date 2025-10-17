package com.crepen.crepenboard.api;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;

// 이 클래스는 실제 Spring Boot 컨텍스트 없이 실행될 수 있습니다.
public class JasyptEncryptorTest {

    // 애플리케이션에서 사용하는 것과 동일한 암호화 키와 알고리즘을 사용해야 합니다.
    private static final String ENCRYPT_KEY = "2gYswxv4Xypx4BL2tkOd"; // 👈 실제 사용할 비밀 키로 변경
    private static final String ALGORITHM = "PBEWITHHMACSHA512ANDAES_256";

    @Test
    void encryptPlainText() {

        System.out.println();

        // 암호화할 평문
        String plainText = ""; // 👈 여기에 암호화할 평문을 입력

        StandardPBEStringEncryptor jasyptEncryptor = new StandardPBEStringEncryptor();
        jasyptEncryptor.setPassword(ENCRYPT_KEY);
        jasyptEncryptor.setAlgorithm(ALGORITHM);

        jasyptEncryptor.setIvGenerator(new RandomIvGenerator());

        // 암호화 수행
        String encryptedText = jasyptEncryptor.encrypt(plainText);

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("평문 (Plaintext): " + plainText);
        System.out.println("암호화 키 (Encrypt Key): " + ENCRYPT_KEY);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        // 이 결과를 application.yml에 ENC(...) 형태로 사용해야 합니다.
        System.out.println("✅ 암호문 (Encrypted Text): ENC(" + encryptedText + ")");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");

        // (선택) 복호화 테스트: 암호화가 제대로 되었는지 확인
        String decryptedText = jasyptEncryptor.decrypt(encryptedText);
        System.out.println("복호화 테스트 (Decrypted Text): " + decryptedText);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");

        // 평문과 복호화된 값이 일치하는지 단언
        assert plainText.equals(decryptedText);


    }
}