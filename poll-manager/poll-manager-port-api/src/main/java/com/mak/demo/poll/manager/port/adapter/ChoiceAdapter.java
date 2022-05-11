package com.mak.demo.poll.manager.port.adapter;

import com.mak.demo.poll.manager.port.domain.Choice;
import com.mak.demo.poll.manager.port.filter.ChoiceFilter;

public interface ChoiceAdapter extends BaseAdapter<Choice, ChoiceFilter> {

    void deleteByPollId(Long id);
}

