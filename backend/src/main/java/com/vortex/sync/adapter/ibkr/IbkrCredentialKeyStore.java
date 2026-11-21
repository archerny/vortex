package com.vortex.sync.adapter.ibkr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Set;

@Component
public class IbkrCredentialKeyStore {
    private static final SecureRandom RANDOM = new SecureRandom();
    private final Path keyFile;

    public IbkrCredentialKeyStore(@Value("${broker.ibkr.secret-key-file:}") String keyFile) {
        Path base = Path.of("").toAbsolutePath().normalize();
        for (Path candidate = base; candidate != null; candidate = candidate.getParent()) {
            if (Files.isRegularFile(candidate.resolve("backend/pom.xml")) && Files.isDirectory(candidate.resolve("frontend"))) {
                base = candidate; break;
            }
        }
        this.keyFile = base.resolve(keyFile.isBlank() ? ".local/ibkr-sync.key" : keyFile).normalize();
    }

    public synchronized byte[] load(boolean create) throws Exception {
        if (!Files.exists(keyFile, LinkOption.NOFOLLOW_LINKS) && create) {
            Files.createDirectories(keyFile.getParent(), PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------")));
            if (Files.isSymbolicLink(keyFile.getParent()) || !Files.getPosixFilePermissions(keyFile.getParent())
                    .equals(PosixFilePermissions.fromString("rwx------"))) {
                throw new IllegalStateException("Credential key directory permissions are invalid");
            }
            byte[] value = new byte[32]; RANDOM.nextBytes(value);
            try (FileChannel output = FileChannel.open(keyFile, Set.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE),
                    PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-------")))) {
                ByteBuffer buffer = ByteBuffer.wrap(value);
                while (buffer.hasRemaining()) output.write(buffer);
                output.force(true);
            } catch (FileAlreadyExistsException concurrentCreation) {
                // A concurrent writer won; always use its complete key below.
            }
        }
        if (Files.isSymbolicLink(keyFile.getParent()) || !Files.getPosixFilePermissions(keyFile.getParent())
                .equals(PosixFilePermissions.fromString("rwx------"))) {
            throw new IllegalStateException("Credential key directory permissions are invalid");
        }
        if (Files.isSymbolicLink(keyFile) || !Files.isRegularFile(keyFile, LinkOption.NOFOLLOW_LINKS)
                || !Files.getPosixFilePermissions(keyFile).equals(PosixFilePermissions.fromString("rw-------"))) {
            throw new IllegalStateException("Credential key permissions are invalid");
        }
        try (FileChannel input = FileChannel.open(keyFile, StandardOpenOption.READ, LinkOption.NOFOLLOW_LINKS)) {
            ByteBuffer value = ByteBuffer.allocate(33);
            while (value.hasRemaining() && input.read(value) > 0) { }
            if (value.position() != 32) throw new IllegalStateException("Credential key is invalid");
            return Arrays.copyOf(value.array(), 32);
        }
    }
}
