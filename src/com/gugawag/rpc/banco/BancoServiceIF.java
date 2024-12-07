package com.gugawag.rpc.banco;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BancoServiceIF extends Remote {

    double saldo(String conta) throws RemoteException;

    int quantidadeContas() throws RemoteException;

    // Adicionar nova conta
    void adicionarConta(String numero, double saldoInicial) throws RemoteException;

    // Pesquisar conta por número
    String pesquisarConta(String numero) throws RemoteException;

    // Remover conta por número
    boolean removerConta(String numero) throws RemoteException;
}
