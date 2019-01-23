package sbnz.soft.nikola.domain.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.io.Serializable;

@Role(Role.Type.EVENT)
@Expires("12h")
public class UrgentDialysisEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	public UrgentDialysisEvent() {
		super();
	}
	
}
