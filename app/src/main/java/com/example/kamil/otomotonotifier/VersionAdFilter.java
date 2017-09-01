package com.example.kamil.otomotonotifier;

/**
 * Created by kamil on 01/09/2017.
 */

public class VersionAdFilter extends AdFilter {
    String version;

    @Override
    boolean isTheAdGoodToAdd(Ad ad) {
        String adVersion = ad.getVersion();
        if(AdFilterTools.stringsEqual(version, adVersion)) {
            return true;
        } else {
            return false;
        }
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
