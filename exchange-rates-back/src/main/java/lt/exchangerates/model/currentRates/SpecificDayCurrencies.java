package lt.exchangerates.model.currentRates;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AvailableCurrencies")
public class SpecificDayCurrencies {

	@XmlElement(name = "item")
	protected List<CurrencyItem> currencyItem;
}
