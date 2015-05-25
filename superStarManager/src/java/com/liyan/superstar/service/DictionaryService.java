package com.liyan.superstar.service;

import com.liyan.common.action.MsgBean;
import com.liyan.superstar.model.Dictionary;

public interface DictionaryService {

	MsgBean checkDictSort(Dictionary dict);

	MsgBean checkDictValue(Dictionary dict);
	
}
