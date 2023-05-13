/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.github.britooo.looca.api.core.Looca;
import service.ConexaoBancoLocal;
import java.time.LocalDateTime;
import com.github.britooo.looca.api.util.Conversor;
import static com.github.britooo.looca.api.util.Conversor.formatarBytes;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.processos.ProcessoGrupo;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.servicos.Servico;
import com.github.britooo.looca.api.group.servicos.ServicoGrupo;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.group.temperatura.Temperatura;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import model.LeituraComponente;
import model.UsuarioModel;
import model.LeituraModel;
import model.LeituraUsuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import service.ConexaoBancoNuvem;

/**
 *
 * @author BELLA
 */
public class Controller {

    //Instanciando conexao Banco Local
    ConexaoBancoLocal connectionLocal = new ConexaoBancoLocal();
    JdbcTemplate con = connectionLocal.getConnection();

    //Instanciando conexao Banco
    ConexaoBancoNuvem connectionNuvem = new ConexaoBancoNuvem();
    JdbcTemplate conNuvem = connectionNuvem.getConnection();

    // Instanciando Looca + Classes monitoradas
    Looca looca = new Looca();

    Conversor conversor = new Conversor();

    Memoria memoria = new Memoria();
    Processador processador = new Processador();
    List<Rede> rede = new ArrayList();
    DiscoGrupo discoGrupo = new DiscoGrupo();

    Sistema sistema = new Sistema();
    Temperatura temperatura = new Temperatura();
    List<Disco> listaDisco = new ArrayList();

    // Instanciando Model de leitura - dados que vêm do looca
    LeituraModel leituraModel = new LeituraModel();
    

    public List<UsuarioModel> selectDadosUsuario(String usuario, String senha) {

        List<UsuarioModel> listaUsuario = new ArrayList();

        listaUsuario = con.query("SELECT * FROM tbUsuario WHERE nomeUsuario = ? AND senhaUsuario = ?",
                new BeanPropertyRowMapper(UsuarioModel.class), usuario, senha);

        return listaUsuario;
    }
    
    
    public List<UsuarioModel> selectDadosUsuarioNuvem(String usuario, String senha) {

        List<UsuarioModel> listaUsuarioNuvem = new ArrayList();

        listaUsuarioNuvem = conNuvem.query("SELECT * FROM tbUsuario WHERE nomeUsuario = ? AND senhaUsuario = ?",
                new BeanPropertyRowMapper(UsuarioModel.class), usuario, senha);

        return listaUsuarioNuvem;
    }

    
    /*-----------------------------------------------------------------------------------*/
    
    public List<LeituraUsuario> selectLeituraUsuario(String usuario, String senha) {

        List<LeituraUsuario> listaLeituraUsuario = new ArrayList();

        listaLeituraUsuario = con.query("select idLeitura , fkConfig, fkAlertaComponente , c.fkMaquina, fkComponente , nSerie ,  nomeUsuario from tbLeitura as l"
                + " join tbConfig as c on l.fkConfig = c.idConfig join tbMaquina as m on m.idMaquina = c.fkMaquina "
                + "join tbUsuario as u on u.fkMaquina = m.idMaquina where nomeUsuario = ? and senhaUsuario = ? order by idLeitura desc limit 1 ;",
                new BeanPropertyRowMapper(LeituraUsuario.class), usuario, senha);

        return listaLeituraUsuario;
    }

    public List<LeituraUsuario> selectLeituraUsuarioNuvem(String usuario, String senha) {

        List<LeituraUsuario> listaLeituraUsuarioNuvem = new ArrayList();

        listaLeituraUsuarioNuvem = conNuvem.query(
                "select top 1 idLeitura, fkConfig, fkAlertaComponente ,c.fkMaquina, fkComponente ,nSerie , nomeUsuario"
                + " from tbLeitura as l join tbConfig as c on l.fkConfig = c.idConfig join tbMaquina as m on m.idMaquina = c.fkMaquina join tbUsuario as u on u.fkMaquina = m.idMaquina where nomeUsuario = ? and senhaUsuario = ? order by idLeitura desc ;",
                new BeanPropertyRowMapper(LeituraUsuario.class), usuario, senha);

        return listaLeituraUsuarioNuvem;
    }
    
   /*----------------------------------------------------------------------------*/
    
    
    public void insertTbLeituraLocal(Integer fkConfig, Integer fkAlertaComponente) {

        con.update("insert into tbLeitura values (?, ? ,? , ?, ?)",
                null, leituraModel.getLeitura(), leituraModel.getDataHoraLeitura(),
                fkConfig, fkAlertaComponente);
    }

    
    public void insertTbLeituraNuvem(Integer fkConfig, Integer fkAlertaComponente) {

        conNuvem.update("insert into tbLeitura(leitura, dataHoraLeitura , fkConfig, fkAlertaComponente) values (? ,? , ?, ?)",
                leituraModel.getLeitura(), leituraModel.getDataHoraLeitura(),
                fkConfig, fkAlertaComponente);
    }
    
     
     /*--------------------------------------------------------------------------------*/
    
    
    public void inserirNoBanco(Integer fkConfig, Integer fkAlertaComponente) {

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                //data e hora 
                leituraModel.setDataHoraLeitura(LocalDateTime.now());

                //inserindo a leitura da memoria em uso do looca
                System.out.println("----------Memoria----------");

                //Uso memória
                leituraModel.setLeitura(looca.getMemoria().getEmUso().doubleValue());

                insertTbLeituraLocal(fkConfig, fkAlertaComponente);
                insertTbLeituraNuvem(fkConfig, fkAlertaComponente);

                System.out.println("Memória em uso: " + leituraModel.getLeitura());

                //Memória disponível
                leituraModel.setLeitura(looca.getMemoria().getDisponivel().doubleValue());

                insertTbLeituraLocal(fkConfig, fkAlertaComponente);
                insertTbLeituraNuvem(fkConfig, fkAlertaComponente);

                System.out.println("Memória Disponível: " + leituraModel.getLeitura());

                //---------------------------------------------------------------------------//
                //inserindo a leitura processador
                System.out.println("----------Processador----------");

                //Frequência processador
                leituraModel.setLeitura(looca.getProcessador().getFrequencia().doubleValue());

                insertTbLeituraLocal(fkConfig, fkAlertaComponente);
                insertTbLeituraNuvem(fkConfig, fkAlertaComponente);

                System.out.println("Frequência do processador: " + leituraModel.getLeitura());

                //Uso processador
                leituraModel.setLeitura(looca.getProcessador().getUso().doubleValue());

                insertTbLeituraLocal(fkConfig, fkAlertaComponente);
                insertTbLeituraNuvem(fkConfig, fkAlertaComponente);

                System.out.println("Processador em uso: " + leituraModel.getLeitura());

                //---------------------------------------------------------------------------//
                // leitura rede
                /*System.out.println("----------Rede----------");

                System.out.println("Interfaces da rede: " + looca.getRede().getGrupoDeInterfaces().getInterfaces());
                System.out.println("HostName: " + looca.getRede().getParametros().getHostName());
                */
                //---------------------------------------------------------------------------//
                //inserindo leitura de disco
                System.out.println("----------Disco----------");

                //Tamanho total disco
                leituraModel.setLeitura(looca.getGrupoDeDiscos().getTamanhoTotal().doubleValue());

                insertTbLeituraLocal(fkConfig, fkAlertaComponente);
                insertTbLeituraNuvem(fkConfig, fkAlertaComponente);

                System.out.println("Tamanho total do disco: " + leituraModel.getLeitura());
                //---------------------------------------------------------------------------//

            }
        }, 0, 100000);
    }
}
