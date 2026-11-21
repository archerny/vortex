package com.vortex.sync.adapter.ibkr;

import org.junit.jupiter.api.Test;
import java.util.Base64;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IbkrSecretCipherTest {
    @Test
    void authenticatedEncryptionRejectsTamperingAndWrongKeysWithoutExposingSecrets() throws Exception {
        var keys = mock(IbkrCredentialKeyStore.class);
        when(keys.load(anyBoolean())).thenReturn(new byte[32]);
        var cipher = new IbkrSecretCipher(keys);
        String token = "synthetic-token-with-&/#";
        String first = cipher.encrypt(token);
        assertNotEquals(first, cipher.encrypt(token));
        assertFalse(first.contains(token));
        assertEquals(token, cipher.decrypt(first));
        byte[] damaged = Base64.getDecoder().decode(first.substring(3));
        damaged[damaged.length - 1] ^= 1;
        var error = assertThrows(IllegalStateException.class,
                () -> cipher.decrypt("v1:" + Base64.getEncoder().encodeToString(damaged)));
        assertNull(error.getCause());
        assertFalse(error.getMessage().contains(token));
        byte[] wrong = new byte[32]; wrong[0] = 1;
        when(keys.load(false)).thenReturn(wrong);
        assertThrows(IllegalStateException.class, () -> cipher.decrypt(first));
        assertThrows(IllegalStateException.class, () -> cipher.decrypt("unsupported-envelope"));
    }
}
