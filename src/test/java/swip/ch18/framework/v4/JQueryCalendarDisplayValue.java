package swip.ch18.framework.v4;


import swip.ch17datepicker.jquerydatepicker.JQueryByClassName;
import swip.ch18.framework.Browser;

import java.time.Month;
import java.util.function.Function;

import static swip.ch18.framework.v4.JQueryById.UI_DATEPICKER_DIV;


public enum JQueryCalendarDisplayValue implements Function<Browser, Integer> {

    /**
     * Locate the integer value representing displayed year on a calendar
     */
    DISPLAY_YEAR {
        public Integer apply(Browser browser) {
            String text = browser.untilFound(UI_DATEPICKER_DIV)
                .getText(JQueryByClassName.DISPLAY_YEAR);
            return Integer.parseInt(text);
        }
    },               //<1>

    /**
     * Locate the integer value representing displayed month on a calendar
     */
    DISPLAY_MONTH {
        public Integer apply(Browser browser) {
            String text = browser.untilFound(UI_DATEPICKER_DIV)
                .getText(JQueryByClassName.DISPLAY_MONTH).toUpperCase();
            return Month.valueOf(text).ordinal();
        }
    }             //<2>
}
