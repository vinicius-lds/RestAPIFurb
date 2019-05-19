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
        this.id = id;
        this.idUsuario = idUsuario;
        this.produtos = produtos;
        this.valorTotal = valorTotal;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getProdutos() {
        return produtos;
    }

    public void setProdutos(String produtos) {
        this.produtos = produtos;
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(int valorTotal) {
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
