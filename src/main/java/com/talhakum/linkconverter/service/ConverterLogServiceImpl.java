package com.talhakum.linkconverter.service;

import com.talhakum.linkconverter.model.ConverterLog;
import com.talhakum.linkconverter.repository.ConverterLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Talha Kum
 */
@Service
public class ConverterLogServiceImpl implements ConverterLogService {

	@Autowired
	private ConverterLogRepository converterLogRepository;

	@Override
	public void saveConverterLog(ConverterLog converterLog) {
		converterLogRepository.save(converterLog);
	}

}
