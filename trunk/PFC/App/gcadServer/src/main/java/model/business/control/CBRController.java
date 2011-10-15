package model.business.control;

import java.util.ArrayList;
import java.util.List;

import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.EnumAlgorithmCBR;
import model.business.control.CBR.retrieveAlgorithms.EuclDistanceMethod;
import model.business.control.CBR.retrieveAlgorithms.NNMethod;
import model.business.knowledge.Groups;
import model.business.knowledge.Operation;
import model.business.knowledge.Operations;
import model.business.knowledge.Project;
import model.business.knowledge.Subgroups;

/**
 * This class represents the CBR Controller
 */
public class CBRController {

	public static List<Project> executeAlgorithm(long sessionId, EnumAlgorithmCBR algorithmName, List<Project> cases, Project caseToEval, ConfigCBR config, int k) throws NonPermissionRoleException, NotLoggedException, Exception {
		SessionController.checkPermission(sessionId, new Operation(Groups.CBR.name(), Subgroups.CBR.name(), Operations.Execute.name()));		

		List<Project> result = new ArrayList<Project>();
		switch(algorithmName) {
		case NN:
			result = NNMethod.evaluateSimilarity(caseToEval, cases, config, k);
			break;
		case Euclidean:
			result = EuclDistanceMethod.evaluateSimilarity(caseToEval, cases, config, k);
			break;
		//case Sim:
	//		result = SimMethod.evaluateSimilarity(caseToEval, cases, config, k);
	//		break;
		
		}
		return result;
	}
}
