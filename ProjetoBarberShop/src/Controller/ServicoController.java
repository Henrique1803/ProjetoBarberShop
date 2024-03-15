/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Controller.Helper.ServicoHelper;
import Model.DAO.ServicoDAO;
import Model.Servico;
import View.ServicoView;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author henri
 */
public class ServicoController {
    
    private final ServicoView view;
    private final ServicoHelper helper;

    public ServicoController(ServicoView view) {
        this.view = view;
        this.helper = new ServicoHelper(view);
    }
    
    public void atualizaTabela(){
        //buscar lista no banco de dados
        ServicoDAO servicoDAO = new ServicoDAO();
        ArrayList<Servico> servicos = servicoDAO.selectAll();
        
        //exibir esta lista na view
        this.helper.preencherTabela(servicos);
    } 
    
    public void cadastrar(){
        
        if(helper.validarCampos()){
      
            Servico servico = (Servico) helper.obterModelo();
            //Salva Objeto no banco de dados
            ServicoDAO servicoDAO = new ServicoDAO();
            servicoDAO.insert(servico);
            //inserir elemento na tabela
            atualizaTabela();
            helper.limparTela();
        
        }
    }
    
    public int editar(){
        JTable tabela = view.getTabelaServicos();
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada != -1) {
            int id = Integer.parseInt(tabela.getValueAt(linhaSelecionada, 0).toString());

            ServicoDAO servicoDAO = new ServicoDAO();
            helper.preencherCampos(servicoDAO.buscar(id));
            
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
            Servico servico = (Servico) helper.obterModelo();
            servico.setId(id);
            //Atualiza o Objeto no banco de dados
            ServicoDAO servicoDAO = new ServicoDAO();
            servicoDAO.update(servico);

            atualizaTabela();
            helper.limparTela();
            
            view.getBtnCadastrar().setVisible(true);
            view.getBtnSalvar().setVisible(false);
        }
    }
    
    public void excluir(){
        JTable tabela = view.getTabelaServicos();
        int linhaSelecionada = tabela.getSelectedRow();
        
        if (linhaSelecionada != -1) {
            int id = Integer.parseInt(tabela.getValueAt(linhaSelecionada, 0).toString());

            ServicoDAO servicoDAO = new ServicoDAO();
            servicoDAO.delete(servicoDAO.buscar(id));
            
            atualizaTabela();
            helper.limparTela();
            view.getBtnCadastrar().setVisible(true);
            view.getBtnSalvar().setVisible(false);
        
        } else {
            JOptionPane.showMessageDialog(view, "Nenhuma linha selecionada.");
        }
    }
    
}
