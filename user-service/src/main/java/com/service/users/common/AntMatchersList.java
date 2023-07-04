package com.service.users.common;

import java.util.ArrayList;

public final class AntMatchersList {

    public static String[] getAntMatchersList() {
        return new ArrayList<String>() {{
            add("/login");
            add("/register");
        }}.toArray(new String[0]);
    }

    public static String[] getAntMatchersAdminList() {
        return new String[]{

        };
    }
}
