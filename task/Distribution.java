package task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class Distribution {
    
    private ArrayList<Person> persons;
    private BigDecimal bankDeposit;

    public Distribution(ArrayList<Person> persons, String bankDeposit) {
        this.persons = persons;
        this.bankDeposit = new BigDecimal(bankDeposit);
    }

    public ArrayList<Person> evenDistribution() {

        // 0 Этап. Подготовительный
        int members = persons.size();       // Между сколькими людьми происходит распределение
        BigDecimal sum = BigDecimal.ZERO;   // Сумма депозита и клиентских средств
        BigDecimal average;                 // Средняя сумма на клиента

        // I Этап. Суммируем банковский депозит и кошельки
        for (Person person : persons) {
            sum = sum.add(person.getWallet());
        }
        sum = sum.add(bankDeposit);

        // II Этап. Вычисляем среднее
        average = sum.divide(BigDecimal.valueOf(members), RoundingMode.UP);

        // III Этап. Проверка сумм участников распределения
        // Проверяем есть ли люди, сумма на счету которых больше среднего
        // если такие есть, то в распределении депозита они не участвуют
        boolean richPeopleFlag = false;
        for (int index = 0; index < persons.size(); index++) {
            if (persons.get(index).getWallet().compareTo(average) == 1) {
                sum = sum.subtract(persons.get(index).getWallet());
                persons.get(index).setAppendFromBank(BigDecimal.ZERO);
                richPeopleFlag = true;
                members -= 1;
            }
        }

        // Если нужно, пересчитываем среднее значение
        if (richPeopleFlag) {
            average = average.divide(BigDecimal.valueOf(members), RoundingMode.UP);
        }

        // Вычисление излишка, полученного при округлении
        BigDecimal remainder = average.multiply(new BigDecimal(members)).subtract(sum);

        // IV Этап. Распределение
        for (int index = 0; index < persons.size(); index++) {

            // Проверка участия клиента в распределении
            if (persons.get(index).getAppendFromBank().compareTo(BigDecimal.ZERO) != 0) {

                // Разница между средним и кошелком -- сумма, добавленная банком
                persons.get(index).setAppendFromBank(average.subtract(persons.get(index).getWallet()).setScale(2, RoundingMode.DOWN));

                // Вычисленное среднее поступает ему на сумму
                persons.get(index).setWallet(average.setScale(2, RoundingMode.CEILING));
            }
        }

        // V Этап. Распределение остатка случайному участнику
        if (remainder.compareTo(BigDecimal.ZERO) != 0) {
            int randMember = (int)(Math.random() * members);

            while (randMember != -1) {
                if (persons.get(randMember).getAppendFromBank().compareTo(BigDecimal.ZERO) != 0) {
                    persons.get(randMember).changeWallet(remainder.negate());
                    persons.get(randMember).changeAppendFromBank(remainder.negate());
                    randMember = -1;
                }
            }
        }

        return persons;
    }


    public ArrayList<String> getMinAppendPersons() {

        ArrayList<String> result = new ArrayList<>();

        // I Этап. Сортировка клиентов с помощью дерева
        Map<BigDecimal, String> sortedPersons = new TreeMap<>();

        for (Person person : persons) {
            sortedPersons.put(
                person.getAppendFromBank(),
                person.getName());
        }

        // II Этап. Отбор трех клиентов с минимальной суммой полученной от банка
        int counter = 0;
        for (String personName : sortedPersons.values()) {
            if (counter < 3) {
                result.add(personName);
                counter++;
            }
        }
        
        return result;
    }
}