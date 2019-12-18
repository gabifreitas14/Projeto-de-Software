import corejava.Console;
import excecao.*;
import modelo.*;
import servico.*;
import singleton.SingletonPerfis;
import util.Util;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;

public class Principal {
    @SuppressWarnings("resource")
    static
    ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

    public static int chamaPresidiario() throws PresidiarioNaoEncontradoException {
        PresidiarioAppService presidiarioAppService = (PresidiarioAppService) fabrica.getBean("presidiarioAppService");

        int opcaoPresi = 0;
        String nome;
        String dataEntrada;
        String dataSoltura;
        Presidiario novoPresidiario;
        do {
            System.out.println('\n' + "As seguintes opera��es podem ser feitas:");
            System.out.println('\n' + "1. Cadastrar um presidiario");
            System.out.println("2. Alterar um presidiario");
            System.out.println("3. Remover um presidiario");
            System.out.println("4. Listar todos os presidiarios");
            System.out.println("5. Voltar ao menu anterior");
            System.out.println("0. Sair");

            opcaoPresi = Console.readInt('\n' +
                    "Digite um n�mero entre 0 e 5:");

            switch (opcaoPresi) {
                case 1: {
                    nome = Console.readLine('\n' +
                            "Informe o nome do presidiario: ");
                    dataEntrada = Console.readLine(
                            "Informe a data de entrada: ");
                    dataSoltura = Console.readLine(
                            "Informe a data de sa�da: ");

                    novoPresidiario = new Presidiario(nome, Util.strToDate(dataEntrada), Util.strToDate(dataSoltura));

                    long numero = presidiarioAppService.inclui(novoPresidiario);

                    System.out.println('\n' + "Presidiario n�mero " +
                            numero + " inclu�do com sucesso!");

                    break;
                }

                case 2: {
                    int resposta = Console.readInt('\n' +
                            "Digite o n�mero do presidi�rio que voc� deseja alterar: ");

                    try {
                        novoPresidiario = presidiarioAppService.recuperaPresidiario(resposta);
                    } catch (PresidiarioNaoEncontradoException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' +
                            "N�mero = " + novoPresidiario.getId() +
                            "    Nome = " + novoPresidiario.getNome() +
                            "    Data de Entrada = " + novoPresidiario.getDataPrisao() +
                            "    Data de Sa�da = " + novoPresidiario.getDataSoltura() +
                            "    Vers�o = " + novoPresidiario.getVersao());

                    System.out.println('\n' + "O que voc� deseja alterar?");
                    System.out.println('\n' + "1. Nome");
                    System.out.println("2. Data de Entrada ");
                    System.out.println("3. Data de Sa�da");


                    int opcaoAlteracao = Console.readInt('\n' +
                            "Digite um n�mero de 1 a 3:");

                    switch (opcaoAlteracao) {
                        case 1:
                            String novoNome = Console.
                                    readLine("Digite o novo nome: ");

                            novoPresidiario.setNome(novoNome);

                            try {
                                presidiarioAppService.altera(novoPresidiario);

                                System.out.println('\n' +
                                        "Altera��o de nome efetuada com sucesso!");
                            } catch (PresidiarioNaoEncontradoException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;

                        case 2:
                            String novaDataEntrada = Console.
                                    readLine("Digite a data de entrada: ");

                            novoPresidiario.setDataPrisao(Util.strToDate(novaDataEntrada));

                            try {
                                presidiarioAppService.altera(novoPresidiario);

                                System.out.println('\n' +
                                        "Altera��o de data de entrada efetuada " +
                                        "com sucesso!");
                            } catch (PresidiarioNaoEncontradoException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;

                        case 3:
                            String novaDataSoltura = Console.
                                    readLine("Digite s data de soltura: ");

                            novoPresidiario.setDataSoltura(Util.strToDate(novaDataSoltura));

                            try {
                                presidiarioAppService.altera(novoPresidiario);

                                System.out.println('\n' +
                                        "Altera��o de data de soltura efetuada " +
                                        "com sucesso!");
                            } catch (PresidiarioNaoEncontradoException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;


                        default:
                            System.out.println('\n' + "Op��o inv�lida!");
                    }

                    break;
                }

                case 3: {
                    int resposta = Console.readInt('\n' +
                            "Digite o n�mero do presidiario que voc� deseja remover: ");

                    try {
                        novoPresidiario = presidiarioAppService.recuperaPresidiario(resposta);
                    } catch (PresidiarioNaoEncontradoException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' +
                            "N�mero = " + novoPresidiario.getId() +
                            "    Nome = " + novoPresidiario.getNome() +
                            "    Data Entrada = " + novoPresidiario.getDataPrisao() +
                            "    Data Sa�da = " + novoPresidiario.getDataSoltura() +
                            "    Vers�o = " + novoPresidiario.getVersao());

                    String resp = Console.readLine('\n' +
                            "Confirma a remo��o do presidiario?");

                    if (resp.equals("s")) {
                        try {
                            presidiarioAppService.exclui(novoPresidiario);
                            System.out.println('\n' +
                                    "Presidiario removido com sucesso!");
                        } catch (PresidiarioNaoEncontradoException e) {
                            System.out.println('\n' + e.getMessage());
                        }
                    } else {
                        System.out.println('\n' +
                                "Presidiario n�o removido.");
                    }

                    break;
                }

                case 4: {
                    List<Presidiario> presidiarios = presidiarioAppService.recuperaPresidiarios();

                    for (Presidiario presidiario : presidiarios) {
                        System.out.println('\n' +
                                "N�mero = " + presidiario.getId() +
                                "    Nome = " + presidiario.getNome() +
                                "    Data Entrada = " + presidiario.getDataPrisao() +
                                "    Data Sa�da = " + presidiario.getDataSoltura() +
                                "    Vers�o = " + presidiario.getVersao());
                    }

                    break;
                }

                case 0: {
                    return 0;
                }

                case 5:
                    return 5;

                default:
                    System.out.println('\n' + "Op��o inv�lida!");
            }
        } while (true);
    }

    public static int chamaAlocacao() throws AlocacaoNaoEncontradaException {
        AlocacaoAppService alocacaoAppService = (AlocacaoAppService) fabrica.getBean("alocacaoAppService");

        int opcaoAloc = 0;
        long idPresidiario;
        long idCela;
        String dataEntrada;
        String dataSaida;
        Alocacao novaAlocacao;
        Cela cela;
        CelaAppService celaAppService = (CelaAppService) fabrica.getBean("celaAppService");
        Presidiario presidiario;
        PresidiarioAppService presidiarioAppService = (PresidiarioAppService) fabrica.getBean("presidiarioAppService");
        do {
            System.out.println('\n' + "As seguintes opera��es podem ser feitas:");
            System.out.println('\n' + "1. Cadastrar uma aloca�ao");
            System.out.println("2. Alterar uma aloca�ao");
            System.out.println("3. Remover uma aloca�ao");
            System.out.println("4. Listar todos as aloca�oes");
            System.out.println("5. Voltar ao menu anterior");
            System.out.println("0. Sair");

            opcaoAloc = Console.readInt('\n' +
                    "Digite um n�mero entre 0 e 5:");

            switch (opcaoAloc) {
                case 1: {
                    dataEntrada = Console.readLine('\n' +
                            "Informe a data de entrada: ");
                    dataSaida = Console.readLine(
                            "Informe a data de sa�da: ");

                    idPresidiario = parseLong(Console.readLine(
                            "Informe o id do presidiario: "));


                    try {
                        presidiario = presidiarioAppService.recuperaPresidiario(idPresidiario);
                    } catch (PresidiarioNaoEncontradoException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    idCela = parseLong(Console.readLine(
                            "Informe o id da cela: "));

                    try {
                        cela = celaAppService.recuperaCela(idCela);
                    } catch (CelaNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    novaAlocacao = new Alocacao(Util.strToDate(dataEntrada), Util.strToDate(dataSaida), cela, presidiario);

                    long numero = alocacaoAppService.inclui(novaAlocacao);

                    System.out.println('\n' + "Aloca�ao n�mero " +
                            numero + " inclu�do com sucesso!");

                    break;
                }

                case 2: {
                    int resposta = Console.readInt('\n' +
                            "Digite o n�mero da aloca�ao que voc� deseja alterar: ");

                    try {
                        novaAlocacao = alocacaoAppService.recuperaAlocacao(resposta);
                    } catch (AlocacaoNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' +
                            "N�mero = " + novaAlocacao.getId() +
                            "    Data de Entrada = " + novaAlocacao.getDataEntrada() +
                            "    Data de Sa�da = " + novaAlocacao.getDataSaida() +
                            "    Id Presidi�rio = " + novaAlocacao.getPresidiario().getId() +
                            "    Id Cela = " + novaAlocacao.getCela().getId() +
                            "    Vers�o = " + novaAlocacao.getVersao());

                    System.out.println('\n' + "O que voc� deseja alterar?");
                    System.out.println("1. Data de Entrada ");
                    System.out.println("2. Data de Sa�da");
                    System.out.println("3. Id Presidi�rio");
                    System.out.println("4. Id Cela");


                    int opcaoAlteracao = Console.readInt('\n' +
                            "Digite um n�mero de 1 a 4:");

                    switch (opcaoAlteracao) {
                        case 1:
                            String novaDataEntrada = Console.
                                    readLine("Digite a data de entrada: ");

                            novaAlocacao.setDataEntrada(Util.strToDate(novaDataEntrada));

                            try {
                                alocacaoAppService.altera(novaAlocacao);

                                System.out.println('\n' +
                                        "Altera��o de data de entrada efetuada " +
                                        "com sucesso!");
                            } catch (AlocacaoNaoEncontradaException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;

                        case 2:
                            String novaDataSaida = Console.
                                    readLine("Digite s data de saida: ");

                            novaAlocacao.setDataSaida(Util.strToDate(novaDataSaida));

                            try {
                                alocacaoAppService.altera(novaAlocacao);

                                System.out.println('\n' +
                                        "Altera��o de data de soltura efetuada " +
                                        "com sucesso!");
                            } catch (AlocacaoNaoEncontradaException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;

                        case 3:
                            long idPres = parseLong(Console.
                                    readLine("Digite o id do presidi�rio: "));

                            try {
                                presidiario = presidiarioAppService.recuperaPresidiario(idPres);
                                novaAlocacao.setPresidiario(presidiario);
                            } catch (PresidiarioNaoEncontradoException e) {
                                System.out.println('\n' + e.getMessage());
                            }

                            try {
                                alocacaoAppService.altera(novaAlocacao);

                                System.out.println('\n' +
                                        "Altera��o do id presidi�rio efetuada " +
                                        "com sucesso!");
                            } catch (AlocacaoNaoEncontradaException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;
                        case 4:
                            long idCel = parseLong(Console.
                                    readLine("Digite o id da cela: "));

                            try {
                                cela = celaAppService.recuperaCela(idCel);
                                novaAlocacao.setCela(cela);
                            } catch (CelaNaoEncontradaException e) {
                                System.out.println('\n' + e.getMessage());
                            }

                            try {
                                alocacaoAppService.altera(novaAlocacao);

                                System.out.println('\n' +
                                        "Altera��o do id cela efetuada " +
                                        "com sucesso!");
                            } catch (AlocacaoNaoEncontradaException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;


                        default:
                            System.out.println('\n' + "Op��o inv�lida!");
                    }

                    break;
                }

                case 3: {
                    int resposta = Console.readInt('\n' +
                            "Digite o n�mero da aloca�ao que voc� deseja remover: ");

                    try {
                        novaAlocacao = alocacaoAppService.recuperaAlocacao(resposta);
                    } catch (AlocacaoNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' +
                            "N�mero = " + novaAlocacao.getId() +
                            "    Data Entrada = " + novaAlocacao.getDataEntrada() +
                            "    Data Sa�da = " + novaAlocacao.getDataSaida() +
                            "    Id presidi�rio = " + novaAlocacao.getPresidiario().getId() +
                            "    Id cela = " + novaAlocacao.getCela().getId() +
                            "    Vers�o = " + novaAlocacao.getVersao());

                    String resp = Console.readLine('\n' +
                            "Confirma a remo��o da aloca�ao? (S/N)");

                    if (resp.equals("s")) {
                        try {
                            alocacaoAppService.exclui(novaAlocacao);
                            System.out.println('\n' +
                                    "Aloca�ao removida com sucesso!");
                        } catch (AlocacaoNaoEncontradaException e) {
                            System.out.println('\n' + e.getMessage());
                        }
                    } else {
                        System.out.println('\n' +
                                "Aloca�ao n�o removida.");
                    }

                    break;
                }

                case 4: {
                    List<Alocacao> alocacoes = alocacaoAppService.recuperaAlocacoesERelacionados();

                    for (Alocacao alocacao : alocacoes) {
                        System.out.println('\n' +
                                "N�mero = " + alocacao.getId() +
                                "    Data Entrada = " + alocacao.getDataEntrada() +
                                "    Data Sa�da = " + alocacao.getDataSaida() +
                                "    Id presidi�rio = " + alocacao.getPresidiario().getId() +
                                "    Nome presidi�rio = " + alocacao.getPresidiario().getNome() +
                                "    Id cela = " + alocacao.getCela().getId() +
                                "    Vers�o = " + alocacao.getVersao());
                    }

                    break;
                }

                case 0: {
                    return 0;
                }

                case 5:
                    return 5;

                default:
                    System.out.println('\n' + "Op��o inv�lida!");
            }
        } while (true);
    }

    public static int chamaCela() throws CelaNaoEncontradaException {
        CelaAppService celaAppService = (CelaAppService) fabrica.getBean("celaAppService");

        PrisaoAppService prisaoAppService = (PrisaoAppService) fabrica.getBean("prisaoAppService");
        Prisao prisao;
        int opcaoCela = 0;
        int capacidade;
        String tipo;
        Cela novaCela;
        do {
            System.out.println('\n' + "As seguintes opera��es podem ser feitas:");
            System.out.println('\n' + "1. Cadastrar uma Cela");
            System.out.println("2. Alterar uma cela");
            System.out.println("3. Remover uma cela");
            System.out.println("4. Listar todas as celas");
            System.out.println("5. Voltar ao menu anterior");
            System.out.println("0. Sair");

            opcaoCela = Console.readInt('\n' +
                    "Digite um n�mero entre 0 e 5:");

            switch (opcaoCela) {
                case 1: {
                    capacidade = Integer.parseInt(Console.readLine('\n' +
                            "Informe a capacidade da cela: "));
                    tipo = Console.readLine(
                            "Informe o tipo de cela: ");

                    long idPrisao = parseLong(Console.readLine(
                            "Informe o id da pris�o: "));

                    try {
                        prisao = prisaoAppService.recuperaPrisao(idPrisao);
                    } catch (PrisaoNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    novaCela = new Cela(capacidade, tipo, prisao);

                    long numero = celaAppService.inclui(novaCela);

                    System.out.println('\n' + "Cela n�mero " +
                            numero + " inclu�do com sucesso!");

                    break;
                }

                case 2: {
                    int resposta = Console.readInt('\n' +
                            "Digite o n�mero da cela que voc� deseja alterar: ");

                    try {
                        novaCela = celaAppService.recuperaCela(resposta);
                    } catch (CelaNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' +
                            "N�mero = " + novaCela.getId() +
                            "    Capacidade = " + novaCela.getCapacidade() +
                            "    Tipo = " + novaCela.getTipo() +
                            "    Id pris�o = " + novaCela.getPrisao().getId() +
                            "    Vers�o = " + novaCela.getVersao());

                    System.out.println('\n' + "O que voc� deseja alterar?");
                    System.out.println('\n' + "1. Capacidade");
                    System.out.println('\n' + "2. Tipo ");
                    System.out.println('\n' + "3. Id pris�o ");

                    int opcaoAlteracao = Console.readInt('\n' +
                            "Digite um n�mero de 1 a 3:");

                    switch (opcaoAlteracao) {
                        case 1:
                            int novaCapacidade = Integer.parseInt(Console.
                                    readLine("Digite a nova capacidade: "));

                            novaCela.setCapacidade(novaCapacidade);

                            try {
                                celaAppService.altera(novaCela);

                                System.out.println('\n' +
                                        "Altera��o de nome efetuada com sucesso!");
                            } catch (CelaNaoEncontradaException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;

                        case 2:
                            String novoTipo = Console.
                                    readLine("Digite o novo tipo: ");

                            novaCela.setTipo(novoTipo);

                            try {
                                celaAppService.altera(novaCela);

                                System.out.println('\n' +
                                        "Altera��o do tipo feita " +
                                        "com sucesso!");
                            } catch (CelaNaoEncontradaException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;
                        case 3:
                            long novoIdPrisao = parseLong(Console.
                                    readLine("Digite o novo id pris�o: "));

                            try {
                                prisao = prisaoAppService.recuperaPrisao(novoIdPrisao);
                            } catch (PrisaoNaoEncontradaException e) {
                                System.out.println('\n' + e.getMessage());
                                break;
                            }
                            novaCela.setPrisao(prisao);

                            try {
                                celaAppService.altera(novaCela);

                                System.out.println('\n' +
                                        "Altera��o do id pris�o feita " +
                                        "com sucesso!");
                            } catch (CelaNaoEncontradaException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;

                        default:
                            System.out.println('\n' + "Op��o inv�lida!");
                    }

                    break;
                }

                case 3: {
                    int resposta = Console.readInt('\n' +
                            "Digite o n�mero da cela que voc� deseja remover: ");

                    try {
                        novaCela = celaAppService.recuperaCela(resposta);
                    } catch (CelaNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' +
                            "N�mero = " + novaCela.getId() +
                            "    Capacidade = " + novaCela.getCapacidade() +
                            "    Tipo = " + novaCela.getTipo() +
                            "    Id pris�o = " + novaCela.getPrisao().getId() +
                            "    Endere�o = " + novaCela.getPrisao().getEndereco() +
                            "    Vers�o = " + novaCela.getVersao());

                    String resp = Console.readLine('\n' +
                            "Confirma a remo��o da cela? (S/N) ");

                    if (resp.equals("s")) {
                        try {
                            celaAppService.exclui(novaCela);
                            System.out.println('\n' +
                                    "Cela removida com sucesso!");
                        } catch (CelaNaoEncontradaException e) {
                            System.out.println('\n' + e.getMessage());
                        }
                    } else {
                        System.out.println('\n' +
                                "Cela n�o removida.");
                    }

                    break;
                }

                case 4: {
                    List<Cela> celas = celaAppService.recuperaCelas();

                    for (Cela cela : celas) {
                        System.out.println('\n' +
                                "N�mero = " + cela.getId() +
                                "    Capacidade = " + cela.getCapacidade() +
                                "    Tipo = " + cela.getTipo() +
                                "    Id pris�o = " + cela.getPrisao().getId() +
                                "    Endere�o = " + cela.getPrisao().getEndereco() +
                                "    Vers�o = " + cela.getVersao());
                    }

                    break;
                }

                case 0: {
                    return 0;
                }

                case 5:
                    return 5;

                default:
                    System.out.println('\n' + "Op��o inv�lida!");
            }
        } while (true);
    }

    public static int chamaPrisao() throws PrisaoNaoEncontradaException {
        PrisaoAppService prisaoAppService = (PrisaoAppService) fabrica.getBean("prisaoAppService");

        int opcaoPrisao = 0;
        int capacidade;
        String endereco;
        String nivelSeguranca;
        Prisao novaPrisao;
        do {
            System.out.println('\n' + "As seguintes opera��es podem ser feitas:");
            System.out.println('\n' + "1. Cadastrar uma Prisao");
            System.out.println("2. Alterar uma prisao");
            System.out.println("3. Remover uma prisao");
            System.out.println("4. Listar todas as prisoes");
            System.out.println("5. Voltar ao menu anterior");
            System.out.println("0. Sair");

            opcaoPrisao = Console.readInt('\n' +
                    "Digite um n�mero entre 0 e 5:");

            switch (opcaoPrisao) {
                case 1: {
                    capacidade = Integer.parseInt(Console.readLine('\n' +
                            "Informe a capacidade da prisao: "));
                    endereco = Console.readLine(
                            "Informe o endere�o da pris�o: ");
                    nivelSeguranca = Console.readLine(
                            "Informe o n�vel de seguran�a da pris�o: ");
                    novaPrisao = new Prisao(capacidade, endereco, nivelSeguranca);

                    long numero = prisaoAppService.inclui(novaPrisao);

                    System.out.println('\n' + "Pris�o n�mero " +
                            numero + " inclu�do com sucesso!");

                    break;
                }

                case 2: {
                    int resposta = Console.readInt('\n' +
                            "Digite o n�mero da prisao que voc� deseja alterar: ");

                    try {
                        novaPrisao = prisaoAppService.recuperaPrisao(resposta);
                    } catch (PrisaoNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' +
                            "N�mero = " + novaPrisao.getId() +
                            "    Capacidade = " + novaPrisao.getCapacidade() +
                            "    Endere�o = " + novaPrisao.getEndereco() +
                            "    N�vel de Seguran�a = " + novaPrisao.getNivelSeguranca() +
                            "    Vers�o = " + novaPrisao.getVersao());

                    System.out.println('\n' + "O que voc� deseja alterar?");
                    System.out.println('\n' + "1. Capacidade");
                    System.out.println("2. Endere�o ");
                    System.out.println("3. N�vel de Seguran�a ");


                    int opcaoAlteracao = Console.readInt('\n' +
                            "Digite um n�mero de 1 a 3:");

                    switch (opcaoAlteracao) {
                        case 1:
                            int novaCapacidade = Integer.parseInt(Console.
                                    readLine("Digite a nova capacidade: "));

                            novaPrisao.setCapacidade(novaCapacidade);

                            try {
                                prisaoAppService.altera(novaPrisao);

                                System.out.println('\n' +
                                        "Altera��o de nome efetuada com sucesso!");
                            } catch (PrisaoNaoEncontradaException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;

                        case 2:
                            String novoEndereco = Console.
                                    readLine("Digite o novo endere�o: ");

                            novaPrisao.setEndereco(novoEndereco);

                            try {
                                prisaoAppService.altera(novaPrisao);

                                System.out.println('\n' +
                                        "Altera��o do endere�o feita " +
                                        "com sucesso!");
                            } catch (PrisaoNaoEncontradaException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;

                        case 3:
                            String novoNivel = Console.
                                    readLine("Digite o novo n�vel de seguran�a: ");

                            novaPrisao.setNivelSeguranca(novoNivel);

                            try {
                                prisaoAppService.altera(novaPrisao);

                                System.out.println('\n' +
                                        "Altera��o do n�vel de seguran�a feita " +
                                        "com sucesso!");
                            } catch (PrisaoNaoEncontradaException e) {
                                System.out.println('\n' + e.getMessage());
                            } catch (EstadoDeObjetoObsoletoException e) {
                                System.out.println('\n' + "A opera��o n�o foi " +
                                        "efetuada: os dados que voc� " +
                                        "tentou salvar foram modificados " +
                                        "por outro usu�rio.");
                            }

                            break;

                        default:
                            System.out.println('\n' + "Op��o inv�lida!");
                    }

                    break;
                }

                case 3: {
                    int resposta = Console.readInt('\n' +
                            "Digite o n�mero da prisao que voc� deseja remover: ");

                    try {
                        novaPrisao = prisaoAppService.recuperaPrisao(resposta);
                    } catch (PrisaoNaoEncontradaException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' +
                            "N�mero = " + novaPrisao.getId() +
                            "    Capacidade = " + novaPrisao.getCapacidade() +
                            "    Endere�o = " + novaPrisao.getEndereco() +
                            "    N�vel de Seguran�a = " + novaPrisao.getNivelSeguranca() +
                            "    Vers�o = " + novaPrisao.getVersao());

                    String resp = Console.readLine('\n' +
                            "Confirma a remo��o da prisao? (S/N) ");

                    if (resp.equals("s")) {
                        try {
                            prisaoAppService.exclui(novaPrisao);
                            System.out.println('\n' +
                                    "Prisao removida com sucesso!");
                        } catch (PrisaoNaoEncontradaException e) {
                            System.out.println('\n' + e.getMessage());
                        }
                    } else {
                        System.out.println('\n' +
                                "Pris�o n�o removido.");
                    }

                    break;
                }

                case 4: {
                    List<Prisao> prisoes = prisaoAppService.recuperaPrisoes();

                    for (Prisao prisao : prisoes) {
                        System.out.println('\n' +
                                "N�mero = " + prisao.getId() +
                                "    Capacidade = " + prisao.getCapacidade() +
                                "    Endere�o = " + prisao.getEndereco() +
                                "    N�vel de Seguran�a = " + prisao.getNivelSeguranca() +
                                "    Vers�o = " + prisao.getVersao());
                    }

                    break;
                }

                case 0: {
                    return 0;
                }

                case 5:
                    return 5;

                default:
                    System.out.println('\n' + "Op��o inv�lida!");
            }
        } while (true);
    }

    public static void main(String[] args) throws PresidiarioNaoEncontradoException, AlocacaoNaoEncontradaException, CelaNaoEncontradaException, PrisaoNaoEncontradaException {

        LoginAppService loginAppService = (LoginAppService) fabrica.getBean("loginAppService");
        Usuario usuario;

        String conta = "";
        String senha = "";
        boolean logou = false;

        conta = Console.readLine("Informe seu login: ");
        senha = Console.readLine("Informe sua senha: ");
        usuario = loginAppService.logar(conta, senha);
        if (usuario != null) {
            System.out.println("Logado com sucesso.");
            logou = true;
        }
        while (!logou) {
            System.out.println("Ocorreu um erro ao fazer login, dados incorretos");

            conta = Console.readLine("Informe seu login: ");
            senha = Console.readLine("Informe sua senha: ");
            try {
                usuario = loginAppService.logar(conta, senha);
            } catch (Throwable e) {
                System.out.println('\n' + e.getMessage());
                break;
            }
            if (usuario != null) {
                System.out.println("Login efetuado com sucesso");
                logou = true;
            }
        }
        SingletonPerfis singletonPerfis = SingletonPerfis.getSingletonPerfis();

        String[] listaPerfis = new String[0];
        List<Perfil> perfis = new ArrayList<Perfil>();
        if (usuario != null) {
            perfis = usuario.getPerfis();
            listaPerfis = new String[perfis.size()];
        }
        int count = 0;
        for (Perfil p : perfis) {
            listaPerfis[count] = p.getPerfil();
            count ++;
        }
        singletonPerfis.setPerfis(listaPerfis);


        int opcao = 0;
        int n = 0;
        do {
            System.out.println('\n' + "Bem vindo ao Prison System!");
            System.out.println("Nosso sistema est� passando por algumas mudan�as, por isso, talvez, alguns recursos estejam indispon�veis.");
            System.out.println('\n' + "Escolha uma das op��es abaixo");
            System.out.println('\n' + "1. Presidi�rio");
            System.out.println("2. Aloca��o");
            System.out.println("3. Cela");
            System.out.println("4. Pris�o");
            System.out.println("0. Sair");

            opcao = Console.readInt('\n' +
                    "Digite um n�mero entre 0 e 4:");

            switch (opcao) {
                case 1: {
                    n = chamaPresidiario();
                    break;
                }
                case 2: {
                    n = chamaAlocacao();
                    break;

                }
                case 3: {
                    n = chamaCela();
                    break;
                }
                case 4: {
                    n = chamaPrisao();
                    break;
                }
                case 0:
                    break;

            }

        } while (opcao != 0 && n != 0);
    }
}

