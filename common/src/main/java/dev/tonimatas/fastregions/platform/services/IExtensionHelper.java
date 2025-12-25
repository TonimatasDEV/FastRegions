package dev.tonimatas.fastregions.platform.services;

import dev.tonimatas.fastregions.region.allowlist.AllowedListExtension;

public interface IExtensionHelper {

    /**
     * Gets the blocks allow list extension implementation.
     *
     * @return The blocks allow list extension.
     */
    AllowedListExtension blocks();

    /**
     * Gets the entities allow list extension implementation.
     *
     * @return The entities allow list extension.
     */
    AllowedListExtension entities();
}
