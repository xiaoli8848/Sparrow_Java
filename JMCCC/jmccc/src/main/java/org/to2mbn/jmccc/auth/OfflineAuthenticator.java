package org.to2mbn.jmccc.auth;

import org.to2mbn.jmccc.util.UUIDUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class OfflineAuthenticator implements Authenticator, Serializable {

    private static final long serialVersionUID = 1L;

    private String playerName;
    private String uuid;
    private String token;

    /**
     * Constructs an OfflineAuthenticator.
     *
     * @param playerName the player name
     * @throws NullPointerException     if <code>playerName==null</code>
     * @throws IllegalArgumentException if <code>playerName.length()==0</code>
     */
    public OfflineAuthenticator(String playerName) throws AuthenticationException {
        Objects.requireNonNull(playerName);
        this.playerName = playerName;
        try {
            this.uuid = UUIDUtils.unsign(generateUUID());
            this.token = UUIDUtils.randomUnsignedUUID();
        } catch (UnsupportedEncodingException e) {
            throw new AuthenticationException("UTF-8 is not supported", e);
        }
        if (this.playerName.length() == 0) {
            throw new IllegalArgumentException("Zero length player name");
        }
    }

    @Override
    public AuthInfo auth() throws AuthenticationException {
        return new AuthInfo(playerName, token, uuid, Collections.unmodifiableMap(new HashMap<String, String>()), "mojang");
    }

    private UUID generateUUID() throws UnsupportedEncodingException {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes("UTF-8"));
    }

    @Override
    public String toString() {
        return "OfflineAuthenticator[" + playerName + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof OfflineAuthenticator) {
            OfflineAuthenticator another = (OfflineAuthenticator) obj;
            return playerName.equals(another.playerName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return playerName.hashCode();
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setAlex() throws UnsupportedEncodingException {
        while ((uuid.hashCode() & 1) == 1) {
            try {
                this.uuid = UUIDUtils.unsign(generateUUID());
            } catch (UnsupportedEncodingException e) {
                throw e;
            }
        }
    }

    public void setSteve() throws UnsupportedEncodingException {
        while ((uuid.hashCode() & 1) == 0) {
            try {
                this.uuid = UUIDUtils.unsign(generateUUID());
            } catch (UnsupportedEncodingException e) {
                throw e;
            }
        }
    }

    /**
     * Return the type of the skin of the authenticator.
     *
     * @return 0 -> Steve, 1 -> Alex.
     */
    public int getSkinType() {
        return uuid.hashCode() & 1;
    }
}
