package lt.exchangerates.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCalculatedRates {
	
	private String ccyName;
	private String ccyCode;
	private BigDecimal ratio;
	private BigDecimal changeUnit;
	private String changePercent;
	private String date;
	
}
