package com.kap.controleusuario.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatLocalDateTime {
	
	private LocalDateTime localDate;
	private String formatDateTime;
	
	public FormatLocalDateTime(LocalDateTime data) {
		localDate = data;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		this.formatDateTime = localDate.format(dtf);
	}
	
	public String formatDateTime() {
		return formatDateTime;
	}
}
