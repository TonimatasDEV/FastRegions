package dev.tonimatas.fastregions.impl.allowlist;

import dev.tonimatas.fastregions.region.allowlist.AllowedListExtension;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;

public final class EntitiesExtension implements AllowedListExtension {
    @Override
    public List<String> getIDs() {
        return BuiltInRegistries.ENTITY_TYPE.entrySet().stream().map(Map.Entry::getKey).map(ResourceKey::location).map(ResourceLocation::toString).toList();
    }
}
