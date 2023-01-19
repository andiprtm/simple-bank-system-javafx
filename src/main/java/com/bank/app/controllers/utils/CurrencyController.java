package com.bank.app.controllers.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CurrencyController {
    public String getIndonesianCurrency (BigDecimal decimal) {
        DecimalFormat indonesiaCurrency = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        indonesiaCurrency.setDecimalFormatSymbols(formatRp);
        return indonesiaCurrency.format(decimal);
    }
}
