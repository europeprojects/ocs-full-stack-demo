package com.mak.demo.poll.manager.app.service;


import com.mak.demo.poll.manager.port.adapter.BaseAdapter;
import com.mak.demo.poll.manager.port.domain.BaseDomain;

public abstract class BaseServiceProxy<D extends BaseDomain, Filter, Repository extends BaseAdapter<D, Filter>> {

  public abstract Repository getRepository();
}
	