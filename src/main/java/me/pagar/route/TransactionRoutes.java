package me.pagar.route;

import me.pagar.model.PagarMeException;
import me.pagar.route.responseobject.Transaction;

import java.util.HashMap;

public class TransactionRoutes {

    private HttpRequester httpRequester;
    private PagarMeAPiConfigurations configurations;

    public TransactionRoutes(HttpRequester httpRequester, PagarMeAPiConfigurations configurations){
        this.httpRequester = httpRequester;
        this.configurations = configurations;
    }

    public Transaction create(CanBecomeKeyValueVariable parameters) throws PagarMeException{
        String url = configurations.baseUrl.concat(PagarMeEndpointsFormats.TRANSACTION_CREATE);
        String jsonString = parameters.toJson();
        HashMap<String, String> headers = new HashMap<String, String>();
        HttpResponse apiResponse = httpRequester.post(url, jsonString, headers);

        Integer statusCode = apiResponse.statusCode();
        String apiResponseBody = apiResponse.body();
        Boolean apiReturnOk = statusCode >= 200 && statusCode <= 299;
        if(apiReturnOk){
            String jsonResponse = apiResponseBody;
            Transaction newTransaction = new Transaction(jsonResponse);
            return newTransaction;
        } else {
            throw new PagarMeException(statusCode, url, "POST", apiResponseBody);
        }
    }
}