package nulp.trspo.flight.api.rest;

import lombok.RequiredArgsConstructor;
import nulp.trspo.flight.api.FlightApiDelegate;
import nulp.trspo.flight.domain.service.FlightService;
import nulp.trspo.flight.mapping.FlightMapper;
import nulp.trspo.flight.persistence.entity.FlightEntity;
import nulp.trspo.flight.model.FlightDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FlightApiDelegateImpl implements FlightApiDelegate {
    private final FlightService flightService;
    private final FlightMapper flightMapper;
    @Override
    public ResponseEntity<FlightDto> addFlight(FlightDto flightDto) {
        FlightEntity flightEntity = flightMapper.flightDtoToFlight(flightDto);
        FlightDto savedFlight = flightMapper.flightToFlightDto(flightService.addFlight(flightEntity));
        return ResponseEntity.ok(savedFlight);
    }

    @Override
    public ResponseEntity<Void> deleteFlightById(BigDecimal flightId) {
        flightService.deleteFlightById(flightId.longValue());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<FlightDto>> getFlight() {
        List<FlightEntity> flightEntities = flightService.getFlights();
        return ResponseEntity.ok(flightMapper.entitiesToDTOs(flightEntities));
    }

    @Override
    public ResponseEntity<FlightDto> getFlightById(BigDecimal flightId) {
        FlightEntity flightEntity = flightService.getFlightById(flightId.longValue());
        return ResponseEntity.ok(flightMapper.flightToFlightDto(flightEntity));
    }

    @Override
    public ResponseEntity<FlightDto> updateFlightById(BigDecimal flightId, FlightDto flightDto) {
        FlightEntity flightEntity = flightMapper.flightDtoToFlight(flightDto);
        FlightDto updatedFlight = flightMapper.flightToFlightDto(flightService.updateFlightById(flightId.longValue(), flightEntity));
        return ResponseEntity.ok(updatedFlight);
    }
}
