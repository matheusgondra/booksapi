package com.matheusgondra.booksapi.infrastructure.enums;

public enum PublicRoutes {
    SIGNUP("/signup"),
    LOGIN("/login");

    private final String route;

    PublicRoutes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
