
package com.mycompany.lojapedacospizza.core;

import com.mycompany.lojapedacospizza.controle.LojaController;
import com.mycompany.lojapedacospizza.objetos.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClienteLogic extends Thread {
    private final LojaController lojaController = LojaController.getInstancia();
    private final Cliente cliente;
    private boolean parar = false;
    
    
    public final Object lock = new Object();
    
    private final int limiteX = 240;
    private final int posCadeira = 90;
    private static final int UNIDADELARGURA = 30;
    
    private int quantidade;
    private String tipoPizza;

    
    public ClienteLogic(String nome, int x, int y) {
        cliente = new Cliente(nome, x, y);
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void parar() {
        parar = true;
        synchronized(lock) {
            lock.notify();
        }
    }
    
    public boolean checarMostrarPedir() {
        return (cliente.x == limiteX);
    }

    public boolean checarMostrarBalaoComer() {
        return cliente.x == posCadeira;
    }
    
    public void keyPressed(String keyCode) {
        
        moverSePuder(keyCode);
        
        lojaController.desenharCliente(cliente);
        
        if(cliente.x == limiteX) {
            lojaController.desenharBalao("Pedir");
        }
        else if(cliente.x == posCadeira) {
            lojaController.desenharBalao("Comer");
        }
    }
    
    public void moverSePuder(String keyCode) {
        if(keyCode.equals("RIGHT")) {
            if(cliente.x + UNIDADELARGURA <= limiteX) {
                cliente.x += UNIDADELARGURA;
            }
        }
        else {
            if(cliente.x - UNIDADELARGURA >= UNIDADELARGURA) {
                cliente.x -= UNIDADELARGURA;
            }
        }
    }
    
    public void clienteSolicitarPizza(int quantidade, String tipoPizza) {
        this.quantidade = quantidade;
        this.tipoPizza = tipoPizza;
        synchronized(lock) {
            lock.notify();
        }
    }
    
    public void receberPizza(String tipoPizza, int pedacos) {
        cliente.adicionarPizza(tipoPizza, pedacos);
    }
    
    @Override
    public void run() {
        
        while(true) {
            esperar();
            if(parar) {
                break;
            }
            try {
                Mesa.mesaEsperandoCozinhar.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(ClienteLogic.class.getName()).log(Level.SEVERE, null, ex);
            }

            Mesa.mesaEsperandoCozinhar.release();
            
            lojaController.solicitarMesa(cliente.nome, quantidade, tipoPizza);
        }
    }
    
    public void esperar() {
        synchronized(lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void comer() {
        cliente.comer();
    }
}
