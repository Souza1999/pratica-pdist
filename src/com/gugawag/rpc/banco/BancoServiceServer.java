package com.gugawag.rpc.banco;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class BancoServiceServer extends UnicastRemoteObject implements BancoServiceIF {

    private List<Conta> contas;

    public BancoServiceServer() throws RemoteException {
        contas = new ArrayList<>();
        // Inicializando com contas padrão
        contas.add(new Conta("1", 100.0));
        contas.add(new Conta("2", 156.0));
        contas.add(new Conta("3", 950.0));
    }

    @Override
    public double saldo(String conta) throws RemoteException {
        for (Conta c : contas) {
            if (c.getNumero().equals(conta)) {
                return c.getSaldo();
            }
        }
        throw new RemoteException("Conta não encontrada!");
    }

    @Override
    public int quantidadeContas() throws RemoteException {
        return contas.size();
    }

    @Override
    public void adicionarConta(String numero, double saldoInicial) throws RemoteException {
        // Verificar se já existe uma conta com o número fornecido
        for (Conta c : contas) {
            if (c.getNumero().equals(numero)) {
                throw new RemoteException("Conta já existe!");
            }
        }
        contas.add(new Conta(numero, saldoInicial));
        System.out.println("Conta adicionada: " + numero + " com saldo inicial: " + saldoInicial);
    }

    @Override
    public String pesquisarConta(String numero) throws RemoteException {
        for (Conta c : contas) {
            if (c.getNumero().equals(numero)) {
                return "Conta: " + c.getNumero() + ", Saldo: " + c.getSaldo();
            }
        }
        return "Conta não encontrada!";
    }

    @Override
    public boolean removerConta(String numero) throws RemoteException {
        for (Conta c : contas) {
            if (c.getNumero().equals(numero)) {
                contas.remove(c);
                System.out.println("Conta removida: " + numero);
                return true;
            }
        }
        return false;
    }
}
