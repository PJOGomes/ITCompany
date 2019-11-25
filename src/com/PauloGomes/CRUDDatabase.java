package com.PauloGomes;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
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
import java.util.Date;

import static javax.xml.bind.DatatypeConverter.parseInteger;

public class CRUDDatabase {

//Function: getDoc
//Description: Function to get the file specified as the database
//
//@Input: no input
//
//@Output: a Document corresponding to the file used as database
    public static Document getDoc() {
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
            return doc;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Function: appendDocument
    //Description: function to append a node to the XML file used as database
    //
    //@Input: A Document corresponding to the file used as database
    //
    //@Output: no output
    public static void appendDocument(Document doc) {
        try {
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

    //Function: createFile
    //Description: Function to create a node with a new programmer in the xml file used as database
    //
    //@Input: An arraylist of type ActiveProgrammer, an arraylist of type ProjectTeam, and an object of type ActiveProgrammer to be inputed
    //
    //@Output: no output
    public static void createFile(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams, ActiveProgrammer prog) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(prog.getStartDatePresentProject());

        try {

            Document doc = getDoc();

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

            //Append the node create to the root node
            root.appendChild(programmer);

            //Appending to File
            appendDocument(doc);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //Function: createFile
    //Description:Function to create a node with a new project in the xml file used as database
    //
    //@Input: An arraylist of type ActiveProgrammer, an arraylist of type ProjectTeam, and an object of type ProjectTeam to be inputed
    //
    //@Output: no output
    public static void createFile(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams, ProjectTeam proj) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Document doc = getDoc();
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

            //Insert each one of the programmers in the project
            for (int i = 0; i <proj.getMembers().size() ; i++) {
                Element member = doc.createElement("member");
                member.appendChild(doc.createTextNode(proj.getMembers().get(i)));
                project.appendChild(member);
            }
            //Insert each function of each programmer
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

           appendDocument(doc);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //Function: readFile
    //Description: Function to read the programmers and projects from an xml file used as database
    //
    //@Input: An arraylist of type ActiveProgrammer and an arraylist of type ProjectTeam
    //
    //@Output: no output
    public static void readFile(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Date startDate = new Date();
        Date endDate = new Date();
        try {

            //Get the file used as database
            Document doc = getDoc();

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

    //Function: updateFile
    //Description: Function to update the details of a programmer in the xml file used as database
    //
    //@Input: An arraylist of type ActiveProgrammer, an arraylist of type ProjectTeam, the id of the programmer being edited
    //and a string to tell the function it is editing a programmer
    //
    //@Output: no output
    public static void updateFile(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams, String id, String change) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {

            //Get the file used as database
            Document doc = getDoc();

            //If it is a programmer being edited
            if(change.equals("ActiveProgrammer")) {
                //Get the programmer being edited
                ActiveProgrammer p = new ActiveProgrammer();
                for(ActiveProgrammer prog: programmers)
                {
                    if(prog.getId()==Integer.parseInt(id))
                    {
                        p = prog;
                    }
                }

                //Search the xml file for the programmer being edited
                NodeList progList = doc.getElementsByTagName("programmer");
                for (int i = 0; i <progList.getLength() ; i++) {
                    Node eachNode = progList.item(i);
                    Element single = (Element) eachNode;
                    //Change the Node tags if it has the same id that was passed as parameter
                    if(single.getElementsByTagName("id").item(0).getTextContent().equals(id)){
                        single.getElementsByTagName("firstName").item(0).setTextContent(p.getFirstName());
                        single.getElementsByTagName("lastName").item(0).setTextContent(p.getLastName());
                        single.getElementsByTagName("startDatePresentProject").item(0).setTextContent(dateFormat.format(p.getStartDatePresentProject()));
                        single.getElementsByTagName("daysWorked").item(0).setTextContent(Integer.toString(p.getDaysWorkedMonth()));
                        single.getElementsByTagName("wage").item(0).setTextContent(Double.toString(p.getWage()));
                        single.getElementsByTagName("paymentMethod").item(0).setTextContent(Integer.toString(p.getPaymentMethod()));
                        single.getElementsByTagName("active").item(0).setTextContent(Boolean.toString(p.isActive()));
                    }
                }

            } else
            {
                System.out.println("Error Updating Database");
            }

            //Append to file

           appendDocument(doc);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //Function: deleteFile
    //Description: Function to delete a programmer or a project from the file used as database
    //
    //@Input: An arraylist of type ActiveProgrammer, an arraylist of type ProjectTeam, the id of the programmer or project being edited
    //and a string to tell the function if it is editing a programmer or a project
    //
    //@Output:no output
    public static void deleteFile(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams, int id, String deleteFrom) {
        try {
            Document doc = getDoc();
            //Get XML root
            Element root = doc.getDocumentElement();
            //If we are deleting a programmer
            if(deleteFrom.equals("programmers"))
            {
                NodeList listprog = doc.getElementsByTagName("programmer");
                for (int i = 0; i <listprog.getLength() ; i++) {
                    Node eachNode = listprog.item(i);
                    Element single = (Element) eachNode;
                    if(single.getElementsByTagName("id").item(0).getTextContent().equals(Integer.toString(id))){
                        root.removeChild(eachNode);
                    }
                }
            //if we are deleting a project
            } else if(deleteFrom.equals("projects")) {
                NodeList listproj = doc.getElementsByTagName("project");
                for (int i = 0; i <listproj.getLength() ; i++) {
                    Node eachNode = listproj.item(i);
                    Element single = (Element) eachNode;
                    if(single.getElementsByTagName("id").item(0).getTextContent().equals(Integer.toString(id))){
                        root.removeChild(eachNode);
                    }
                }
                //If we can't get a type node to be deleted
            }  else {
                System.out.println("Error deleting");
                return;
            }
            //Write in the document
            appendDocument(doc);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //Function: loadBackup
    //Description: Function to upload the content of the backup database if the user decides to discart all changes made in the current session
    //
    //@Input: no input
    //
    //@Output: no output
    public static void loadBackup() throws IOException {
        File xmlDoc = new File(".\\src\\database.xml");
//            If file doesn't exist create an empty file
        if(!xmlDoc.exists()){
            xmlDoc.createNewFile();
        }
        //Empty file
        new FileOutputStream(".\\src\\database.xml").close();
        //Copy backup
        OutputStream os = new FileOutputStream(".\\src\\database.xml");
        Files.copy(Paths.get(".\\src\\backup.xml"), os);
        os.close();

    }

    //Function: saveExit
    //Description: Function to save the current database to the backup database if we exit the program with success
    //
    //@Input: no input
    //
    //@Output: no output
    public static void saveExit() throws IOException {
        //Clean backup
        new FileOutputStream(".\\src\\backup.xml").close();
        //Copy to backup
        OutputStream os = new FileOutputStream(".\\src\\backup.xml");
        Files.copy(Paths.get(".\\src\\database.xml"), os);
        os.close();
    }

    //Function: saveHistory
    //Description: Function to save a project that is either deleted or reaches the end date in a xml file containing the project history
    //
    //@Input: A type ProjectTeam object
    //
    //@Output: no output
    public static void saveHistory(ProjectTeam proj){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {

            //Get the history file
            File xmlDoc = new File(".\\src\\ProjectHistory.xml");
//            If file doesn't exist create an empty file
            if(!xmlDoc.exists()){
                xmlDoc.createNewFile();
            }

            //If the file exists read it's content and pass it to objects
            DocumentBuilderFactory dbfact = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuild = dbfact.newDocumentBuilder();
            Document doc = dbuild.parse(xmlDoc);

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

            //Write in the xml file, appending the node to the xml file
            DOMSource source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StreamResult result = new StreamResult(".\\src\\ProjectHistory.xml");
            transformer.transform(source, result);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //Function: readHistory
    //Description: Fucntion to read the projects that ended or were deleted
    //
    //@Input: An arrayList of type Project Team
    //
    //@Output: no output
    public static void readHistory(ArrayList<ProjectTeam> historyList) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            //Get the file of the Projects history
            File xmlDoc = new File(".\\src\\ProjectHistory.xml");
//            If file doesn't exist create an empty file
            if(!xmlDoc.exists()){
                xmlDoc.createNewFile();
            }
            //If the file exists read it's content and pass it to objects
            DocumentBuilderFactory dbfact = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuild = dbfact.newDocumentBuilder();
            Document doc = dbuild.parse(xmlDoc);

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
                    historyList.add(temp);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Function: readDate
    //Description: Function to read the date contained in te file used as database that represents the date that the system had it's
    //last exit with status 0 (success)
    //
    //@Input:no input
    //
    //@Output: a type Date value that represents the current system date (in the database)
    public static Date readDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            getDoc();
            //Get the tag that contains the date
            NodeList nodeList = getDoc().getElementsByTagName("date");
            Element single = (Element) nodeList.item(0);
            return dateFormat.parse(single.getElementsByTagName("last").item(0).getTextContent());

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //In case we don't get the date in the database
        return dateFormat.parse("00/00/000");
    }

    //Function: saveDate
    //Description: Function to save the system date to the xml file
    //
    //@Input: no input
    //
    //@Output: no output
    public static void saveDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            String date = dateFormat.format(Menu.getSysDate());
            Document doc= getDoc();
            NodeList nodeList = doc.getElementsByTagName("date");
            Element single = (Element) nodeList.item(0);
            single.getElementsByTagName("last").item(0).setTextContent(date);
           appendDocument(doc);

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //Function: readMockup
    //Description: Function that reads a mockup database if at the beginning of the program we don't have the minimum requirements
    //to run the program (4 programmers and 2 projects with 2 programmers)
    //
    //@Input: no input
    //
    //@Output: no output
    public static void readMockup(ArrayList<ActiveProgrammer> programmers, ArrayList<ProjectTeam> teams) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Date startDate = new Date();
        Date endDate = new Date();
        try {

            //Get the mockup database
            File xmlDoc = new File(".\\src\\routineStart.xml");
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
