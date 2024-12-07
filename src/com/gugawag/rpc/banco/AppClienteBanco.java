package com.gugawag.rpc.banco;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AppClienteBanco {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        // Obtém o IP do servidor a partir dos argumentos passados ao programa
        String servidor = args.length > 0 ? args[0] : "localhost";

        // Conecta ao registro RMI no servidor especificado
        Registry registry = LocateRegistry.getRegistry(servidor, 1099);
        BancoServiceIF banco = (BancoServiceIF) registry.lookup("BancoService");

        Scanner entrada = new Scanner(System.in);
        int opcao;

        do {
            menu();
            opcao = entrada.nextInt();

            switch (opcao) {
                case 1: { // Consultar saldo
                    System.out.println("Digite o número da conta:");
                    String conta = entrada.next();
                    try {
                        System.out.println("Saldo: " + banco.saldo(conta));
                    } catch (RemoteException e) {
                        System.out.println("Erro ao consultar saldo: " + e.getMessage());
                    }
                    break;
                }
                case 2: { // Quantidade de contas
                    try {
                        System.out.println("Quantidade de contas: " + banco.quantidadeContas());
                    } catch (RemoteException e) {
                        System.out.println("Erro ao consultar quantidade de contas: " + e.getMessage());
                    }
                    break;
                }
                case 3: { // Adicionar conta
                    System.out.println("Digite o número da nova conta:");
                    String numero = entrada.next();
                    System.out.println("Digite o saldo inicial:");
                    double saldoInicial = entrada.nextDouble();
                    try {
                        banco.adicionarConta(numero, saldoInicial);
                        System.out.println("Conta adicionada com sucesso!");
                    } catch (RemoteException e) {
                        System.out.println("Erro ao adicionar conta: " + e.getMessage());
                    }
                    break;
                }
                case 4: { // Pesquisar conta
                    System.out.println("Digite o número da conta para pesquisa:");
                    String numero = entrada.next();
                    try {
                        String resultado = banco.pesquisarConta(numero);
                        System.out.println("Resultado da pesquisa: " + resultado);
                    } catch (RemoteException e) {
                        System.out.println("Erro ao pesquisar conta: " + e.getMessage());
                    }
                    break;
                }
                case 5: { // Remover conta
                    System.out.println("Digite o número da conta a ser removida:");
                    String numero = entrada.next();
                    try {
                        boolean removido = banco.removerConta(numero);
                        if (removido) {
                            System.out.println("Conta removida com sucesso!");
                        } else {
                            System.out.println("Conta não encontrada!");
                        }
                    } catch (RemoteException e) {
                        System.out.println("Erro ao remover conta: " + e.getMessage());
                    }
                    break;
                }
                case 9: {
                    System.out.println("Saindo...");
                    break;
                }
                default: {
                    System.out.println("Opção inválida! Tente novamente.");
                }
            }
        } while (opcao != 9);

        entrada.close();
    }

    public static void menu() {
        System.out.println("\n=== BANCO RMI (ou FMI?!) ===");
        System.out.println("1 - Saldo da conta");
        System.out.println("2 - Quantidade de contas");
        System.out.println("3 - Adicionar conta");
        System.out.println("4 - Pesquisar conta");
        System.out.println("5 - Remover conta");
        System.out.println("9 - Sair");
    }
}
