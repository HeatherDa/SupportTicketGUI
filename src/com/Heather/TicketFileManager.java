package com.Heather;
import java.io.*;
import java.text.*;
import java.util.*;
public class TicketFileManager {
    //save resolved tickets

    TicketFileManager() throws IOException {
    }

    public static void writeFiles(LinkedList<Ticket> ticketQ) throws IOException {
        String date=(String.valueOf(new Date())).replaceAll(" ","_");
        String filedate=date.replaceAll(":","-");
        String filenameR="Resolved_tickets_as_of_"+filedate+".txt";
        writeR(filenameR,resolvedTicket.getResolvedTickets());
        String filenameTQ="TicketQ.txt";
        writeQ(filenameTQ, ticketQ);
    }

    public static Vector<Ticket> read(String filename) throws IOException {
        Vector<Ticket> open = new Vector<Ticket>();
        try {
            FileReader reader = new FileReader(filename);
            BufferedReader bufReader = new BufferedReader(reader);

            try {
                String line = bufReader.readLine();
                while (line != null) {
                    ArrayList<String> lines = new ArrayList<>();//to hold the lines to make a Ticket object.  Split on , and :, then strip first whitespace
                    String[] aline = line.split(",");//split into sections containing heading and data
                    for (String a : aline) {
                        String[] bline = a.split(": ");//split sections into heading and data
                        lines.add(bline[1]);//get rid of heading and save to ArrayList
                    }
                    int p = Integer.parseInt(lines.get(2));
                    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                    Date d = format.parse(lines.get(4));
                    Ticket t = new Ticket(lines.get(1), p, lines.get(3), d);//lines.get(0) would be the ID.  We're just assigning new ID numbers, because otherwise there might end up being two with the same number.
                    open.add(t);//save the ticket

                    line = bufReader.readLine();
                }

            }catch (IOException io) {
                System.out.println("There was an error while reading " + filename + ".");
            } catch (ParseException e) {
                System.out.println("date format in file incorrect");
            }

            bufReader.close();   //This closes the inner FileReader too
        }catch (FileNotFoundException fe) {
            //it doesn't matter if it doesn't exist.
        }
        return open;
    }
    private static void writeR(String filename, ArrayList<resolvedTicket>resolvedTickets) throws IOException {
        FileWriter writer = new FileWriter(filename);
        BufferedWriter bufWriter = new BufferedWriter(writer);

        try {
            for(resolvedTicket rt:resolvedTickets) {
                bufWriter.write(rt.toString());
            }
            bufWriter.close();
        }catch(IOException ioe){
            System.out.println("There was an error writing to the file "+filename+".");
        }

    }

    private static void writeQ(String filename, LinkedList<Ticket>TicketQ) throws IOException {

        FileWriter writer = new FileWriter(filename);
        BufferedWriter bufWriter = new BufferedWriter(writer);

        try {
            for(Ticket t:TicketQ) {
                bufWriter.write(t.toString());
            }
            bufWriter.close();
        }catch(IOException ioe){
            System.out.println("There was an error writing to the file "+filename+".");
        }

    }
}

