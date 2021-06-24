package task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;


public class Distribution {
    
    private ArrayList<Person> persons;
    private BigDecimal bankDeposit;

    public Distribution(ArrayList<Person> persons, String bankDeposit) {
        this.persons = persons;
        this.bankDeposit = new BigDecimal(bankDeposit);
    }

    public ArrayList<Person> evenDistribution() {

        // 0 Этап. Подготовительный
        int members = persons.size();           // Между сколькими людьми происходит распределение
        BigDecimal sum = BigDecimal.ZERO;       // Сумма депозита и клиентских средств
        BigDecimal average = BigDecimal.ZERO;   // Средняя сумма на клиента

        // Список участников (для честного распределения)
        ArrayList<Boolean> membersList = new ArrayList<>(Collections.nCopies(members, false));

        // I Этап. Суммируем банковский депозит и кошельки
        for (Person person : persons) {
            sum = sum.add(person.getWallet());
        }
        sum = sum.add(bankDeposit);

        // II Этап. Проверка сумм участников распределения
        // Проверяем есть ли люди, сумма на счету которых больше среднего
        // если такие есть, то в распределении депозита они не участвуют
        int index = 0;
        while (index < persons.size()) {

            // Вычисляем среднее
            average = sum.divide(BigDecimal.valueOf(members), RoundingMode.UP);

            // Если он участвует в распределении и его счет больше среднего
            if (!membersList.get(index) && persons.get(index).getWallet().compareTo(average) == 1) {

                // Обнавляем общую сумму
                sum = sum.subtract(persons.get(index).getWallet());
                persons.get(index).setAppendFromBank(BigDecimal.ZERO);
                
                // Клиент не участвует в распределении
                membersList.set(index, true);

                members -= 1;

                index = -1;
            }
            index++;
        }

        // Вычисление излишка, полученного при округлении
        BigDecimal remainder = average.multiply(new BigDecimal(members)).subtract(sum);

        // IV Этап. Распределение
        for (index = 0; index < persons.size(); index++) {

            // Проверка участия клиента в распределении
            if (persons.get(index).getAppendFromBank().compareTo(BigDecimal.ZERO) != 0) {

                // Разница между средним и кошелком -- сумма, добавленная банком
                persons.get(index).setAppendFromBank(average.subtract(persons.get(index).getWallet()).setScale(2, RoundingMode.DOWN));

                // Вычисленное среднее поступает ему на сумму
                persons.get(index).setWallet(average.setScale(2, RoundingMode.CEILING));
            }
        }

        // V Этап. Вычитание излишков у случайных участников
        if (remainder.compareTo(BigDecimal.ZERO) != 0) {
    
            int r = (int)(remainder.doubleValue() * 100);

            // Выбираем случаного участника
            int randMember = 0;

            while (r > 0) {
                randMember = (int)(Math.random() * persons.size());

                // Проверяем, что он участвует в распределении
                if (!membersList.get(randMember)) {

                    persons.get(randMember).changeWallet(BigDecimal.valueOf(-0.01));
                    persons.get(randMember).changeAppendFromBank(BigDecimal.valueOf(-0.01));
                    membersList.set(randMember, true);

                    r -= 1;
                }
            }
        }

        return persons;
    }


    public ArrayList<String> getMinAppendPerson() {

        ArrayList<String> result = new ArrayList<>();

        // Получаем все суммы, полученные от банка
        ArrayList<BigDecimal> appendFromBank = new ArrayList<>();
        for (Person person : persons) {
            appendFromBank.add(person.getAppendFromBank());
        }

        // Сортируем значения
        Collections.sort(appendFromBank);

        // Минимальная сумма полученная от банка
        BigDecimal minimum = Collections.min(appendFromBank);
        
        // Поиск пользователей с минимальной суммой
        for (Person person: persons) {
            if (person.getAppendFromBank().compareTo(minimum) == 0) {
                result.add(person.getName());
            }
        }

        return result;
    }
}