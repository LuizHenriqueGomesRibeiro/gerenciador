package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoProdutos;

/**
 * Servlet implementation class servletFornecimento
 */
public class servletFornecimento extends servlet_recuperacao_login {
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoFornecimento daofornecimento = new daoFornecimento();
	daoProdutos daoproduto = new daoProdutos();
       
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
			String idProduto = request.getParameter("idProduto");
			int idProdutoLong = Integer.parseInt(idProduto);
			int fornecimento_pai_id_int = Integer.parseInt(fornecimento_pai_id);

			try {
				
				dataPedido = dataPedido.replaceAll("\\/", "-");
				
				ModelFornecimento fornecedor = daofornecimento.consultaFornecedor(fornecimento_pai_id_int, idProdutoLong, super.getUsuarioLogado(request).getId());
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate data = LocalDate.parse(dataPedido, formatter);
				
				long quantidadeDiasAdicionais = fornecedor.getTempoentrega();
				data = data.plusDays(quantidadeDiasAdicionais);

				String dataEntrega = data.format(formatter);
				dataEntrega = dataEntrega.replaceAll("\\-", "/");
				System.out.println(dataEntrega);
				
				quantidade = quantidade.replaceAll("\\.", "");
				Long quantidade_int = Long.parseLong(quantidade);
				
				ModelFornecimento modelFornecimento = daofornecimento.consultaFornecedor(fornecimento_pai_id_int, idProdutoLong, super.getUsuarioLogado(request).getId());
				ModelPedidos modelPedidos = new ModelPedidos();
				modelPedidos.setFornecedor_pai_id(modelFornecimento);
				modelPedidos.setQuantidade(quantidade_int);
				modelPedidos.setDataPedido(dataPedido);
				modelPedidos.setDataEntrega(dataEntrega);
				
				
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				String numero = "R$" + daoproduto.somaProdutos(this.getUsuarioLogado(request).getId()) + ",00";
				request.setAttribute("soma", numero);
				
				List<ModelProdutos> produtos = daoproduto.listarProdutos(super.getUsuarioLogado(request).getId());
				request.setAttribute("produtos", produtos);
				
				RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
				despache.forward(request, response);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			System.out.println(valor_R$Long);
			ModelProdutos modelProdutos = new ModelProdutos();
			modelProdutos.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			
			daoFornecimento daoFornecimento = new daoFornecimento();
			
			daoFornecimento.gravarNovoFornecedor(nomeFornecedor, modelProdutos, tempoentregaLong, valor_R$Long);
			
			request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
			String numero = "R$" + daoproduto.somaProdutos(this.getUsuarioLogado(request).getId()) + ",00";
			request.setAttribute("soma", numero);
	
			List<ModelProdutos> produtos = daoproduto.listarProdutos(super.getUsuarioLogado(request).getId());
			request.setAttribute("produtos", produtos);
			
			RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
			despache.forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
