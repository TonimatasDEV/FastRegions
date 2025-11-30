package dev.tonimatas.fastregions.platform.services;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Gets the version of FastRegions.
     *
     * @return The version of FastRegions.
     */
    String getModVersion();
}
