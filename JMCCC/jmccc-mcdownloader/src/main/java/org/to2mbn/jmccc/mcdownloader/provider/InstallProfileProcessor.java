package org.to2mbn.jmccc.mcdownloader.provider;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.to2mbn.jmccc.mcdownloader.download.ResultProcessor;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class InstallProfileProcessor implements ResultProcessor<byte[], String> {

    private MinecraftDirectory mcdir;

    public InstallProfileProcessor(MinecraftDirectory mcdir) {
        this.mcdir = mcdir;
    }

    @Override
    public String process(byte[] arg) throws Exception {
        String version = null;
        try (ZipInputStream in = new ZipInputStream(new ByteArrayInputStream(arg))) {
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                if ("install_profile.json".equals(entry.getName())) {
                    version = writeJson(processJson(new JSONObject(new JSONTokener(new InputStreamReader(in, "UTF-8")))));
                    in.closeEntry();
                    break;
                }
                in.closeEntry();
            }
        }

        if (version == null) {
            throw new IllegalArgumentException("No install_profile.json has found");
        }

        return version;
    }

    protected JSONObject processJson(JSONObject installprofile) {
        return installprofile.getJSONObject("versionInfo");
    }

    private String writeJson(JSONObject json) throws IOException {
        String version = json.getString("id");
        File output = mcdir.getVersionJson(version);

        if (!output.getParentFile().exists()) {
            output.getParentFile().mkdirs();
        }
        try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(output)), "UTF-8")) {
            writer.write(json.toString(4));
        }

        return version;
    }

}
