package com.vortex.sync.adapter.ibkr;

import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Component
public class IbkrSecretCipher {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final byte[] AAD = "vortex:ibkr:flex-token:v1".getBytes(StandardCharsets.UTF_8);
    private final IbkrCredentialKeyStore keyStore;

    public IbkrSecretCipher(IbkrCredentialKeyStore keyStore) { this.keyStore = keyStore; }

    public String encrypt(String token) {
        try {
            byte[] nonce = new byte[12]; RANDOM.nextBytes(nonce);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyStore.load(true), "AES"), new GCMParameterSpec(128, nonce));
            cipher.updateAAD(AAD);
            byte[] ciphertext = cipher.doFinal(token.getBytes(StandardCharsets.UTF_8));
            return "v1:" + Base64.getEncoder().encodeToString(ByteBuffer.allocate(nonce.length + ciphertext.length)
                    .put(nonce).put(ciphertext).array());
        } catch (Exception error) {
            throw new IllegalStateException("Cannot securely save IBKR credentials");
        }
    }

    public String decrypt(String value) {
        try {
            if (!value.startsWith("v1:")) throw new IllegalArgumentException();
            byte[] payload = Base64.getDecoder().decode(value.substring(3));
            if (payload.length < 29) throw new IllegalArgumentException();
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyStore.load(false), "AES"),
                    new GCMParameterSpec(128, Arrays.copyOf(payload, 12)));
            cipher.updateAAD(AAD);
            return new String(cipher.doFinal(payload, 12, payload.length - 12), StandardCharsets.UTF_8);
        } catch (Exception error) {
            throw new IllegalStateException("Cannot read stored IBKR credentials; restore the credential key");
        }
    }

}
