package com.Sparrow.Utils.user;

import com.Sparrow.Utils.texture;
import org.to2mbn.jmccc.auth.yggdrasil.YggdrasilAuthenticator;
import org.to2mbn.jmccc.auth.yggdrasil.core.GameProfile;
import org.to2mbn.jmccc.auth.yggdrasil.core.ProfileService;
import org.to2mbn.jmccc.auth.yggdrasil.core.yggdrasil.YggdrasilAPIProvider;
import org.to2mbn.jmccc.auth.yggdrasil.core.yggdrasil.YggdrasilServiceBuilder;
import org.to2mbn.jmccc.util.UUIDUtils;

import java.util.UUID;

public class libUser extends user{
    private String password;
    private GameProfile gameProfile;
    private ProfileService PROFILE_SERVICE;
    private String server;

    public libUser(String userName, String password, String server) {
        super(userName);
        this.password = password;
        yggdrasilAPIProvider provider = new yggdrasilAPIProvider(server);
        YggdrasilServiceBuilder yggdrasilBuilder = YggdrasilServiceBuilder.create()
                .setAPIProvider(provider);
        this.PROFILE_SERVICE = yggdrasilBuilder.buildProfileService();
        this.server = server;
    }

    public boolean getInfo(){
        if(this.gameProfile == null || this.texture == null || this.authenticator == null) {
            try {
                this.gameProfile = PROFILE_SERVICE.getGameProfile(PROFILE_SERVICE.lookupUUIDByName(getUserName()));
                this.texture = new texture(PROFILE_SERVICE.getTextures(gameProfile).getSkin());
                this.authenticator = YggdrasilAuthenticator.password(userName, password);
                return true;
            } catch (Exception e) {
                return false;
            }
        }else{
            return true;
        }
    }

    public String getPassword() {
        return password;
    }

    public String getServer() {
        return server;
    }
}

class yggdrasilAPIProvider implements YggdrasilAPIProvider {
    private String server;

    private String authenticate;
    private String refresh;
    private String validate;
    private String invalidate;
    private String signout;
    private String profile;
    private String profileLookup;

    public yggdrasilAPIProvider(String server){
        if(server.charAt(server.length()-1)!='/'){
            this.server = server+"/";
        }else{
            this.server = server;
        }
        this.authenticate = server + "authenticate";
        this.refresh = server + "refrash";
        this.validate = server + "validate";
        this.invalidate = server + "invalidate";
        this.signout = server + "signout";
        this.profile = server + "profile/minecraft/";
        this.profileLookup = server + "profileLookup";
    }

    @Override
    public String authenticate() {
        return authenticate;
    }

    @Override
    public String refresh() {
        return refresh;
    }

    @Override
    public String validate() {
        return validate;
    }

    @Override
    public String invalidate() {
        return invalidate;
    }

    @Override
    public String signout() {
        return signout;
    }

    @Override
    public String profile(UUID profileUUID) {
        return profile + UUIDUtils.unsign(profileUUID);
    }

    @Override
    public String profileLookup() {
        return profileLookup;
    }
}