package com.MQ.UI.H5;

import com.MQ.UI.JavaFX.launcherUI;
import org.to2mbn.jmccc.auth.yggdrasil.YggdrasilAuthenticator;
import org.to2mbn.jmccc.auth.yggdrasil.core.AuthenticationService;

public class YggdrasilAuthenticator_H5 extends YggdrasilAuthenticator {
    String username = "";
    String password = "";
    public YggdrasilAuthenticator_H5(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public YggdrasilAuthenticator_H5(AuthenticationService sessionService) {
        super(sessionService);
    }

    @Override
    protected PasswordProvider tryPasswordLogin() {
        return YggdrasilAuthenticator.createPasswordProvider(username, password, null);
    }
}
