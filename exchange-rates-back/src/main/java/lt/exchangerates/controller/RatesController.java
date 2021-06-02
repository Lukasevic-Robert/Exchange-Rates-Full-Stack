package lt.exchangerates.controller;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lt.exchangerates.DTO.RequestRatesForCurrency;
import lt.exchangerates.DTO.RequestedRatesForSpecificDate;
import lt.exchangerates.DTO.ResponseCalculatedRates;
import lt.exchangerates.model.FxRatesHandling;
import lt.exchangerates.model.currentRates.SpecificDayCurrencies;
import lt.exchangerates.service.RatesService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rates")
public class RatesController {

	private final RatesService ratesService;


//	@PostMapping("/getFxRatesForCurrency")
//	public FxRatesHandling getFxRatesForCurrency(@RequestBody RequestRatesForCurrency requestRatesForCurrency) {
//
//		String URI = ratesService.getFullURI("/getFxRatesForCurrency?", requestRatesForCurrency.toString());
//		return ratesService.decodeXml(FxRatesHandling.class, URI);
//	}
	
	//using xml file download instead
	@PostMapping("/getFxRatesForCurrency")
	public List<ResponseCalculatedRates> getFxRatesForCurrency(@RequestBody RequestRatesForCurrency requestRatesForCurrency) {

		String URI = ratesService.getSpecificCcyFullURI(requestRatesForCurrency);
		SpecificDayCurrencies specificDayCurrencies = ratesService.decodeXml(SpecificDayCurrencies.class, URI);
		List<SpecificDayCurrencies> pairCurrenciesList = ratesService.toSpecificCcyPairs(specificDayCurrencies);
		List<ResponseCalculatedRates> response = ratesService.toResponseCalculatedRatesList(pairCurrenciesList);
		
		return ratesService.filteredResponse(response, requestRatesForCurrency.getDtFrom());
	}

//	@GetMapping("/getFxRates")
//	public FxRatesHandling getFxRates(@RequestBody RequestedRatesForSpecificDate requestedRatesForSpecificDate) {
//
//		String URI = ratesService.getFullURI("/getFxRates?", requestedRatesForSpecificDate.toString());
//		return ratesService.decodeXml(FxRatesHandling.class, URI);
//	}
	
	
	//using xml file download instead
	@PostMapping("/FxRates")
	public List<ResponseCalculatedRates> getFxRates(@RequestBody RequestedRatesForSpecificDate requestedRatesForSpecificDate) {
		Date date = ratesService.stringToDate(requestedRatesForSpecificDate.getDt());
		List<SpecificDayCurrencies> pairCurrenciesList = ratesService.getCurrencySequenceDtFromDtTo(date);
		return ratesService.toResponseCalculatedRatesList(pairCurrenciesList);
	}

	@GetMapping("/getCurrentFxRates")
	public FxRatesHandling getCurrentFxRates(@RequestParam String tp) {
		
		String URI = ratesService.getFullURI("/getCurrentFxRates?", "tp=" + tp);
		return ratesService.decodeXml(FxRatesHandling.class, URI);
	}
	
	
	@GetMapping("/getAvailableCurrencies")
	public SpecificDayCurrencies getAvailableCurrencies() {
		return ratesService.getSpecificDayCurrencies(new Date());
	}
}
