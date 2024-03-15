/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Controller.Helper.AgendaDoDiaHelper;
import Model.Agendamento;
import Model.DAO.AgendamentoDAO;
import View.AgendaDoDiaView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author henri
 */
public class AgendaDoDiaController {
    
    private final AgendaDoDiaView view;
    private final AgendaDoDiaHelper helper;

    public AgendaDoDiaController(AgendaDoDiaView view) {
        this.view = view;
        this.helper = new AgendaDoDiaHelper(view);
    }
    
    public void atualizaTabela(Date dataEscolhida){
        //buscar lista no banco de dados
        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        ArrayList<Agendamento> agendamentos = agendamentoDAO.selectAll();

        // Ordena a lista usando um Comparator
        Collections.sort(agendamentos, Comparator.comparing(Agendamento::getHoraFormatada));

        
        //exibir esta lista na view
        this.helper.preencherTabela(agendamentos, dataEscolhida);
    } 
    
    
    
}
