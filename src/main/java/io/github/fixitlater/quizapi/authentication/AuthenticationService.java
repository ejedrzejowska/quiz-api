package io.github.fixitlater.quizapi.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private UserKeyRepository userKeyRepository;

    @Autowired
    public AuthenticationService(UserKeyRepository userKeyRepository) {
        this.userKeyRepository = userKeyRepository;
    }

    public boolean canRead(String apiKey) {
        Optional<UserKey> key = userKeyRepository.findFirstByApiKey(apiKey);
        if (key.isPresent() && (key.get().getRole()!=null)) {
            return key.get().isActive();
        }
        return false;
    }

    public boolean canWrite(String apiKey) {
        Optional<UserKey> key = userKeyRepository.findFirstByApiKey(apiKey);
        UserKey userKey;
        if (key.isPresent()) {
            userKey = key.get();
            return userKey.isActive() && (userKey.getRole() == ApiRole.ADMIN);
        }
        return false;
    }
}
