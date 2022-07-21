package com.ing.tech.homework.resource;

import com.ing.tech.homework.dto.UserDto;
import com.ing.tech.homework.dto.wrappers.AccountDtoWrapper;
import com.ing.tech.homework.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserResource {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(userService.getLoggedInUser(principal));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCurrentUser(Principal principal) {
        userService.deleteLoggedInUser(principal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/balances")
    @PreAuthorize("hasAuthority('read_balances')")
    ResponseEntity<List<AccountDtoWrapper>> getBalances(Principal principal) {
        return ResponseEntity.ok(userService.getBalances(principal));
    }
}
