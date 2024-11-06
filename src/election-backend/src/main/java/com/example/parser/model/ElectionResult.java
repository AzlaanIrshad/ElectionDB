package com.example.parser.model;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "EML", namespace = "urn:oasis:names:tc:evs:schema:eml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectionResult {

    @XmlElement(name = "TransactionId", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private int transactionId;

    @XmlElement(name = "ManagingAuthority", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private ManagingAuthority managingAuthority;

    @XmlElement(name = "Count", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private Count count;

    public int getTransactionId() {
        System.out.println("TransactionId: " + transactionId);
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public ManagingAuthority getManagingAuthority() {
        System.out.println("ManagingAuthority: " + managingAuthority);
        return managingAuthority;
    }

    public void setManagingAuthority(ManagingAuthority managingAuthority) {
        this.managingAuthority = managingAuthority;
    }

    public Count getCount() {
        System.out.println("Count: " + count);
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class ManagingAuthority {
    @XmlElement(name = "AuthorityIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private AuthorityIdentifier authorityIdentifier;

    public AuthorityIdentifier getAuthorityIdentifier() {
        System.out.println("AuthorityIdentifier: " + authorityIdentifier);
        return authorityIdentifier;
    }

    public void setAuthorityIdentifier(AuthorityIdentifier authorityIdentifier) {
        this.authorityIdentifier = authorityIdentifier;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class AuthorityIdentifier {
    @XmlAttribute(name = "Id")
    private String id;

    @XmlValue
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class Count {
    @XmlElement(name = "Election", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private Election election;

    public Election getElection() {
        System.out.println("Election: " + election);
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class Election {
    @XmlElement(name = "ElectionIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private ElectionIdentifier electionIdentifier;

    @XmlElement(name = "Contests", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private Contests contests;

    public ElectionIdentifier getElectionIdentifier() {
        return electionIdentifier;
    }

    public void setElectionIdentifier(ElectionIdentifier electionIdentifier) {
        this.electionIdentifier = electionIdentifier;
    }

    public Contests getContests() {
        return contests;
    }

    public void setContests(Contests contests) {
        this.contests = contests;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class ElectionIdentifier {
    @XmlAttribute(name = "Id")
    private String id;

    @XmlElement(name = "ElectionName", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String electionName;

    @XmlElement(name = "ElectionCategory", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String electionCategory;

    @XmlElement(name = "ElectionSubcategory", namespace = "http://www.kiesraad.nl/extensions")
    private String electionSubcategory;

    @XmlElement(name = "ElectionDate", namespace = "http://www.kiesraad.nl/extensions")
    private String electionDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getElectionName() {
        return electionName;
    }

    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

    public String getElectionCategory() {
        return electionCategory;
    }

    public void setElectionCategory(String electionCategory) {
        this.electionCategory = electionCategory;
    }

    public String getElectionSubcategory() {
        return electionSubcategory;
    }

    public void setElectionSubcategory(String electionSubcategory) {
        this.electionSubcategory = electionSubcategory;
    }

    public String getElectionDate() {
        return electionDate;
    }

    public void setElectionDate(String electionDate) {
        this.electionDate = electionDate;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class Contests {
    @XmlElement(name = "Contest", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private List<Contest> contests;

    public List<Contest> getContests() {
        return contests;
    }

    public void setContests(List<Contest> contests) {
        this.contests = contests;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class Contest {
    @XmlElement(name = "ContestIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private ContestIdentifier contestIdentifier;

    @XmlElement(name = "TotalVotes", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private TotalVotes totalVotes;

    public ContestIdentifier getContestIdentifier() {
        return contestIdentifier;
    }

    public void setContestIdentifier(ContestIdentifier contestIdentifier) {
        this.contestIdentifier = contestIdentifier;
    }

    public TotalVotes getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(TotalVotes totalVotes) {
        this.totalVotes = totalVotes;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class ContestIdentifier {
    @XmlAttribute(name = "Id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class TotalVotes {
    @XmlElement(name = "Selection", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private List<Selection> selections;

    public List<Selection> getSelections() {
        return selections;
    }

    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class Selection {
    @XmlElement(name = "AffiliationIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private AffiliationIdentifier affiliationIdentifier;

    @XmlElement(name = "ValidVotes", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private int validVotes;

    @XmlElement(name = "Candidate", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private Candidate candidate;

    public AffiliationIdentifier getAffiliationIdentifier() {
        return affiliationIdentifier;
    }

    public void setAffiliationIdentifier(AffiliationIdentifier affiliationIdentifier) {
        this.affiliationIdentifier = affiliationIdentifier;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class AffiliationIdentifier {
    @XmlAttribute(name = "Id")
    private String id;

    @XmlElement(name = "RegisteredName", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String registeredName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisteredName() {
        return registeredName;
    }

    public void setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class Candidate {
    @XmlElement(name = "CandidateIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private CandidateIdentifier candidateIdentifier;

    public CandidateIdentifier getCandidateIdentifier() {
        return candidateIdentifier;
    }

    public void setCandidateIdentifier(CandidateIdentifier candidateIdentifier) {
        this.candidateIdentifier = candidateIdentifier;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class CandidateIdentifier {
    @XmlAttribute(name = "ShortCode")
    private String shortCode;

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}
