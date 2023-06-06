package model;

/**
 *
 * @author Leo
 */
public class UsuarioModel {

    private Integer idUsuario;
    private String hostName;
    private String nomeUsuario;
    private String emailUsuario;
    private String senhaUsuario;
    private String statusUsuario;
    private Integer fkEmpresa;
    private Integer fkPermissao;

    public UsuarioModel(Integer idUsuario, 
            String hostName, String nomeUsuario,
            String emailUsuario, String senhaUsuario,
            String statusUsuario, Integer fkEmpresa,
            Integer fkPermissao) {
        this.idUsuario = idUsuario;
        this.hostName = hostName;
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.senhaUsuario = senhaUsuario;
        this.statusUsuario = statusUsuario;
        this.fkEmpresa = fkEmpresa;
        this.fkPermissao = fkPermissao;
    }

    public UsuarioModel() {
        
    }
    
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getStatusUsuario() {
        return statusUsuario;
    }

    public void setStatusUsuario(String statusUsuario) {
        this.statusUsuario = statusUsuario;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public Integer getFkPermissao() {
        return fkPermissao;
    }

    public void setFkPermissao(Integer fkPermissao) {
        this.fkPermissao = fkPermissao;
    }
    
    @Override
    public String toString() {
        return String.format
        ("""
         LoginModel
         idUsuario: %d | hostName: %s | nomeUsuario: %s |
         emailUsuario: %s | senha: %s | statusUsuario: %s|
         fkEmpresa: %d | fkPermissao: %d
         
         """, 
          idUsuario, hostName, nomeUsuario, emailUsuario, senhaUsuario, 
          statusUsuario, fkEmpresa, fkPermissao);
               
    }
}