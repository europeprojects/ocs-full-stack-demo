package com.mak.demo.poll.manager.rdms.mapper;

import java.util.List;

public interface BaseMapper<E, D> {

  E toEntity(D domainObject);

  List<E> toListEntity(List<D> entities);

  D toDomainObject(E entity);

  List<D> toListDomainObject(List<E> entities);

}