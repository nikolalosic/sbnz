package sbnz.soft.nikola.domain.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.io.Serializable;
import java.util.Date;

@Role(Role.Type.EVENT)
@Expires("30s")
public class HeartBeatEvent implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private Date executionTime;
    
    public HeartBeatEvent() { 
    	super();
    }

	public HeartBeatEvent(Date executionTime) {
		super();
		this.executionTime = executionTime;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}
}
