package dev.tonimatas.fastregions.region.allowlist;

import java.util.ArrayList;
import java.util.List;

public record AllowedList(List<String> allowed) {
    public AllowedList() {
        this(new ArrayList<>());
    }

    public static AllowedList empty() {
        return new AllowedList(new ArrayList<>());
    }

    public void add(String id) {
        this.allowed.add(id);
    }

    public void remove(String id) {
        this.allowed.remove(id);
    }

    public boolean contains(String id) {
        return this.allowed.contains(id);
    }
}
