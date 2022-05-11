package com.mak.demo.poll.manager.rdms.adapter;

import com.mak.demo.poll.manager.port.adapter.BaseAdapter;
import com.mak.demo.poll.manager.port.domain.BaseDomain;
import com.mak.demo.poll.manager.port.filter.BaseFilter;
import com.mak.demo.poll.manager.rdms.mapper.BaseMapper;
import com.mak.demo.poll.manager.rdms.repository.BaseRepository;
import com.mak.demo.poll.manager.rdms.repository.BaseRepositoryProxy;
import com.mak.demo.poll.manager.rdms.specification.BaseFilterSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;


public abstract class BaseAdapterImpl<E, D extends BaseDomain, M extends BaseMapper<E, D>, F extends BaseFilter, R extends BaseRepository<E>, S extends BaseFilterSpecification<E, F>> extends
        BaseRepositoryProxy<E, F, S> implements BaseAdapter<D, F> {

  public abstract R getRepository();
  public abstract M getMapper();

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
  public Optional<D> findById(Long id) {
    return getRepository().findById(id).map(getMapper()::toDomainObject);
  }

  @Override
  public Stream<D> findByIdIn(Set<Long> idSet) {
    return getRepository().findByIdIn(idSet).map(getMapper()::toDomainObject) ;
  }

  @Override
  public Optional<D> get(Long id) {
    return findById(id);
  }

  @Override
  public List<D> getAll() {
     return getMapper().toListDomainObject(getRepository().findAll());
  }

  @Override
  public void deleteById(Long id) {
    getRepository().deleteById(id);
  }

  @Override
  public void delete(D deleted) {
    getRepository().delete(getMapper().toEntity(deleted));
  }

  @Override
  public void deleteByIdIn(Set<Long> idSet) {
    //getRepository().deleteByIdIn(idSet);
  }

  @Override
  public D save(D persisted) {
    return getMapper().toDomainObject(getRepository().save(getMapper().toEntity(persisted))) ;
  }

  @Override
  public Stream<D> saveAll(List<D> entities) {
    return getRepository().saveAll(getMapper().toListEntity(entities)).stream().map(getMapper()::toDomainObject);
  }

  @Override
  public Page<D> getAllFilter(F f) {
    return getRepository().findAll(getSpecification().filter(f), buildPageable(f)).map(getMapper()::toDomainObject);
  }

  @Override
  public Page<D> getPageable(Pageable pageable) {
    return getRepository().findAll(pageable).map(getMapper()::toDomainObject);
  }

  protected Pageable buildPageable(F filter) {
    Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageItemCount());
    if (Objects.nonNull((filter.getSortProperty()))) {
      if (filter.isSortAsc()) {
        pageable = PageRequest.of(filter.getPageNumber(), filter.getPageItemCount(), Sort.by(filter.getSortProperty()).ascending());
      } else {
        pageable = PageRequest.of(filter.getPageNumber(), filter.getPageItemCount(), Sort.by(filter.getSortProperty()).descending());
      }
    }
    return pageable;
  }
}
