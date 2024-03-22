
package com.mycompany.lojapedacospizza.objetos;


public abstract class Pizza {
    protected int pedacosRestantes;
    protected String tipo;
    protected final Object lock = new Object();
    
    public Pizza(int pedacosRestantes) {
        this.pedacosRestantes = pedacosRestantes;
    }
    
    public String getTipo() {
        return tipo;
    }
    public int getPedacosRestantes() {
        return pedacosRestantes;
    }
    public void adicionarPedacos(int pedacos) {
        pedacosRestantes += pedacos;
    }
    
    public void removerPedacos(int pedacos) {
        pedacosRestantes -= pedacos;
    }
}