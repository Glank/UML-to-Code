package kirstein.uml;

import java.util.HashMap;

import kirstein.parsing.WarningLog;
import kirstein.umlet.*;

public class ClassLinkerUtil {
	public static void linkClasses(UMLetDocument uml, HashMap<UMLetClass, UMLClass> classMap){
		for(int i = 0; i < uml.countRelations(); i++){
			linkClass(uml.getRelation(i),uml,classMap);
		}
	}
	
	private static void linkClass(UMLetRelation relation, UMLetDocument uml, HashMap<UMLetClass, UMLClass> classMap){
		if(relation.getLineType()==UMLetRelationLineType.DASHED){
			WarningLog.log(new UMLetToUMLParsingWarning("Relation Not Parsed: Dashed Line Type"));
			return;
		}
		
		//get start and end class connections
		UMLetClass start,end;
		start = relation.getStartClass(uml);
		end = relation.getEndClass(uml);
		
		//check for unconnected end points
		if(start==null || end==null){
			WarningLog.log(new UMLetToUMLParsingWarning("Relation Not Parsed: Unconnected"));
			return;
		}
		
		//do by direction
		switch(relation.getDirection()){
		//only single-direction relations matter
		case BIDIRECTIONAL:
		case NON_DIRECTIONAL:
			WarningLog.log(new UMLetToUMLParsingWarning("Relation Not Parsed: "+relation.getDirection()));
			break;
		case FORWARD:
			//currently, only implements and extends are parsed
			if(relation.getForwardPointType()!=UMLetRelationPointType.HOLLOW_POINT){
				WarningLog.log(new UMLetToUMLParsingWarning("Relation Not Parsed: "+relation.getForwardPointType()));
				break;
			}
			classMap.get(start).extendOrImplement(classMap.get(end));
			break;
		case BACKWARD:
			if(relation.getBackwardPointType()!=UMLetRelationPointType.HOLLOW_POINT){
				WarningLog.log(new UMLetToUMLParsingWarning("Relation Not Parsed: "+relation.getBackwardPointType()));
				break;
			}
			classMap.get(end).extendOrImplement(classMap.get(start));
		}
	}
}
