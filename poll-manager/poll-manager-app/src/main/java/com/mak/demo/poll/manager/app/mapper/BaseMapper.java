package com.mak.demo.poll.manager.app.mapper;

import java.util.List;

public interface BaseMapper<DomainObject, DTO> {

  DTO toDTO(DomainObject domainObject);

  List<DTO> toListDTO(List<DomainObject> domainObjects);

  DomainObject toDomainObject(DTO dto);

  List<DomainObject> toListDomainObject(List<DTO> dtoList);

}