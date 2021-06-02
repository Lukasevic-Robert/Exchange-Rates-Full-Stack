package lt.exchangerates.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import lt.exchangerates.DTO.RequestRatesForCurrency;
import lt.exchangerates.DTO.ResponseCalculatedRates;
import lt.exchangerates.model.currentRates.CurrencyItem;
import lt.exchangerates.model.currentRates.SpecificDayCurrencies;

@Service
public class RatesService {

	private final String URI = "http://www.lb.lt/webservices/fxrates/FxRates.asmx";
	private final String xmlURI = "https://www.lb.lt/lt/currency/daylyexport/?xml=1&class=Eu&type=day&date_day=";

	@SuppressWarnings("unchecked")
	public <T> T decodeXml(Class<T> clazz, String URI) {
		T rates = null;
		try {
			URL url = new URL(URI);
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			rates = (T) jaxbUnmarshaller.unmarshal(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rates;
	}

	public SpecificDayCurrencies getSpecificDayCurrencies(Date date) {

		String validDate = dateToString(date);
		SpecificDayCurrencies specificDayCurrencies = decodeXml(SpecificDayCurrencies.class, xmlURI + validDate);
		while (specificDayCurrencies.getCurrencyItem().size() < 2) {
			date = DateUtils.addDays(date, -1);
			validDate = dateToString(date);
			specificDayCurrencies = decodeXml(SpecificDayCurrencies.class, xmlURI + validDate);
		}
		return specificDayCurrencies;
	}

	public List<SpecificDayCurrencies> getCurrencySequenceDtFromDtTo(Date date) {

		SpecificDayCurrencies specificDayCurrencies = getSpecificDayCurrencies(date);
		Date firstDate = specificDayCurrencies.getCurrencyItem().get(1).getData();
		Date dayBefore = DateUtils.addDays(firstDate, -1);
		List<SpecificDayCurrencies> pairCurrenciesList = new ArrayList<>();
		pairCurrenciesList.add(getSpecificDayCurrencies(dayBefore));
		pairCurrenciesList.add(specificDayCurrencies);

		return pairCurrenciesList;
	}

	public List<SpecificDayCurrencies> toSpecificCcyPairs(SpecificDayCurrencies specificDayCurrencies) {
		List<SpecificDayCurrencies> pairCurrenciesList = new ArrayList<>();
		SpecificDayCurrencies ccyBefore = new SpecificDayCurrencies();
		ccyBefore.setCurrencyItem(new ArrayList<>());
		SpecificDayCurrencies ccyAfter = new SpecificDayCurrencies();
		ccyAfter.setCurrencyItem(new ArrayList<>());

		int loopLength = specificDayCurrencies.getCurrencyItem().size();
		int j = 0;
		for (int i = 1; i < loopLength; i++) {
			ccyBefore.getCurrencyItem().add(specificDayCurrencies.getCurrencyItem().get(i));
			ccyAfter.getCurrencyItem().add(specificDayCurrencies.getCurrencyItem().get(j));
			j++;
		}
		pairCurrenciesList.add(ccyBefore);
		pairCurrenciesList.add(ccyAfter);

		return pairCurrenciesList;
	}

	public List<ResponseCalculatedRates> toResponseCalculatedRatesList(List<SpecificDayCurrencies> currenciesSequence) {

		List<ResponseCalculatedRates> currenciesSequenceResponse = new ArrayList<>();
		int loopLength = currenciesSequence.get(0).getCurrencyItem().size();
		for (int i = 1; i < loopLength; i++) {
			currenciesSequenceResponse.add(toResponseCalculatedRates(currenciesSequence.get(0).getCurrencyItem().get(i),
					currenciesSequence.get(1).getCurrencyItem().get(i)));
		}
		return currenciesSequenceResponse;
	}

	public ResponseCalculatedRates toResponseCalculatedRates(CurrencyItem before, CurrencyItem after) {
		BigDecimal beforeRatio = new BigDecimal(before.getSantykis().replace(',', '.'));
		BigDecimal afterRatio = new BigDecimal(after.getSantykis().replace(',', '.'));
		BigDecimal hundred = new BigDecimal(100);
		BigDecimal changeUnit = afterRatio.subtract(beforeRatio);
		String changePercent = String
				.valueOf(changeUnit.multiply(hundred).divide(beforeRatio, 4, RoundingMode.HALF_UP));

		ResponseCalculatedRates responseCalculatedRates = new ResponseCalculatedRates(before.getPavadinimas(),
				before.getValiutos_kodas(), afterRatio, changeUnit, changePercent, dateToString(after.getData()));

		return responseCalculatedRates;
	}
	
	public List<ResponseCalculatedRates> filteredResponse(List<ResponseCalculatedRates> currenciesSequenceResponse, String initialDate){
		return currenciesSequenceResponse.stream().filter(i -> !stringToDate(i.getDate()).before(stringToDate(initialDate))).collect(Collectors.toList());
	}

	public String dateToString(Date date) {
		String pattern = "yyyy-MM-dd";
		String dateInString = new SimpleDateFormat(pattern).format(date);
		return dateInString;
	}

	public Date stringToDate(String date) {
		Date dateFromString = new Date();
		try {
			dateFromString = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateFromString;
	}

	public String getFullURI(String endpoint, String params) {
		String fullURI = URI + endpoint + params;
		return fullURI;
	}

	public String getSpecificCcyFullURI(RequestRatesForCurrency requestRatesForCurrency) {
		Date dateWeekBefore = DateUtils.addDays(stringToDate(requestRatesForCurrency.getDtFrom()), -7);
		String date = dateToString(dateWeekBefore);
		String URI = "https://www.lb.lt/lt/currency/exportlist/?xml=1&currency=" + requestRatesForCurrency.getCcy()
				+ "&ff=1&class=Eu&type=day&date_from_day=" + date + "&date_to_day=" + requestRatesForCurrency.getDtTo();
		return URI;
	}
}
