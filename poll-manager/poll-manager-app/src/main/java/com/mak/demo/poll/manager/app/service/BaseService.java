package com.mak.demo.poll.manager.app.service;


import com.mak.demo.poll.manager.port.adapter.BaseAdapter;
import com.mak.demo.poll.manager.port.domain.BaseDomain;
import com.mak.demo.poll.manager.port.filter.BaseFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.Optional;


@Slf4j
public abstract class BaseService<D extends BaseDomain, F extends BaseFilter> extends
    BaseServiceProxy<D, F, BaseAdapter<D, F>> {

  public Optional<D> get(Long id) {
    return getRepository().get(id);
  }

  public D put(D domainObject) {
    return save(domainObject);
  }

  public D save(D domainObject) { return getRepository().save(domainObject); }

  public void delete(Long id) {
    getRepository().deleteById(id);
  }

  public Page<D> getAllFilter(F filter) {
    return getRepository().getAllFilter(filter);
  }

  public List<D> getAll() {
    return getRepository().getAll();
  }

  public Page<D> getPageable(Pageable pageable) {
    return getRepository().getPageable(pageable);
  }

  protected boolean isSuccess(HttpStatus httpStatus) {
    return httpStatus == HttpStatus.OK || httpStatus == HttpStatus.CREATED || httpStatus == HttpStatus.ACCEPTED;
  }

}

    