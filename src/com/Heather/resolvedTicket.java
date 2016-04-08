package com.Heather;
import java.util.ArrayList;
import java.util.Date;

public class resolvedTicket extends Ticket {
    private Date dateResolved;
    private String resolution;
    private static ArrayList<resolvedTicket> resolvedTickets=new ArrayList<>();

    public resolvedTicket(int id, String desc, int p, String rep, Date date, Date rdate, String resolve) {
        super(desc, p, rep, date);
        this.ticketID=id;
        this.dateResolved=rdate;
        this.resolution=resolve;
    }

    public static void setResolvedTickets(resolvedTicket r){
        resolvedTickets.add(r);
    }
    public static ArrayList<resolvedTicket> getResolvedTickets(){
        return resolvedTickets;
    }
    @Override
    public String toString(){
        return"ID "+(ticketID)+"\nDescription: "+this.getDescription()+"\nAssigned priority "+this.getPriority()+
                " \nReported by "+this.getReporter()+" on "+this.getDateReported()+" \nResolved on "+this.dateResolved+
                " \nDescription of resolution: "+this.resolution+"\n\n";
    }
}
