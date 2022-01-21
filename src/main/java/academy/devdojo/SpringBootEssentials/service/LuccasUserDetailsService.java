package academy.devdojo.SpringBootEssentials.service;

import academy.devdojo.SpringBootEssentials.repository.LuccasUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LuccasUserDetailsService implements UserDetailsService {
    private final LuccasUserRepository luccasUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        return Optional.ofNullable(luccasUserRepository.findByUserName(username))
                .orElseThrow(() -> new UsernameNotFoundException("Luccas User not fount"));
    }
}
