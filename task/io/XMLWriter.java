package task.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import task.Person;

public class XMLWriter {
 
    public static void write(String fileName, ArrayList<Person> persons, ArrayList<String> minAppendPerson) {
        
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = null;
        
        try (OutputStreamWriter outputStream =  new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8)) {

            writer = factory.createXMLStreamWriter(outputStream);
            writer.writeStartDocument();

            writer.writeStartElement("total");
 
            writer.writeStartElement("result");
            for (Person person : persons) {
                writer.writeStartElement("Person");
                writer.writeAttribute("name", person.getName());
                writer.writeAttribute("wallet", person.getWalletInString());
                writer.writeAttribute("appendFromBank", person.getAppendFromBankInString());
                writer.writeEndElement();
            }
            writer.writeEndElement();
            

            writer.writeStartElement("minimum");

            for (String name : minAppendPerson) {
                writer.writeStartElement("Person");
                writer.writeAttribute("name", name);
                writer.writeEndElement();
            }

            writer.writeEndElement();

            writer.writeEndElement();

            writer.writeEndDocument();
 
            writer.flush();
            writer.close();

        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }
}