package br.com.furb.restapifurb.model.comanda;

import java.util.UUID;

public class ComandaDTO {

    private UUID id;
    private UUID idUsuario;
    // private List<ProdutoDTO> produtos;
    private String produtos;
    private int valorTotal;

    public ComandaDTO() {
    }

    public ComandaDTO(UUID id, UUID idUsuario, String produtos, int valorTotal) {
        this.setId(id);
        this.setIdUsuario(idUsuario);
        this.setProdutos(produtos);
        this.setValorTotal(valorTotal);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if(idUsuario == null){
            throw new IllegalArgumentException("ID inválido");
        }
        this.id = id;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        if(idUsuario == null){
            throw new IllegalArgumentException("ID Usuário inválido");
        }
        this.idUsuario = idUsuario;
    }

    public String getProdutos() {
        return produtos;
    }

    public void setProdutos(String produtos) {
        if(produtos == null || produtos.isEmpty()){
            throw new IllegalArgumentException("Produto inválido");
        }
        this.produtos = produtos;
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(int valorTotal) {
        if(valorTotal < 0){
            throw new IllegalArgumentException("Valor total inválido");
        }
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "ComandaDTO{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", produtos='" + produtos + '\'' +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
