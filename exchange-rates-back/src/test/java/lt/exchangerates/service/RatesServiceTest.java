package lt.exchangerates.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import lt.exchangerates.DTO.ResponseCalculatedRates;
import lt.exchangerates.model.currentRates.CurrencyItem;
import lt.exchangerates.model.currentRates.SpecificDayCurrencies;

@TestInstance(Lifecycle.PER_CLASS)
class RatesServiceTest {

	RatesService ratesService = new RatesService();

	CurrencyItem currencyItemB = new CurrencyItem("Australijos doleris", "AUD", "1,5573", stringToDate("2021-05-03"));
	CurrencyItem currencyItemA = new CurrencyItem("Australijos doleris", "AUD", "1,5608", stringToDate("2021-05-04"));
	
	CurrencyItem currencyItemD = new CurrencyItem("Danijos krona", "DDK", "7,4365", stringToDate("2021-05-03"));
	CurrencyItem currencyItemC = new CurrencyItem("Danijos krona", "DDK", "7,4361", stringToDate("2021-05-04"));
	
	ResponseCalculatedRates responseCalculatedRates = new ResponseCalculatedRates("Australijos doleris", "AUD",
			new BigDecimal("1.5608"), new BigDecimal("0.0035"), "0.2247", "2021-05-04");
	ResponseCalculatedRates responseCalculatedRates2 = new ResponseCalculatedRates("Danijos krona", "DDK",
			new BigDecimal("7.4361"), new BigDecimal("-0.0004"), "-0.0054", "2021-05-04");
	
	List<ResponseCalculatedRates> responseCalculatedRatesList = new ArrayList<>();
	
	SpecificDayCurrencies ccyBefore = new SpecificDayCurrencies(new ArrayList<>());
	SpecificDayCurrencies ccyAfter = new SpecificDayCurrencies(new ArrayList<>());
	SpecificDayCurrencies pairTest = new SpecificDayCurrencies(new ArrayList<>());
	
	List<SpecificDayCurrencies> currenciesSequence = new ArrayList<>();
	List<SpecificDayCurrencies> specificCcyPairs = new ArrayList<>();
	
	@BeforeAll
	public void setUp() {
		
		
		pairTest.getCurrencyItem().add(currencyItemA);
		pairTest.getCurrencyItem().add(currencyItemB);
		
		ccyBefore.getCurrencyItem().add(new CurrencyItem());
		ccyBefore.getCurrencyItem().add(currencyItemB);
		ccyBefore.getCurrencyItem().add(currencyItemD);
		
		ccyAfter.getCurrencyItem().add(new CurrencyItem());
		ccyAfter.getCurrencyItem().add(currencyItemA);
		ccyAfter.getCurrencyItem().add(currencyItemC);
		
		currenciesSequence.add(ccyBefore);
		currenciesSequence.add(ccyAfter);
		
		responseCalculatedRatesList.add(responseCalculatedRates);
		responseCalculatedRatesList.add(responseCalculatedRates2);
	}
	

	@Test
	void testToSpecificCcyPairs() {
		SpecificDayCurrencies after = new SpecificDayCurrencies(new ArrayList<>());
		after.getCurrencyItem().add(currencyItemA);
		SpecificDayCurrencies before = new SpecificDayCurrencies(new ArrayList<>());
		before.getCurrencyItem().add(currencyItemB);
		
		List<SpecificDayCurrencies> list = new ArrayList<>();
		list.add(before);
		list.add(after);
		
		assertEquals(list, ratesService.toSpecificCcyPairs(pairTest));
	}

	@Test
	void testToResponseCalculatedRatesList() {
		assertEquals(responseCalculatedRatesList, ratesService.toResponseCalculatedRatesList(currenciesSequence));
	}

	@Test
	void testToResponseCalculatedRates() {
		assertEquals(responseCalculatedRates, ratesService.toResponseCalculatedRates(currencyItemB, currencyItemA));
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

}
