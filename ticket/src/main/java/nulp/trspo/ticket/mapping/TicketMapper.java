package nulp.trspo.ticket.mapping;

import nulp.trspo.ticket.model.TicketDto;
import nulp.trspo.ticket.persistence.entity.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TicketFlightMapper.class, PassengerMapper.class})
public interface TicketMapper {

    TicketDto ticketToTicketDto (TicketEntity ticket);
    TicketEntity ticketDtoToTicket (TicketDto ticketDto);
}
