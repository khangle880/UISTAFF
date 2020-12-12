package classComponent;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.util.converter.NumberStringConverter;

public class MyNumberStringConverter extends NumberStringConverter {
    public MyNumberStringConverter() {
        super();
    }

    public MyNumberStringConverter(Locale locale, String pattern) {
        super(locale, pattern);
    }

    public MyNumberStringConverter(Locale locale) {
        super(locale);
    }

    public MyNumberStringConverter(NumberFormat numberFormat) {
        super(numberFormat);
    }

    public MyNumberStringConverter(String pattern) {
        super(pattern);
    }

    @Override
    public Number fromString(String value) {
        // to transform the double, given by the textField, just multiply by 100 and
        // round if any left
        Number rValue = Math.round(super.fromString(value).doubleValue() * 100);
        return rValue.longValue();
    }

    @Override
    public String toString(Number value) {
        if (value == null) {
            return "0";
        }
        // Check for too big long value
        // If the long is too big, it could result in a strange double value.
        if (value.longValue() > 1000000000000l || value.longValue() < -1000000000000l) {
            return "0";
        }
        BigDecimal myBigDecimal = new BigDecimal(value.longValue());
        // to convert the long to a double (currency with fractional digits)
        myBigDecimal = myBigDecimal.movePointLeft(2);
        double asDouble = myBigDecimal.doubleValue();
        if (asDouble == Double.NEGATIVE_INFINITY || asDouble == Double.POSITIVE_INFINITY) {
            return "";
        }
        return super.toString(asDouble);
    }
}