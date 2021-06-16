package task;

import java.util.ArrayList;

import task.io.XMLParser;
import task.io.XMLWriter;

public class Main {

    public static void main(String[] args) {

        // Исходные данные и результат
        String inputFile  = "bank.xml";
        String outputFile = "result.xml";

        // Получение исходных данных из файла
        ArrayList<Person> persons = XMLParser.parsePersons(inputFile);
        String bankDeposit = XMLParser.parseAttribute(inputFile, "Bank", 0);

        // Распределение денег между людьми
        Distribution distribution = new Distribution(persons, bankDeposit);
        persons = distribution.evenDistribution();
        ArrayList<String> minAppendPersons = distribution.getMinAppendPersons();

        // Запись результата
        XMLWriter.write(outputFile, persons, minAppendPersons);
    }
}