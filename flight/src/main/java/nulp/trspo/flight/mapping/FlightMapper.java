package nulp.trspo.flight.mapping;

import nulp.trspo.flight.persistence.entity.FlightEntity;
import nulp.trspo.flight.model.FlightDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PlaneMapper.class, TimestampMapper.class})
public interface FlightMapper {

    FlightDto flightToFlightDto(FlightEntity flight);
    FlightEntity flightDtoToFlight(FlightDto flightDto);
    List<FlightDto> entitiesToDTOs(List<FlightEntity> flightEntities);
}
