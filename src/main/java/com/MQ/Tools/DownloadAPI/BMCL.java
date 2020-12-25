package com.MQ.Tools.DownloadAPI;

import org.to2mbn.jmccc.mcdownloader.provider.DefaultLayoutProvider;

public class BMCL extends DefaultLayoutProvider {

    @Override
    protected String getLibraryBaseURL() {
        return "http://bmclapi2.bangbang93.com/libraries/";
    }

    @Override
    protected String getVersionBaseURL() {
        return "http://bmclapi2.bangbang93.com/versions/";
    }

    @Override
    protected String getAssetIndexBaseURL() {
        return "http://bmclapi2.bangbang93.com/indexes/";
    }

    @Override
    protected String getVersionListURL() {
        return "http://bmclapi2.bangbang93.com/mc/game/version_manifest.json";
    }

    @Override
    protected String getAssetBaseURL() {
        return "http://bmclapi2.bangbang93.com/assets/";
    }

}