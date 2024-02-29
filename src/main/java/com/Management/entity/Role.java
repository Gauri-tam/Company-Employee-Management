package com.Management.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.Management.entity.Permission.*;

@RequiredArgsConstructor
public enum Role {
    CEO(
            Set.of(
                    CEO_CREATE,
                    CEO_READ,
                    CEO_UPDATE,
                    CEO_DELETE,
                    MANAGER_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    TL_READ
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE
            )
    ),
    TL(
            Set.of(
                    TL_READ
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthority(){
        var authority = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissions()))
                .collect(Collectors.toList());
        authority.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authority;
    }
}
