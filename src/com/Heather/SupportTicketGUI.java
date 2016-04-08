package com.Heather;

import javax.swing.*;
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
    private JComboBox<Ticket> selectTicketToDelete;
    private JPanel rootPanel;
    private JLabel descriptionLabel;
    private JLabel reporterLabel;
    private JLabel priorityLabel;
    private JLabel resolutionDescriptionLabel;

    Vector<Ticket> ticketVector;

    DefaultComboBoxModel<Ticket> selectDelete;
    DefaultListModel<Ticket> allOpenTicketsModel;


public SupportTicketGUI() throws IOException {
    super("Support Ticket Program");//Set title bar

    ticketVector=TicketFileManager.read("TicketQ");

    setContentPane(rootPanel);
    pack();
    setVisible(true);

    selectDelete = new DefaultComboBoxModel<>();
    allOpenTicketsModel=new DefaultListModel<>();

    selectTicketToDelete.setModel(selectDelete);
    openTicketList.setModel(allOpenTicketsModel);










}





}
