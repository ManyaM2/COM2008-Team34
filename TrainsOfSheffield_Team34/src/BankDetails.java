public class BankDetails {

    private String cardName;
    private String cardNumber;
    private String expiryDate;
    private String holderName;
    private int securityCode;

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

    public int getSecurityCode() {
        return securityCode;
    }

    public BankDetails (String cName, String cNumber, String date, String hName, int code) {
        this.cardName = cName;
        this.cardNumber = cNumber;
        this.expiryDate = date;
        this.holderName = hName;
        this.securityCode = code;
    }
}
