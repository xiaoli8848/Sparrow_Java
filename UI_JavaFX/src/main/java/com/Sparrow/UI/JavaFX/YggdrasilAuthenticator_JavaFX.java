package com.Sparrow.UI.JavaFX;

import org.to2mbn.jmccc.auth.yggdrasil.YggdrasilAuthenticator;
import org.to2mbn.jmccc.auth.yggdrasil.core.AuthenticationService;

public class YggdrasilAuthenticator_JavaFX extends YggdrasilAuthenticator {

    public YggdrasilAuthenticator_JavaFX() {
        super();
    }

    public YggdrasilAuthenticator_JavaFX(AuthenticationService sessionService) {
        super(sessionService);
    }

    @Override
    protected PasswordProvider tryPasswordLogin() {
        // 这个方法会在进行被动刷新时调用
        String email = launcherUI.controller.getUserName();
        String password = launcherUI.controller.getPassword();

        return YggdrasilAuthenticator.createPasswordProvider(email, password, null);
    }

}