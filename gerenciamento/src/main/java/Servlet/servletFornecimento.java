package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelData;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import DAO.DaoGenerico;
import DAO.daoEntradasRelatorio;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.daoVendasRelatorio;

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
		
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("incluirPedido")) {

			String fornecimento_pai_id = request.getParameter("fornecimento_pai_id");
			String quantidade = request.getParameter("quantidade");
			String dataPedido = request.getParameter("dataPedido");
			Long idProduto = Long.parseLong(request.getParameter("idProduto"));
			Long fornecimento_pai_id_int = Long.parseLong(fornecimento_pai_id);

			try {
				dataPedido = dataPedido.replaceAll("\\/", "-");
				
				ModelFornecimento fornecedor = daofornecimento.consultaFornecedor(fornecimento_pai_id_int, idProduto, super.getUsuarioLogado(request).getId());
				
				LocalDate data = LocalDate.parse(dataPedido, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				data = data.plusDays(fornecedor.getTempoentrega());
				
				ModelPedidos modelPedidos = new ModelPedidos();
				modelPedidos.setFornecedor_pai_id(fornecedor);
				modelPedidos.setQuantidade(Long.parseLong(dao.tirarPonto(quantidade)));
				modelPedidos.setDatapedido(dataPedido);
				modelPedidos.setDataentrega(data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				modelPedidos.setValor(fornecedor.getValor());
				modelPedidos.setNome(daoproduto.consultaProduto(idProduto, super.getUsuarioLogado(request).getId()).getNome());
				modelPedidos.setUsuario_pai_id(super.getUsuarioLogado(request));
				
				pedido.gravarPedido(modelPedidos);
				
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				String numero = "R$" + daoproduto.somaProdutos(this.getUsuarioLogado(request).getId()) + ",00";
				request.setAttribute("soma", numero);
				String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " LIMIT 10";
				List<ModelProdutos> produtos = daoproduto.listarProdutos(sql, super.getUsuarioLogado(request).getId());
				request.setAttribute("produtos", produtos);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("confirmarPedido")){
			Long id = Long.parseLong(request.getParameter("id"));
			String id_produto = request.getParameter("id_produto");
			String quantidade = request.getParameter("quantidade");
			String data = request.getParameter("data");
			
			try {
				ModelData modelData = new ModelData();
				modelData.setDatavenda(data);
				modelData.setUsuario_pai_id(super.getUsuarioLogado(request));
				
				String sql = "SELECT * FROM pedidos WHERE id = " + id;
				Long valortotal = daopedidos.listarPedidos(sql).get(0).getValorTotal();
				modelData.setValortotal(valortotal);
				
				boolean booleana = daoEntradaRelatorio.buscarData(modelData);
				
				if(booleana) {
					daoEntradaRelatorio.atualizarDataEValor(modelData);
				}else {
					daoEntradaRelatorio.inserirDataEValor(modelData);
				}
			} catch (SQLException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int status = 2;
			try {
				daoproduto.adicionaProdutoCaixa(Integer.parseInt(id_produto), Integer.parseInt(quantidade));
				daopedidos.mudarStatus(id, status);

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelarPedido")){
			Long id = Long.parseLong(request.getParameter("id"));
			try {
				int status = 1;
				daopedidos.mudarStatus(id, status);
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarFornecedor")){
			String id = request.getParameter("id");
			daofornecimento.excluirFornecedor(id);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String nomeFornecedor = request.getParameter("nomeFornecedor");
			String id = request.getParameter("id");
			String tempoentrega = request.getParameter("tempoentrega");
			String valor = request.getParameter("valor");
			Long tempoentregaLong = Long.parseLong(tempoentrega);
			
			valor = valor.replaceAll("\\.", "").replaceAll("\\,00", "");
			
	        String valor_R$ = valor.replace("R$", "");
	        valor_R$ = valor_R$.replaceAll("[^0-9]", "");

			Long valor_R$Long = Long.parseLong(valor_R$);
			ModelProdutos modelProdutos = new ModelProdutos();
			modelProdutos.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			
			daoFornecimento daoFornecimento = new daoFornecimento();
			
			daoFornecimento.gravarNovoFornecedor(nomeFornecedor, modelProdutos, tempoentregaLong, valor_R$Long);
			
			request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
			String numero = "R$" + daoproduto.somaProdutos(this.getUsuarioLogado(request).getId()) + ",00";
			request.setAttribute("soma", numero);
			String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " LIMIT 10";
			List<ModelProdutos> produtos = daoproduto.listarProdutos(sql, super.getUsuarioLogado(request).getId());
			request.setAttribute("produtos", produtos);
			
			RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
			despache.forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
