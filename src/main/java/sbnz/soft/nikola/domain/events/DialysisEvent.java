package sbnz.soft.nikola.domain.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.io.Serializable;
import java.util.Date;

@Role(Role.Type.EVENT)
@Expires("12h")
public class DialysisEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date executionTime;
	private int amount;
	
	public DialysisEvent() {
		
	}

	public DialysisEvent(Date executionTime, int amount) {
		super();
		this.executionTime = executionTime;
		this.amount = amount;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
