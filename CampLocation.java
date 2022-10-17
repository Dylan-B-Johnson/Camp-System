// Copyright 2022 Row 3

package campSystem;

import java.util.UUID;

public class CampLocation {
    private double pricePerCamper;
    private UUID id;
    private String name;
    private static CampLocation campLocation;

    public CampLocation() {
        this.id = UUID.randomUUID();
    }

    public CampLocation getInstance() {

    }
}
