/*
 * Copyright (c) 2021 Sparrow All Rights Reserved.
 * FileName: YggdrasilAuthenticator_JavaFX.java
 * @author: 1662423349@qq.com
 * @date: 2021/6/12 下午9:17
 * @version: 1.0
 */

package com.Sparrow.UI.JavaFX;

import org.to2mbn.jmccc.auth.AuthenticationException;
import org.to2mbn.jmccc.auth.yggdrasil.CharacterSelector;
import org.to2mbn.jmccc.auth.yggdrasil.YggdrasilAuthenticator;
import org.to2mbn.jmccc.auth.yggdrasil.core.AuthenticationService;
import org.to2mbn.jmccc.auth.yggdrasil.core.yggdrasil.YggdrasilServiceBuilder;
import org.to2mbn.jmccc.util.UUIDUtils;

import java.util.Objects;

public class YggdrasilAuthenticator_JavaFX extends YggdrasilAuthenticator {
    private String username_temp;
    private String password_temp;

    public YggdrasilAuthenticator_JavaFX(AuthenticationService sessionService) {
        super(sessionService);
    }

    public static YggdrasilAuthenticator_JavaFX password(String username, String password) throws AuthenticationException {
        YggdrasilAuthenticator_JavaFX temp = password(username, password, null);
        temp.username_temp = username;
        temp.password_temp = password;
        return temp;
    }

    public static YggdrasilAuthenticator_JavaFX password(String username, String password, CharacterSelector characterSelector) throws AuthenticationException {
        YggdrasilAuthenticator_JavaFX temp = password(username, password, characterSelector, UUIDUtils.randomUnsignedUUID());
        temp.username_temp = username;
        temp.password_temp = password;
        return temp;
    }

    public static YggdrasilAuthenticator_JavaFX password(String username, String password, CharacterSelector characterSelector, String clientToken) throws AuthenticationException {
        YggdrasilAuthenticator_JavaFX temp = password(username, password, characterSelector, clientToken, YggdrasilServiceBuilder.defaultAuthenticationService());
        temp.username_temp = username;
        temp.password_temp = password;
        return temp;
    }

    public static YggdrasilAuthenticator_JavaFX password(String username, String password, CharacterSelector characterSelector, String clientToken, AuthenticationService service) throws AuthenticationException {
        YggdrasilAuthenticator_JavaFX temp = password(service, createPasswordProvider(username, password, characterSelector), clientToken);
        temp.username_temp = username;
        temp.password_temp = password;
        return temp;
    }

    public static YggdrasilAuthenticator_JavaFX password(AuthenticationService service, YggdrasilAuthenticator.PasswordProvider passwordProvider, String clientToken) throws AuthenticationException {
        Objects.requireNonNull(service);
        Objects.requireNonNull(passwordProvider);
        Objects.requireNonNull(clientToken);
        YggdrasilAuthenticator_JavaFX auth = new YggdrasilAuthenticator_JavaFX(service);
        auth.refreshWithPassword(passwordProvider);
        return auth;
    }

    @Override
    protected PasswordProvider tryPasswordLogin() {
        // 这个方法会在进行被动刷新时调用

        return YggdrasilAuthenticator.createPasswordProvider(username_temp, password_temp, null);
    }

}