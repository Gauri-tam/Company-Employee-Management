package com.Management.enamurate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    CEO_CREATE("create"),
    CEO_READ("read"),
    CEO_UPDATE("update"),
    CEO_DELETE("delete"),
    MANAGER_CREATE("create"),
    MANAGER_READ("read"),
    MANAGER_UPDATE("update"),
    TEAM_LEADER_READ("read");

    @Getter
    private final String permissions;
}
