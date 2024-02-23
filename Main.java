package com.example;

import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmpregadoDAO dao = new EmpregadoDAO();
//        Scanner input = new Scanner(System.in);

        int escolha;

        do {
            escolha = menu();

            switch(escolha) {

                case 0 -> {
                    System.out.println("Finalizando programa");
                }

                case 1 -> {
                    if(inserirEmpregado()) {
                        System.out.println("\nInserido com sucesso!\n");
                    } else {
                        System.out.println("\nNão foi possível inserir o empregado\n");
                    }
                }

                case 2 -> {
                    if (alterarNome()) {
                        System.out.println("\nNome alterado com sucesso!\n");
                    } else {
                        System.out.println("\nNão foi possível alterar o nome do empregado\n");
                    }
                }

                case 3 -> {
                    if(alterarSalario()) {
                        System.out.println("\nSalário alterado com sucesso!\n");
                    } else {
                        System.out.println("\nNão foi possível alterar o salário\n");
                    }

                }

                case 4 -> {
                    if (excluirEmpregado()) {
                        System.out.println("\nEmpregado excluído com sucesso!\n");
                    } else {
                        System.out.println("\nNão foi possível excluir esse empregado!\n");
                    }
                }

                case 5 -> {
                    List<Empregado> resultEmpregados = dao.buscarEmpregado();

                    if (resultEmpregados != null) {
                        System.out.print("\n");
                        resultEmpregados.forEach(System.out::println);
                        System.out.print("\n");
                    } else {
                        System.out.println("\nNão há registros na tabela de empregados!\n");
                    }
                }

                case 6 -> {
                     Empregado empregado = buscarEmpregado();

                    if(empregado != null) {
                        System.out.println("\n" + empregado + "\n");
                    } else {
                        System.out.println("\nNão foi encontrado nenhum empregado com esse ID!\n");
                    }
                }

                case 7 -> {
                     List<Empregado> resultEmpregados = buscarEmpregadoPorTrabalho();
                    if (resultEmpregados != null)  {
                        System.out.println("\n");
                        resultEmpregados.forEach(System.out::println);
                        System.out.println("\n");
                    } else {
                        System.out.println("\nNão há empregados com esse trabalho!\n");
                    }
                }
                default -> System.out.println("\nNão há essa opção!\n");
            }
        } while (escolha != 0);
    }

    public static int menu() {
        Scanner input = new Scanner(System.in);

        System.out.println("" +
                "[1] - Inserir Empregado \n" +
                "[2] - Alterar nome\n" +
                "[3] - Alterar salário\n" +
                "[4] - Remover Empregado\n" +
                "[5] - Retornar todos Empregados\n" +
                "[6] - Buscar por número\n" +
                "[7] - Buscar por trabalho\n" +
                "[0] - Sair\n" +
                ""
        );


        System.out.print("Escolha: ");

        int escolha = input.nextInt();

        return escolha;
    }
    public static boolean inserirEmpregado() {
        Scanner input = new Scanner(System.in);
        EmpregadoDAO dao = new EmpregadoDAO();
        boolean verifResp = false;

//        Integer numMgr;
        int numEmp, numMgr;
        String nameEmp, jobEmp;
        float salEmpFormatted = 0;


        do {
            // verificação ID
            do {
                try {
                    System.out.print("Número empregado: ");
                    numEmp = input.nextInt();

                    if (numEmp >= 1000 && numEmp <= 9999) {
                        verifResp = true;
                    } else {
                        System.out.println("\u001B[31m\nDigite um número positivo com 4 caracteres!\u001B[0m\n");
                        verifResp = false;
                    }

                } catch (InputMismatchException ex) {
                    System.out.println("\u001B[31m\nDigite um caracter válido!\u001B[0m\n");
                    input.next();
                    verifResp = false;
                }
            } while (!verifResp);

            // verificação NOME
            do {
                System.out.print("Digite o nome do empregado: ");
                nameEmp = input.next().toUpperCase();

                if (nameEmp.length() <= 9) {
                    verifResp = true;
                } else {
                    System.out.println("\u001B[31m\nNome não pode ser maior que 9 caracteres!\u001B[0m\n");
                    verifResp = false;
                }

            } while (!verifResp);

            // verificação JOB
            do {
                System.out.print("Trabalho empregado: ");
                jobEmp = input.next().toUpperCase();

                if (jobEmp.length() <= 9) {
                    verifResp = true;
                } else {
                    System.out.println("\u001B[31m\nJob não pode ser maior que 9 caracteres!\u001B[0m\n");
                    verifResp = false;
                }

            } while (!verifResp);

            // verificação MANAGER (MGR)
            do {
                try {
                    System.out.print("MGR empregado: ");
                    numMgr = input.nextInt();

                    if (numMgr >= 1000 && numMgr <= 9999) {
                        verifResp = true;
                    } else {
                        System.out.println("\u001B[31m\nDigite um número positivo com 4 caracteres!\u001B[0m\n");
                        verifResp = false;
                    }

                } catch (InputMismatchException ex) {
                    System.out.println("\u001B[31m\nDigite um caracter válido!\u001B[0m\n");
                    input.next();
                    verifResp = false;
                }
            } while (!verifResp);

            // Verificação SALARIO
            do {
                try {
                    System.out.print("Salario empregado: ");
                    float salEmp = input.nextFloat();

                    System.out.println(salEmp);

                    DecimalFormat df = new DecimalFormat("#.##");
                    salEmpFormatted = Float.parseFloat(df.format(salEmp));

                    if (salEmpFormatted >= 0 && salEmpFormatted <= 9999999) { // Alterado o valor de 9000 de acordo com seu comentário
                        verifResp = true;
                    } else {
                        System.out.println("\u001B[31m\nO salário deve ser maior ou igual a zero e menor que 9000!\u001B[0m\n");
                        verifResp = false;
                    }

                } catch (InputMismatchException ex) {
                    System.out.println("\u001B[31m\nDigite um caracter válido!\u001B[0m\n");
                    input.next();
                    verifResp = false;
                } catch (NumberFormatException ex) {
                    System.out.println("\u001B[31m\nDigite um número válido!\u001B[0m\n");
                    input.next();
                    verifResp = false;
                }
            } while (!verifResp);

            // Verificação EMPREGADO
            System.out.println(salEmpFormatted);

//                System.out.print("Comissão empregado: ");
//                Float comEmp = input.nextFloat();
//
//                System.out.print("Numero departamento: ");
//                Integer numDept = input.nextInt();
//
//                // configuração de data
//                LocalDate agora = LocalDate.now();
////            DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("uuuu-MM-dd");
////            String dataFormatada = formatterData.format(agora);
//
//                Empregado empregado = new Empregado(numEmp, nameEmp, jobEmp, numMgr, Date.valueOf(agora), salEmp, comEmp, numDept);
//
//                return dao.inserirEmpregado(empregado);
        } while(!verifResp);

        return true;
    }

    public static boolean alterarNome() {
        Scanner input = new Scanner(System.in);
        EmpregadoDAO dao = new EmpregadoDAO();

        try {
            System.out.print("ID empregado: ");
            Integer numEmp = input.nextInt();

            System.out.print("Qual o novo nome do empregado: ");
            String nomeEmp = input.next().toUpperCase();

            return dao.alterarNome(numEmp, nomeEmp);
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getMessage());
            return false;
        } finally {
            input.close();
        }

    }

    private static boolean alterarSalario() {
        Scanner input = new Scanner(System.in);
        EmpregadoDAO dao = new EmpregadoDAO();

        try {
            System.out.print("ID empregado: ");
            Integer numEmp = input.nextInt();

            System.out.print("Qual o novo salário do empregado: ");
            float salEmp = input.nextInt();

            return dao.alterarSalario(numEmp, salEmp);
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getMessage());
            return false;
        } finally {
            input.close();
        }

    }

    private static boolean excluirEmpregado() {
        Scanner input = new Scanner(System.in);
        EmpregadoDAO dao = new EmpregadoDAO();

        try {
            System.out.print("ID empregado: ");
            Integer numEmp = input.nextInt();

            return dao.excluirEmpregado(numEmp);
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getMessage());
            return false;
        } finally {
            input.close();
        }

    }

    private static Empregado buscarEmpregado() {
        Scanner input = new Scanner(System.in);
        EmpregadoDAO dao = new EmpregadoDAO();

        try {
            System.out.print("ID empregado: ");
            Integer numEmp = input.nextInt();

            return dao.buscarEmpregado(numEmp);
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getMessage());
            return null;
        } finally {
            input.close();
        }

    }

    private static List<Empregado> buscarEmpregadoPorTrabalho() {
        Scanner input = new Scanner(System.in);
        EmpregadoDAO dao = new EmpregadoDAO();

        try {
            System.out.print("Trabalho empregado: ");
            String jobEmp = input.next().toUpperCase();

            return dao.buscarPorTrabalho(jobEmp);
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getMessage());
            return null;
        } finally {
            input.close();
        }
    }
}
