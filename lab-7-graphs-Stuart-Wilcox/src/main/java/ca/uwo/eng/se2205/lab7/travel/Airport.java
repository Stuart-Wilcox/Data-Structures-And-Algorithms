package ca.uwo.eng.se2205.lab7.travel;

import com.google.common.base.MoreObjects;

import javax.annotation.concurrent.Immutable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Describes an Airport with a "code" (e.g. YYZ) and a latitude and longitude.
 */
@Immutable
public final class Airport {

    private final String code;
    private final double latitude;
    private final double longitude;

    public Airport(String code, double latitude, double longitude) {
        checkArgument(code != null && code.length() == 3,
                      "Code can not be null or under 3 chars");

        this.code = code;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * The <strong>unique</strong> code for the airport
     * @return 3-letter code for the airport
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the latitude of the Airport
     * @return latitude coordinate
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude coordinate
     * @return Longitude
     */
    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(code)
                .add("latitude", latitude)
                .add("longitude", longitude)
                .toString();
    }

    public Double distanceTo(Airport other){
        return Math.sqrt(Math.pow(other.getLatitude()-this.getLatitude(), 2) + Math.pow(other.getLongitude()-this.getLongitude(), 2));
    }

    @Override
    public boolean equals(Object o) {
        return this.getCode().equals(((Airport)o).getCode());
    }
}
