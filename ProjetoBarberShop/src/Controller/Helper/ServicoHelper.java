/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Helper;


import Model.Servico;
import View.ServicoView;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author henri
 */
public class ServicoHelper implements IHelper{
     private final ServicoView view;

    public ServicoHelper(ServicoView view) {
        this.view = view;
    }
    
    public boolean validarCampos(){
        String descricao = view.getTextDescricao().getText();
        
        String valorStr = view.getTextValor().getText();
        
        if(descricao.equals("") || valorStr.equals("")){
            view.exibeMensagem("Preencha todos os campos!");
            return false;
        }else if(!isFloat(valorStr)){
            view.exibeMensagem("Valor precisa ser um número!");
            return false;
        }
        
        return true;
        
    }
    
    public void preencherTabela(ArrayList<Servico> servicos){
        DefaultTableModel tableModel = (DefaultTableModel) view.getTabelaServicos().getModel();
        tableModel.setNumRows(0);
        
        //percorrer a lista preenchendo o table model
        for (Servico servico : servicos) {
            
            tableModel.addRow(new Object[]{
                servico.getId(),
                servico.getDescricao(),
                servico.getValor()
                
            });
            
        }
    }

    @Override
    public Object obterModelo() {
        
        String descricao = view.getTextDescricao().getText();
        
        Float valor = Float.parseFloat(view.getTextValor().getText());
        
        Servico servico = new Servico(0, descricao, valor);
        
        return servico;
    }

    @Override
    public void limparTela() {
        view.getTextDescricao().setText("");
        view.getTextValor().setText("");
    }

    public void preencherCampos(Servico servico) {
        view.getTextDescricao().setText(servico.getDescricao());
        view.getTextValor().setText(Float.toString(servico.getValor()));
    }
    
    // Função para verificar se uma string representa um número float
    public static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
