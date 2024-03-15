/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Usuario;
import View.Agenda;
import View.AgendaDoDiaView;
import View.ClienteView;
import View.Login;
import View.MenuPrincipal;
import View.ServicoView;
import View.UsuarioView;
import java.util.Date;
import singleton.Sessao;

/**
 *
 * @author henri
 */
public class MenuPrincipalController {
    
    private final MenuPrincipal view;

    public MenuPrincipalController(MenuPrincipal view) {
        this.view = view;
    }
    
    public void navegarParaAgenda(){
        Agenda agenda = new Agenda();
        agenda.setVisible(true);
    }

    public void navegarParaCliente() {
        ClienteView cliente = new ClienteView();
        cliente.setVisible(true);
    }
    
    public void navegarParaServico() {
        ServicoView servico = new ServicoView();
        servico.setVisible(true);
    }
    
    public void navegarParaUsuario() {
        Usuario usuarioLogado = Sessao.getInstance().getUsuario();
        if(usuarioLogado.getNivelAcesso().equals("administrador")){
            UsuarioView usuario = new UsuarioView();
            usuario.setVisible(true);
        }else{
            view.exibeMensagem("Acesso negado! Para controlar os usuários é necessário nível de acesso de 'administrador'.");
        }
    }
    
    public void navegarParaAgendaDoDia(Date dataEscolhida) {
        AgendaDoDiaView agendaDoDiaView = new AgendaDoDiaView(dataEscolhida);
        agendaDoDiaView.setVisible(true);
        agendaDoDiaView.setDataEscolhida(dataEscolhida);
    }

    public void navegarParaLogin() {
        Login login = new Login();
        login.setVisible(true);
        this.view.dispose();
        Sessao.getInstance().setUsuario(null);
    }
    
}
