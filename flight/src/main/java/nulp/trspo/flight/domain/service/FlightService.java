package nulp.trspo.flight.domain.service;

import lombok.RequiredArgsConstructor;
import nulp.trspo.flight.persistence.entity.FlightEntity;
import nulp.trspo.flight.persistence.repository.FlightRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightEntity addFlight(FlightEntity flight) {
        flight.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        return flightRepository.save(flight);
    }

    @CacheEvict(value = "flights", key = "#flightId")
    public void deleteFlightById(Long flightId) {
        flightRepository.deleteById(flightId);
    }

    @Cacheable(value = "flights", key = "allFlights")
    public List<FlightEntity> getFlights() {
        List<FlightEntity> flightEntities = flightRepository.findAll();
        flightEntities.forEach((this::setCurrentPrice));
        flightEntities = flightEntities.stream().filter((flight -> !flight.getAvailableSeats().equals(0))).toList();
        return flightEntities;
    }
    @Cacheable(value = "flights", key = "#flightId")
    public FlightEntity getFlightById(Long flightId) {
        FlightEntity flightEntity = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        setCurrentPrice(flightEntity);
        return flightEntity;
    }

    private void setCurrentPrice(FlightEntity flight) {
        float initialPrice = flight.getPrice();
        int seatCount = flight.getPlane().getSeatCount();
        int availableCount = flight.getAvailableSeats();
        int initialDaysToDeparture = (int) ((System.currentTimeMillis() - flight.getCreatedDate().getTime())/ 86400000);
        int currentDaysToDeparture = (int) ((System.currentTimeMillis() - flight.getDepartureDate().getTime())/ 86400000);

        float currentPrice = (float) (initialPrice *
                        (1.0 + (float) (seatCount - availableCount) / seatCount) *
                        (1.0 + (float) (initialDaysToDeparture - currentDaysToDeparture) / initialDaysToDeparture));
        flight.setPrice(currentPrice);
    }

    @CachePut(value = "flights", key = "#flightId")
    public FlightEntity updateFlightById(Long flightId, FlightEntity newFlight) {
        newFlight.setId(flightId);
        return flightRepository.save(newFlight);
    }
}
