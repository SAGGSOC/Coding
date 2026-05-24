import java.util.Set;

public enum VehicleType {
    MOTORCYCLE (1, Set.of(SpotType.SMALL, SpotType.MEDIUM, SpotType.LARGE)),
    CAR        (1, Set.of(SpotType.MEDIUM, SpotType.LARGE)),
    TRUCK      (1, Set.of(SpotType.LARGE)),
    BUS        (3, Set.of(SpotType.LARGE));

    private final int requiredSpots;
    private final Set<SpotType> compatibleSpotTypes;

    VehicleType(int requiredSpots, Set<SpotType> compatibleSpotTypes) {
        this.requiredSpots = requiredSpots;
        this.compatibleSpotTypes = compatibleSpotTypes;
    }

    public int getRequiredSpots() {
        return requiredSpots;
    }

    public Set<SpotType> getCompatibleSpotTypes() {
        return compatibleSpotTypes;
    }

    public boolean isMultiSpot() {
        return requiredSpots > 1;
    }

    public boolean fitsIn(SpotType spotType) {
        return compatibleSpotTypes.contains(spotType);
    }

}