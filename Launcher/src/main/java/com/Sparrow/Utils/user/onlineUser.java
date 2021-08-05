package com.Sparrow.Utils.user;

import com.Sparrow.Utils.texture;
import org.to2mbn.jmccc.auth.yggdrasil.YggdrasilAuthenticator;
import org.to2mbn.jmccc.auth.yggdrasil.core.GameProfile;
import org.to2mbn.jmccc.auth.yggdrasil.core.ProfileService;
import org.to2mbn.jmccc.auth.yggdrasil.core.yggdrasil.YggdrasilServiceBuilder;

public class onlineUser extends user {
    private static final ProfileService PROFILE_SERVICE = YggdrasilServiceBuilder.defaultProfileService();
    private final String password;
    private String email;
    private GameProfile gameProfile;

    public onlineUser(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    public boolean getInfo() {
        if (gameProfile == null || texture == null || authenticator == null) {
            try {
                this.authenticator = YggdrasilAuthenticator.password(email, password);
                this.userName = this.authenticator.auth().getUsername();
                this.gameProfile = PROFILE_SERVICE.getGameProfile(PROFILE_SERVICE.lookupUUIDByName(this.userName));
                this.texture = new texture(PROFILE_SERVICE.getTextures(gameProfile).getSkin());
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return true;
        }
    }

    public String getPassword() {
        return password;
    }
}
