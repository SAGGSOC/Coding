class ParkingSpot{
    private final String spotId;
    private final int floorNumber;
    private final int spotNumber;
    private final SpotType type;
    private final boolean isHandicap;
    private final boolean hasEVCharging;
    private Vehicle vehicle;
    private boolean occupied;

    public ParkingSpot(String spotId, int floorNumber, int spotNumber,
                       SpotType type, boolean isHandicap, boolean hasEVCharging) {
        this.spotId = spotId;
        this.floorNumber = floorNumber;
        this.spotNumber = spotNumber;
        this.type = type;
        this.isHandicap = isHandicap;
        this.hasEVCharging = hasEVCharging;
        this.occupied = false;
    }

    public boolean canFit(VehicleType vehicleType) {
        if (occupied)
            return false;
        if (vehicleType.isMultiSpot())
            return false;  // multi-spot handled by floor-level logic
        return vehicleType.fitsIn(type);
    }



}