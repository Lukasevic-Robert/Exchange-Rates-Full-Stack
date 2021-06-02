package lt.exchangerates.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestRatesForCurrency {

	private String tp;
	private String ccy;
	private String dtFrom;
	private String dtTo;

	@Override
	public String toString() {
		return String.format("tp=" + tp + "&" + "ccy=" + ccy + "&" + "dtFrom=" + dtFrom + "&" + "dtTo=" + dtTo);

	}
}
