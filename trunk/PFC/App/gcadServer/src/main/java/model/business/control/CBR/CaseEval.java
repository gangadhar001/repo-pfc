package model.business.control.CBR;

import model.business.knowledge.Project;

/***
 * Class that represent the evaluation of a case, project in this case.
 */
public class CaseEval implements Comparable<Object>{
	
	private Project caseP;
	private double  eval;
	
	public CaseEval(Project caseP, Double eval)
	{
		this.caseP = caseP;
		this.eval = eval;
	}	
	
	public Project getCaseP() {
		return caseP;
	}

	public void setCaseP(Project caseP) {
		this.caseP = caseP;
	}

	public double getEval() {
		return eval;
	}

	public void setEval(double eval) {
		this.eval = eval;
	}

	public String toString()
	{
		return caseP + " -> "+ eval;
	}

	public int compareTo(Object o) {
		if(!(o instanceof CaseEval))
			return 0;
		CaseEval other = (CaseEval)o;
		if(other.getEval()< eval)
			return -1;
		else if(other.getEval() > eval)
			return 1;
		else
			return 0;
	}
}
