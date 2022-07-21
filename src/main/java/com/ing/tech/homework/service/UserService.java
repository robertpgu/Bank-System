package com.ing.tech.homework.service;

import com.ing.tech.homework.dto.UserDto;
import com.ing.tech.homework.dto.wrappers.AccountDtoWrapper;
import com.ing.tech.homework.entity.Account;
import com.ing.tech.homework.entity.User;
import com.ing.tech.homework.exceptions.UserNotFoundException;
import com.ing.tech.homework.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDto getLoggedInUser(Principal principal) {
        User user = userRepository.findUserByUsername(principal.getName()).orElseThrow(UserNotFoundException::new);

        return new UserDto(
                user.getUsername(),
                user.getPassword()
        );
    }

    public List<AccountDtoWrapper> getBalances(Principal principal) {
        User user = userRepository.findUserByUsername(principal.getName()).orElseThrow(UserNotFoundException::new);
        Set<Account> accounts = user.getAccounts();

        return accounts.stream().map(AccountDtoWrapper::new).collect(Collectors.toList());
    }

    public void deleteLoggedInUser(Principal principal) {
        User user = userRepository.findUserByUsername(principal.getName()).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(user.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
    }
}
