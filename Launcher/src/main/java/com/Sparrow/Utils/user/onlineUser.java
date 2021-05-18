package com.Sparrow.Utils.user;

import com.Sparrow.Utils.texture;
import org.to2mbn.jmccc.auth.yggdrasil.YggdrasilAuthenticator;
import org.to2mbn.jmccc.auth.yggdrasil.core.GameProfile;
import org.to2mbn.jmccc.auth.yggdrasil.core.ProfileService;
import org.to2mbn.jmccc.auth.yggdrasil.core.yggdrasil.YggdrasilServiceBuilder;

public class onlineUser extends user {
    private static final ProfileService PROFILE_SERVICE = YggdrasilServiceBuilder.defaultProfileService();
    private final String password;
    private GameProfile gameProfile;

    public onlineUser(String userName, String password) {
        super(userName);
        this.password = password;
    }

    public boolean getInfo() {
        if (gameProfile == null || texture == null || authenticator == null) {
            try {
                this.gameProfile = PROFILE_SERVICE.getGameProfile(PROFILE_SERVICE.lookupUUIDByName(getUserName()));
                this.texture = new texture(PROFILE_SERVICE.getTextures(gameProfile).getSkin());
                this.authenticator = YggdrasilAuthenticator.password(userName, password);
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
