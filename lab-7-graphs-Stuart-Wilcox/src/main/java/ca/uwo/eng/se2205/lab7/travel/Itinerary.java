package ca.uwo.eng.se2205.lab7.travel;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Defines the results from the {@link FlightShopper} of how to get from
 */
@Immutable
@ParametersAreNonnullByDefault
public final class Itinerary {

    private final ImmutableList<Flight> flights;

    private final double cost;
    private final Airport departure, arrival;

    public Itinerary(List<Flight> flights) {

        checkNotNull(flights, "flights == null");
        checkArgument(flights.size() > 0,
                "Must have at least 1 flight in an itinerary, found: {}", flights);
        this.flights = ImmutableList.copyOf(flights);

        this.departure = flights.get(0).getDeparture();
        this.arrival = flights.get(flights.size() - 1).getArrival();

        this.cost = flights.stream().mapToDouble(Flight::getCost).sum();
    }

    /**
     * Gets the {@link List} of {@link Flight}s to take to get between a destination.
     * @return Non-{@code null} listing of flights
     */
    public ImmutableList<Flight> getFlights() {
        return flights;
    }

    /**
     * Get the total cost of the trip
     * @return Total cost of the itinerary
     */
    public double getCost() {
        return cost;
    }

    /**
     * Get the departure {@link Airport}
     * @return Non-{@code null} {@code Airport} the trip departs from
     */
    public Airport getDeparture() {
        return departure;
    }

    /**
     * {@link Airport} the itinerary ends at
     * @return Non-{@code null} {@link Airport} the trip ends at.
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
                .add("flights", flights)
                .toString();
    }

}
