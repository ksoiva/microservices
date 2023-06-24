package nulp.trspo.flight.mapping;

import nulp.trspo.flight.persistence.entity.PlaneEntity;
import nulp.trspo.flight.model.PlaneDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaneMapper {

    PlaneDto planeToPlaneDto (PlaneEntity plane);
    PlaneEntity planeDtoToPlane (PlaneDto planeDto);
}
