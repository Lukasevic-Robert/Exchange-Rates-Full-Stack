package lt.exchangerates.model.currentRates;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AvailableCurrencies")
public class SpecificDayCurrencies {

	@XmlElement(name = "item")
	protected List<CurrencyItem> currencyItem;
}
