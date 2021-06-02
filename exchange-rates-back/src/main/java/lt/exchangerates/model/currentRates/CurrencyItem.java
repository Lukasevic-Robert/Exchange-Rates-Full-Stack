package lt.exchangerates.model.currentRates;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrencyItem", propOrder = { "pavadinimas", "valiutos_kodas", "santykis", "data" })
public class CurrencyItem {

	 @XmlElement(name = "pavadinimas")
	protected String pavadinimas;
	 
	 @XmlElement(name = "valiutos_kodas")
	protected String valiutos_kodas;
	 
	 @XmlElement(name = "santykis")
	protected String santykis;
	 
	 @XmlElement(name = "data")
	protected Date data;
	
}
