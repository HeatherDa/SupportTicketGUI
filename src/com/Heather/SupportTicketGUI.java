package com.Heather;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

public class SupportTicketGUI extends JFrame {
    private JList<Ticket> openTicketList;
    private JButton deleteTicketButton;
    private JTextField descriptionTextField;
    private JButton addTicketButton;
    private JTextField reporterTextField;
    private JTextField priorityTextField;
    private JTextField resolutionDescriptionTextField;
    private JButton quitButton;
    private JPanel rootPanel;
    private JLabel descriptionLabel;
    private JLabel reporterLabel;
    private JLabel priorityLabel;
    private JLabel resolutionDescriptionLabel;
    private JComboBox priorityComboBox;
    private JList searchResultsList;
    private JTextField searchTextField;
    private JButton searchForTicketButton;
    private JLabel searchResultsLabel;
    private JComboBox criteriaTypeComboBox;
    private JTextField toResolveATicketTextField;

    private  Vector<Ticket> ticketVector;
    private Vector<Ticket> results;


    DefaultListModel<Ticket> allOpenTicketsModel;
    DefaultListModel<Ticket> searchResultsModel;
    DefaultComboBoxModel<Integer>priorityComboModel;
    DefaultComboBoxModel<String>criteriaTypeComboModel;

    public SupportTicketGUI() throws IOException {
        super("Support Ticket Program");//Set title bar

        ticketVector=TicketFileManager.read("TicketQ.txt");//get information from file about tickets.

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(800,500));
        pack();
        setVisible(true);

        //Model assignments
        allOpenTicketsModel=new DefaultListModel<>();
        searchResultsModel=new DefaultListModel<>();
        priorityComboModel=new DefaultComboBoxModel<>();
        criteriaTypeComboModel=new DefaultComboBoxModel<>();

        openTicketList.setModel(allOpenTicketsModel);
        searchResultsList.setModel(searchResultsModel);
        priorityComboBox.setModel(priorityComboModel);
        criteriaTypeComboBox.setModel(criteriaTypeComboModel);

        //put numbers in priority combobox
        priorityComboModel.addElement(1);
        priorityComboModel.addElement(2);
        priorityComboModel.addElement(3);
        priorityComboModel.addElement(4);
        priorityComboModel.addElement(5);

        //put types in criteriaType combobox
        criteriaTypeComboModel.addElement("Ticket ID");
        criteriaTypeComboModel.addElement("Description");
        criteriaTypeComboModel.addElement("Reporter");


        refreshOpenTickets();

        //Listeners go here
        addTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ticketDescription=descriptionTextField.getText();
                String ticketReporter=reporterTextField.getText();
                //int ticketPriority= Integer.parseInt(priorityTextField.getText());//replace with drop down list to select priority
                int ticketPriority= (Integer) priorityComboBox.getSelectedItem();
                Ticket t=new Ticket(ticketDescription, ticketPriority, ticketReporter, new Date());
                addTicketInPriorityOrder(ticketVector, t); //add ticket to Vector in the correct order
                //Maybe you can only add strings to a jlist?
                allOpenTicketsModel.addElement(t);//can't add ticketVector, will duplicate entries.  must add Element by index position.
                refreshOpenTickets();
            }
        });
        searchForTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
                refreshResults();
            }
        });


        deleteTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex=openTicketList.getSelectedIndex();
                int searchIndex=searchResultsList.getSelectedIndex();
                Ticket selectedTicket;
                try {
                    selectedTicket = ticketVector.get(selectedIndex);
                    String descResolve = resolutionDescriptionTextField.getText();
                    resolvedTicket resolved = new resolvedTicket(selectedTicket.getTicketID(), selectedTicket.getDescription(), selectedTicket.getPriority(), selectedTicket.getReporter(), selectedTicket.getDateReported(), new Date(), descResolve);
                    resolvedTicket.setResolvedTickets(resolved);
                }catch(ArrayIndexOutOfBoundsException ai){
                    selectedIndex=searchIndex;
                    selectedTicket = ticketVector.get(selectedIndex);
                    String descResolve = resolutionDescriptionTextField.getText();
                    resolvedTicket resolved = new resolvedTicket(selectedTicket.getTicketID(), selectedTicket.getDescription(), selectedTicket.getPriority(), selectedTicket.getReporter(), selectedTicket.getDateReported(), new Date(), descResolve);
                    resolvedTicket.setResolvedTickets(resolved);
                    results.remove(selectedTicket);
                }

                //openTicketList.remove(selectedIndex);// Only works without this.  Why?
                ticketVector.remove(selectedTicket);
                refreshOpenTickets();//refreshes list so you can see that deleted ticket is gone.
                refreshResults();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.OK_OPTION==JOptionPane.showConfirmDialog(SupportTicketGUI.this, "Are you sure you want to save and exit?", "Exit?", JOptionPane.OK_CANCEL_OPTION)){
                    try {
                        TicketFileManager.writeFiles(ticketVector);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        });

    }

    protected static void addTicketInPriorityOrder(Vector<Ticket> tickets, Ticket newTicket){

        //Logic: assume the list is either empty or sorted

        if (tickets.size() == 0 ) {//Special case - if list is empty, add ticket and return
            tickets.add(newTicket);
            return;
        }

        //Tickets with the HIGHEST priority number go at the front of the list. (e.g. 5=server on fire)
        //Tickets with the LOWEST value of their priority number (so the lowest priority) go at the end

        int newTicketPriority = newTicket.getPriority();

        for (int x = 0; x < tickets.size() ; x++) {    //use a regular for loop so we know which element we are looking at

            //if newTicket is higher or equal priority than the this element, add it in front of this one, and return
            if (newTicketPriority >= tickets.get(x).getPriority()) {
                tickets.add(x, newTicket);
                return;
            }
        }

        //Will only get here if the ticket is not added in the loop
        //If that happens, it must be lower priority than all other tickets. So, add to the end.
        tickets.addElement(newTicket);//addElement works like addLast
    }
    public Vector<Ticket>search(){
        results=new Vector<>();
        String criteria=searchTextField.getText();
        String type= (String) criteriaTypeComboBox.getSelectedItem();
        criteria=criteria.toLowerCase();
        try {
            for (Ticket t : ticketVector) {
                if (type.equalsIgnoreCase("Ticket ID")) {
                    if (t.getTicketID() == Integer.parseInt(criteria)) {
                        results.add(t);
                    }
                } else if (type.equalsIgnoreCase("Description")) {
                    if (t.getDescription().contains(criteria)) {
                        results.add(t);
                    }
                } else if (type.equalsIgnoreCase("Reporter"))
                    if (t.getReporter().contains(criteria)) {
                        results.add(t);
                    }
            }
        }catch(NumberFormatException ne){
            if(results.size()==0) {
                searchResultsLabel.setText("There are no tickets containing this criteria.");
                }
        }
        return results;
    }

    public void refreshOpenTickets() {
        allOpenTicketsModel.clear();
        for (Ticket tic:ticketVector){
            allOpenTicketsModel.addElement(tic);
        }
        descriptionTextField.setText("");
        resolutionDescriptionTextField.setText("");
        reporterTextField.setText("");
    }

    public void refreshResults(){
        searchResultsModel.clear();
        for(Ticket r:results){
            searchResultsModel.addElement(r);
        }
        searchTextField.setText("");
    }



}
