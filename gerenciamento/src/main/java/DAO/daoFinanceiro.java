package DAO;

import java.sql.Connection;

import conexao.conexao;

public class daoFinanceiro {
private Connection connection;
	
	public daoFinanceiro(){
		connection = conexao.getConnection();
	}
	
	
}
