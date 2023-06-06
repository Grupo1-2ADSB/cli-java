/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cli;

import controller.Controller;
import java.util.List;
import java.util.Scanner;
import model.LeituraUsuario;
import model.UsuarioModel;

/**
 *
 * @author BELLA
 */
public class Login {

    public static void main(String[] args) {

        Controller controller = new Controller();

        Scanner leitor = new Scanner(System.in);

        System.out.println("Digite o nome de Usuario");
        String usuario = leitor.nextLine();

        System.out.println("Digite a senha do Usuario");
        String senha = leitor.nextLine();

        /*-------------------------------------------------------------------------*/
        //invocando o método selectDadosUsuario             
        List<UsuarioModel> listaUsuario = controller.selectDadosUsuarioLocal(usuario, senha);
        System.out.println(listaUsuario);

        //invocando o método selectDadosUsuario             
        List<UsuarioModel> listaUsuarioNuvem = controller.selectDadosUsuarioNuvem(usuario, senha);
        System.out.println(listaUsuarioNuvem);

        /*-------------------------------------------------------------------------*/
        //invocando o método selectLeituraUsuario
        List<LeituraUsuario> listaLeituraUsuario = controller.selectLeituraUsuario(usuario, senha);
        System.out.println(listaLeituraUsuario);

        //invocando o método selectLeituraUsuarioNuvem
        List<LeituraUsuario> listaLeituraUsuarioNuvem = controller.selectLeituraUsuarioNuvem(usuario, senha);
        System.out.println(listaLeituraUsuarioNuvem);

        /*-----------------------------------------------------------------------------*/
        if (listaUsuario.isEmpty() && listaUsuarioNuvem.isEmpty()) {

            System.out.println("Usuário não encontrado");
        } else {

            System.out.println("Bem-vindo de volta, %s!" + usuario);

            controller.inserirNoBanco();
        }

    }

}
