package swip.ch15table;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import swip.ch14elements.framework.Browser;
import swip.ch14elements.framework.Element;
import swip.framework.WebDriverRunner;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@RunWith(WebDriverRunner.class)
public class TableIT {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private Browser browser;

    @Inject
    public void setWebDriver(WebDriver webDriver) {
        browser = new Browser(webDriver);
    }

    @Test
    public void testReadFromTable() {

        browser.get("http://localhost:8080/people-table.html");

        Function<List<Element>, Person> mapper = (cells) -> new Person(
                Integer.parseInt(cells.get(0).getText()),
                cells.get(1).getText(),
                cells.get(2).getText(),
                Integer.parseInt(cells.get(3).getText())
        );
        Table<Person> table = new Table<>(
                browser.findElement(By.tagName("table")),
                mapper
        );

        TableContents<Person> actual = table.getContents();

        TableContents<Person> expected = new TableContents<>(
                Arrays.asList("Id", "First Name", "Last Name", "Age"),
                Arrays.asList(
                        new Person(1, "Eve", "Jackson", 94)
                        , new Person(2, "John", "Doe", 80)
                        , new Person(3, "Adam", "Johnson", 67)
                        , new Person(4, "Jill", "Smith", 50)
                )
        );

        assertEquals(expected.describeDiff(actual), expected, actual);
    }

    @Test
    public void missingExpectedValues() {

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("unexpected rows appeared: [new Person(\"3\",\"Adam\",\"Johnson\",67)]\n" +
                "expected rows not found: [new Person(\"5\",\"Jack\",\"Clyde\",78)] ");

        browser.get("http://localhost:8080/people-table.html");

        Function<List<Element>, Person> mapper = (cells) -> new Person(
                Integer.parseInt(cells.get(0).getText()),
                cells.get(1).getText(),
                cells.get(2).getText(),
                Integer.parseInt(cells.get(3).getText())
        );
        Table<Person> table = new Table<>(
                browser.findElement(By.tagName("table")),
                mapper
        );

        TableContents<Person> actual = table.getContents();

        TableContents<Person> expected = new TableContents<>(
                Arrays.asList("Id", "First Name", "Last Name", "Age"),
                Arrays.asList(
                        new Person(1, "Eve", "Jackson", 94)
                        , new Person(2, "John", "Doe", 80)
                        , new Person(4, "Jill", "Smith", 50)
                        , new Person(5, "Jack", "Clyde", 78)
                )
        );

        assertEquals(expected.describeDiff(actual), expected, actual);
    }
}