package ca.uwo.eng.se2205.lab7.travel;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Describes a Flight between two {@link Airport}s
 */
@ParametersAreNonnullByDefault
@Immutable
public final class Flight {

    private final double cost;

    private final Airport departure;
    private final Airport arrival;

    public Flight(Airport departure, Airport arrival, double cost) {
        checkArgument(cost >= 0.0, "cost must not be negative");
        this.cost = cost;

        this.departure = checkNotNull(departure, "departure == null");
        this.arrival = checkNotNull(arrival, "arrival == null");
    }


    /**
     * Cost of the flight
     * @return Positive cost of the flight
     */
    public double getCost() {
        return cost;
    }

    /**
     * Starting {@link Airport} location
     * @return Non-{@code null} {@code Airport} the flight starts from.
     */
    public Airport getDeparture() {
        return departure;
    }

    /**
     * Ending {@link Airport} location
     * @return Non-{@code null} {@code Airport} the flight lands at
     */
    public Airport getArrival() {
        return arrival;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cost", cost)
                .add("departure", departure)
                .add("arrival", arrival)
                .toString();
    }
}
