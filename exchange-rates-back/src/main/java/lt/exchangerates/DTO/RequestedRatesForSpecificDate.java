package lt.exchangerates.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestedRatesForSpecificDate {
	
	private String tp;
	private String dt;
	
	@Override
	public String toString() {
		return String.format("tp=" + tp + "&" + "dt=" + dt);
	}

}
