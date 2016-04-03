package swip.ch18datepicker.jquery.v3;


import swip.ch15pageflow.v2.framework.Browser;

import java.util.function.Consumer;

import static swip.ch17jquerydatepicker.JQueryById.DATE_FIELD;

public class Trigger implements Consumer<Browser> {

    @Override
    public void accept(Browser browser) {
        browser.click(DATE_FIELD);
    }
}