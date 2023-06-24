package nulp.trspo.ticket.mapping;

import nulp.trspo.ticket.model.PassengerDto;
import nulp.trspo.ticket.persistence.entity.PassengerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerMapper {

    PassengerDto passengerToPassengerDto (PassengerEntity passenger);
    PassengerEntity passengerDtoToPassenger (PassengerDto passengerDto);
}

