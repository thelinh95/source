package swip.ch17datepicker.jquerydatepicker.v4;


import swip.ch15pageflow.v2.framework.Browser;

import java.time.Month;

import static swip.ch17datepicker.jquerydatepicker.v3.JQueryByClassName.*;
import static swip.ch17datepicker.jquerydatepicker.v3.JQueryById.DATE_FIELD;
import static swip.ch17datepicker.jquerydatepicker.v3.JQueryById.UI_DATEPICKER_DIV;


public class JQueryDatePickerPage {

    private final Browser browser;    //<1>

    private final Datepicker datepicker;   //<2>

    public void pick(Month month, int day, int year) {
        datepicker.pick(month, day, year);
    }  //<9>

    public String getDate() {
        return browser.getInputText(DATE_FIELD);
    }       //<10>

    public JQueryDatePickerPage(Browser b) {   //<3>
        this.browser = b;
        this.datepicker = new Datepicker(  //<4>
            new Calendar(browser,
                (Browser browser) -> {
                    browser.click(DATE_FIELD);
                }
            ),
            new YearPicker(browser,
                (Browser browser) -> {
                    for (int i = 0; i < 12; i++) {
                        previousMonth();
                    }
                },
                (Browser browser) -> {
                    for (int i = 0; i < 12; i++) {
                        nextMonth();
                    }
                },
                (Browser browser) ->
                    Integer.parseInt(
                        browser.untilFound(UI_DATEPICKER_DIV)
                            .getText(DISPLAY_YEAR)
                    )

            ),
            new MonthPicker(browser,
                (Browser browser) -> {
                    previousMonth();
                },
                (Browser browser) -> {
                    nextMonth();
                },
                (Browser browser) ->
                    Month.valueOf(
                        browser.untilFound(UI_DATEPICKER_DIV)
                            .getText(DISPLAY_MONTH)
                            .toUpperCase()
                    ).ordinal()
            ), new JQueryDayPicker(browser));
    }

    private void previousMonth() {
        browser.findElement(UI_DATEPICKER_DIV)
            .click(PREV_MONTH_BUTTON);  //<3>
    }

    private void nextMonth() {
        browser.findElement(UI_DATEPICKER_DIV)
            .click(NEXT_MONTH_BUTTON);  //<4>
    }

}
