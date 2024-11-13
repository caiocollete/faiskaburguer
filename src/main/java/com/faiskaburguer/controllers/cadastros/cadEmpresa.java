package com.faiskaburguer.controllers.cadastros;

import com.faiskaburguer.db.dal.EmpresaDAL;
import com.faiskaburguer.db.dal.EnderecoDAL;
import com.faiskaburguer.db.entidade.Empresa;
import com.faiskaburguer.db.entidade.Endereco;
import com.faiskaburguer.db.util.Mascaras;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class cadEmpresa {

    @FXML
    private TextField emp_razao_field;

    @FXML
    private TextField emp_fantasia_field;

    @FXML
    private TextField emp_cnpj_field;

    @FXML
    private TextField emp_telefone_field;

    @FXML
    private TextField emp_email_field;

    @FXML
    private TextField emp_cep_field;

    @FXML
    private TextField emp_rua_field;

    @FXML
    private TextField emp_valoremb_field;

    @FXML
    private TextField emp_nrua_field;


    @FXML
    protected void CadastrarEmpresa(){
        String razao = emp_razao_field.getText();
        String fantasia = emp_fantasia_field.getText();
        String cnpj = Mascaras.mascCnpj(emp_cnpj_field.getText());
        String telefone = Mascaras.mascFone(emp_telefone_field.getText());
        String email = emp_email_field.getText();
        String cep = emp_cep_field.getText();
        String rua = emp_rua_field.getText();
        String nrua = emp_nrua_field.getText();
        String valoremb = emp_valoremb_field.getText();


        //public Empresa(String emp_razao, String emp_fantasia, String emp_cnpj, Endereco endereco, String emp_fone, String emp_email, Double emp_vlremb) {
        Endereco endereco = new Endereco(cep,rua,nrua);
        Empresa empresa = new Empresa(razao,fantasia,cnpj,endereco,telefone,email,Double.parseDouble(valoremb));

        EnderecoDAL enderecoDAL = new EnderecoDAL();
        if(enderecoDAL.gravar(endereco)){
            EmpresaDAL empresaDAL = new EmpresaDAL();
            empresaDAL.gravar(empresa);
        }

        emp_razao_field.clear();
        emp_fantasia_field.clear();
        emp_cnpj_field.clear();
        emp_telefone_field.clear();
        emp_email_field.clear();
        emp_cep_field.clear();
        emp_rua_field.clear();
        emp_nrua_field.clear();
        emp_valoremb_field.clear();
    }


}
