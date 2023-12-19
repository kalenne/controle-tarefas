package com.kap.controleusuario.utils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class FormatLocalDateTime {
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	public LocalDateTime userToDb (String date) throws ParseException {
		LocalDateTime data = LocalDateTime.parse(date, dtf);
    	return data;
    }
	
	public String dbToUser(LocalDateTime localDate) {
		return localDate.format(dtf) ;
	}
}

