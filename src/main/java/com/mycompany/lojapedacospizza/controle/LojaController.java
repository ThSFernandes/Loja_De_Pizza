
package com.mycompany.lojapedacospizza.controle;

import com.mycompany.lojapedacospizza.core.*;
import com.mycompany.lojapedacospizza.objetos.*;
import com.mycompany.lojapedacospizza.view.*;
import java.util.List;


public class LojaController {
    private static LojaController lojaController;
    
    private LojaFXMLController lojaFXMLController;
    private LojaLogic lojaLogic;
    private Desenho desenho;
    private Mesa mesa;
    private GerenciadorClientes gerenciadorClientes;
    private Cozinheiro cozinheiro;
    
    public synchronized static LojaController getInstancia() {
        if(lojaController == null) {
            lojaController = new LojaController();
        }
        return lojaController;
    }
    
    private LojaController() {
    }
    
    public void adicionarClienteLogic(String nome) {
        gerenciadorClientes.adicionarClienteLogic(nome);
    }
    
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
    
    public void setDesenho(Desenho desenho) {
        this.desenho = desenho;
    }
    
    public void setFXMLController(LojaFXMLController lojaFXMLController) {
        this.lojaFXMLController = lojaFXMLController;
    }
    
    public void setLojaLogic(LojaLogic lojaLogic) {
        this.lojaLogic = lojaLogic;
    }
    
    public void setClienteAtual(String clienteAtual) {
        gerenciadorClientes.setClienteAtual(clienteAtual);
    }
    
    public void setGerenciadorClientes(GerenciadorClientes gerenciadorClientes) {
        this.gerenciadorClientes = gerenciadorClientes;
    }
    
    public void setCozinheiro(Cozinheiro cozinheiro) {
        this.cozinheiro = cozinheiro;
    }
    
    public void notificarCozinhando() {
        lojaFXMLController.notificarCozinhando();
    }
    
    public void solicitarMesa(String nome, int quantidadeSolicitada, String tipoPizza) {
        mesa.solicitarMesa(nome, quantidadeSolicitada, tipoPizza);
    }
    
    public void clienteSolicitarPizza(int quantidadeSolicitada, String tipoPizza) {
        ClienteLogic clienteLogic = gerenciadorClientes.getClienteLogicAtual();
        
        clienteLogic.clienteSolicitarPizza(quantidadeSolicitada, tipoPizza);
    }
    
    public void entregarPizza(String nome, String tipoPizza, int pedacos, int pedacosRestantes) {
        ClienteLogic clienteLogic = gerenciadorClientes.getClienteLogicPorNome(nome);
        clienteLogic.receberPizza(tipoPizza, pedacos);
        
        desenho.atualizarPizzas(tipoPizza, pedacosRestantes);
        desenho.desenharTela();
        
        lojaFXMLController.entregarPizza(tipoPizza, pedacos);
    }

    public void viewFechada() {
        mesa.viewFechada();
        gerenciadorClientes.pararClientes();
    }

    public void keyPressed(String keyCode) {
        gerenciadorClientes.getClienteLogicAtual().keyPressed(keyCode);
    }
    
    public void setHorario(int horas, int minutos) {
        lojaFXMLController.setHorario(horas, minutos);
    }

    public Cliente getCliente() {
        return gerenciadorClientes.getClienteAtual();
    }
    
    public List<Cliente> getClientes() {
        return gerenciadorClientes.getClientes();
    }

    public void desenharCliente(Cliente cliente) {
        desenho.desenharTela();
    }

    public void desenharCliente(String nome) {
        desenho.desenharTela();
    }

    public void desenharBalao(String texto) {
        desenho.desenharBalao(texto);
    }

    public void mouseClique(int x, int y) {
        lojaLogic.mouseClique(x, y);
    }
    
    public void desenharTela() {
        desenho.desenharTela();
    }

    public void habilitarPedir() {
        lojaFXMLController.habilitarPedir();
    }
    
    public boolean checarMostrarBalaoPedir() {
        return gerenciadorClientes.getClienteLogicAtual().checarMostrarPedir();
    }

    public int getCanvasAltura() {
        return desenho.getCanvasAltura();
    }
    
    public int getClienteAltura() {
        return desenho.getClienteAltura();
    }
    
    public Area getBalaoArea() {
        return desenho.getBalaoPedirArea();
    }

    public boolean nomeExiste(String nome) {
        return gerenciadorClientes.nomeExiste(nome);
    }
    
    public void desabilitarPedir() {
        lojaFXMLController.desabilitarPedir();
    }

    public void pararCozinheiro() {
        cozinheiro.parar();
    }

    public void cozinhar(Pizza pizzaSolicitada, int quantidadeCozinhar) {
        cozinheiro.cozinhar(pizzaSolicitada, quantidadeCozinhar);
    }

    public Area getBalaoComerArea() {
        return desenho.getBalaoComerArea();
    }

    public void comer() {
        gerenciadorClientes.getClienteLogicAtual().comer();
        desenho.desenharTela();
    }

    public boolean checarMostrarBalaoComer() {
        return gerenciadorClientes.getClienteLogicAtual().checarMostrarBalaoComer();
    }
}
