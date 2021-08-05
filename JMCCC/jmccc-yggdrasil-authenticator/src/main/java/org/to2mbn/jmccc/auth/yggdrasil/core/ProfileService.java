package org.to2mbn.jmccc.auth.yggdrasil.core;

import org.to2mbn.jmccc.auth.AuthenticationException;

import java.util.UUID;

public interface ProfileService {

    PropertiesGameProfile getGameProfile(UUID profileUUID) throws AuthenticationException;

    PlayerTextures getTextures(GameProfile profile) throws AuthenticationException;

    UUID lookupUUIDByName(String playerName) throws AuthenticationException;

}
