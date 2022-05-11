package com.mak.demo.poll.manager.rdms.repository;


import com.mak.demo.poll.manager.rdms.specification.BaseFilterSpecification;

public abstract class BaseRepositoryProxy<E, F, S extends BaseFilterSpecification<E, F>> {

  protected abstract S getSpecification();

}
