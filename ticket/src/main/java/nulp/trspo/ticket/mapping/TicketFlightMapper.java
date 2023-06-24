package nulp.trspo.ticket.mapping;

import nulp.trspo.ticket.model.TicketFlightDto;
import nulp.trspo.ticket.persistence.entity.FlightEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TimestampMapper.class})
public interface TicketFlightMapper {

    TicketFlightDto flightToFlightDto (FlightEntity flight);
    FlightEntity flightDtoToFlight (TicketFlightDto flightDto);
}
