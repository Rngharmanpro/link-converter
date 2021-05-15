package com.talhakum.linkconverter.kafka.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Talha Kum
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ConverterLogEvent implements Serializable {

	private static final long serialVersionUID = 5105074006518164811L;

	protected String accessUrl;
	protected String clientIp;
	protected Date createDate;
	private String request;
	private String response;

}
