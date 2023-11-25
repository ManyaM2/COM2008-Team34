import java.sql.Connection;

public class BankDetails {

    private String cardName;
    private String cardNumber;
    private String expiryDate;
    private String holderName;
    private String securityCode;

    public String getCardName() {
        return cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void addToDatabase(Connection connection) {
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        try {
            dbUpdateOps.addBankingDetails(connection, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BankDetails (String cName, String cNumber, String date, String hName, String code) {
        this.cardName = cName;
        this.cardNumber = cNumber;
        this.expiryDate = date;
        this.holderName = hName;
        this.securityCode = code;
    }
}
