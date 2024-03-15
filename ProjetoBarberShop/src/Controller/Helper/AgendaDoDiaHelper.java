/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Helper;

import Model.Agendamento;
import View.AgendaDoDiaView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author henri
 */
public class AgendaDoDiaHelper implements IHelper{
    
    private final AgendaDoDiaView view;

    public AgendaDoDiaHelper(AgendaDoDiaView view) {
        this.view = view;
    }
    
    public void preencherTabela(ArrayList<Agendamento> agendamentos, Date dataEscolhida){
        DefaultTableModel tableModel = (DefaultTableModel) view.getTabelaAgendamentos().getModel();
        tableModel.setNumRows(0);
        
        //percorrer a lista preenchendo o table model
        for (Agendamento agendamento : agendamentos) {
            
            DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            // Formata a data
            String dataEscolhidaStr = formato.format(dataEscolhida);
            String dataAgendamento = formato.format(agendamento.getData());
            
            if(dataAgendamento.equals(dataEscolhidaStr)){
                tableModel.addRow(new Object[]{
                    agendamento.getId(),
                    agendamento.getCliente().getNome(),
                    agendamento.getServico().getDescricao(),
                    agendamento.getValor(),
                    agendamento.getDataFormatada(),
                    agendamento.getHoraFormatada(),
                    agendamento.getObservacao()
                });
            }
        }
    }

    @Override
    public Object obterModelo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void limparTela() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
  
    
}
