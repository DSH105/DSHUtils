package io.github.dsh105.dshutils.config.options;

import io.github.dsh105.dshutils.config.YAMLConfig;


public abstract class Options {

    protected YAMLConfig config;

    public Options(YAMLConfig config) {
        this.config = config;
    }

    public abstract void setDefaults();

    protected void set(String path, Object defObject, String... comments) {
        this.config.set(path, this.config.get(path, defObject), comments);
    }
}