package modelo;

/**
 *  @author Fl�via de Jesus Correia
 * 
 * */

public class Professor extends Endereco{
	

	/**
	 * em professor, falta fazer arquivo, fazer verifica��o da �rea de atua��o para 
	 * n�o colocar �tens repetidos no combobox
	 */
	private String nome;
	private String matricula;
    private String area;
    private String carga_Horaria;
    private String cpf;
	private String rg;
	private String uf;
	private String dataExpedicao;
	private String orgaoExp;
	
	public Professor(String id, String rua, String numero, String complemento, String bairro, String cidade,
			String estado, String nome, String matricula, String area, String carga_Horaria, String cpf, String rg,
			String uf, String dataExpedicao, String orgaoExp) {
		super(id, rua, numero, complemento, bairro, cidade, estado);
		this.nome = nome;
		this.matricula = matricula;
		this.area = area;
		this.carga_Horaria = carga_Horaria;
		this.cpf = cpf;
		this.rg = rg;
		this.uf = uf;
		this.dataExpedicao = dataExpedicao;
		this.orgaoExp = orgaoExp;
	}
	
	 public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCarga_Horaria() {
        return carga_Horaria;
    }

    public void setCarga_Horaria(String dados) {
        this.carga_Horaria = dados;
    }
   
    public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}
	

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getDataExpedicao() {
		return dataExpedicao;
	}

	public void setDataExpedicao(String dataExpedicao) {
		this.dataExpedicao = dataExpedicao;
	}

	public String getOrgaoExp() {
		return orgaoExp;
	}

	public void setOrgaoExp(String orgaoExp) {
		this.orgaoExp = orgaoExp;
	}
}