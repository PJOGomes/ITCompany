package com.PauloGomes;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources_sv;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Date;

import static javax.xml.bind.DatatypeConverter.parseInteger;

public class CRUDDatabase {

    public static void createFile(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams, ActiveProgrammer prog) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(prog.getStartDatePresentProject());

        try {

            File xmlDoc = new File(".\\src\\database.xml");
//            If file doesn't exist create an empty file
            if(!xmlDoc.exists()){
                xmlDoc.createNewFile();
            }

            //If the file exists read it's content and pass it to objects
            DocumentBuilderFactory dbfact = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuild = dbfact.newDocumentBuilder();
            Document doc = dbuild.parse(xmlDoc);
            //Get root Element
            Element root = doc.getDocumentElement();

            //Create XML fields

            Element programmer = doc.createElement("programmer");

            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(Integer.toString(prog.getId())));
            programmer.appendChild(id);

            Element firstName = doc.createElement("firstName");
            firstName.appendChild(doc.createTextNode(prog.getFirstName()));
            programmer.appendChild(firstName);

            Element lastName = doc.createElement("lastName");
            lastName.appendChild(doc.createTextNode(prog.getLastName()));
            programmer.appendChild(lastName);

            Element startDatePresentProject = doc.createElement("startDatePresentProject");
            startDatePresentProject.appendChild(doc.createTextNode(date));
            programmer.appendChild(startDatePresentProject);

            Element daysWorked = doc.createElement("daysWorked");
            daysWorked.appendChild(doc.createTextNode(Integer.toString(prog.getDaysWorkedMonth())));
            programmer.appendChild(daysWorked);

            Element wage = doc.createElement("wage");
            wage.appendChild(doc.createTextNode(Double.toString(prog.getWage())));
            programmer.appendChild(wage);

            Element paymentMethod = doc.createElement("paymentMethod");
            paymentMethod.appendChild(doc.createTextNode(Integer.toString(prog.getPaymentMethod())));
            programmer.appendChild(paymentMethod);

            Element active = doc.createElement("active");
            active.appendChild(doc.createTextNode(Boolean.toString(prog.isActive())));
            programmer.appendChild(active);

            root.appendChild(programmer);

            //Appending to File
            DOMSource source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StreamResult result = new StreamResult(".\\src\\database.xml");
            transformer.transform(source, result);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void createFile(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams, ProjectTeam proj) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {

            File xmlDoc = new File(".\\src\\database.xml");
//            If file doesn't exist create an empty file
            if(!xmlDoc.exists()){
                xmlDoc.createNewFile();
            }

            //If the file exists read it's content and pass it to objects
            DocumentBuilderFactory dbfact = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuild = dbfact.newDocumentBuilder();
            Document doc = dbuild.parse(xmlDoc);
            //Get root Element
            Element root = doc.getDocumentElement();

            //Create XML fields
            Element project = doc.createElement("project");

            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(Integer.toString(proj.getId())));
            project.appendChild(id);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(proj.getName()));
            project.appendChild(name);

            for (int i = 0; i <proj.getMembers().size() ; i++) {
                Element member = doc.createElement("member");
                member.appendChild(doc.createTextNode(proj.getMembers().get(i)));
                project.appendChild(member);
            }

            for (int i = 0; i <proj.getFunctions().size() ; i++) {
                Element function = doc.createElement("function");
                function.appendChild(doc.createTextNode(proj.getFunctions().get(i)));
                project.appendChild(function);
            }

            Element beginDate = doc.createElement("beginDate");
            beginDate.appendChild(doc.createTextNode(dateFormat.format(proj.getBeginDate())));
            project.appendChild(beginDate);

            Element endDate = doc.createElement("endDate");
            endDate.appendChild(doc.createTextNode(dateFormat.format(proj.getEndDate())));
            project.appendChild(endDate);

            //Append created node to root

            root.appendChild(project);

            //Append to file

            DOMSource source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StreamResult result = new StreamResult(".\\src\\database.xml");
            transformer.transform(source, result);



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void readFile(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            File xmlDoc = new File(".\\src\\database.xml");
//            If file doesn't exist create an empty file
            if(!xmlDoc.exists()){
                xmlDoc.createNewFile();
            }

            //If the file exists read it's content and pass it to objects
            DocumentBuilderFactory dbfact = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuild = dbfact.newDocumentBuilder();
            Document doc = dbuild.parse(xmlDoc);

            //Parse Active Programmers ArrayList
            NodeList programmersList = doc.getElementsByTagName("programmer");
            for (int i = 0; i <programmersList.getLength(); i++) {
                //Read each programmer's data
                Node eachNode = programmersList.item(i);
                if(eachNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element single = (Element) eachNode;
                    int id = Integer.parseInt(single.getElementsByTagName("id").item(0).getTextContent());
                    String firstName = single.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = single.getElementsByTagName("lastName").item(0).getTextContent();
                    date = dateFormat.parse(single.getElementsByTagName("startDatePresentProject").item(0).getTextContent());
                    int daysWorked = Integer.parseInt(single.getElementsByTagName("daysWorked").item(0).getTextContent());
                    double wage = Double.parseDouble(single.getElementsByTagName("wage").item(0).getTextContent());
                    int paymentMethod = Integer.parseInt(single.getElementsByTagName("paymentMethod").item(0).getTextContent());
                    boolean active = Boolean.valueOf(single.getElementsByTagName("active").item(0).getTextContent());
                    //Call Programmer constructor and add the object to the programmer's list
                    ActiveProgrammer temp = new ActiveProgrammer(id, firstName, lastName,date, daysWorked, wage, paymentMethod, active);
                    programmers.add(temp);
                }
            }

            //Parse Project ArrayList
            NodeList projectList = doc.getElementsByTagName("project");
            for (int i = 0; i <projectList.getLength(); i++) {
                //Read each project´s data
                Node eachNode = projectList.item(i);
                if(eachNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element single = (Element) eachNode;
                    int countMembers = single.getElementsByTagName("member").getLength();
                    int countFunctions = single.getElementsByTagName("function").getLength();
                    int id = Integer.parseInt(single.getElementsByTagName("id").item(0).getTextContent());
                    String name = single.getElementsByTagName("name").item(0).getTextContent();
                    //Pass every member to an arraylist of members
                    ArrayList<String> tempMembers = new ArrayList<>();
                    for (int j=0; j<countMembers ;j++){
                        String aux = single.getElementsByTagName("member").item(j).getTextContent();
                        tempMembers.add(aux);
                        System.out.println(tempMembers.get(j));
                    }
                    //Pass every function to an arraylist of functions
                    ArrayList<String> tempFunctions = new ArrayList<>();
                    for (int j=0; j<countFunctions ;j++){
                        String aux = single.getElementsByTagName("function").item(j).getTextContent();
                        tempFunctions.add(aux);
                    }
                    startDate = dateFormat.parse(single.getElementsByTagName("beginDate").item(0).getTextContent());
                    endDate = dateFormat.parse(single.getElementsByTagName("endDate").item(0).getTextContent());
                    //Call Project constructor and add the object to the project's list
                    ProjectTeam temp = new ProjectTeam(id, name, tempMembers, tempFunctions, startDate, endDate);
                    teams.add(temp);
                }
            }
        } catch (Exception e) { //Exception handling
            System.out.println(e.getMessage());
        }

    }

    public static void updateFile(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {

    }

    public static void deleteFile(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {

    }

}
