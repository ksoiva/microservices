package nulp.trspo.ticket.domain.service;

import lombok.RequiredArgsConstructor;
import nulp.trspo.ticket.dictionary.Services;
import nulp.trspo.ticket.persistence.entity.FlightEntity;
import nulp.trspo.ticket.persistence.entity.PlaneEntity;
import nulp.trspo.ticket.persistence.entity.TicketEntity;
import nulp.trspo.ticket.persistence.repository.FlightRepository;
import nulp.trspo.ticket.persistence.repository.TicketRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;

    public TicketEntity addTicket(TicketEntity ticket) {
        FlightEntity flight = flightRepository
                .findById(ticket.getFlight().getId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        setCurrentPrice(flight);
        ticket.setFlight(flight);
        calcTotalPrice(ticket);
        calcSeat(ticket);
        return ticketRepository.save(ticket);
    }

    private void calcTotalPrice(TicketEntity ticket) {
        Float totalPrice = ticket.getFlight().getPrice();
        if (ticket.getBaggage()) {
            totalPrice += Services.BAGGAGE.getValue();
        }
        if (ticket.getPriority()) {
            totalPrice += Services.PRIORITY.getValue();
        }
        ticket.setTotalPrice(totalPrice);
    }

    private void calcSeat(TicketEntity ticket) {
        FlightEntity flight = flightRepository.findById(ticket.getFlight().getId()).orElseThrow(()-> new RuntimeException("Flight not found"));
        PlaneEntity plane = ticket.getFlight().getPlane();
        int availableSeats = ticket.getFlight().getAvailableSeats();

        int seatsPerRow = plane.getSeatCount() / plane.getRowCount();
        int occupiedSeats = Math.floorMod(availableSeats, seatsPerRow);
        char seatChar = (char) (65 + seatsPerRow - (occupiedSeats == 0 ? seatsPerRow : occupiedSeats));
        int row = plane.getRowCount() - (int) Math.ceil((double) availableSeats / seatsPerRow) + 1;

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);
        ticket.setSeat(Integer.toString(row) + seatChar);
    }

    @CacheEvict(value = "tickets", key = "#ticketId")
    public void deleteTicketById(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    @Cacheable(value = "tickets", key = "#ticketId")
    public Optional<TicketEntity> getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    @CachePut(value = "tickets", key = "#ticketId")
    public TicketEntity updateTicketById(Long ticketId, TicketEntity newTicket) {
        newTicket.setId(ticketId);
        return ticketRepository.save(newTicket);
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
}
