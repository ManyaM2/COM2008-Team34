public class BankDetails {

    private String cardName;
    private Integer cardNumber;
    private String expiryDate;
    private String holderName;
    private String securityCode;

    public String getCardName() {
        return cardName;
    }

    public Integer getCardNumber() {
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

    public BankDetails (String cName, Integer cNumber, String date, String hName, String code) {
        this.cardName = cName;
        this.cardNumber = cNumber;
        this.expiryDate = date;
        this.holderName = hName;
        this.securityCode = code;
    }
}
