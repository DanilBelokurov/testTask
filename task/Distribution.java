package task;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class Distribution {
    
    private ArrayList<Person> persons;
    private int bank;

    public Distribution(ArrayList<Person> persons, String bank) {
        this.persons = persons;
        this.bank = (int)( Float.valueOf(bank) * 100);
    }

    public ArrayList<Person> evenDistribution() {

        // I Этап. Распределяем бюджет поравну и находим среднее
        int personsNum = persons.size(); // Количество клиентов
        int proportion = bank / personsNum; // Сумма в банке / кол-во клиентов

        int average = 0; // Средняя сумма

        for (Person person : persons) {
            person.changeWallet(proportion);
            person.changeAppendFromBank(proportion);
            average += person.getWallet();       
        }
        average /= personsNum;

        // II Этап. Вычисление разницы до среднего каждого клиента
        ArrayList<Integer> targets = new ArrayList<>();
        for (Person person : persons) {
            targets.add(average - person.getWallet());  
        }
        ArrayList<Integer> difference = new ArrayList<>(targets);


        // III Этап. Распределение

        // Положительное значение target -- клиенту надо добавить
        // Отрицательное значение target -- у клиента надо отнять
        int positiveTargetsNum = 0;
        for (Integer target : targets) {
            if (target > 0) {
                positiveTargetsNum += 1;
            }
        }

        while (positiveTargetsNum > 0) {

            int addition = 0;

            for (int index = 0; index < targets.size(); index++) {
                
                // Поиск того, кто будет отдавать 
                if (targets.get(index) < 0) {

                    int targetValue = targets.get(index);

                    // Вычисление суммы, которую он может отдать
                    addition = targetValue / positiveTargetsNum;
                    targets.set(index, targetValue - (addition * positiveTargetsNum));

                    // Перераспределение денег
                    for (int i = 0; i < targets.size(); i++) {
                        if (targets.get(i) > 0) {
                            targets.set(i, targets.get(i) + addition);
                        }
                    }

                    // Перерасчет target
                    positiveTargetsNum = 0;
                    for (Integer target : targets) {
                        if (target > 0) {
                            positiveTargetsNum += 1;
                        }
                    }                    
                }
            }

        }

        // IV Этап. Формирование результата
        for (int index = 0; index < personsNum; index++) {
            persons.get(index).changeWallet(difference.get(index) - targets.get(index));
            persons.get(index).changeAppendFromBank(difference.get(index) - targets.get(index));
        }

        return persons;
    }


    public ArrayList<String> getMinAppendPersons() {

        ArrayList<String> result = new ArrayList<>();

        // I Этап. Сортировка клиентов с помощью дерева
        Map<Integer, String> sortedPersons = new TreeMap<>();

        for (Person person : persons) {
            sortedPersons.put(
                person.getAppendFromBank(),
                person.getName());
        }

        // II Этап. Отбор трех клиентов с минимальной суммой от банка
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