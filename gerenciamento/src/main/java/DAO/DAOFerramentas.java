package DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.google.gson.Gson;

import conexao.conexao;

public class DAOFerramentas {
	
	public String dataAtual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtual = dateFormat.format(new Date());
        return dataAtual;
	}
	
	public Date stringToDate(String string) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(string);
        return date;
	}
	
	public String colocarPonto(String numero) {
		int posicao = numero.length() - 3;
		while (posicao > 0) {
			numero = numero.substring(0, posicao) + "." + numero.substring(posicao);
			posicao -= 3;
		}
		return numero;
    }
	
	public String tirarPonto(String numero) {
		numero = numero.replace(".", "");
		return numero;
    }
	
	public int converterDinheiroInteger(String dinheiro) {
		int integer = Integer.parseInt(dinheiro.replace(".", "").replace("R$", "").replace(",00", ""));
		return integer;
    }
	
	public String converterIntegerDinheiro(int integer) {
		String numeroString = String.valueOf(integer);
		int posicao = numeroString.length() - 3;
		while (posicao > 0) {
			numeroString = numeroString.substring(0, posicao) + "." + numeroString.substring(posicao);
			posicao -= 3;
		}
		numeroString = "R$" + numeroString + ",00";
		return numeroString;
    }
	
	public String converterDatas(String data) throws ParseException {
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
	
	public String transformarFormatoData(String dataString, String formatoOriginal, String novoFormato) throws ParseException {
		SimpleDateFormat formatoOriginalData = new SimpleDateFormat(formatoOriginal);
		SimpleDateFormat formatoNovoData = new SimpleDateFormat(novoFormato);
		java.util.Date data = formatoOriginalData.parse(dataString);
		String dataFormatada = formatoNovoData.format(data);
		return dataFormatada;
	}
	
	public String plusDias(String dataPedido, Long tempo) {
		LocalDate data = LocalDate.parse(dataPedido, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		data = data.plusDays(tempo);
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public String json(String string) {
		Gson gson = new Gson();
		String json = gson.toJson(string);
		return json;
	}
}



