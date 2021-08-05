package org.to2mbn.jmccc.version;

import java.io.Serializable;
import java.util.Objects;

public class DownloadInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;
    private String checksum;
    private long size;

    /**
     * Constructor of DownloadInfo.
     *
     * @param url      the download url, set it null if the url is unknown
     * @param checksum the SHA-1 checksum, set it null if the checksum is unknown
     * @param size     the file size, set it -1 if the size is unknown
     */
    public DownloadInfo(String url, String checksum, long size) {
        Objects.requireNonNull(url);
        Objects.requireNonNull(checksum);
        this.url = url;
        this.checksum = checksum;
        this.size = size;
    }

    /**
     * Gets the download url, null if the url is unknown
     *
     * @return the url, null if the url is unknown
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the SHA-1 checksum.
     *
     * @return the SHA-1 checksum, null if the checksum is unknown
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * Gets the file size, -1 if the size is unknown
     *
     * @return the file size, -1 if the size is unknown
     */
    public long getSize() {
        return size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, checksum, size);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof DownloadInfo) {
            DownloadInfo another = (DownloadInfo) obj;
            return Objects.equals(url, another.url) && Objects.equals(checksum, another.checksum) && size == another.size;
        }
        return false;
    }

    @Override
    public String toString() {
        return url;
    }

}
