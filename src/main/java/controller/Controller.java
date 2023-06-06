/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.github.britooo.looca.api.util.Conversor;
import java.io.IOException;
import service.ConexaoBancoLocal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.UsuarioModel;
import model.LeituraModel;
import model.LeituraUsuario;
import model.MaquinaUnidade;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import service.ConexaoBancoNuvem;
import slack.Slack;

/**
 *
 * @author BELLA
 */
public class Controller {

    //Instanciando conexao Banco Local - Mysql
    ConexaoBancoLocal connectionLocal = new ConexaoBancoLocal();
    JdbcTemplate conLocal = connectionLocal.getConnection();

    //Instanciando conexao Banco Nuvem - Azure
    ConexaoBancoNuvem connectionNuvem = new ConexaoBancoNuvem();
    JdbcTemplate conNuvem = connectionNuvem.getConnection();

    //Instanciando Looca + Classes monitoradas
    Looca looca = new Looca();

    //Instanciando Model de leitura - dados que vêm do looca
    LeituraModel leituraModel = new LeituraModel();

    //Grupo de discos
    List<Volume> listaDiscos = new ArrayList(looca.getGrupoDeDiscos().getVolumes());

    //Grupo de redes internet e wi-fi
    List<RedeInterface> listaRedes = new ArrayList(looca.getRede().getGrupoDeInterfaces().getInterfaces());

    Conversor conversor = new Conversor();

    MaquinaUnidade maquinaUnidade = new MaquinaUnidade();

    Integer nSerie;
    Integer nSerieLocal;
    
    Slack slack = new Slack();

    //Select de dados do usuário - Login Local
    public List<UsuarioModel> selectDadosUsuarioLocal(String usuario, String senha) {

        List<UsuarioModel> listaUsuario = new ArrayList();

        listaUsuario = conLocal.query("SELECT * FROM tbUsuario WHERE nomeUsuario = ? AND senhaUsuario = ?",
                new BeanPropertyRowMapper(UsuarioModel.class), usuario, senha);

        return listaUsuario;
    }

    //Select de dados do usuário - Login Nuvem
    public List<UsuarioModel> selectDadosUsuarioNuvem(String usuario, String senha) {

        List<UsuarioModel> listaUsuarioNuvem = new ArrayList();

        listaUsuarioNuvem = conNuvem.query("SELECT * FROM tbUsuario WHERE nomeUsuario = ? AND senhaUsuario = ?",
                new BeanPropertyRowMapper(UsuarioModel.class), usuario, senha);

        return listaUsuarioNuvem;
    }

    /*-----------------------------------------------------------------------------------*/
    //Leituras do usuário - local
    public List<LeituraUsuario> selectLeituraUsuario(String usuario, String senha) {

        List<LeituraUsuario> listaLeituraUsuario = new ArrayList();

        listaLeituraUsuario = conLocal.query("select idLeitura , fkConfig, fkAlertaComponente , c.fkMaquina, fkComponente , nSerie ,  nomeUsuario from tbLeitura as l"
                + " join tbConfig as c on l.fkConfig = c.idConfig join tbMaquina as m on m.idMaquina = c.fkMaquina "
                + "join tbUsuario as u on u.fkMaquina = m.idMaquina where nomeUsuario = ? and senhaUsuario = ? order by idLeitura desc limit 1 ;",
                new BeanPropertyRowMapper(LeituraUsuario.class), usuario, senha);

        for (int i = 0; i < listaLeituraUsuario.size(); i++) {
            nSerieLocal = listaLeituraUsuario.get(i).getnSerie();
        }

        System.out.println("nSerieLocal = " + nSerieLocal);

        return listaLeituraUsuario;
    }

    //Leituras do usuário - nuvem
    public List<LeituraUsuario> selectLeituraUsuarioNuvem(String usuario, String senha) {

        List<LeituraUsuario> listaLeituraUsuarioNuvem = new ArrayList();

        listaLeituraUsuarioNuvem = conNuvem.query(
                "select top 1 idLeitura, fkConfig, fkAlertaComponente ,c.fkMaquina, fkComponente ,nSerie , nomeUsuario"
                + " from tbLeitura as l join tbConfig as c on l.fkConfig = c.idConfig join tbMaquina as m on m.idMaquina = c.fkMaquina join tbUsuario as u on u.fkMaquina = m.idMaquina where nomeUsuario = ? and senhaUsuario = ? order by idLeitura desc ;",
                new BeanPropertyRowMapper(LeituraUsuario.class), usuario, senha);

        for (int i = 0; i < listaLeituraUsuarioNuvem.size(); i++) {
            nSerie = listaLeituraUsuarioNuvem.get(i).getnSerie();
        }

        System.out.println("nSerie = " + nSerie);

        return listaLeituraUsuarioNuvem;
    }

    /*----------------------------------------------------------------------------*/
    //Inserção de leituras - Local
    public void insertTbLeituraLocal(Integer fkConfig, Integer fkAlertaComponente) {

        conLocal.update("insert into tbLeitura(idLeitura ,leitura, dataHoraLeitura , fkConfig, fkAlertaComponente values (? ,? , ?, ?, ?)",
                null, leituraModel.getLeitura(), leituraModel.getDataHoraLeitura(),
                fkConfig, fkAlertaComponente);
    }

    //Inserção de leituras - Nuvem
    public void insertTbLeituraNuvem(Integer fkConfig, Integer fkAlertaComponente) {

        conNuvem.update("insert into tbLeitura(leitura, dataHoraLeitura , fkConfig, fkAlertaComponente) values (? ,? , ?, ?)",
                leituraModel.getLeitura(), leituraModel.getDataHoraLeitura(),
                fkConfig, fkAlertaComponente);
    }

    //Select de dados de unidade de medida Nuvem
    public List<MaquinaUnidade> selectDadosUnidadeMedidaNuvem(Integer nSerie) {

        List<MaquinaUnidade> maquinaUnidade = new ArrayList();

        maquinaUnidade = conNuvem.query("SELECT * FROM [dbo].[tbMaquina] as maq \n"
                + "	JOIN [dbo].[tbConfig] as conf ON conf.fkMaquina = maq.idMaquina\n"
                + "		JOIN [dbo].[tbComponente] AS comp ON conf.fkComponente = comp.idComponente\n"
                + "			JOIN [dbo].[tbTipoComponente] as tipoComp ON comp.fkTipoComponente = tipoComp.idTipoComponente\n"
                + "				JOIN [dbo].[tbUnidadeComponente] as uniC ON uniC.fkTipoComponente = tipoComp.idTipoComponente\n"
                + "					JOIN [dbo].[tbUnidade] as uni ON uniC.fkUnidade = uni.idUnidade\n"
                + "						WHERE maq.nSerie = ?;",
                new BeanPropertyRowMapper(MaquinaUnidade.class), nSerie);

        return maquinaUnidade;
    }

    //Select de dados de unidade de medida Local
    public List<MaquinaUnidade> selectDadosUnidadeMedidaLocal(Integer nSerieLocal) {

        List<MaquinaUnidade> maquinaUnidade = new ArrayList();

        maquinaUnidade = conNuvem.query("SELECT * FROM tbMaquina as maq\n"
                + "JOIN tbConfig as conf ON conf.fkMaquina = maq.idMaquina\n"
                + "JOIN tbComponente AS comp ON conf.fkComponente = comp.idComponente\n"
                + "JOIN tbTipoComponente as tipoComp ON comp.fkTipoComponente = tipoComp.idTipoComponente\n"
                + "JOIN tbUnidadeComponente as uniC ON uniC.fkTipoComponente = tipoComp.idTipoComponente\n"
                + "JOIN tbUnidade as uni ON uniC.fkUnidade = uni.idUnidade WHERE maq.nSerie = ?;",
                new BeanPropertyRowMapper(MaquinaUnidade.class), nSerieLocal);

        return maquinaUnidade;
    }

    public String converterUnidadeMedida(Long valor, String leitura) {

        List<MaquinaUnidade> maqUni = selectDadosUnidadeMedidaNuvem(nSerie);

        String leituraFormatada = "";

        for (int i = 0; i < maqUni.size(); i++) {

            System.out.println(String.format("comp: %s \nsigla: %s", maqUni.get(i).getTipoComponente(), maqUni.get(i).getSigla()));
            if (maqUni.get(i).getTipoComponente().equalsIgnoreCase("Memória RAM") && leitura.equalsIgnoreCase("memoria")) {
                //if (maqUni.get(i).getSigla().equalsIgnoreCase("GB")) {
                leituraFormatada = conversor.formatarBytes(valor);
                //System.out.println(leituraFormatada);
                //}
            } else if (maqUni.get(i).getTipoComponente().equalsIgnoreCase("Placa de rede") && leitura.equalsIgnoreCase("rede")) {
                leituraFormatada = Long.toString((valor * 8) / 1000000);
            } else if (maqUni.get(i).getTipoComponente().equalsIgnoreCase("Disco Rígido") && leitura.equalsIgnoreCase("disco")) {
                if (maqUni.get(i).getSigla().equalsIgnoreCase("GB")) {
                    leituraFormatada = conversor.formatarBytes(valor);
                    //System.out.println(leituraFormatada);
                } else {
                    leituraFormatada = Long.toString(valor / 1099511627);
                    //System.out.println(leituraFormatada);
                }
            }
        }
        System.out.println(leituraFormatada);
        return leituraFormatada;
    }

    /*----------------------------------------------------------------------------------*/
    public String converterUnidadeMedidaLocal(Long valor, String leitura) {

        List<MaquinaUnidade> maqUni = selectDadosUnidadeMedidaLocal(nSerieLocal);

        String leituraFormatada = "";

        for (int i = 0; i < maqUni.size(); i++) {

            System.out.println(String.format("comp: %s \nsigla: %s", maqUni.get(i).getTipoComponente(), maqUni.get(i).getSigla()));
            if (maqUni.get(i).getTipoComponente().equalsIgnoreCase("Memória RAM") && leitura.equalsIgnoreCase("memoria")) {
                //if (maqUni.get(i).getSigla().equalsIgnoreCase("GB")) {
                leituraFormatada = conversor.formatarBytes(valor);
                //System.out.println(leituraFormatada);
                //}
            } else if (maqUni.get(i).getTipoComponente().equalsIgnoreCase("Placa de rede") && leitura.equalsIgnoreCase("rede")) {
                leituraFormatada = Long.toString((valor * 8) / 1000000);
            } else if (maqUni.get(i).getTipoComponente().equalsIgnoreCase("Disco Rígido") && leitura.equalsIgnoreCase("disco")) {
                if (maqUni.get(i).getSigla().equalsIgnoreCase("GB")) {
                    leituraFormatada = conversor.formatarBytes(valor);
                    //System.out.println(leituraFormatada);
                } else {
                    leituraFormatada = Long.toString(valor / 1099511627);
                    //System.out.println(leituraFormatada);
                }
            }
        }
        System.out.println(leituraFormatada);
        return leituraFormatada;
    }
    /*--------------------------------------------------------------------------------*/
    //Método de inserção no banco com timer task para inserir a cada x tempo
    public void inserirNoBanco() {

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                //data e hora 
                leituraModel.setDataHoraLeitura(LocalDateTime.now());

                System.out.println("teste" + looca.getMemoria().getEmUso());

                String leituraMemoriaFormatada = converterUnidadeMedida(looca.getMemoria().getEmUso(), "Memoria");

                //System.out.println("leituraMemoriaFormatada" + leituraMemoriaFormatada);
                leituraModel.setLeitura(leituraMemoriaFormatada);

                System.out.println("Memória em uso: " + leituraModel.getLeitura());
                
                // Slack notificação memoria
                String leituraMemoriaTotal = converterUnidadeMedida(looca.getMemoria().getTotal(), "Memoria");
               
                System.out.println(leituraMemoriaFormatada);
                
                String leituraMemoriaFormatadaF = leituraMemoriaFormatada.replace(",", ".").replace("GiB", "");
                String leituraMemoriaTotalFormatada = leituraMemoriaTotal.replace(",", ".").replace("GiB", "");
                System.out.println(leituraMemoriaFormatada);
                System.out.println(leituraMemoriaTotalFormatada);

                if ((Double.parseDouble(leituraMemoriaFormatadaF) / Double.parseDouble(leituraMemoriaTotalFormatada)) * 100 >= 80.0) {
                    try {
                        slack.validaMemoria(nSerie);
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                /*-------------------------------------------------------------------------*/
                List<MaquinaUnidade> listaMaquina = selectDadosUnidadeMedidaNuvem(nSerie);
                List<MaquinaUnidade> listaMaquinaLocal = selectDadosUnidadeMedidaLocal(nSerieLocal);

                System.out.println("\nLISTA MAQUINA " + listaMaquina + "\n");
                Boolean jaInseriuRam = true;
                Integer fkAlerta = 1;
                /*chumbado por enquanto*/

                for (MaquinaUnidade maquinaDaVez : listaMaquina) {
                    if (jaInseriuRam == true) {
                        if (maquinaDaVez.getTipoComponente().equalsIgnoreCase("Memória RAM")) {

                            insertTbLeituraNuvem(maquinaDaVez.getIdConfig(), fkAlerta);
                            //insertTbLeituraLocal(maquinaDaVez.getIdConfig(), fkAlerta);

                            jaInseriuRam = false;
                            System.out.println("**********inseriu ram");
                        }
                    }
                }

                Boolean jaInseriuRamLocal = true;
                /*Integer fkAlerta = 1;*/
 /*chumbado por enquanto*/

                for (MaquinaUnidade maquinaDaVez : listaMaquinaLocal) {
                    if (jaInseriuRamLocal == true) {
                        if (maquinaDaVez.getTipoComponente().equalsIgnoreCase("Memória RAM")) {

                            //insertTbLeituraNuvem(maquinaDaVez.getIdConfig(), fkAlerta);
                            insertTbLeituraLocal(maquinaDaVez.getIdConfig(), fkAlerta);

                            jaInseriuRamLocal = false;
                            System.out.println("**********inseriu ram");
                        }
                    }
                }

                //---------------------------------------------------------------------------//
                //Discos em uso
                Boolean jaInseriuDisco = true;

                for (Volume disco : listaDiscos) {

                    String leituraDiscoFormatada = converterUnidadeMedida((disco.getTotal() - disco.getDisponivel()), "disco");

                    leituraModel.setLeitura(leituraDiscoFormatada);

                    System.out.println("Em uso do disco " + disco.getNome() + " "
                            + leituraModel.getLeitura());

                    for (MaquinaUnidade maquinaDaVez : listaMaquina) {
                        if (jaInseriuDisco == true) {
                            if (maquinaDaVez.getTipoComponente().equalsIgnoreCase("Disco Rígido")) {

                                insertTbLeituraNuvem(maquinaDaVez.getIdConfig(), 1);
                                //insertTbLeituraLocal(maquinaDaVez.getIdConfig(), 1);
                                jaInseriuDisco = false;
                                System.out.println("**********inseriu disco");
                            }
                        }
                    }
                    
                    // Slack notificação Disco
                    if ((Double.parseDouble(leituraDiscoFormatada) / disco.getTotal()) * 100 >= 90.0) {
                        try {
                            slack.validaDisco(nSerie);
                        } catch (IOException | InterruptedException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }

                Boolean jaInseriuDiscoLocal = true;

                for (Volume disco : listaDiscos) {

                    String leituraDiscoFormatada = converterUnidadeMedida((disco.getTotal() - disco.getDisponivel()), "disco");

                    leituraModel.setLeitura(leituraDiscoFormatada);

                    System.out.println("Em uso do disco " + disco.getNome() + " "
                            + leituraModel.getLeitura());

                    for (MaquinaUnidade maquinaDaVez : listaMaquinaLocal) {
                        if (jaInseriuDiscoLocal == true) {
                            if (maquinaDaVez.getTipoComponente().equalsIgnoreCase("Disco Rígido")) {

                                //insertTbLeituraNuvem(maquinaDaVez.getIdConfig(), 1);
                                insertTbLeituraLocal(maquinaDaVez.getIdConfig(), 1);
                                jaInseriuDiscoLocal = false;
                                System.out.println("**********inseriu disco");
                            }
                        }
                    }

                }

                //Redes em uso internet e wi-fi
                System.out.println(listaRedes.size());
                Boolean jaInseriuRede = true;
                for (int i = listaRedes.size() - 1; i >= 0; i--) {

                    if (listaRedes.get(i).getBytesRecebidos().doubleValue() != 0
                            && listaRedes.get(i).getBytesEnviados().doubleValue() != 0) {

                        Long leituraBytesRecebidos = listaRedes.get(i).getBytesRecebidos();
                        Long leituraBytesEnviados = listaRedes.get(i).getBytesEnviados();

                        String leituraByteRecebidoConvertida = converterUnidadeMedida(leituraBytesRecebidos, "rede");
                        String leituraByteEnviadosConvertida = converterUnidadeMedida(leituraBytesEnviados, "rede");

                        leituraModel.setLeitura(leituraByteRecebidoConvertida);
                        leituraModel.setLeitura(leituraByteEnviadosConvertida);

                        System.out.println("-----------------------------------------------------");
                        System.out.println("Em uso da rede: " + listaRedes.get(i).getNome() + " : "
                                + leituraModel.getLeitura());

                        System.out.println("Bytes recebidos: " + leituraByteRecebidoConvertida);
                        System.out.println("Bytes enviados: " + leituraByteEnviadosConvertida);
                        System.out.println("-----------------------------------------------------");

                        for (MaquinaUnidade maquinaDaVez : listaMaquina) {
                            if (jaInseriuRede == true) {
                                if (maquinaDaVez.getTipoComponente().equalsIgnoreCase("Placa de Rede")) {

                                    insertTbLeituraNuvem(maquinaDaVez.getIdConfig(), 1);
                                    //insertTbLeituraLocal(maquinaDaVez.getIdConfig(), 1);
                                    jaInseriuRede = false;
                                    System.out.println("**********inseriu rede");
                                }
                            }
                        }

                    }
                }

                Boolean jaInseriuRedeLocal = true;
                for (int i = listaRedes.size() - 1; i >= 0; i--) {

                    if (listaRedes.get(i).getBytesRecebidos().doubleValue() != 0
                            && listaRedes.get(i).getBytesEnviados().doubleValue() != 0) {

                        Long leituraBytesRecebidos = listaRedes.get(i).getBytesRecebidos();
                        Long leituraBytesEnviados = listaRedes.get(i).getBytesEnviados();

                        String leituraByteRecebidoConvertida = converterUnidadeMedida(leituraBytesRecebidos, "rede");
                        String leituraByteEnviadosConvertida = converterUnidadeMedida(leituraBytesEnviados, "rede");

                        leituraModel.setLeitura(leituraByteRecebidoConvertida);
                        leituraModel.setLeitura(leituraByteEnviadosConvertida);

                        System.out.println("-----------------------------------------------------");
                        System.out.println("Em uso da rede: " + listaRedes.get(i).getNome() + " : "
                                + leituraModel.getLeitura());

                        System.out.println("Bytes recebidos: " + leituraByteRecebidoConvertida);
                        System.out.println("Bytes enviados: " + leituraByteEnviadosConvertida);
                        System.out.println("-----------------------------------------------------");

                        for (MaquinaUnidade maquinaDaVez : listaMaquinaLocal) {
                            if (jaInseriuRedeLocal == true) {
                                if (maquinaDaVez.getTipoComponente().equalsIgnoreCase("Placa de Rede")) {

                                    //insertTbLeituraNuvem(maquinaDaVez.getIdConfig(), 1);
                                    insertTbLeituraLocal(maquinaDaVez.getIdConfig(), 1);
                                    jaInseriuRedeLocal = false;
                                    System.out.println("**********inseriu rede");
                                }
                            }
                        }

                    }
                }
                //---------------------------------------------------------------------------//
                //Uso processador
                String leituraUsoProcessador = looca.getProcessador().getUso().toString();

                leituraModel.setLeitura(leituraUsoProcessador);

                System.out.println("Processador em uso: " + leituraModel.getLeitura());

                Boolean jaInseriuCpu = true;
                
                // Slack notificação Processador
                if (Double.parseDouble(leituraUsoProcessador) >= 90.0) {
                    try {
                        slack.validaProcessador(nSerie);
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                for (MaquinaUnidade maquinaDaVez : listaMaquina) {
                    if (jaInseriuCpu == true) {
                        if (maquinaDaVez.getTipoComponente().equalsIgnoreCase("CPU")) {

                            insertTbLeituraNuvem(maquinaDaVez.getIdConfig(), 1);
                            //insertTbLeituraLocal(maquinaDaVez.getIdConfig(), 1);
                            jaInseriuCpu = false;
                            System.out.println("**********inseriu cpu");
                        }
                    }
                }

                Boolean jaInseriuCpuLocal = true;

                for (MaquinaUnidade maquinaDaVez : listaMaquinaLocal) {
                    if (jaInseriuCpuLocal == true) {
                        if (maquinaDaVez.getTipoComponente().equalsIgnoreCase("CPU")) {

                            //insertTbLeituraNuvem(maquinaDaVez.getIdConfig(), 1);
                            insertTbLeituraLocal(maquinaDaVez.getIdConfig(), 1);
                            jaInseriuCpuLocal = false;
                            System.out.println("**********inseriu cpu");
                        }
                    }
                }
                
            }
        }, 0, 10000);
    }
}
