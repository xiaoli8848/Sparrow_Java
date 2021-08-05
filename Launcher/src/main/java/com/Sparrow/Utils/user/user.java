package com.Sparrow.Utils.user;

import com.Sparrow.Utils.texture;
import org.to2mbn.jmccc.auth.Authenticator;

public abstract class user {
    protected String userName;
    protected com.Sparrow.Utils.texture texture;
    protected Authenticator authenticator;

    public user(String userName) {
        setUserName(userName);
    }

    user() {
    }

    ;

    public String getUserName() {
        return userName;
    }

    protected void setUserName(String userName) {
        this.userName = userName;
    }

    public texture getTexture() {
        return texture;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    @Override
    public String toString() {
        return userName;
    }
}