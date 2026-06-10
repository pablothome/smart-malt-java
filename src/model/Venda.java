package model;

import java.sql.Timestamp;

public class Venda {

	private int idVenda;
	private String cpfCliente;
	private int idProduto;
	private int quantidade;
	private double precoVenda;
	private double totalVenda;
	private Timestamp dataVenda;

	private String nomeCliente;
	private String nomeProduto;

	public Venda() {
	}

	public Venda(String cpfCliente, int idProduto, int quantidade, double precoVenda, double totalVenda) {
		this.cpfCliente = cpfCliente;
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.precoVenda = precoVenda;
		this.totalVenda = totalVenda;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public double getTotalVenda() {
		return totalVenda;
	}

	public void setTotalVenda(double totalVenda) {
		this.totalVenda = totalVenda;
	}

	public Timestamp getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Timestamp dataVenda) {
		this.dataVenda = dataVenda;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
}