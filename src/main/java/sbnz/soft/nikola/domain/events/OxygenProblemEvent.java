package sbnz.soft.nikola.domain.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.io.Serializable;

@Role(Role.Type.EVENT)
@Expires("30m")
public class OxygenProblemEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	public OxygenProblemEvent() {
		super();
	}
}
