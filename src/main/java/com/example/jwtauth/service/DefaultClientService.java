package com.example.jwtauth.service;

import com.example.jwtauth.entity.ClientEntity;
import com.example.jwtauth.exception.LoginException;
import com.example.jwtauth.exception.RegistrationException;
import com.example.jwtauth.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DefaultClientService implements ClientService{
    private final ClientRepository clientRepository;

    @Override
    public void register(String clientId, String clientSecret) {
        if(clientRepository.findById(clientId).isPresent())
            throw new RegistrationException(
                    "Client with id: " + clientId + " already registered");

        String hash = BCrypt.hashpw(clientSecret, BCrypt.gensalt());
        clientRepository.save(new ClientEntity(clientId, hash));
    }

    @Override
    public void checkCredentials(String clientId, String clientSecret) {
        Optional<ClientEntity> optionalUserEntity = clientRepository
                .findById(clientId);
        if (optionalUserEntity.isEmpty())
            throw new LoginException(
                    "Client with id: " + clientId + " not found");

        ClientEntity clientEntity = optionalUserEntity.get();

        if (!BCrypt.checkpw(clientSecret, clientEntity.getHash()))
            throw new LoginException("Secret is incorrect");
    }
}
