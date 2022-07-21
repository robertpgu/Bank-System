package com.ing.tech.homework.security.roles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.ing.tech.homework.security.roles.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Set.of(TRANSFER_MONEY,
            REQUEST_MONEY,
            READ_BALANCES,
            ACCEPT_MONEY,
            REFUSE_MONEY,
            SEE_PENDING_TRANSACTIONS,
            SEE_TRANSACTIONS_HISTORY,
            ADD_NEW_ACCOUNT)),
    GUEST(Set.of());

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> collect = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        collect.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return collect;
    }
}
