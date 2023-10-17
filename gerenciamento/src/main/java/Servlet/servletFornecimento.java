package Servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import DAO.API;
import DAO.DaoGenerico;
import DAO.daoEntradasRelatorio;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelData;
import model.ModelFornecimento;
import model.ModelParametros;
import model.ModelPedidos;
import model.ModelProdutos;
import model.ModelUsuarios;

/**
 * Servlet implementation class servletFornecimento
 */
public class servletFornecimento extends servlet_recuperacao_login {
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoFornecimento daofornecimento = new daoFornecimento();
	daoProdutos daoproduto = new daoProdutos();
	daoPedidos pedido = new daoPedidos();
	daoPedidos daopedidos = new daoPedidos();
	daoEntradasRelatorio daoEntradaRelatorio = new daoEntradasRelatorio();
	DaoGenerico dao = new DaoGenerico();
	SQLProdutos sqlprodutos = new SQLProdutos();
	SQLPedidos sqlpedidos = new SQLPedidos();
	API api = new API();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletFornecimento() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String acao = request.getParameter("acao");
		try {
			int id = super.getUsuarioLogado(request).getId();
			ModelUsuarios usuario = super.getUsuarioLogado(request);
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("incluirPedido")) {
				
				ModelParametros parametros = api.parametrosIncluirPedido(request);
				ModelFornecimento fornecedor = new ModelFornecimento().setFornecedor(parametros, id, usuario);
				ModelPedidos modelPedidos = new ModelPedidos();

				pedido.gravarPedido(modelPedidos.setPedido(fornecedor, parametros, usuario, modelPedidos));
				api.setarAtributos(request, usuario);
					
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("confirmarPedido")){
				String quantidade = request.getParameter("quantidade");
				String data = request.getParameter("data");
				int status = 2;
				
				ModelData modelData = new ModelData();
				modelData.setDatavenda(data);
				modelData.setUsuario_pai_id(super.getUsuarioLogado(request));

				modelData.setValortotal(daopedidos.listarPedidos(sqlpedidos.procuraPedido(Long.parseLong(request.getParameter("id")))).get(0).getValorTotal());
				boolean booleana = daoEntradaRelatorio.buscarData(modelData);

				if (booleana) {
					daoEntradaRelatorio.atualizarDataEValor(modelData);
				} else {
					daoEntradaRelatorio.inserirDataEValor(modelData);
				}

				daoproduto.adicionaProdutoCaixa(Long.parseLong(request.getParameter("id_produto")), Integer.parseInt(quantidade));
				daopedidos.mudarStatus(Long.parseLong(request.getParameter("id")), status);
					
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelarPedido")){
				Long id_pedido = Long.parseLong(request.getParameter("id"));
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarFornecedor")){
				daofornecimento.excluirFornecedor(Long.parseLong(request.getParameter("id")));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String nomeFornecedor = request.getParameter("nomeFornecedor");
			String tempoentrega = request.getParameter("tempoentrega");
			
			ModelProdutos modelProdutos = new ModelProdutos();
			modelProdutos.setId(request.getParameter("id") != null && !request.getParameter("id").isEmpty() ? Long.parseLong(request.getParameter("id")) : null);
			
			new daoFornecimento().gravarNovoFornecedor(nomeFornecedor, modelProdutos, Integer.parseInt(tempoentrega), dao.converterDinheiroInteger(request.getParameter("valor")));
			
			api.setarAtributos(request, super.getUsuarioLogado(request));
			request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
