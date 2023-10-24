package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.Despache;
import DAO.DaoGenerico;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import Servlet.API.Extends.APIProdutos;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFornecimento;
import model.ModelProdutos;
import model.ModelUsuarios;

/**
 * Servlet implementation class servlet_cadastro_e_atualizacao_produtos
 */
public class servlet_cadastro_e_atualizacao_produtos extends APIProdutos {
	private static final long serialVersionUID = 1L;

    public servlet_cadastro_e_atualizacao_produtos() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String acao = request.getParameter("acao");
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listar")) {
				listar(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")){
				paginar(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("excluir")){
				excluir(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("configuracoes")){
				configuracoes(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("historioPedidos")){
				historicoPedidos(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("exclusaoAjax")){
				exclusaoAjax(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cadastrarProduto")){
				cadastrarProduto(request, response);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	protected void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.setarAtributos(request, response);
	}
	
	protected void paginar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.setarAtributos(request, response);	
	}
	
	protected void excluir(HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.excluir(request);
		super.setarAtributos(request, response);
	}
	
	protected void configuracoes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = parametrosConfiguracoes(request);
		super.impressaoJSON(response, json);
	}

	protected void historicoPedidos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = parametrosHistoricoPedidos(request);
		super.impressaoJSON(response, json);
	}
	
	protected void exclusaoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = parametrosExclusaoAjax(request);
		super.impressaoJSON(response, json);
	}
	
	protected void cadastrarProduto(HttpServletRequest request, HttpServletResponse response) throws Exception {
		parametrosCadastrarProduto(request);
		super.setarAtributos(request, response);
	}
}
