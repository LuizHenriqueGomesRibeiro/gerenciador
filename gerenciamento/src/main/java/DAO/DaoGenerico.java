package DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DaoGenerico {
	public String converterDatas(String data) {
		
		String[] parte = data.split(" ");
		data = parte[0];

		if (verificarFormatoData(data, "yyyy-MM-dd")) {
			data = transformarFormatoData(data, "yyyy-MM-dd", "dd/MM/yyyy");
        } else if (verificarFormatoData(data, "dd/MM/yyyy")) {
        	data = transformarFormatoData(data, "dd/MM/yyyy", "yyyy-MM-dd");
        }
		return data;
	}
	
	public boolean verificarFormatoData(String data, String formato) {
        try {
            LocalDate.parse(data, DateTimeFormatter.ofPattern(formato));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	public String transformarFormatoData(String dataString, String formatoOriginal, String novoFormato) {
		try {
			SimpleDateFormat formatoOriginalData = new SimpleDateFormat(formatoOriginal);
			SimpleDateFormat formatoNovoData = new SimpleDateFormat(novoFormato);

			java.util.Date data = formatoOriginalData.parse(dataString);

			String dataFormatada = formatoNovoData.format(data);

			return dataFormatada;
		} catch (ParseException e) {
			e.printStackTrace();
			
			return null;
		}
	}
}
