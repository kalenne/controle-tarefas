package com.kap.controleusuario.utils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormatDate {

	DateTimeFormatter userformato = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
     
    public LocalDate userToDb (String date) throws ParseException {
    	LocalDate data = LocalDate.parse(date, userformato);
    	return data;
    }
    
    public String dbToUser(LocalDate date) {
    	return date.format(userformato);
    }
}
