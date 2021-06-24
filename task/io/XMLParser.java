package task.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import task.Person;

public class XMLParser {

    public static String parseAttribute(String fileName, String elementName, int attributeIndex) {
        
        String result = "";
        
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader xmlReader = null;

        try (InputStreamReader inputStream = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8)){
            
            xmlReader = factory.createXMLStreamReader(inputStream);

            while (xmlReader.hasNext()) {
                xmlReader.next();
                
                if (xmlReader.isStartElement() && xmlReader.getLocalName().equals(elementName)) {
                    return xmlReader.getAttributeValue(attributeIndex);
                }
            }

        } catch (FileNotFoundException | XMLStreamException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static ArrayList<Person> parsePersons(String fileName) {

        ArrayList<Person> persons = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader xmlReader = null;

        try (InputStreamReader inputStream = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8)){

            xmlReader = factory.createXMLStreamReader(inputStream);

            while (xmlReader.hasNext()) {
                xmlReader.next();
                
                if (xmlReader.isStartElement() && xmlReader.getLocalName().equals("Person")) {
                    persons.add(new Person(
                        xmlReader.getAttributeValue(0),
                        xmlReader.getAttributeValue(1)));
                }
            }                    
        } catch (FileNotFoundException | XMLStreamException ex) {
            ex.printStackTrace();
        }  catch (IOException ex) {
            ex.printStackTrace();
        }

        return persons;
    }
}