package modelo;

import java.util.ArrayList;

/**
 *  @author Fl�via de Jesus Correia
 * 
 * */

public class Disciplina {
    private String  area_Disciplina;
    private String  nome_Disciplina;
    private String  semestre;
    private String  curso;
    private String tipoDisciplina;
    private ArrayList<String>  preRequisitos;
    private String 	carga_Horaria;
    private boolean ePreRequisito;
    private boolean optativa;
    private boolean obrigatoria;
    private boolean teorica;
    private boolean pratica;
    private boolean estagio;

    public String getArea_Disciplina() {
        return area_Disciplina;
    }

    public void setArea_Disciplina(String area_Disciplina) {
        this.area_Disciplina = area_Disciplina;
    }

    public String getNome_Disciplina() {
        return nome_Disciplina;
    }

    public void setNome_Disciplina(String nome_Disciplina) {
        this.nome_Disciplina = nome_Disciplina;
    }

    public String getCarga_Horaria() {
        return carga_Horaria;
    }

    public void setCarga_Horaria(String carga_Horaria) {
        this.carga_Horaria = carga_Horaria;
    }
    
    public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	
	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}  
	
    public String getTipoDisciplina() {
		return tipoDisciplina;
	}

	public void setTipoDisciplina(String tipoDisciplina) {
		this.tipoDisciplina = tipoDisciplina;
	}

	public ArrayList<String> getPreRequisitos() {
		return preRequisitos;
	}

	public void setPreRequisitos(ArrayList<String> preRequisitos2) {
		this.preRequisitos = preRequisitos2;
	}

	public boolean getTeorica() {
        return teorica;
    }

    public void setTeorica(boolean teorica) {
        this.teorica = teorica;
    }

    public boolean getPratica() {
        return pratica;
    }

    public void setPratica(boolean pratica) {
        this.pratica = pratica;
    }

    public boolean getEstagio() {
        return estagio;
    }

    public void setEstagio(boolean estagio) {
        this.estagio = estagio;
    }

	public boolean getEPreRequisito() {
		return ePreRequisito;
	}

	public void setEPreRequisito(boolean ePreRequisito) {
		this.ePreRequisito = ePreRequisito;
	}

	public boolean getOptativa() {
		return optativa;
	}

	public void setOptativa(boolean optativa) {
		this.optativa = optativa;
	}

	public boolean isObrigatoria() {
		return obrigatoria;
	}

	public void setObrigatoria(boolean obrigatoria) {
		this.obrigatoria = obrigatoria;
	}
    
}