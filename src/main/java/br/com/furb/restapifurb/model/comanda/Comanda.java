package br.com.furb.restapifurb.model.comanda;

import br.com.furb.restapifurb.model.usuario.Usuario;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "comandas")
public class Comanda {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    private Usuario usuario;
    // private List<Produto> produtos;
    @Column(nullable = false)
    private String produtos;
    private int valorTotal;

    public Comanda() {
    }

    public Comanda(Usuario usuario, String produtos, int valorTotal) {
        this.setProdutos(produtos);
        this.setUsuario(usuario);
        this.setValorTotal(valorTotal);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if(usuario == null){
            throw new IllegalArgumentException("Usuário inválido");
        }
        this.usuario = usuario;
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
}
