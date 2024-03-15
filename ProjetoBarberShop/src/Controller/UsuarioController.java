/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Controller.Helper.UsuarioHelper;
import Model.DAO.UsuarioDAO;
import Model.Usuario;
import View.UsuarioView;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author henri
 */
public class UsuarioController {
    
    private final UsuarioView view;
    private final UsuarioHelper helper;

    public UsuarioController(UsuarioView view) {
        this.view = view;
        this.helper = new UsuarioHelper(view);
    }
    
    public void atualizaTabela(){
        //buscar lista no banco de dados
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ArrayList<Usuario> usuarios = usuarioDAO.selectAll();
        
        //exibir esta lista na view
        this.helper.preencherTabela(usuarios);
    } 
    
    public void cadastrar(){
        
        if(helper.validarCampos()){
      
            Usuario usuario = (Usuario) helper.obterModelo();
            //Salva Objeto no banco de dados
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.insert(usuario);
            //inserir elemento na tabela
            atualizaTabela();
            helper.limparTela();
        
        }
    }
    
    public int editar(){
        JTable tabela = view.getTabelaUsuarios();
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada != -1) {
            int id = Integer.parseInt(tabela.getValueAt(linhaSelecionada, 0).toString());

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            helper.preencherCampos(usuarioDAO.buscar(id));
            
            view.getBtnCadastrar().setVisible(false);
            view.getBtnSalvar().setVisible(true);
            
            return id;
        } else {
            JOptionPane.showMessageDialog(view, "Nenhuma linha selecionada.");
            return 0;
        }
    }
    
    public void salvar(int id){
        if(helper.validarCampos()){
            //Buscar Objeto Cliente da Tela
            Usuario usuario = (Usuario) helper.obterModelo();
            usuario.setId(id);
            //Atualiza o Objeto no banco de dados
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.update(usuario);

            atualizaTabela();
            helper.limparTela();
            
            view.getBtnCadastrar().setVisible(true);
            view.getBtnSalvar().setVisible(false);
        }
    }
    
    public void excluir(){
        JTable tabela = view.getTabelaUsuarios();
        int linhaSelecionada = tabela.getSelectedRow();
        
        if (linhaSelecionada != -1) {
            int id = Integer.parseInt(tabela.getValueAt(linhaSelecionada, 0).toString());

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.delete(usuarioDAO.buscar(id));
            
            atualizaTabela();
            helper.limparTela();
            view.getBtnCadastrar().setVisible(true);
            view.getBtnSalvar().setVisible(false);
        
        } else {
            JOptionPane.showMessageDialog(view, "Nenhuma linha selecionada.");
        }
    }
    
}
