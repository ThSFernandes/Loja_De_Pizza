
package com.mycompany.lojapedacospizza.controle;

import com.mycompany.lojapedacospizza.core.ClienteLogic;
import com.mycompany.lojapedacospizza.objetos.Cliente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GerenciadorClientes {
    
    
    private HashMap<String, ClienteLogic> clientesPorNome = new HashMap<>();
    private List<ClienteLogic> clientesLogic = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    
    private String clienteAtual;
    
    
    public void adicionarClienteLogic(String nome) {
        if(clientesLogic.isEmpty()) {
            clienteAtual = nome;
        }
        
        int y = (clientesLogic.size() + 2) * 30;
        ClienteLogic clienteLogic = new ClienteLogic(nome, 30, y);
        clienteLogic.start();
        clientes.add(clienteLogic.getCliente());
        clientesLogic.add(clienteLogic);
        clientesPorNome.put(nome, clienteLogic);
    }
    
    public void setClienteAtual(String clienteAtual) {
        this.clienteAtual = clienteAtual;
    }
    
    public ClienteLogic getClienteLogicAtual() {
        return clientesPorNome.get(clienteAtual);
    }
    
    public ClienteLogic getClienteLogicPorNome(String nome) {
        return clientesPorNome.get(nome);
    }
    
    public Cliente getClienteAtual() {
        return clientesPorNome.get(clienteAtual).getCliente();
    }
    
    public List<Cliente> getClientes() {
        return clientes;
    }
    
    public boolean nomeExiste(String nome) {
        return clientesPorNome.containsKey(nome);
    }
    
    public void pararClientes() {
        for(ClienteLogic clienteLogic: clientesLogic) {
            clienteLogic.parar();
        }
    }
}
