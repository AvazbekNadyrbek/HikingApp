package com.berchtesgaden.explorer.service;

import com.berchtesgaden.explorer.domain.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationValidator {

    public boolean isReadyForPublication(Location location) {
        if (location == null) return false;
        
        return location.getName() != null && !location.getName().isBlank()
                && location.getLatitude() != null
                && location.getLongitude() != null
                && location.getDescription() != null && location.getDescription().length() > 10;
    }
}
