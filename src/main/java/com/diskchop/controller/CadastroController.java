package com.diskchop.controller;

import com.diskchop.model.dao.ClienteDao;
import com.diskchop.model.dao.EnderecoDao;
import com.diskchop.model.dao.TelefoneDao;
import com.diskchop.model.entity.*;
import com.diskchop.model.util.MensagensSistema;
import com.diskchop.model.util.TelaMensagensSistema;
import com.diskchop.model.util.Validador;
import com.diskchop.model.util.erros.ClienteInvalidoException;
import com.diskchop.model.util.erros.EnderecoInvalidoException;
import com.diskchop.model.util.erros.ErrorHandler;
import com.diskchop.model.util.erros.TelefoneInvalidoException;
import com.diskchop.view.Cadastro;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CadastroController {
    private Cadastro view;
    private Cliente cliente;
    private ClienteDao clienteDao;// Model
    private Validador validador;
    private Endereco endereco;
    private EnderecoDao enderecoDao;
    private Telefone telefone;
    private TelefoneDao telefoneDao;

    public CadastroController(Cadastro view) {
        this.view = view;
        this.cliente = new Cliente();
        this.clienteDao = new ClienteDao();
        this.validador = new Validador();
        this.endereco = new Endereco();
        this.enderecoDao = new EnderecoDao();
        this.telefone = new Telefone();
        this.telefoneDao = new TelefoneDao();
        configureActions();
        configurarTela();
    }

    public void configureActions(){
        view.getBotaoVoltar().addActionListener(e -> {voltarMenu();});
        view.getBotaoLimparCampos().addActionListener(e -> limparCampos());
        //BOTOES CLIENTES
        view.getBotaoNovoCliente().addActionListener(e -> {
            ligaDesligaBotoesCliente(); limparCampos(); limparTabela(view.getTableClientes());});
        view.getBotaoCancelar().addActionListener(e -> {
            ligaDesligaBotoesCliente();});
        view.getBotaoSalvarCliente().addActionListener(e -> {
            ligaDesligaBotoesCliente(); salvarNovoCliente(); buscarClienteCpf();});
        view.getBotaoEditarCliente().addActionListener(e -> {
            ligaDesligaBotoesCliente();limparTabela(view.getTableClientes());});
        view.getBotaoBuscarCliente ().addActionListener(e -> buscarClienteId());
        view.getBotaoExcluirCliente().addActionListener(e -> {excluirCliente(); limparCampos(); limparTabela(view.getTableClientes());});
        //BOTOES ENDERECO
        view.getBotaoAdicionarEndereco().addActionListener(e -> {ligaDesligaBotoesEndereco(); limparCamposEndereco(); limparTabela(view.getTableEnderecos());});
        view.getBotaoCancelarEndereco().addActionListener(e -> {ligaDesligaBotoesEndereco();});
        view.getBotaoLimparCamposEndereco().addActionListener(e -> {limparCamposEndereco();});
        view.getBotaoSalvarEndereco().addActionListener(e -> {ligaDesligaBotoesEndereco(); salvarEndereco(); buscarClienteId();});
        view.getBotaoEditarEndereco().addActionListener(e -> {ligaDesligaBotoesEndereco(); limparTabela(view.getTableEnderecos());});
        view.getBotaoExcluirEndereco().addActionListener(e -> {excluirEndereco(); limparCampos(); limparTabela(view.getTableEnderecos());});
       // view.getBotaoBuscarEndereco().addActionListener(e -> buscarEnderecoCLiente());
        //BOTOES TELEFONE
        view.getBotaoCancelarTelefone().addActionListener(e ->{ligaDesligaBotoesTelefone();});
        view.getBotaoLimparCamposTelefone().addActionListener(e -> {limparCamposTelefone();});
        view.getBotaoAdicionarTelefone().addActionListener(e -> {ligaDesligaBotoesTelefone(); limparCamposTelefone(); limparTabela(view.getTableTelefones());;});
        view.getBotaoSalvarTelefone().addActionListener(e -> {ligaDesligaBotoesTelefone(); salvarTelefone(); buscarClienteId();});
        view.getBotaoEditarTelefone().addActionListener(e -> {ligaDesligaBotoesTelefone(); limparTabela(view.getTableTelefones());});
       // view.getBotaoBuscarTelefone().addActionListener(e -> buscarTelefoneCLiente());
       // view.getBotaoExcluirTelefone().addActionListener(e -> );

        view.getTextFieldNome().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                   buscarClienteNome();
                }
            }
        });
        view.getTextFieldCpf().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                  buscarClienteCpf();
                }
            }
        });

    }

    private void configurarTela(){
        view.setSize(1320, 800);
        view.getPaneltop().setBackground(new Color(20,20,25));
        //view.setResizable(true);
        view.setLocationRelativeTo(null);
        view.getTableClientes().setModel(new DefaultTableModel(
                new Object[][]{}, // Nenhuma linha inicial
                new String[]{"ID", "NOME", "CPF", "REGIME", "STATUS", "OBS", "DATA_CADASTRO"} // Cabeçalhos das colunas
        ));
        view.getTableEnderecos().setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "LOGRADOURO", "NUMERO", "BAIRRO", "CIDADE", "CEP",
                        "COMPLEMENTO","AREA", "DATA_CADASTRO", "OBS"}
        ));
        view.getTableTelefones().setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "TELEFONE", "CONTATO", "DATA_CADASTRO"}
        ));
    }

    /*** funcionalidades ***/
    private void ligaDesligaBotoesCliente(){
        ligarBotoesSalvarCancelarCliente();
        ligarBotoesModoSalvar();
    }

    private void ligaDesligaBotoesEndereco(){
        ligarBotoesSalvarCancelarEndereco();
        ligarBotoesModoSalvar();
    }

    private void ligaDesligaBotoesTelefone(){
        ligarBotoesSalvarCancelarTelefone();
        ligarBotoesModoSalvar();
    }

    private void ligarBotoesSalvarCancelarCliente(){
        for (JButton botao : view.getBotoesSalvarCancelarCliente()) {
            if (botao.isEnabled()) {
                botao.setEnabled(false);
            } else {
                botao.setEnabled(true);
            }
        }
    }

    private void ligarBotoesSalvarCancelarEndereco(){
        for (JButton botao : view.getBotoesSalvarCancelarEndereco()) {
            if (botao.isEnabled()) {
                botao.setEnabled(false);
            } else {
                botao.setEnabled(true);
            }
        }
    }

    private void ligarBotoesSalvarCancelarTelefone(){
        for (JButton botao : view.getBotoesSalvarCancelarTelefone()) {
            if (botao.isEnabled()) {
                botao.setEnabled(false);
            } else {
                botao.setEnabled(true);
            }
        }
    }

    private void ligarBotoesModoSalvar() {
        for (JButton botao : view.getBotoesModoSalvar()) {
            if (botao.isEnabled()) {
                botao.setEnabled(false);
            } else {
                botao.setEnabled(true);
            }
        }
    }

    public void voltarMenu(){
        view.dispose();
    }

    private void limparCampos(){
        for (JTextField textField : view.getTodosJTextFields()) {
            textField.setText("");
        }
    }

    private void limparCamposEndereco(){
        for (JTextField textField : view.getEnderecoJTextFields()) {
            textField.setText("");
        }
    }

    private void limparCamposTelefone(){
        for (JTextField textField : view.getTelefoneJTextFields()) {
            textField.setText("");
        }
    }

    public void escreverCamposCliente(Cliente cliente){
        view.getTextFieldId().setText(cliente.getIdCliente().toString());
        view.getTextFieldNome().setText(cliente.getNome());
        view.getTextFieldCpf().setText(cliente.getCpf());
        view.getTextFieldObsCLiente().setText(cliente.getObservacao());
    }

    public void escreverCamposEndereco(Endereco endereco){
        view.getTextFieldIdEndereco().setText(endereco.getIdEndereco().toString());
        view.getTextFieldLogradouro().setText(endereco.getLogradouro());
        view.getTextFieldNumeroEndereco().setText(endereco.getNumero());
        view.getTextFieldBairro().setText(endereco.getBairro());
        view.getTextFieldObsEnderecos().setText(endereco.getObservacaoEndereco());
        view.getTextFieldCidade().setText(endereco.getCidade());
        view.getTextFieldCep().setText(endereco.getCep());
        view.getTextFieldComplemento().setText(endereco.getComplemento());
    }

    public void escreverCamposTelefone(Telefone telefone){
        view.getTextFieldIdTelefone().setText(telefone.getIdTelefone().toString());
        view.getTextFieldTelefone().setText(telefone.getTelefone());
        view.getTextFieldContato().setText(telefone.getContato());
    }

    private void limparTabela(JTable tabela){
        DefaultTableModel novoModelo = (DefaultTableModel) tabela.getModel();
        novoModelo.setRowCount(0);
    }

    /*** funções carregar ***/
    private Telefone carregarNovoTelefone(){
        Telefone novoTelefone = new Telefone();
        String telefoneNumero = view.getTextFieldTelefone().getText();
        novoTelefone.setTelefone(telefoneNumero);
        String contato = view.getTextFieldContato().getText().toUpperCase();
        novoTelefone.setContato(contato);
        if (novoTelefone.getDataCadastroTelefone() == null) {novoTelefone.setDataCadastroTelefone();}
        return novoTelefone;
    }
    private Telefone carregarTelefoneExistente(){
        Telefone novoTelefone = new Telefone();
        Long idTelefone = Long.valueOf(view.getTextFieldIdTelefone().getText());
        novoTelefone.setIdTelefone(idTelefone);
        String telefoneNumero = view.getTextFieldTelefone().getText();
        novoTelefone.setTelefone(telefoneNumero);
        String contato = view.getTextFieldContato().getText();
        novoTelefone.setContato(contato);
        if (novoTelefone.getDataCadastroTelefone() == null) {novoTelefone.setDataCadastroTelefone();}
        return novoTelefone;
    }
    private Cliente carregarCliente(){
        Cliente novoCliente = new Cliente();
        Long idCliente = Long.valueOf(view.getTextFieldId().getText());
        novoCliente.setIdCliente(idCliente);
        novoCliente.setNome(view.getTextFieldNome().getText().toUpperCase());
        novoCliente.setCpf(view.getTextFieldCpf().getText());
        novoCliente.setObservacao(view.getTextFieldObsCLiente().getText().toUpperCase());
        novoCliente.setRegime(view.getComboCadPessoa());
        novoCliente.setStatusCliente(view.getComboStatusCliente());
        return novoCliente;
    }

    private Cliente carregarNovoCliente() {
        Cliente carregarCliente = new Cliente();
        carregarCliente.setNome(view.getTextFieldNome().getText().toUpperCase());
        carregarCliente.setCpf(view.getTextFieldCpf().getText());
        carregarCliente.setObservacao(view.getTextFieldObsCLiente().getText().toUpperCase());
        carregarCliente.setRegime(view.getComboCadPessoa());
        carregarCliente.setStatusCliente(StatusCliente.ATIVO);
        if (carregarCliente.getDataCadastroCliente() == null) {carregarCliente.setDataCadastroCliente();}
        System.out.println(carregarCliente.getNome());
        return carregarCliente;
    }

    private Endereco carregarNovoEndereco() {
        Endereco novoEndereco = new Endereco();
        String logradouro = view.getTextFieldLogradouro().getText().toUpperCase();
        novoEndereco.setLogradouro(logradouro);
        String numero = view.getTextFieldNumeroEndereco().getText();
        novoEndereco.setNumero(numero);
        String bairro = view.getTextFieldBairro().getText().toUpperCase();
        novoEndereco.setBairro(bairro);
        String cidade = view.getTextFieldCidade().getText().toUpperCase();
        novoEndereco.setCidade(cidade);
        String cep = view.getTextFieldCep().getText();
        novoEndereco.setCep(cep);
        String complemento = view.getTextFieldComplemento().getText().toUpperCase();
        novoEndereco.setComplemento(complemento);
        Tipo tipo = view.getComboTipo();
        novoEndereco.setTipo(tipo);
        String obsEnd = view.getTextFieldObsEnderecos().getText().toUpperCase();
        novoEndereco.setObservacaoEndereco(obsEnd);
        if (novoEndereco.getDataCadastroEndereco() == null) {novoEndereco.setDataCadastroEndereco();}
        return novoEndereco;
    }

    private Endereco carregarEnderecoExistente() {
        Endereco novoEndereco = new Endereco();
        novoEndereco.setIdEndereco(Long.valueOf(view.getTextFieldIdEndereco().getText()));
        novoEndereco.setLogradouro(view.getTextFieldLogradouro().getText().toUpperCase());
        novoEndereco.setNumero(view.getTextFieldNumeroEndereco().getText());
        novoEndereco.setBairro(view.getTextFieldBairro().getText().toUpperCase());
        novoEndereco.setCidade(view.getTextFieldCidade().getText().toUpperCase());
        novoEndereco.setCep(view.getTextFieldCep().getText());
        novoEndereco.setComplemento(view.getTextFieldComplemento().getText().toUpperCase());
        novoEndereco.setTipo(view.getComboTipo());
        novoEndereco.setObservacaoEndereco(view.getTextFieldObsEnderecos().getText().toUpperCase());
        if (novoEndereco.getDataCadastroEndereco() == null) {novoEndereco.setDataCadastroEndereco();}
        return novoEndereco;
    }

    /*** funções Cliente***/
    private void salvarNovoCliente() {
       try {
           Cliente novoCliente;
           if(view.getTextFieldId() == null || view.getTextFieldId().getText().equals("")){
                novoCliente = carregarNovoCliente();
                validador.validarCliente(novoCliente);
                if (clienteDao.isCpfCadastrado(novoCliente.getCpf())) {
                    TelaMensagensSistema.mostrarErro(MensagensSistema.CLIENTE_CPF_EXISTENTE);
                    return;
                }
                if (clienteDao.salvarCliente(novoCliente)) {
                    TelaMensagensSistema.mostrarInformacao(MensagensSistema.CLIENTE_CADASTRO_SUCESSO);
                }
           } else {
               novoCliente = carregarCliente();
               validador.validarCliente(novoCliente);
               if (clienteDao.salvarCliente(novoCliente)) {
                   TelaMensagensSistema.mostrarInformacao("CLIENTE ATUALIZADO COM SUCESSO!");
               }
           }
       } catch (ClienteInvalidoException e) {
           TelaMensagensSistema.mostrarErro(e.getMessage());
       } catch (Exception e) {
           ErrorHandler.handle(e);
       }
    }
    private void salvarEndereco() {
        try {
            Endereco novoEndereco;
            Long idCliente = Long.parseLong(view.getTextFieldId().getText());
            if(view.getTextFieldId() == null || view.getTextFieldId().getText().equals("")){
                TelaMensagensSistema.mostrarErro("Selecione um cliente válido");
                return;
            }
            if(view.getTextFieldIdEndereco() == null || view.getTextFieldIdEndereco().getText().equals("")){
                novoEndereco = carregarNovoEndereco();
                validador.validarEndereco(novoEndereco);
                enderecoDao.adicionarEnderecoCliente(idCliente, novoEndereco);
                TelaMensagensSistema.mostrarInformacao(MensagensSistema.ENDERECO_CADASTRO_SUCESSO);
            } else {
                novoEndereco = carregarEnderecoExistente();
                validador.validarEndereco(novoEndereco);
                enderecoDao.adicionarEnderecoCliente(idCliente, novoEndereco);
                TelaMensagensSistema.mostrarInformacao("ENDERECO ATUALIZADO COM SUCESSO!");
            }
        } catch (EnderecoInvalidoException e) {
            TelaMensagensSistema.mostrarErro(e.getMessage());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
    }

    private void salvarTelefone() {
        try {
            Telefone novoTelefone;
            Long idCliente = Long.valueOf(view.getTextFieldId().getText());
            if(view.getTextFieldId() == null || view.getTextFieldId().getText().equals("")){
                TelaMensagensSistema.mostrarErro("Selecione um cliente válido");
                return;
            }
            if(view.getTextFieldIdTelefone() == null || view.getTextFieldIdTelefone().getText().equals("")){
                novoTelefone = carregarNovoTelefone();
                validador.validarTelefone(novoTelefone);
                telefoneDao.adicionarTelefoneCliente(idCliente, novoTelefone);
                TelaMensagensSistema.mostrarInformacao("TELEFONE CADASTRADO COM SUCESSO");
            } else {
                novoTelefone = carregarTelefoneExistente();
                validador.validarTelefone(novoTelefone);
                telefoneDao.adicionarTelefoneCliente(idCliente, novoTelefone);
                TelaMensagensSistema.mostrarInformacao("TELEFONE ATUALIZADO COM SUCESSO!");
            }
        } catch (TelefoneInvalidoException e) {
            TelaMensagensSistema.mostrarErro(e.getMessage());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
    }

    private void excluirCliente(){
        try{
            if (!TelaMensagensSistema.mostrarConfirmacao("Você tem certeza?")) {
                return;
            }
            Cliente editarCliente;
            editarCliente = carregarCliente();
            clienteDao.excluirCliente(editarCliente.getIdCliente());
            TelaMensagensSistema.mostrarInformacao(MensagensSistema.CLIENTE_EXLUIDO_SUCESSO);
        } catch (ClienteInvalidoException e) {
            TelaMensagensSistema.mostrarErro(e.getMessage());
        } catch (Exception e){
            ErrorHandler.handle(e);
        }
    }


    private void excluirEndereco(){
        try{
            if (!TelaMensagensSistema.mostrarConfirmacao("Voce tem certeza?")){
                return;
            }
            Long idEndereco = Long.valueOf(view.getTextFieldIdEndereco().getText());
            Cliente cliente = clienteDao.buscarClientePorId(Long.valueOf(view.getTextFieldId().getText()));
            enderecoDao.excluirEndereco(idEndereco);
            TelaMensagensSistema.mostrarInformacao(MensagensSistema.ENDERECO_EXLUIDO_SUCESSO);
        } catch (EnderecoInvalidoException e) {
            TelaMensagensSistema.mostrarErro(e.getMessage());
        } catch (Exception e){
            ErrorHandler.handle(e);
        }
    }

    private void buscarClienteId() {
        limparTabela(view.getTableClientes());
        limparTabela(view.getTableEnderecos());
        limparTabela(view.getTableTelefones());
        if(view.getTextFieldId().getText().trim().isEmpty()){
            TelaMensagensSistema.mostrarErro("Digite um id válido!");
            return;
        }
        Long id = Long.valueOf(view.getTextFieldId().getText());
        Cliente cliente;
        cliente = clienteDao.buscarClientePorId(id);
        if(cliente == null){
            return;
        }
        DefaultTableModel modeloCliente = modeloTabelaCliente();
        modeloCliente.addRow(carregarTabelaClienteUnico(cliente));
        escreverCamposCliente(cliente);
        DefaultTableModel modeloEndereco = modeloTabelaEndereco();
        List<Endereco> enderecos = cliente.getEnderecos();
        escreverTabelaEnderecos(enderecos, modeloEndereco);
        if (!cliente.getEnderecos().isEmpty()) {
            escreverCamposEndereco(cliente.getEnderecos().getFirst());
        } else {
            limparCamposEndereco();
        }
        DefaultTableModel modeloTelefone = modeloTabelaTelefone();
        List<Telefone> telefones = cliente.getTelefones();
        escreverTabelaTelefones(telefones, modeloTelefone);
        if (!cliente.getTelefones().isEmpty()) {
            escreverCamposTelefone(cliente.getTelefones().getFirst());
        } else {
            limparCamposTelefone();
        }
    }

    private DefaultTableModel modeloTabelaCliente(){
        DefaultTableModel modeloCliente = new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "NOME", "CPF", "REGIME", "STATUS", "OBS", "DATA_CADASTRO"}
        );
        view.getTableClientes().setModel(modeloCliente);
        modeloCliente.setRowCount(0);
        return modeloCliente;
    }

    private Object[] carregarTabelaClienteUnico(Cliente cliente){
        Object[] rowData = {
                cliente.getIdCliente(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getRegime(),
                cliente.getStatusCliente(),
                cliente.getObservacao(),
                cliente.getDataCadastroCliente().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
        };
        return rowData;
    }

    private DefaultTableModel modeloTabelaEndereco(){
        DefaultTableModel modeloEndereco = new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "LOGRADOURO", "NUMERO", "BAIRRO", "CIDADE", "CEP",
                        "COMPLEMENTO","AREA", "DATA_CADASTRO", "OBS"}
        );
        view.getTableEnderecos().setModel(modeloEndereco);
        modeloEndereco.setRowCount(0);
        return modeloEndereco;
    }

    private void escreverTabelaEnderecos(List<Endereco>enderecos, DefaultTableModel modeloEndereco){
        for (Endereco endereco : enderecos) {
            Object[] rowData = {
                    endereco.getIdEndereco(),
                    endereco.getLogradouro(),
                    endereco.getNumero(),
                    endereco.getBairro(),
                    endereco.getCidade(),
                    endereco.getCep(),
                    endereco.getComplemento(),
                    endereco.getTipo(),
                    endereco.getDataCadastroEndereco().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    endereco.getObservacaoEndereco()
            };
            modeloEndereco.addRow(rowData);
        }
    }

    private DefaultTableModel modeloTabelaTelefone(){
        DefaultTableModel modeloTelefone = new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "TELEFONE", "CONTATO", "DATA_CADASTRO"}
        );
        view.getTableTelefones().setModel(modeloTelefone);
        modeloTelefone.setRowCount(0);
        return modeloTelefone;
    }

    private void escreverTabelaTelefones(List<Telefone>telefones, DefaultTableModel modeloTelefone){
        for (Telefone telefone : telefones) {
            Object[] rowData = {
                    telefone.getIdTelefone(),
                    telefone.getTelefone(),
                    telefone.getContato(),
                    telefone.getDataCadastroTelefone().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            };
            modeloTelefone.addRow(rowData);
        }
    }




    private void buscarClienteNome() {
        limparTabela(view.getTableClientes());
        limparTabela(view.getTableEnderecos());
        limparTabela(view.getTableTelefones());
        String nome = view.getTextFieldNome().getText();
        List<Cliente> cliente;
        cliente = clienteDao.buscarClientePorNome(nome);
        if (!cliente.isEmpty()) {
            DefaultTableModel modeloCliente = modeloTabelaCliente();
            for (Cliente cliente1 : cliente) {
                Object[] rowData = {
                        cliente1.getIdCliente(),
                        cliente1.getNome(),
                        cliente1.getCpf(),
                        cliente1.getRegime(),
                        cliente1.getStatusCliente(),
                        cliente1.getDataCadastroCliente().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        cliente1.getObservacao()
                };
                modeloCliente.addRow(rowData);
            }
        } else {
            TelaMensagensSistema.mostrarInformacao(MensagensSistema.CLIENTE_NAO_ENCONTRADO);
        }
        limparTabela(view.getTableEnderecos());
        limparTabela(view.getTableTelefones());
    }

    public void buscarClienteCpf(){
        limparTabela(view.getTableClientes());
        limparTabela(view.getTableEnderecos());
        limparTabela(view.getTableTelefones());
        String cpf = view.getTextFieldCpf().getText();
        Cliente cliente;
        cliente = clienteDao.buscarClientePorCpf(cpf);
        System.out.println(cliente.getDataCadastroCliente());
        DefaultTableModel modeloCliente = modeloTabelaCliente();
        modeloCliente.addRow(carregarTabelaClienteUnico(cliente));
        escreverCamposCliente(cliente);
        DefaultTableModel modeloEndereco = modeloTabelaEndereco();
        List<Endereco> enderecos = cliente.getEnderecos();
        for (Endereco endereco : enderecos) {
            Object[] rowData = {
                    endereco.getIdEndereco(),
                    endereco.getLogradouro(),
                    endereco.getNumero(),
                    endereco.getBairro(),
                    endereco.getCidade(),
                    endereco.getCep(),
                    endereco.getComplemento(),
                    endereco.getTipo(),
                    endereco.getDataCadastroEndereco().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    endereco.getObservacaoEndereco()
            };
            modeloEndereco.addRow(rowData);
        }
        if (!cliente.getEnderecos().isEmpty()) {
            escreverCamposEndereco(cliente.getEnderecos().getFirst());
        } else {
            limparCamposEndereco();
        }
        DefaultTableModel modeloTelefone = modeloTabelaTelefone();
        List<Telefone> telefones = cliente.getTelefones();
        for (Telefone telefone : telefones) {
            Object[] rowData = {
                    telefone.getIdTelefone(),
                    telefone.getTelefone(),
                    telefone.getContato(),
                    telefone.getDataCadastroTelefone().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            };
            modeloTelefone.addRow(rowData);
        }
        if (!cliente.getTelefones().isEmpty()) {
            escreverCamposTelefone(cliente.getTelefones().getFirst());
        } else {
            limparCamposTelefone();
        }
    }

    /*** funções Endereço ***/






    /*** funções Telefone ***/


    private void excluirTelefone(){
        try{
            if (!TelaMensagensSistema.mostrarConfirmacao("Voce tem certeza?")){
                return;
            }
            Telefone telefone;
            telefone = carregarNovoTelefone();
            telefoneDao.excluirTelefone(Long.valueOf(view.getTextFieldIdTelefone().getText()));
            TelaMensagensSistema.mostrarInformacao(MensagensSistema.TELEFONE_EXLUIDO_SUCESSO);
            cliente = clienteDao.buscarClientePorId(Long.valueOf(view.getTextFieldId().getText()));
            DefaultTableModel modeloTelefone = modeloTabelaTelefone();
            List<Telefone> telefones = cliente.getTelefones();
            escreverTabelaTelefones(telefones, modeloTelefone);
            if (!cliente.getTelefones().isEmpty()) {
                escreverCamposTelefone(cliente.getTelefones().getFirst());
            } else {
                limparCamposTelefone();
            }
        } catch (EnderecoInvalidoException e) {
            TelaMensagensSistema.mostrarErro(e.getMessage());
        } catch (Exception e){
            ErrorHandler.handle(e);
        }
    }

    /*** funções de busca ***/
    /*
    public void buscarEnderecoCLiente(){
        DefaultTableModel novoModelo = new DefaultTableModel(
                new Object[][] {}, // Dados inicialmente vazios
                new String[] {"ID", "Logradouro", "Numero", "Bairro", "Cidade", "Uf",
                        "CEP", "Complemento","Tipo", "Data_Cadastro", "Obs"} // Cabeçalhos das colunas
        );
        view.getTableCadEnderecos().setModel(novoModelo);
        novoModelo.setRowCount(0);
        Long idCliente = view.getTextFieldCadIdLong();
        List<Endereco>enderecos;
        enderecos = enderecoDao.buscarEnderecosDoCliente(idCliente);


        if (enderecos != null && !enderecos.isEmpty()) {
            for (Endereco endereco : enderecos) {
                Object[] rowData = {
                        endereco.getIdEndereco(),
                        endereco.getLogradouro(),
                        endereco.getNumero(),
                        endereco.getBairro(),
                        endereco.getCidade(),
                        endereco.getUf(),
                        endereco.getCep(),
                        endereco.getComplemento(),
                        endereco.getTipo(),
                        endereco.getDataCadastroEndereco().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        endereco.getObservacaoEndereco()
                };

                // Adicionando linha ao modelo
                novoModelo.addRow(rowData);
            }
        }
    }


    public void buscarTelefoneCLiente(){
        DefaultTableModel novoModelo = new DefaultTableModel(
                new Object[][] {}, // Dados inicialmente vazios
                new String[] {"ID", "Telefone", "Contato", "Data_Cadastro"} // Cabeçalhos das colunas
        );
        view.getTableCadTelefones().setModel(novoModelo);
        novoModelo.setRowCount(0);
        Long idCliente = view.getTextFieldCadIdLong();
        List<Telefone>telefones;
        telefones = telefoneDao.buscarTelefonesDoCliente(idCliente);


        if (telefones != null && !telefones.isEmpty()) {
            for (Telefone telefone : telefones) {
                Object[] rowData = {
                        telefone.getIdTelefone(),
                        telefone.getTelefone(),
                        telefone.getContato(),
                        telefone.getDataCadastroTelefone().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                };
                novoModelo.addRow(rowData);
            }
        }
    }*/

    private void tratarExcecao(Exception e) {
        if (e instanceof IllegalArgumentException) {
            TelaMensagensSistema.mostrarErro(e.getMessage());
        } else if (e instanceof ConstraintViolationException) {
            TelaMensagensSistema.mostrarErro(MensagensSistema.ERRO_VIOLACAO_RESTRICAO);
        } else if (e instanceof PersistenceException) {
            TelaMensagensSistema.mostrarErro(MensagensSistema.ERRO_BANCO_DADOS);
        } else {
            e.printStackTrace(); // Apenas para logs
            TelaMensagensSistema.mostrarErro(MensagensSistema.ERRO_GENERICO);
        }
    }


}
