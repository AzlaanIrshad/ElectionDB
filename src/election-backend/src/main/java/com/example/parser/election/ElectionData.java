package com.example.parser.election;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "EML", namespace = "urn:oasis:names:tc:evs:schema:eml")
public class ElectionData {

    private String transactionId;
    private String electionName;
    private String electionDate;
    private int totalVotes;
    private List<Contest> contests = new ArrayList<>();

    public ElectionData() {}

    @XmlElement(name = "TransactionId")
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @XmlElement(name = "ElectionName")
    public String getElectionName() {
        return electionName;
    }

    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

    @XmlElement(name = "ElectionDate")
    public String getElectionDate() {
        return electionDate;
    }

    public void setElectionDate(String electionDate) {
        this.electionDate = electionDate;
    }

    @XmlElement(name = "TotalVotes")
    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    @XmlElement(name = "Contests")
    public List<Contest> getContests() {
        return contests;
    }

    public void setContests(List<Contest> contests) {
        this.contests = contests;
    }
}
