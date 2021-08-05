package org.to2mbn.jmccc.mcdownloader.provider;

import org.to2mbn.jmccc.mcdownloader.download.DownloadTask;
import org.to2mbn.jmccc.mcdownloader.download.FileDownloadTask;
import org.to2mbn.jmccc.version.Library;

import java.io.File;
import java.net.URI;

public class JarLibraryDownloadHandler implements LibraryDownloadHandler {

    @Override
    public DownloadTask<Void> createDownloadTask(File target, Library library, URI libraryUri) {
        return new FileDownloadTask(libraryUri, target);
    }

}
