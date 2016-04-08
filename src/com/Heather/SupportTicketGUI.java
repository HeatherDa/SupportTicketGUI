package com.Heather;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
/**
 * Created by iv2070fj on 4/7/2016.
 */
public class SupportTicketGUI extends JFrame {
    private JList<Ticket> openTicketList;
    private JButton deleteTicketButton;
    private JTextField descriptionTextField;
    private JButton addTicketButton;
    private JTextField reporterTextField;
    private JTextField priorityTextField;
    private JTextField resolutionDescriptionTextField;
    private JButton quitButton;
    private JComboBox selectTicketToDelete;

    Vector<Ticket> ticketVector;

    DefaultComboBoxModel<Ticket> selectTickettoDelete;
    DefaultListModel<Ticket> allOpenTicketsModel;


public SupportTicketGUI() throws IOException {
    super("What is this?");//What does this do?

    ticketVector=TicketFileManager.read("TicketQ");











}





}
