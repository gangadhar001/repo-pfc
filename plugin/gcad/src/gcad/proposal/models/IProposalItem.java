package gcad.proposal.models;

import java.util.Date;

import org.eclipse.core.runtime.IAdaptable;

public interface IProposalItem extends IAdaptable{
	
	String getTitle();
	String getDescription();
	Date getDate();
	
	static IProposalItem[] NONE = new IProposalItem[] {};
	
}
