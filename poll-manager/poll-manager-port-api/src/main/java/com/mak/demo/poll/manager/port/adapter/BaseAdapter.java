package com.mak.demo.poll.manager.port.adapter;

import com.mak.demo.poll.manager.port.domain.BaseDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;


public interface BaseAdapter<D extends BaseDomain, Filter> {

  Optional<D> findById(Long id);
  Stream<D> findByIdIn(Set<Long> idSet);
  Optional<D> get(Long id);
  List<D> getAll();
  void deleteById(Long id);
  void delete(D deleted);
  void deleteByIdIn(Set<Long> idSet);
  D save(D persisted);
  Stream<D> saveAll(List<D> entities);
  Page<D> getAllFilter(Filter filter);
  Page<D> getPageable(Pageable pageable);

}

