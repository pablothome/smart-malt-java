package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma compra realizada no sistema. Contém o fornecedor e a lista de
 * itens comprados.
 */
public class Compra {

	private int idFornecedor;
	private List<ItemCompra> itens;

	public Compra() {
		this.itens = new ArrayList<>();
	}

	public void adicionarItem(ItemCompra item) {
		this.itens.add(item);
	}

	public double getTotal() {
		double total = 0;

		for (ItemCompra item : itens) {
			total += item.getTotal();
		}

		return total;
	}

	public int getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public List<ItemCompra> getItens() {
		return itens;
	}

	public void setItens(List<ItemCompra> itens) {
		this.itens = itens;
	}
}