package com.talhakum.linkconverter.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Talha Kum
 */
@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ConverterLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String accessUrl;

	private String clientIp;

	private Date createDate;

	private String request;

	private String response;

}
